package GradDataWarehousing.HW3;

import GradDataWarehousing.HWResources.HW1Arrays;
import GradDataWarehousing.HWResources.InventoryBuilder;
import GradDataWarehousing.HWResources.SkuPrice;
import Utils.Timing.AbstractStopwatch;
import Utils.Timing.SYSStopwatch;
import Utils.Timing.TimeUnit;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

import static GradDataWarehousing.HWResources.Utils.isMilkSku;
import static Utils.Console.Printing.*;
import static Utils.Console.Printing.println;
import static Utils.StringUtils.*;
import static Utils.Console.Special.*;


public class HW3 {

    static final String OUTPUT_PATH = "." + File.separatorChar + "output3.csv"; //results go here
    static final String ALL_PRODUCTS_PATH = "." +
            File.separatorChar + "src" +
            File.separatorChar + "GradDataWarehousing" +
            File.separatorChar + "HW1" +
            File.separatorChar + "Products";  // pre-processed list of skus and prices

    // student specific params
    static final String START_DATE_STRING = "2017-01-01";
    static final String END_DATE_STRING = "2018-01-01";
    static final int CUST_LOW = 1140;
    static final int CUST_HI = 1180;
    static final double PRICE_MULT = 1.1;
    static final int MAX_ITEMS = 100;
    static final int WEEKEND_INCREASE = 50;

    // map for pairing <sku and price, count>
    static final ConcurrentMap<SkuPrice, AtomicInteger> SKU_PRICE_MAP_COUNT = new ConcurrentHashMap<>();
    // map to keep constant track of inventory
    static final ConcurrentMap<Integer, Integer> MY_INVENTORY = new ConcurrentHashMap<>();
    // map to store sku-avg pairs for inventory readjustment and ordering
    static final ConcurrentHashMap<Integer, Integer> SKU_AVG_MAP = new ConcurrentHashMap<>();
    // map to store the year to date num cases ordered so far, per sku
    static final ConcurrentHashMap<Integer, Integer> YTD_CASES = new ConcurrentHashMap<>();

    // Max size for an given collection of all the products
    static int max_all_items;
    //date format for day iteration
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    // the list of all products created at start to easily index for random sku-price from all
    static SkuPrice[] allMyProdcuts;
    // vars holding the runtime counts for metrics
    static int total_items_bought = 0;
    static int total_customers = 0;
    static double total_sales_USD = 0;
    // class wide access to variable that allows us to fall through main body if number items bough so far > items allotted to customer
    static boolean fallThrough = false;
    // local variables for specific tasks
    static BufferedWriter writer;
    static LocalDate start;
    static LocalDate end;
    static Date startDate;
    static Date endDate;
    // reusable value for sku tracking
    static Integer sku;
    // reusable value for price tracking
    static double price;
    static int currQuant;
    static int casesYTD;
    static LocalDate date;
    static int itemsCount;
    static int custCount;

    /**
     * Method to generate one (1) random integer between low (inclusive) and hi (exclusive)
     */
    public static int randRange(int low, int hi) {
        return new Random().nextInt(hi-low) + low;
    }

    /**
     * Special delineation of randRange to generate a random number between 1 and 100
     */
    public static int randPct() {
        return randRange(1, 101);
    }

