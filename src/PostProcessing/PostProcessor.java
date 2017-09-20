package PostProcessing;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

public class PostProcessor {

    private static String pathname = "." + File.separatorChar + "output.txt";
    private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private static Date thisDate;
    private static int custNum;
    private static int itemNum;
    private static String sku;
    private static double price;

    private static Date prevDate;
    private static int prevCustNum = -1;
    private static int prevItemNum = -1;
    private static String prevSku = "";
    private static double prevPrice = 0;

    private static int lines = 0;
    private static double sales = 0;
    private static int number_of_customers = 0;

    private static HashMap<String, Integer> productCounts = new HashMap<String, Integer>();

//    public static boolean forceDone = false;

    public static void main(String[] args) throws Exception {
        String thisLine = null;
        try {
            prevDate = formatter.parse("1900-01-01");
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        try {

            FileInputStream fs= new FileInputStream(pathname);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));

//            thisLine = br.readLine();

//            while (!forceDone && (thisLine = br.readLine()) != null) {
            while ((thisLine = br.readLine()) != null) {
//                System.out.println(thisLine);
                lines++;
                String[] fields = thisLine.split("\\|");
//                System.out.println(Arrays.toString(fields));

                try {
                    thisDate = formatter.parse(fields[0].trim());
                    custNum = Integer.parseInt (fields[1].trim());
                    itemNum = Integer.parseInt(fields[2].trim());
                    sku = fields[3].trim();
                    price = Double.parseDouble(fields[4].trim());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                productCounts.put(sku, productCounts.getOrDefault(sku, 0) + 1);
                sales = sales + price;

                if ( !thisDate.equals(prevDate) || !(custNum == prevCustNum) ) {
                    number_of_customers++;
                }

                prevDate = thisDate;
                prevCustNum = custNum;
//                if (lines >= 100) forceDone = true;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Number of Customers: " + number_of_customers);

        NumberFormat asCurrency = NumberFormat.getCurrencyInstance();
        System.out.println("Total Sales: " + asCurrency.format(sales) );

        System.out.println("Total items bought: " + lines);

        ValueComparator bvc =  new ValueComparator(productCounts);
        TreeMap<String,Integer> sorted_map = new TreeMap<String,Integer>(bvc);
        sorted_map.putAll(productCounts);
        int count = 1; int max = 10;
        System.out.println("Top 10 selling items with counts...");
        for (Map.Entry<String, Integer> entry : sorted_map.entrySet()) {
            if (count > max) break;
            System.out.println(count + ". SKU: " + entry.getKey() + ", Count: " + entry.getValue());
            count++;
        }
    }
}

class ValueComparator implements Comparator<String> {

    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}