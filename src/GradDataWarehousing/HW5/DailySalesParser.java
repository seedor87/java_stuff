package GradDataWarehousing.HW5;

import GradDataWarehousing.HWResources.SkuPrice;
import Utils.StopWatches.AbstractStopwatch;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static Utils.Console.Special.*;
import static Utils.Console.Printing.println;

public class DailySalesParser {

    static final String DELIM = "\\|";
    static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    static final String OUTPUT_PATH = "." + File.separatorChar + "Output6" + File.separatorChar + "outputSalesDaily.csv";
    static final String INPUT_PATH = "." + File.separatorChar + "output4.csv";
    static BufferedWriter writer;
    static BufferedReader reader;
    static String start_date_string;
    static LocalDate start;
    static LocalDate end;
    static Date startDate;
    static ConcurrentMap<SkuPrice, AtomicInteger> skuPriceMapCount = new ConcurrentHashMap<>();


    static String compositeKey;
    static String dateKey;
    static String productKey;
    static String storeKey;
    static int numSoldToday;
    static double costOfItemsSold;
    static double salesTotal;
    static double grossProfit;

    /**
     * Method to mathematically round a double to two (2) decimal places
     */
    public static double roundTwoDecimal(double num) {
        num = Math.round(num * 100);
        num = num / 100;
        return num;
    }

    /**
     * Method to write particular line to txt file now
     */
    public static void write(Object... args) throws IOException {

        /** Uncomment to print each entry */
//         println(args);

        String delim = "";
        for (Object o : args) {
            writer.write(delim);
            writer.write(o.toString());
            delim = "|";
        }
        writer.write("\n");
    }

    public static void updateSkuMap(SkuPrice sku) {
        skuPriceMapCount.putIfAbsent(sku, new AtomicInteger(0));
        skuPriceMapCount.get(sku).incrementAndGet();
    }

    public static void main(String[] args) {

        AbstractStopwatch timer = new SYSStopwatch(TimeUnit.SECONDS);
        timer.start();
        println(FG_CYAN, "STARTED");

        try {
            FileInputStream fs = new FileInputStream(INPUT_PATH);
            reader = new BufferedReader(new InputStreamReader(fs));

            File file = new File(OUTPUT_PATH);
            writer = new BufferedWriter(new FileWriter(file));
            write("CompositeKey", "DateKey", "ProductKey", "StoreKey", "NumSoldToday", "CostOfItemSold", "SalesTotal", "GrossProfit");

            String line = reader.readLine(); // throw away
            line = reader.readLine();
            while(line != null) {
                start_date_string = line.split(DELIM)[0];
                startDate = formatter.parse(start_date_string);
                start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                end = start.plusDays(1);

                dateKey = start.toString();
                skuPriceMapCount = new ConcurrentHashMap<>();

                try {
                    int sku = 0;
                    double price = 0;
                    String dateString;
                    for (LocalDate date = start; date.isBefore(end); ) {
                        String[] fields = line.split(DELIM);
                        dateString = fields[0];
                        sku = Integer.parseInt(fields[3]);
                        price = Double.parseDouble(fields[5]);
                        updateSkuMap(new SkuPrice(sku, price));

                        line = reader.readLine();
                        date = formatter.parse(dateString).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }

                for(Map.Entry<SkuPrice, AtomicInteger> pair : skuPriceMapCount.entrySet()) {
                    compositeKey = "1" + String.format("%03d", start.getDayOfYear());
                    dateKey =  start.toString();
                    productKey = "" + pair.getKey().getSku();
                    storeKey = "111";
                    numSoldToday = pair.getValue().intValue();
                    costOfItemsSold = roundTwoDecimal(numSoldToday * pair.getKey().getPrice());
                    salesTotal = roundTwoDecimal(numSoldToday * pair.getKey().getPrice());
                    grossProfit = 0;
                    write(compositeKey, dateKey, productKey, storeKey, numSoldToday, costOfItemsSold, salesTotal, salesTotal, grossProfit);
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        timer.stop();
        println(FG_GREEN, "DONE", timer);
    }
}
