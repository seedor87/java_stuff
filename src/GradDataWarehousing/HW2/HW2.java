package GradDataWarehousing.HW2;

import GradDataWarehousing.HWResources.SkuMapComparator;
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

    static final String INPUT_PATH = "." + File.separatorChar + "output1.txt";   // start from this file
    static final String OUTPUT_PATH = "." + File.separatorChar + "output2.csv"; //results go here

    static final String MILK_PATH = "." + File.separatorChar + "milk.csv"; //only milk
    static final String BABY_FOOD_PATH = "." + File.separatorChar + "baby.food.csv"; //only baby food
    static final String BREAD_PATH = "." + File.separatorChar + "bread.csv"; //only bread
    static final String PEANUT_BUTTER_PATH = "." + File.separatorChar + "peanut.butter.csv"; //only peanut butter
    static final String OTHER_PATH = "." + File.separatorChar + "other.csv"; //everything else

    static final int NUM_WEEKS = 2;
    static String start_date_string;

    static BufferedReader reader;
    static BufferedWriter writer;

    static BufferedWriter milkWriter;
    static BufferedWriter babyFoodWriter;
    static BufferedWriter breadWriter;
    static BufferedWriter peanutButterWriter;
    static BufferedWriter otherWriter;

    static int outputRecCounter = 0;
    static int milkRecCounter = 0;
    static int babyFoodRecCounter = 0;
    static int breadRecCounter = 0;
    static int peanutButterRecCounter = 0;
    static int otherRecCounter = 0;

    static LocalDate start;
    static LocalDate end;
    static Date startDate;

    public static void updateSkuMap(SkuPrice sku) {
        skuPriceMapCount.putIfAbsent(sku, new AtomicInteger(0));
        skuPriceMapCount.get(sku).incrementAndGet();
    }

    public static void write(BufferedWriter dest, Object... args) {

        /** Uncomment to print each entry */
//         println(args);

        String delim = "";
        try {
            for (Object o : args) {
                dest.write(delim);
                dest.write(o.toString());
                delim = " , ";
            }
            dest.write("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        println(fgPurple, "working...");
        int paddingSize = 30;
        char fill = '*';
        printlnDelim("\n",
                padJustify(paddingSize, fill,   "INPUT_PATH ",    " " + INPUT_PATH),
                padJustify(paddingSize, fill,   "OUTPUT_PATH ",   " " + OUTPUT_PATH),
                padJustify(paddingSize, fill,   "MILK_PATH ",   " " + MILK_PATH),
                padJustify(paddingSize, fill,   "BABY_FOOD_PATH ",   " " + BABY_FOOD_PATH),
                padJustify(paddingSize, fill,   "BREAD_PATH ",   " " + BREAD_PATH),
                padJustify(paddingSize, fill,   "PEANUT_BUTTER_PATH ",   " " + PEANUT_BUTTER_PATH),
                padJustify(paddingSize, fill,   "OTHER_PATH ",   " " + OTHER_PATH),
                padJustify(paddingSize, fill,   "NUM_WEEKS ",     " " + NUM_WEEKS)
        );
        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();

        try {
            reader = new BufferedReader(new FileReader(INPUT_PATH));
            writer = new BufferedWriter(new FileWriter(OUTPUT_PATH));

            milkWriter = new BufferedWriter(new FileWriter(MILK_PATH));
            babyFoodWriter = new BufferedWriter(new FileWriter(BABY_FOOD_PATH));
            breadWriter = new BufferedWriter(new FileWriter(BREAD_PATH));
            peanutButterWriter = new BufferedWriter(new FileWriter(PEANUT_BUTTER_PATH));
            otherWriter = new BufferedWriter(new FileWriter(OTHER_PATH));

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
            Map<SkuPrice, Integer> sortedSkuCounts = new TreeMap(new SkuMapComparator(skuPriceMapCount));
            for (Map.Entry<SkuPrice, AtomicInteger> entry : skuPriceMapCount.entrySet()) {
                SkuPrice skuPrice = entry.getKey();
                int avgCountPerDay = entry.getValue().intValue() / (NUM_WEEKS * 7);
                sortedSkuCounts.put(skuPrice, avgCountPerDay);
            }

            Map<Integer, String> skuNameMap = ProductParserForNames.generateSkuNameMap();
            Map<Integer, String> skuItemTypeMap = ProductParserForItemTypes.generateSkuItemTypeMap();

            try {

                println(fgYellow);
                println("Rank  ",
                        "   Sku     ",
                        padCenter(70, ' ', "Name"),
                        padCenter(70, ' ', "Item Type"),
                        "Price",
                        " Avg Per Day");
                write(writer,"sku", "name", "item type", "price", "avg per day");
                outputRecCounter++;
                write(milkWriter,"sku", "name", "item type", "price", "avg per day");
                milkRecCounter++;
                write(babyFoodWriter,"sku", "name", "item type", "price", "avg per day");
                babyFoodRecCounter++;
                write(breadWriter,"sku", "name", "item type", "price", "avg per day");
                breadRecCounter++;
                write(peanutButterWriter,"sku", "name", "item type", "price", "avg per day");
                peanutButterRecCounter++;
                write(otherWriter,"sku", "name", "item type", "price", "avg per day");
                otherRecCounter++;

                int rank = 1;
                for (Map.Entry<SkuPrice, Integer> entry : sortedSkuCounts.entrySet()) {
//                    if(rank > 10 ) { break; }     // UNCOMMENT TO STOP AFTER TOP 10
                    Integer sku = entry.getKey().getSku();
                    Double price = entry.getKey().getPrice();
                    String name = skuNameMap.get(sku);
                    String itemType = skuItemTypeMap.get(sku);
                    String avgPerDay = NumberFormat.getInstance().format(entry.getValue().intValue());
                    println(padToRight(6, ' ', rank + " |"),
                            padCenter(8, ' ', sku + " |"),
                            padCenter(70, ' ', name),
                            padCenter(70, ' ', itemType),
                            "| " + padCenter(5, ' ', price) + " |",
                            padToRight(3, ' ', avgPerDay));
                    rank++;
                    write(writer, sku, name, itemType, price, avgPerDay);
                    outputRecCounter++;

                    switch (itemType.toLowerCase())
                    {
                        case "milk":
                            write(milkWriter, sku, name, itemType, price, avgPerDay);
                            milkRecCounter++;
                            break;
                        case "baby food":
                            write(babyFoodWriter, sku, name, itemType, price, avgPerDay);
                            babyFoodRecCounter++;
                            break;
                        case "bread":
                            write(breadWriter, sku, name, itemType, price, avgPerDay);
                            breadRecCounter++;
                            break;
                        case "peanut butter":
                            write(peanutButterWriter, sku, name, itemType, price, avgPerDay);
                            peanutButterRecCounter++;
                            break;
                        default:
                            write(otherWriter, sku, name, itemType, price, avgPerDay);
                            otherRecCounter++;
                            break;
                    }

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                writer.flush();
                milkWriter.flush();
                babyFoodWriter.flush();
                breadWriter.flush();
                peanutButterWriter.flush();
                otherWriter.flush();

                writer.close();
                milkWriter.close();
                babyFoodWriter.close();
                breadWriter.close();
                peanutButterWriter.close();
                otherWriter.close();
            }

            timer.stop();
            println(fgGreen, "\nDONE", timer, "\nsee file: ", OUTPUT_PATH);
            println(fgGreen, outputRecCounter, " output records");
            println(fgGreen, milkRecCounter, " milk records");
            println(fgGreen, babyFoodRecCounter, " baby food records");
            println(fgGreen, breadRecCounter, " bread records");
            println(fgGreen, peanutButterRecCounter, " peanut butter records");
            println(fgGreen, otherRecCounter, " other records");

            println(fgGreen,
                    milkRecCounter + babyFoodRecCounter + breadRecCounter + peanutButterRecCounter + otherRecCounter,
                    " records in breakout files");

            System.exit(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