    /**
     * Method to check if param date is Weekend day
     */
    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    /**
     * Method to check if is Delivery Day for student unique case
     */
    public static boolean isDeliveryDay(LocalDate date) {
        // Alt case for other students
        /* return date.getDayOfWeek() == DayOfWeek.MONDAY ||
                date.getDayOfWeek() == DayOfWeek.WEDNESDAY ||
                date.getDayOfWeek() == DayOfWeek.FRIDAY;
        */
        return date.getDayOfWeek() == DayOfWeek.TUESDAY ||
                date.getDayOfWeek() == DayOfWeek.THURSDAY ||
                date.getDayOfWeek() == DayOfWeek.SATURDAY;
    }

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
    public static void write(Object... args) {

        /** Uncomment to print each entry */
//         println(args);

        total_items_bought += 1; // count num elements by tracking each atomic entry (or line)
        String delim = "";
        try {
            for (Object o : args) {
                writer.write(delim);
                writer.write(o.toString());
                delim = ",";
            }
            writer.write("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to update the parametrized SkuPrice-key Count-Value entry in the sku count map.
     */
    public static void updateSkuMap(SkuPrice sku) {
        SKU_PRICE_MAP_COUNT.putIfAbsent(sku, new AtomicInteger(0));
        SKU_PRICE_MAP_COUNT.get(sku).incrementAndGet();
    }

    /**
     * Method to retrieve one (1) random item from the parameterized list of SkuPrices
     */
    public static SkuPrice getRandomItemFromArray(SkuPrice[] array) {
        return array[new Random().nextInt(array.length)];
    }

    /**
     * Method to check if inventory map is empty, that is to say if all sku's is at count of 0
     */
    public static boolean isInvEmpty(){
        for(Map.Entry entry : MY_INVENTORY.entrySet()) {
            if ((int) entry.getValue() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to re-order inventory given current date
     * By iterating over all entries in the inventory, we can boost our stock when it falls below 3x Daily avg inventory (1.5x for milks)
     */
    public static void bolsterInventory(LocalDate date) {
        for(Map.Entry<Integer, Integer> entry : MY_INVENTORY.entrySet()) {
            Integer sku = entry.getKey();
            int currentStock = MY_INVENTORY.get(sku);
            int avgDailyStock = SKU_AVG_MAP.get(sku);
            if(isDeliveryDay(date) && !isMilkSku(sku)) {
                if (currentStock < (3 * avgDailyStock)) {
                    int quantityOrdered = (3 * avgDailyStock) - currentStock;
                    int leftOver = quantityOrdered % 12;
                    int numCases = quantityOrdered / 12;
                    if(leftOver != 0) {
                        numCases += 1;
                    }
                    MY_INVENTORY.replace(sku, currentStock + numCases * 12);
                    YTD_CASES.putIfAbsent(sku, numCases);
                    YTD_CASES.replace(sku, YTD_CASES.get(sku) + numCases);
                }
            }
            if(isMilkSku(sku)) {
                if(currentStock < (int) (1.5 * avgDailyStock)) {
                    int quantityOrdered = (int) (1.5 * avgDailyStock) - currentStock;
                    int leftOver = quantityOrdered % 12;
                    int numCases = quantityOrdered / 12;
                    if(leftOver != 0) {
                        numCases += 1;
                    }
                    MY_INVENTORY.replace(sku, currentStock + numCases * 12);
                    YTD_CASES.putIfAbsent(sku, numCases);
                    YTD_CASES.replace(sku, YTD_CASES.get(sku) + numCases);
                }
            }
        }
    }

    /**
     * Method to allow a customer to attempt to but an item based ont he given array of items.
     * This is used across the main body's nested if's to limit the size of the program.
     */
    public static boolean buyItem(SkuPrice[] array) {
        SkuPrice randMilk = getRandomItemFromArray(array);  // get random milk SkuPrice
        sku = randMilk.getSku();     // parse sku out of milk SkuPrice
        price = roundTwoDecimal(randMilk.getPrice() * PRICE_MULT); // parse price out of file and x by factor
        if(MY_INVENTORY.get(sku) -1 > -1) {
            updateSkuMap(new SkuPrice(sku, price));
            total_sales_USD += price;               // increment total sales with price
            currQuant = MY_INVENTORY.get(sku);
            currQuant--;
            casesYTD = YTD_CASES.get(sku);
            MY_INVENTORY.replace(sku, currQuant);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

        // Header to message the user with set parameters and start timer.
        println("Creation Started w/ Params:");
        int paddingSize = 32;
        char fill = '*';
        print(FG_MAGENTA);
        printlnDelim("\n",
                padJustify(paddingSize, fill,     "START_DATE_STRING ",  " " + START_DATE_STRING),
                padJustify(paddingSize, fill,     "END_DATE_STRING ",    " " + END_DATE_STRING),
                padJustify(paddingSize, fill,     "CUST_LOW ",           " " + CUST_LOW),
                padJustify(paddingSize, fill,     "CUST_HI ",            " " + CUST_HI),
                padJustify(paddingSize, fill,     "PRICE_MULT ",         " " + PRICE_MULT),
                padJustify(paddingSize, fill,     "MAX_ITEMS ",          " " + MAX_ITEMS),
                padJustify(paddingSize, fill,     "WEEKEND_INCREASE ",   " " + WEEKEND_INCREASE),
                padJustify(paddingSize, fill,     "OUTPUT_PATH ",        " " + OUTPUT_PATH)
        );
        print(RESET);
        AbstractStopwatch timer = new SYSStopwatch(TimeUnit.SECONDS);
        timer.start();

        // Init inventory, avg.s map, and ytd cases map from parsed file of avg bi-weekly sales
        InventoryBuilder.buildInventory();
        MY_INVENTORY.putAll(InventoryBuilder.inventoryMap);
        SKU_AVG_MAP.putAll(InventoryBuilder.avgsMap);
        YTD_CASES.putAll(InventoryBuilder.casesYTD);
        println("\nInventory Initialized from...");
        println(FG_CYAN, InventoryBuilder.INPUT_PATH);

        // set max to size determined by all skus maps
        max_all_items = SKU_AVG_MAP.size();

        // Build array of all my products for rand access later
        try {
            allMyProdcuts = new SkuPrice[max_all_items];
            FileInputStream fs = new FileInputStream(ALL_PRODUCTS_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line = br.readLine(); // throw away
            int index = 0;
            while((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");
                int sku = Integer.parseInt(fields[4]);
                double price = Double.parseDouble(fields[5].substring(1));
                allMyProdcuts[index] = new SkuPrice(sku, price);
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        println();
        println("All Products List successfully constructed from...");
        println(FG_BLUE, ALL_PRODUCTS_PATH);

        // Parse dates and build java 8 date objects for iteration
        try {
            startDate = formatter.parse(START_DATE_STRING);
            endDate = formatter.parse(END_DATE_STRING);
            start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        // Main body of work
        try {
            println("\nworking...");
            File file = new File(OUTPUT_PATH);
            writer = new BufferedWriter(new FileWriter(file));
            String[] header = {"Date", "Customer#", "Item#", "SKU", "Price", "ItemsLeft", "TotalCasesOrdered"};
            write(header);
            int numCust;
            int numItems;

            for (date = start; date.isBefore(end); date = date.plusDays(1)) {

                /* every day see if we can bolster inventory of milk, all (on acceptable dates), or both */
                bolsterInventory(date);

                // generate random num of customers for today
                numCust = randRange(CUST_LOW, CUST_HI);
                if (isWeekend(date)) {  //if sat or sun, boost numCustomers
                    numCust += WEEKEND_INCREASE;
                }

                for (custCount = 0; custCount < numCust; custCount++) {
                    numItems = randRange(1, MAX_ITEMS+1);   // rand numItems between 0 and MAX_ITEMS+1 (non inclusive)
                    itemsCount = 0;                                 // count items per single given customer
                    fallThrough = false;                            // re-set fallthrough for this customer
                    if (!fallThrough && randPct() <= 70) {          // if random pct is less than 70%
                        if(buyItem(HW1Arrays.MILKS)) {              // if the customer successfully buys the item from the given array
                            write(date, custCount, itemsCount, sku, price, currQuant, casesYTD); // then we write to file
                            itemsCount++;                           // and increase itemsCount for this customer
                        }
                        if (itemsCount >= numItems) {               // If this customer's shopping lists has already been fulfilled...
                        fallThrough = true;                         //...then fallthrough to next customer
                        }
                        if (!fallThrough && randPct() <= 50) {
                           if(buyItem(HW1Arrays.CEREALS)) {
                               write(date, custCount, itemsCount, sku, price, currQuant, casesYTD);
                               itemsCount++;
                           }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            if(buyItem(HW1Arrays.CEREALS)) {
                                write(date, custCount, itemsCount, sku, price, currQuant, casesYTD);
                                itemsCount++;
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 20) {
                        if(buyItem(HW1Arrays.BABY_FOODS)) {
                            write(date, custCount, itemsCount, sku, price, currQuant, casesYTD);
                            itemsCount++;
                        }
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 80) {
                            if(buyItem(HW1Arrays.DIAPERS)) {
                                write(date, custCount, itemsCount, sku, price, currQuant, casesYTD);
                                itemsCount++;
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 1) {
                            if(buyItem(HW1Arrays.DIAPERS)) {
                                write(date, custCount, itemsCount, sku, price, currQuant, casesYTD);
                                itemsCount++;
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 10) {
                        if(buyItem(HW1Arrays.PEANUT_BUTTERS)) {
                            write(date, custCount, itemsCount, sku, price, currQuant, casesYTD);
                            itemsCount++;
                        }
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 90) {
                            if(buyItem(HW1Arrays.JAM_JELLIES)) {
                                write(date, custCount, itemsCount, sku, price, currQuant, casesYTD);
                                itemsCount++;
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            if(buyItem(HW1Arrays.JAM_JELLIES)) {
                                write(date, custCount, itemsCount, sku, price, currQuant, casesYTD);
                                itemsCount++;
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if(!fallThrough && randPct() < 50) {
                        if(buyItem(HW1Arrays.BREADS)) {
                            write(date, custCount, itemsCount, sku, price, currQuant, casesYTD);
                            itemsCount++;
                        }
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                    }

                    if(!fallThrough) {
                        while (itemsCount < numItems) {
                            if(isInvEmpty()) {
                                /**
                                 * This is a special check to avoid halting problem that occurs when the inventory is empty, but we continue to try to extract items regardless.
                                 * To avoid this we try a cursory check to see if there is at least one of any item to sell.
                                 */
                                println(FG_RED,"INVENTORY SOLD OUT");
                                break;
                            }
                            if(buyItem(allMyProdcuts)) {
                                write(date, custCount, itemsCount, sku, price, currQuant, casesYTD);
                                itemsCount++;
                            }
                        }
                    }
                    total_customers += 1;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.flush(); // clean up writer to make sure we get every last drop
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        //stop timer ASAP for accuracy
        timer.stop();
        println(FG_GREEN, "\nDONE", timer, "\n");

        // sort map of <sku-price, counts> by frequency (count)
        Map<SkuPrice, AtomicInteger> sortedSkuCounts = new TreeMap(new SkuPrice.SkuMapComparator(SKU_PRICE_MAP_COUNT));
        sortedSkuCounts.putAll(SKU_PRICE_MAP_COUNT);

        // all of this nonsense prints the results to answer the questions in the assignment
        paddingSize = 50;
        fill = '.';
        print(FG_YELLOW);
        printlnDelim("\n",
                padJustify(paddingSize, fill,    "Total Items Bought: ", " " + NumberFormat.getIntegerInstance().format(total_items_bought)),
                padJustify(paddingSize, fill,    "Total Customers: ", " " + NumberFormat.getIntegerInstance().format(total_customers)),
                padJustify(paddingSize, fill,    "Total sales in USD: ", " $" + String.format("%1$,.2f", total_sales_USD))
        );
        println(padJustify(paddingSize, ' ', "Top 10 Items By Count:"));
        println(padToLength(paddingSize, '='));
        println(padJustify(paddingSize, ' ', " Rank |   SKU    |  Price  ", padToLength(7, '.'), " YTD Sold |", " YTD Cases"));
        int rank = 1;   // value to to count the items as the are printed to verify length and order
        for (Map.Entry<SkuPrice, AtomicInteger> entry : sortedSkuCounts.entrySet()) {
            sku = entry.getKey().getSku();
            price = entry.getKey().getPrice();
            int count = entry.getValue().intValue();
            /* Uncomment to break after top 10 */
//            if( rank > 10 ) {
//                break;
//            }
            println(padJustify(
                    paddingSize,
                    fill,
                    padToRight(5, rank) + " | " + sku + " | ($" + padToLeft(4, '0', price) + ") ",
                    " " + NumberFormat.getInstance().format(count) + " | " + YTD_CASES.get(sku))
            );
            rank++;
        }

        System.exit(0);
    }
}
