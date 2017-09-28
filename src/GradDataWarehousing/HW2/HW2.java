package GradDataWarehousing.HW2;

import GradDataWarehousing.HWResources.SkuPrice;
import Utils.Timers.AbstractTimer;
import Utils.Timers.SYSTimer;

import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static Utils.ConsolePrinting.*;
import static Utils.StringUtils.StringUtils.*;

public class HW2 {

    static final String DELIM = " \\| ";
    static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    static final ConcurrentMap<SkuPrice, AtomicInteger> skuPriceMapCount = new ConcurrentHashMap<>();
    static final String OUTPUT_PATH = "." + File.separatorChar + "output2.csv"; //results go here
    static final String INPUT_PATH = "." + File.separatorChar + "output1.txt";   // start from this file
    static final int NUM_WEEKS = 2;

    static String start_date_string;
    static BufferedReader reader;
    static BufferedWriter writer;
    static LocalDate start;
    static LocalDate end;
    static Date startDate;

    public static void updateSkuMap(SkuPrice sku) {
        skuPriceMapCount.putIfAbsent(sku, new AtomicInteger(0));
        skuPriceMapCount.get(sku).incrementAndGet();
    }

    public static void write(Object... args) {

        /** Uncomment to print each entry */
//         println(args);

        String delim = "";
        try {
            for (Object o : args) {
                writer.write(delim);
                writer.write(o.toString());
                delim = " , ";
            }
            writer.write("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        println(FGPURPLE, "working...");
        int paddingSize = 30;
        char fill = '*';
        printlnDelim("\n",
                padJustify(paddingSize, fill,   "INPUT_PATH ",    " " + INPUT_PATH),
                padJustify(paddingSize, fill,   "OUTPUT_PATH ",   " " + OUTPUT_PATH),
                padJustify(paddingSize, fill,   "NUM_WEEKS ",     " " + NUM_WEEKS)
        );
        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();

        try {
            writer = new BufferedWriter(new FileWriter(OUTPUT_PATH));
            reader = new BufferedReader(new FileReader(INPUT_PATH));
            String line = reader.readLine();
            start_date_string = line.split(DELIM)[0];
            startDate = formatter.parse(start_date_string);
            start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            end = start.plusDays(NUM_WEEKS * 7);

            println(padJustify(paddingSize, fill, "start date ", " " + start));
            println(padJustify(paddingSize, fill, "end date ", " " + end));

            String dateString;
            for (LocalDate date = start; date.isBefore(end); ) {
                line = reader.readLine();
                String[] fields = line.split(DELIM);
                dateString = fields[0];
                int sku = Integer.parseInt(fields[3]);
                double price = Double.parseDouble(fields[4]);
                updateSkuMap(new SkuPrice(sku, price));
                date = formatter.parse(dateString).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            // sort map of <sku-price, avg> by avg count per day;
            Map<SkuPrice, Integer> sortedSkuCounts = new TreeMap(new SkuPrice.SkuMapComparator(skuPriceMapCount));
            for (Map.Entry<SkuPrice, AtomicInteger> entry : skuPriceMapCount.entrySet()) {
                SkuPrice skuPrice = entry.getKey();
                int avgCountPerDay = entry.getValue().intValue() / (NUM_WEEKS * 7);
                sortedSkuCounts.put(skuPrice, avgCountPerDay);
            }

            Map<Integer, String> skuNameMap = ProductParserForNames.generateSkuNameMap();

            try {

                println(FGYELLOW);
                println("Rank  ",
                        "   Sku     ",
                        padCenter(70, ' ', "Name"),
                        "Price",
                        " Avg Per Day");
                write("sku", "name", "price", "avg per day");

                int rank = 1;
                for (Map.Entry<SkuPrice, Integer> entry : sortedSkuCounts.entrySet()) {
//                    if(rank > 10 ) { break; }     // UNCOMMENT TO STOP AFTER TOP 10
                    Integer sku = entry.getKey().getSku();
                    Double price = entry.getKey().getPrice();
                    String name = skuNameMap.get(sku);
                    String avgPerDay = NumberFormat.getInstance().format(entry.getValue().intValue());
                    println(padToRight(6, ' ', rank + " |"),
                            padCenter(8, ' ', sku + " |"),
                            padCenter(70, ' ', name),
                            "| " + padCenter(5, ' ', price) + " |",
                            padToRight(3, ' ', avgPerDay));
                    rank++;
                    write(sku, name, price, avgPerDay);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                writer.flush();
                writer.close();
            }

            timer.stop();
            println(FGGREEN, "\nDONE", timer, "\nsee file: ", OUTPUT_PATH);

            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
