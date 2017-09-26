package GradDataWarehousing.HW3;

import GradDataWarehousing.HWResources.SkuPrice;
import Utils.Timers.AbstractTimer;
import Utils.Timers.SYSTimer;

import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static Utils.ConsolePrinting.*;
import static Utils.ConsolePrinting.fgGreen;
import static Utils.ConsolePrinting.println;
import static Utils.StringUtils.StringUtils.padJustify;

public class HW3PreProcessor {

    static final String DELIM = ", ";
    static final ConcurrentMap<SkuPrice, AtomicInteger> skuPriceMapCount = new ConcurrentHashMap<>();
    static final String OUTPUT_PATH = "." + File.separatorChar + "output3.txt"; //results go here
    static final String INPUT_PATH = "." + File.separatorChar + "output2.csv";   // start from this file
    static final int NUM_WEEKS = 2;

    static BufferedReader reader;
    static BufferedWriter writer;
    static Map<Integer, Integer> inventoryMap = new HashMap<>();

    public static class SkuAvgComparator implements Comparator {
        Map map;

        public SkuAvgComparator(Map map) {
            this.map = map;
        }

        public int compare(Object skuA, Object skuB) {
            Integer avgA = (Integer) map.get(skuA);
            Integer avgB = (Integer) map.get(skuB);
            return (avgB < avgA) ? -1 : 1;
        }
    }

    public static void write(Object... args) {

        /** Uncomment to print each entry */
//         println(args);

        String delim = "";
        try {
            for (Object o : args) {
                writer.write(delim);
                writer.write(o.toString());
                delim = ", ";
            }
            writer.write("\n");
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
                padJustify(paddingSize, fill,   "OUTPUT_PATH ",   " " + OUTPUT_PATH)
                );
        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();

        try {
            FileInputStream fs= new FileInputStream(INPUT_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line = br.readLine();
            while((line = br.readLine()) != null) {
                String[] fields = line.split(DELIM);
                Integer sku = Integer.parseInt(fields[0]);
                Integer avg = Integer.parseInt(fields[3]);
                inventoryMap.put(sku, avg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<Integer, Integer> sortedSkuCounts = new TreeMap(new SkuAvgComparator(inventoryMap));
        sortedSkuCounts.putAll(inventoryMap);

        println();
        println("All Products List successfully constructed from...");
        println(fgRed, sortedSkuCounts);
        timer.stop();
        println(fgGreen, "\nDONE", timer, "\nsee file: ", OUTPUT_PATH);
        System.exit(0);
    }
}
