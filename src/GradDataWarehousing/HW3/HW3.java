package GradDataWarehousing.HW3;

import GradDataWarehousing.HWResources.HW1Arrays;
import GradDataWarehousing.HWResources.SkuMapComparator;
import GradDataWarehousing.HWResources.SkuPrice;
import Utils.Timers.AbstractTimer;
import Utils.Timers.SYSTimer;

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
import static Utils.ConsolePrinting.*;
import static Utils.ConsolePrinting.println;
import static Utils.StringUtils.StringUtils.padJustify;
import static Utils.StringUtils.StringUtils.padToLength;
import static Utils.StringUtils.StringUtils.padToRight;

public class HW3 {

    static final String OUTPUT_PATH = "." + File.separatorChar + "output3.txt"; //results go here
    static final String ALL_PRODUCTS_PATH = "." +
            File.separatorChar + "src" +
            File.separatorChar + "GradDataWarehousing" +
            File.separatorChar + "HW1" +
            File.separatorChar + "myProducts";  // pre-processed list of skus and prices
    // student specific params
    static final String START_DATE_STRING = "2017-01-01";
    static final String END_DATE_STRING = "2018-01-01";
    static final int CUST_LOW = 1140;
    static final int CUST_HI = 1180;
    static final double PRICE_MULT = 1.1;
    static final int MAX_ITEMS = 100;
    static final int WEEKEND_INCREASE = 50;
    //date format for day iteration
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    // the list of all products created at start to easily index for random sku-price from all
    static ArrayList<SkuPrice> allMyProdcuts = new ArrayList<>();
    // vars holding the runtime counts for metrics
    static int total_items_bought = 0;
    static int total_customers = 0;
    static double total_sales_USD = 0;
    // map for pairing <sku and price, count>
    static final ConcurrentMap<SkuPrice, AtomicInteger> SKU_PRICE_MAP_COUNT = new ConcurrentHashMap<>();
    // map to keep constant track of inventory
    static final ConcurrentMap<Integer, Integer> MY_INVENTORY = new ConcurrentHashMap<>();
    // map to store sku-avg pairs for inventory readjustment and ordering
    static final ConcurrentHashMap<Integer, Integer> SKU_AVG_MAP = new ConcurrentHashMap<>();
    // map to store the year to date num cases ordered so far, per sku
    static final ConcurrentHashMap<Integer, Integer> YTD_CASES = new ConcurrentHashMap<>();
    // local variables for specific tasks
    static BufferedWriter writer;
    static LocalDate start;
    static LocalDate end;
    static Date startDate;
    static Date endDate;

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

    public static boolean isDeliveryDay(LocalDate date) {
        /* return date.getDayOfWeek() == DayOfWeek.MONDAY ||
                date.getDayOfWeek() == DayOfWeek.WEDNESDAY ||
                date.getDayOfWeek() == DayOfWeek.FRIDAY; */
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
                delim = " | ";
            }
            writer.write("\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Method to update the parameterized SkuPrice-key Count-Value entry in the sku count map.
     * @param sku
     */
    public static void updateSkuMap(SkuPrice sku) {
        SKU_PRICE_MAP_COUNT.putIfAbsent(sku, new AtomicInteger(0));
        SKU_PRICE_MAP_COUNT.get(sku).incrementAndGet();
    }

    /**
     * Method to retrieve one (1) random item from the pre-fabbed list of all products.
     */
    public static SkuPrice getRandomItemFromAll() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(allMyProdcuts.size());
        return allMyProdcuts.get(randomIndex);
    }

    /**
     * Method to retrieve one (1) random item from the parameterized list of SkuPrices
     */
    public static SkuPrice getRandomItemFromArray(SkuPrice[] array) {
        return array[new Random().nextInt(array.length)];
    }

