package GradDataWarehousing.HW1;

import Utils.Timers.AbstractStopwatch;
import Utils.Timers.SYSStopwatch;

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

import GradDataWarehousing.HWResources.*;
import Utils.Timers.TimeUnit;

import static Utils.ConsolePrinting.*;
import static Utils.StringUtils.StringUtils.*;

public class HW1 {

    static String outputPath = "." + File.separatorChar + "output1.txt"; //results go here
    static String allProductsFilePath = "." +
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
    //date format for day iteration
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    // the list of all products created at start to easily index for random sku-price from all
    static ArrayList<SkuPrice> allMyProdcuts = new ArrayList<>();
    // vars holding the runtime counts for metrics
    static int total_items_bought = 0;
    static int total_customers = 0;
    static double total_sales_USD = 0;
    // map for pairing <sku and price, count>
    static final ConcurrentMap<SkuPrice, AtomicInteger> skuPriceMapCount = new ConcurrentHashMap<>();
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
        skuPriceMapCount.putIfAbsent(sku, new AtomicInteger(0));
        skuPriceMapCount.get(sku).incrementAndGet();
    }

    /**
     * Method to retrieve one (1) random item from the pre-fabbed list of all products.
     */
    public static SkuPrice getRandomItem() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(allMyProdcuts.size());
        return allMyProdcuts.get(randomIndex);
    }

    /**
     * Method to retrieve one (1) random item from the parameterized list of SkuPrices
     */
    public static SkuPrice getRandomItem(SkuPrice[] arr) {
        return arr[new Random().nextInt(arr.length)];
    }

    public static void main(String[] args) {

        // Header to address the user with set parameters and start timer.
        println(FG_MAGENTA, "Creation Started w/ Params:");
        int paddingSize = 32;
        char fill = '*';
        printlnDelim("\n",
                padJustify(paddingSize, fill,     "START_DATE_STRING ",  " " + START_DATE_STRING),
                padJustify(paddingSize, fill,     "END_DATE_STRING ",    " " + END_DATE_STRING),
                padJustify(paddingSize, fill,     "CUST_LOW ",           " " + CUST_LOW),
                padJustify(paddingSize, fill,     "CUST_HI ",            " " + CUST_HI),
                padJustify(paddingSize, fill,     "PRICE_MULT ",         " " + PRICE_MULT),
                padJustify(paddingSize, fill,     "MAX_ITEMS ",          " " + MAX_ITEMS),
                padJustify(paddingSize, fill,     "WEEKEND_INCREASE ",   " " + WEEKEND_INCREASE)
        );
        AbstractStopwatch timer = new SYSStopwatch(TimeUnit.SECONDS);
        timer.start();

        // Build array of all products for rand access later
        try {
            FileInputStream fs= new FileInputStream(allProductsFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line = br.readLine(); // throw away
            while((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");
                int sku = Integer.parseInt(fields[4]);
                double price = Double.parseDouble(fields[5].substring(1));
                allMyProdcuts.add(new SkuPrice(sku, price));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        println();
        println("All Products List successfully constructed from...");
        println(FG_RED, allProductsFilePath);

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
            File file = new File(outputPath);
            writer = new BufferedWriter(new FileWriter(file));
            Integer sku;
            double price;

            for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
                int numCust = randRange(CUST_LOW, CUST_HI);
                if (isWeekend(date)) {  //if sat or sunday, boost numCustomers
                    numCust += WEEKEND_INCREASE;
                }

                for (int custCount = 0; custCount < numCust; custCount++) {
                    int numItems = randRange(1, MAX_ITEMS+1);   // rand numItems between 0 and MAX_ITEMS+1 (non inclusive)
                    int itemsCount = 0; // count items per single given customer
                    boolean fallThrough = false;    // boolean switch to fall through execution if/when items count >= numItems
                    if (!fallThrough && randPct() <= 70) {      // if random pct is less than 70%
                        SkuPrice randMilk = getRandomItem(HW1Arrays.MILKS);  // get random milk SkuPrice
                        sku = randMilk.getSku();     // parse sku out of milk SkuPrice
                        price = roundTwoDecimal(randMilk.getPrice() * PRICE_MULT); // parse price out of file and x by factor
                        updateSkuMap(new SkuPrice(sku, price));
                        total_sales_USD += price;               // increment total sales with price
                        write(date, custCount, itemsCount, sku, price); // write to file
                        itemsCount++;                           // increase itemsCount
                        if (itemsCount >= numItems) {           // if this customer's shopping lists has already been fulfilled...
                            fallThrough = true;                 //...then fallthrough to next customer
                        }
                        if (!fallThrough && randPct() <= 50) {
                            SkuPrice randCereal = getRandomItem(HW1Arrays.CEREALS);
                            sku = randCereal.getSku();
                            price = roundTwoDecimal(randCereal.getPrice() * PRICE_MULT);
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            SkuPrice randCereal = getRandomItem(HW1Arrays.CEREALS);
                            sku = randCereal.getSku();
                            price = roundTwoDecimal(randCereal.getPrice() * PRICE_MULT);
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 20) {
                        SkuPrice randBaby = getRandomItem(HW1Arrays.BABY_FOODS);
                        sku = randBaby.getSku();
                        price = roundTwoDecimal(randBaby.getPrice() * PRICE_MULT);
                        updateSkuMap(new SkuPrice(sku, price));
                        total_sales_USD += price;
                        write(date, custCount, itemsCount, sku, price);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 80) {
                            SkuPrice randDiaper = getRandomItem(HW1Arrays.DIAPERS);
                            sku = randDiaper.getSku();
                            price = roundTwoDecimal(randDiaper.getPrice() * PRICE_MULT);
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 1) {
                            SkuPrice randDiaper = getRandomItem(HW1Arrays.DIAPERS);
                            sku = randDiaper.getSku();
                            price = roundTwoDecimal(randDiaper.getPrice() * PRICE_MULT);
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 10) {
                        SkuPrice randPeanut = getRandomItem(HW1Arrays.PEANUT_BUTTERS);
                        sku = randPeanut.getSku();
                        price = roundTwoDecimal(randPeanut.getPrice() * PRICE_MULT);
                        updateSkuMap(new SkuPrice(sku, price));
                        total_sales_USD += price;
                        write(date, custCount, itemsCount, sku, price);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 90) {
                            SkuPrice randJJ = getRandomItem(HW1Arrays.JAM_JELLIES);
                            sku = randJJ.getSku();
                            price = roundTwoDecimal(randJJ.getPrice() * PRICE_MULT);
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            SkuPrice randJJ = getRandomItem(HW1Arrays.JAM_JELLIES);
                            sku = randJJ.getSku();
                            price = roundTwoDecimal(randJJ.getPrice() * PRICE_MULT);
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if(!fallThrough && randPct() < 50) {
                        SkuPrice randBread = getRandomItem(HW1Arrays.BREADS);
                        sku = randBread.getSku();
                        price = roundTwoDecimal(randBread.getPrice() * PRICE_MULT);
                        updateSkuMap(new SkuPrice(sku, price));
                        total_sales_USD += price;
                        write(date, custCount, itemsCount, sku, price);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                    }

                    if(!fallThrough) {
                        for ( ; itemsCount < numItems; itemsCount++) {
                            SkuPrice randAll = getRandomItem();
                            sku = randAll.getSku();
                            price = roundTwoDecimal(randAll.getPrice() * PRICE_MULT);
                            updateSkuMap(new SkuPrice(sku, price));
                            total_sales_USD += price;
                            write(date, custCount, itemsCount, sku, price);
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
        println(FG_GREEN, "\nDONE", timer, "\n");

        // sort map of <sku-price, counts> by frequency (count)
        Map<SkuPrice, AtomicInteger> sortedSkuCounts = new TreeMap(new SkuPrice.SkuMapComparator(skuPriceMapCount));
        sortedSkuCounts.putAll(skuPriceMapCount);

        // all of this nonsense prints the results to answer the questions in the assignment
        paddingSize = 42;
        fill = '.';
        print(FG_YELLOW);
        printlnDelim("\n",
                padJustify(paddingSize, fill,    "Total Items Bought: ", " " + NumberFormat.getIntegerInstance().format(total_items_bought)),
                padJustify(paddingSize, fill,    "Total Customers: ", " " + NumberFormat.getIntegerInstance().format(total_customers)),
                padJustify(paddingSize, fill,    "Total sales in USD: ", " $" + String.format("%1$,.2f", total_sales_USD))
        );
        println(padJustify(paddingSize, ' ', "Top 10 Items By Count:"));
        println(padToLength(paddingSize, '='));
        println(padJustify(paddingSize, ' ', " Rank |   SKU    |  Price ", "Count "));
        int rank = 1;
        for (Map.Entry<SkuPrice, AtomicInteger> entry : sortedSkuCounts.entrySet()) {
//            if( rank > 100 ) {
//                break;
//            }
            println(padJustify(
                   paddingSize,
                   fill,
                   padToRight(5, rank) + " | " + entry.getKey().getSku() + " | ($" + entry.getKey().getPrice() + ") ",
                   " " + NumberFormat.getInstance().format(entry.getValue().intValue()))
            );
            rank++;
        }
        System.exit(0);
    }
}