package GradDataWarehousing.HW2;

import GradDataWarehousing.HWResources.SkuPrice;
import Utils.Timers.AbstractTimer;
import Utils.Timers.SYSTimer;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static Utils.ConsolePrinting.*;

public class HW2 {

    static String outputPath = "." + File.separatorChar + "output2.txt"; //results go here
    static String inputPath = "." + File.separatorChar + "output.txt";   // start from this file
    static String allProductsFilePath = "." +
            File.separatorChar + "src" +
            File.separatorChar + "GradDataWarehousing" +
            File.separatorChar + "HW1" +
            File.separatorChar + "myProducts";  // pre-processed list of skus and prices

    static final String DELIM = " \\| ";
    static final int NUM_WEEKS = 2;
    static final ConcurrentMap<SkuPrice, AtomicInteger> skuPriceMapCount = new ConcurrentHashMap<>();

    static String start_date_string;
    static String end_date_string;
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    static BufferedReader reader;
    static BufferedWriter writer;
    static LocalDate start;
    static LocalDate end;
    static Date startDate;

    static class SkuMapComparator implements Comparator {
        Map map;

        public SkuMapComparator(Map map) {
            this.map = map;
        }

        public int compare(Object pairA, Object pairB) {
            AtomicInteger countA = (AtomicInteger) map.get((SkuPrice) pairA);
            AtomicInteger countB = (AtomicInteger) map.get((SkuPrice) pairB);
            return (countB.intValue() < countA.intValue()) ? -1 : 1;
        }
    }

    public static void updateSkuMap(SkuPrice sku) {
        skuPriceMapCount.putIfAbsent(sku, new AtomicInteger(0));
        skuPriceMapCount.get(sku).incrementAndGet();
    }

    public static void main(String[] args) {

        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();

        try {
            writer = new BufferedWriter(new FileWriter(outputPath));
            reader = new BufferedReader(new FileReader(inputPath));
            String line = reader.readLine();
            start_date_string = line.split("\\|")[0];
            startDate = formatter.parse(start_date_string);
            start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            end = start.plusDays(NUM_WEEKS * 7);

            for (LocalDate date = start; date.isBefore(end); ) {
                line = reader.readLine();
                String[] fields = line.split(DELIM);
                end_date_string = fields[0];
                int sku = Integer.parseInt(fields[3]);
                double price = Double.parseDouble(fields[4]);
                updateSkuMap(new SkuPrice(sku, price));
                date = formatter.parse(end_date_string).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }

            // sort map of <sku-price, avg> by avg count per day;
            Map<SkuPrice, AtomicInteger> sortedSkuCounts = new TreeMap(new SkuMapComparator(skuPriceMapCount));
            for(Map.Entry<SkuPrice, AtomicInteger> entry : skuPriceMapCount.entrySet()) {
                SkuPrice skuPrice = entry.getKey();
                AtomicInteger avgCountPerDay = new AtomicInteger(entry.getValue().intValue() / (NUM_WEEKS * 7));
                sortedSkuCounts.put(skuPrice, avgCountPerDay);
            }

            timer.stop();
            println(fgGreen, "DONE", timer);

            println(sortedSkuCounts);

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