    public static boolean isInvEmpty(){
        for(Map.Entry entry : MY_INVENTORY.entrySet()) {
            if ((int) entry.getValue() > 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        // Header to address the user with set parameters and start timer.
        println(fgPurple, "Creation Started w/ Params:");
        int paddingSize = 32;
        char fill = '*';
        printlnDelim("\n",
                padJustify(paddingSize, fill,     "START_DATE_STRING ",  " " + START_DATE_STRING),
                padJustify(paddingSize, fill,     "END_DATE_STRING ",    " " + END_DATE_STRING),
                padJustify(paddingSize, fill,     "CUST_LOW ",           " " + CUST_LOW),
                padJustify(paddingSize, fill,     "CUST_HI ",            " " + CUST_HI),
                padJustify(paddingSize, fill,     "PRICE_MULT ",         " " + PRICE_MULT),
                padJustify(paddingSize, fill,     "MAX_ITEMS ",          " " + MAX_ITEMS),
                padJustify(paddingSize, fill,     "WEEKEND_INCREASE ",   " " + WEEKEND_INCREASE),
                padJustify(paddingSize, fill,     "OUTPUT FILE ",        " " + OUTPUT_PATH)
        );
        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();

        // Build array of all products for rand access later
        try {
            FileInputStream fs= new FileInputStream(ALL_PRODUCTS_PATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line;
            while((line = br.readLine()) != null) {
                String[] pair = line.split(", ");
                int sku = Integer.parseInt(pair[0]);
                double price = Double.parseDouble(pair[1]);
                allMyProdcuts.add(new SkuPrice(sku, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        println();
        println("All Products List successfully constructed from...");
        println(fgRed, ALL_PRODUCTS_PATH);

        // Init inventory from parsed file of avg bi-weekly sales
        InventoryBuilder.buildInventory();
        MY_INVENTORY.putAll(InventoryBuilder.inventoryMap);
        SKU_AVG_MAP.putAll(InventoryBuilder.avgsMap);
        for(Map.Entry<Integer, Integer> entry : SKU_AVG_MAP.entrySet()) {
            YTD_CASES.putIfAbsent(entry.getKey(), 0);
        }
        println("Inventory Initialized from:");
        println(fgCyan, InventoryBuilder.INPUT_PATH);

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
            println("working...");
            File file = new File(OUTPUT_PATH);
            writer = new BufferedWriter(new FileWriter(file));
            Integer sku;
            double price;

            for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {

                for(Map.Entry<Integer, Integer> entry : MY_INVENTORY.entrySet()) {
                    sku = entry.getKey();
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
                        if(isMilkSku(entry.getKey()) && currentStock < (1.5 * avgDailyStock)) {
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

                int numCust = randRange(CUST_LOW, CUST_HI);
                if (isWeekend(date)) {  //if sat or sun, boost numCustomers
                    numCust += WEEKEND_INCREASE;
                }

                for (int custCount = 0; custCount < numCust; custCount++) {
                    int numItems = randRange(1, MAX_ITEMS+1);   // rand numItems between 0 and MAX_ITEMS+1 (non inclusive)
                    int itemsCount = 0; // count items per single given customer
                    boolean fallThrough = false;    // boolean switch to fall through execution if/when items count >= numItems
                    if (!fallThrough && randPct() <= 70) {      // if random pct is less than 70%
                        SkuPrice randMilk = getRandomItemFromArray(HW1Arrays.MILKS);  // get random milk SkuPrice
                        sku = randMilk.getSku();     // parse sku out of milk SkuPrice
                        price = roundTwoDecimal(randMilk.getPrice() * PRICE_MULT); // parse price out of file and x by factor
                        if(MY_INVENTORY.get(sku) -1 > -1) {
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;               // increment total sales with price
                            itemsCount++;                           // increase itemsCount
                            int currentQuantity = MY_INVENTORY.get(sku);
                            currentQuantity--;
                            int casesYTD = YTD_CASES.get(sku);
                            MY_INVENTORY.replace(sku, currentQuantity);
                            write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD); // write to file
                        }
                        if (itemsCount >= numItems) {           // if this customer's shopping lists has already been fulfilled...
                        fallThrough = true;                 //...then fallthrough to next customer
                        }
                        if (!fallThrough && randPct() <= 50) {
                            SkuPrice randCereal = getRandomItemFromArray(HW1Arrays.CEREALS);
                            sku = randCereal.getSku();
                            price = roundTwoDecimal(randCereal.getPrice() * PRICE_MULT);
                            if(MY_INVENTORY.get(sku) -1 > -1) {
                                updateSkuMap(new SkuPrice(sku, price));
                                total_sales_USD += price;
                                itemsCount++;
                                int currentQuantity = MY_INVENTORY.get(sku);
                                currentQuantity--;
                                int casesYTD = YTD_CASES.get(sku);
                                MY_INVENTORY.replace(sku, currentQuantity);
                                write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD);
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            SkuPrice randCereal = getRandomItemFromArray(HW1Arrays.CEREALS);
                            sku = randCereal.getSku();
                            price = roundTwoDecimal(randCereal.getPrice() * PRICE_MULT);
                            if(MY_INVENTORY.get(sku) -1 > -1) {
                                updateSkuMap(new SkuPrice(sku, price));
                                total_sales_USD += price;
                                itemsCount++;
                                int currentQuantity = MY_INVENTORY.get(sku);
                                currentQuantity--;
                                int casesYTD = YTD_CASES.get(sku);
                                MY_INVENTORY.replace(sku, currentQuantity);
                                write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD);
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 20) {
                        SkuPrice randBaby = getRandomItemFromArray(HW1Arrays.BABY_FOODS);
                        sku = randBaby.getSku();
                        price = roundTwoDecimal(randBaby.getPrice() * PRICE_MULT);
                        if(MY_INVENTORY.get(sku) -1 > -1) {
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;
                            itemsCount++;
                            int currentQuantity = MY_INVENTORY.get(sku);
                            currentQuantity--;
                            int casesYTD = YTD_CASES.get(sku);
                            MY_INVENTORY.replace(sku, currentQuantity);
                            write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD);
                        }
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 80) {
                            SkuPrice randDiaper = getRandomItemFromArray(HW1Arrays.DIAPERS);
                            sku = randDiaper.getSku();
                            price = roundTwoDecimal(randDiaper.getPrice() * PRICE_MULT);
                            if(MY_INVENTORY.get(sku) -1 > -1) {
                                updateSkuMap(new SkuPrice(sku, price));
                                total_sales_USD += price;
                                itemsCount++;
                                int currentQuantity = MY_INVENTORY.get(sku);
                                currentQuantity--;
                                int casesYTD = YTD_CASES.get(sku);
                                MY_INVENTORY.replace(sku, currentQuantity);
                                write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD);
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 1) {
                            SkuPrice randDiaper = getRandomItemFromArray(HW1Arrays.DIAPERS);
                            sku = randDiaper.getSku();
                            price = roundTwoDecimal(randDiaper.getPrice() * PRICE_MULT);
                            if(MY_INVENTORY.get(sku) -1 > -1) {
                                updateSkuMap(new SkuPrice(sku, price));
                                total_sales_USD += price;
                                itemsCount++;
                                int currentQuantity = MY_INVENTORY.get(sku);
                                currentQuantity--;
                                int casesYTD = YTD_CASES.get(sku);
                                MY_INVENTORY.replace(sku, currentQuantity);
                                write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD);
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 10) {
                        SkuPrice randPeanut = getRandomItemFromArray(HW1Arrays.PEANUT_BUTTERS);
                        sku = randPeanut.getSku();
                        price = roundTwoDecimal(randPeanut.getPrice() * PRICE_MULT);
                        if(MY_INVENTORY.get(sku) -1 > -1) {
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;
                            itemsCount++;
                            int currentQuantity = MY_INVENTORY.get(sku);
                            currentQuantity--;
                            int casesYTD = YTD_CASES.get(sku);
                            MY_INVENTORY.replace(sku, currentQuantity);
                            write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD);
                        }
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 90) {
                            SkuPrice randJJ = getRandomItemFromArray(HW1Arrays.JAM_JELLIES);
                            sku = randJJ.getSku();
                            price = roundTwoDecimal(randJJ.getPrice() * PRICE_MULT);
                            if(MY_INVENTORY.get(sku) -1 > -1) {
                                updateSkuMap(new SkuPrice(sku, price));
                                total_sales_USD += price;
                                itemsCount++;
                                int currentQuantity = MY_INVENTORY.get(sku);
                                currentQuantity--;
                                int casesYTD = YTD_CASES.get(sku);
                                MY_INVENTORY.replace(sku, currentQuantity);
                                write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD);
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            SkuPrice randJJ = getRandomItemFromArray(HW1Arrays.JAM_JELLIES);
                            sku = randJJ.getSku();
                            price = roundTwoDecimal(randJJ.getPrice() * PRICE_MULT);
                            if(MY_INVENTORY.get(sku) -1 > -1) {
                                updateSkuMap(new SkuPrice(sku, price));
                                total_sales_USD += price;
                                itemsCount++;
                                int currentQuantity = MY_INVENTORY.get(sku);
                                currentQuantity--;
                                int casesYTD = YTD_CASES.get(sku);
                                MY_INVENTORY.replace(sku, currentQuantity);
                                write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD);
                            }
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if(!fallThrough && randPct() < 50) {
                        SkuPrice randBread = getRandomItemFromArray(HW1Arrays.BREADS);
                        sku = randBread.getSku();
                        price = roundTwoDecimal(randBread.getPrice() * PRICE_MULT);
                        if(MY_INVENTORY.get(sku) -1 > -1) {
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;
                            itemsCount++;
                            int currentQuantity = MY_INVENTORY.get(sku);
                            currentQuantity--;
                            int casesYTD = YTD_CASES.get(sku);
                            MY_INVENTORY.replace(sku, currentQuantity);
                            write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD);
                        }
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                    }

                    if(!fallThrough) {
                        while (itemsCount < numItems) {
                            if(isInvEmpty()) {
                                break;
                            }
                            SkuPrice randAll = getRandomItemFromAll();
                            sku = randAll.getSku();
                            price = roundTwoDecimal(randAll.getPrice() * PRICE_MULT);
                            if(MY_INVENTORY.get(sku) -1 > -1) {
                                updateSkuMap(new SkuPrice(sku, price));
                                total_sales_USD += price;
                                itemsCount++;
                                int currentQuantity = MY_INVENTORY.get(sku);
                                currentQuantity--;
                                int casesYTD = YTD_CASES.get(sku);
                                MY_INVENTORY.replace(sku, currentQuantity);
                                write(date, custCount, itemsCount, sku, price, currentQuantity, casesYTD);
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

        //stop timer ASAP for acc.
        timer.stop();
        println(fgGreen, "\nDONE", timer, "\n");

        // sort map of <sku-price, counts> by frequency (count)
        Map<SkuPrice, AtomicInteger> sortedSkuCounts = new TreeMap(new SkuMapComparator(SKU_PRICE_MAP_COUNT));
        sortedSkuCounts.putAll(SKU_PRICE_MAP_COUNT);

        // all of this nonsense prints the results to answer the questions in the assignment
        paddingSize = 48;
        fill = '.';
        print(fgYellow);
        printlnDelim("\n",
                padJustify(paddingSize, fill,    "Total Items Bought: ", " " + NumberFormat.getIntegerInstance().format(total_items_bought)),
                padJustify(paddingSize, fill,    "Total Customers: ", " " + NumberFormat.getIntegerInstance().format(total_customers)),
                padJustify(paddingSize, fill,    "Total sales in USD: ", " $" + String.format("%1$,.2f", total_sales_USD))
        );
        println(padJustify(paddingSize, ' ', "Top 10 Items By Count:"));
        println(padToLength(paddingSize, '='));
        println(padJustify(paddingSize, ' ', " Rank |   SKU    |  Price", padToLength(9), "| Count |", " Cases YTD"));
        int rank = 1;
        for (Map.Entry<SkuPrice, AtomicInteger> entry : sortedSkuCounts.entrySet()) {
            if( rank > 10 ) {
                break;
            }
            println(padJustify(
                    paddingSize,
                    fill,
                    padToRight(5, rank) + " | " + entry.getKey().getSku() + " | ($" + entry.getKey().getPrice() + ") ",
                    " " + NumberFormat.getInstance().format(entry.getValue().intValue()) + "  | " + YTD_CASES.get(entry.getKey().getSku()))
            );
            rank++;
        }

        System.exit(0);
    }
}
