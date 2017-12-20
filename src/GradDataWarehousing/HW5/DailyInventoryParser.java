package GradDataWarehousing.HW5;

import GradDataWarehousing.HWResources.SkuPrice;
import Utils.Timers.AbstractTimer;
import Utils.Timers.SYSTimer;
import Utils.Timers.TimeUnit;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static Utils.ConsolePrinting.FG_CYAN;
import static Utils.ConsolePrinting.FG_GREEN;
import static Utils.ConsolePrinting.println;

public class DailyInventoryParser {
    static final String DELIM = "\\|";
    static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    static final String OUTPUT_PATH = "." + File.separatorChar + "Output6" + File.separatorChar + "outputInvDaily.csv";
    static final String INPUT_PATH = "." + File.separatorChar + "output4.csv";
    static BufferedWriter writer;
    static BufferedReader reader;
    static String start_date_string;
    static LocalDate start;
    static LocalDate end;
    static Date startDate;
    static ConcurrentMap<SkuPrice, AtomicInteger> skuPriceMapQuantLeft = new ConcurrentHashMap<>();
    static ConcurrentMap<SkuPrice, AtomicInteger> skuPriceMapYTDCases = new ConcurrentHashMap<>();

    static String compositeKey;
    static String dateKey;
    static String productKey;
    static String storeKey;
    static int numAvailable;
    static double costToStoreUnit;
    static double costToStoreCase;
    static int numCasesPurchasedYTD;

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

    public static void updateSkuMap(Map<SkuPrice, AtomicInteger> map, SkuPrice sku, Integer quantLeft) {
        map.putIfAbsent(sku, new AtomicInteger(0));
        map.get(sku).set(quantLeft);
    }

    public static void main(String[] args) {

        AbstractTimer timer = new SYSTimer(TimeUnit.SECONDS);
        timer.start();
        println(FG_CYAN, "STARTED");

        try {
            FileInputStream fs = new FileInputStream(INPUT_PATH);
            reader = new BufferedReader(new InputStreamReader(fs));

            File file = new File(OUTPUT_PATH);
            writer = new BufferedWriter(new FileWriter(file));
            write("CompositeKey", "DateKey", "ProductKey", "StoreKey", "NumAvail", "CostToStore(UnitLv)", "CostToStore(CaseLv)", "NumCasesPurchYTD");
            int count = 0;
            String line = reader.readLine(); // throw away
            line = reader.readLine();
            while(line != null) {
                start_date_string = line.split(DELIM)[0];
                startDate = formatter.parse(start_date_string);
                start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                end = start.plusDays(1);

                dateKey = start.toString();
                skuPriceMapQuantLeft = new ConcurrentHashMap<>();

                try {
                    int sku = 0;
                    double price = 0;
                    int quantLeft = 0;
                    int ytdCases = 0;

                    String dateString;
                    for (LocalDate date = start; date.isBefore(end); ) {
                        String[] fields = line.split(DELIM);
                        dateString = fields[0];
                        sku = Integer.parseInt(fields[3]);
                        price = Double.parseDouble(fields[5]);
                        quantLeft = Integer.parseInt(fields[6]);
                        ytdCases = Integer.parseInt(fields[7]);
                        SkuPrice skuPrice = new SkuPrice(sku, price);
                        updateSkuMap(skuPriceMapQuantLeft, skuPrice, quantLeft);
                        updateSkuMap(skuPriceMapYTDCases, skuPrice, ytdCases);

                        line = reader.readLine();
                        date = formatter.parse(dateString).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    }
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }

                for(Map.Entry<SkuPrice, AtomicInteger> pair : skuPriceMapQuantLeft.entrySet()) {
                    compositeKey = "1" + String.format("%03d", start.getDayOfYear());
                    dateKey =  start.toString();
                    productKey = "" + pair.getKey().getSku();
                    storeKey = "111";
                    numAvailable = pair.getValue().intValue();
                    costToStoreUnit = pair.getKey().getPrice();
                    costToStoreCase = roundTwoDecimal(12 * costToStoreUnit);
                    numCasesPurchasedYTD = skuPriceMapYTDCases.get(pair.getKey()).intValue();
                    write(compositeKey, dateKey, productKey, storeKey, numAvailable, costToStoreUnit, costToStoreCase, numCasesPurchasedYTD);
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
