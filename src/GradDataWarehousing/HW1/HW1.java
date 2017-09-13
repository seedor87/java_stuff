package GradDataWarehousing.HW1;

import Utils.Timers.AbstractTimer;
import Utils.Timers.SYSTimer;
import Utils.Collections.Tuple;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import static GradDataWarehousing.HW1.HW1Resources.*;
import static Utils.ConsolePrinting.*;
import static Utils.StringUtils.padCenter;
import static Utils.StringUtils.padRight;

public class HW1 {

    static String outputPath = "." + File.separatorChar + "output.txt";
    static String allProductsFilePath = "." +
            File.separatorChar + "src" +
            File.separatorChar + "GradDataWarehousing" +
            File.separatorChar + "HW1" +
            File.separatorChar + "myProducts";

    static final String START_DATE_STRING = "2017-01-01";
    static final String END_DATE_STRING = "2018-01-01";
    static final int CUST_LOW = 1140;
    static final int CUST_HI = 1180;
    static final double PRICE_MULT = 1.1;
    static final int MAX_ITEMS = 70;
    static final int WEEKEND_INCREASE = 50;

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    static ArrayList<Tuple> allMyProdcuts = new ArrayList<>();
    static int total_lines_in_db = 0;

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
     * Method to round a double to two (2) decimal places
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
        // println(args);

        total_lines_in_db += 1; // count num elements by tracking each atomic entry (or line)
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
     * Method to retrieve one (1) random item from the pre-fabbed list of all products.
     */
    public static Tuple getRandomItem() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(allMyProdcuts.size());
        return allMyProdcuts.get(randomIndex);
    }

    /**
     * Method to retrieve one (1) random item from the parameterized list of Tuples
     */
    public static Tuple getRandomItem(Tuple[] arr) {
        return arr[new Random().nextInt(arr.length)];
    }

    public static void main(String[] args) {

        // Header to address the user with set parameters and start timer.
        println(fgPurple, "Creation Started");
        println("Params:");
        final int paddingSize = 32;
        final char fill = '*';
        printlnDelim("\n",
                padCenter("START_DATE_STRING ",  " " + START_DATE_STRING,   paddingSize, fill),
                padCenter("END_DATE_STRING ",    " " + END_DATE_STRING,     paddingSize, fill),
                padCenter("CUST_LOW ",           " " + CUST_LOW,            paddingSize, fill),
                padCenter("CUST_HI ",            " " + CUST_HI,             paddingSize, fill),
                padCenter("PRICE_MULT ",         " " + PRICE_MULT,          paddingSize, fill),
                padCenter("MAX_ITEMS ",          " " + MAX_ITEMS,           paddingSize, fill),
                padCenter("WEEKEND_INCREASE ",   " " + WEEKEND_INCREASE,    paddingSize, fill)
        );
        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();

        // Build array of all products for rand access later
        try {
            FileInputStream fs= new FileInputStream(allProductsFilePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line;
            while((line = br.readLine()) != null) {
                allMyProdcuts.add(new Tuple(line.split(", ")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        println("All Products List successfully constructed from...");
        println(allProductsFilePath);

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
                        Tuple randMilk = getRandomItem(MILKS);  // get random milk tuple
                        sku = (Integer) randMilk.getZero();     // parse sku out of milk tuple
                        price = roundTwoDecimal((Double) randMilk.getOne() * PRICE_MULT); // parse price out of file and x by factor
                        write(date, custCount, itemsCount, sku, price); // write to file
                        itemsCount++;                           // increase itemsCount
                        if (itemsCount >= numItems) {           // if this customer's shopping lists has already been fulfilled...
                            fallThrough = true;                 //...then fallthrough to next customer
                        }
                        if (!fallThrough && randPct() <= 50) {
                            Tuple randCereal = getRandomItem(CEREALS);
                            sku = (Integer) randCereal.getZero();
                            price = roundTwoDecimal((Double) randCereal.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            Tuple randCereal = getRandomItem(CEREALS);
                            sku = (Integer) randCereal.getZero();
                            price = roundTwoDecimal((Double) randCereal.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 20) {
                        Tuple randBaby = getRandomItem(BABY_FOODS);
                        sku = (Integer) randBaby.getZero();
                        price = roundTwoDecimal((Double) randBaby.getOne() * PRICE_MULT);
                        write(date, custCount, itemsCount, sku, price);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 80) {
                            Tuple randDiaper = getRandomItem(DIAPERS);
                            sku = (Integer) randDiaper.getZero();
                            price = roundTwoDecimal((Double) randDiaper.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 1) {
                            Tuple randDiaper = getRandomItem(DIAPERS);
                            sku = (Integer) randDiaper.getZero();
                            price = roundTwoDecimal((Double) randDiaper.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 10) {
                        Tuple randPeanut = getRandomItem(PEANUT_BUTTERS);
                        sku = (Integer) randPeanut.getZero();
                        price = roundTwoDecimal((Double) randPeanut.getOne() * PRICE_MULT);
                        write(date, custCount, itemsCount, sku, price);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 90) {
                            Tuple randJJ = getRandomItem(JAM_JELLIES);
                            sku = (Integer) randJJ.getZero();
                            price = roundTwoDecimal((Double) randJJ.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            Tuple randJJ = getRandomItem(JAM_JELLIES);
                            sku = (Integer) randJJ.getZero();
                            price = roundTwoDecimal((Double) randJJ.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, price);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if(!fallThrough && randPct() < 50) {
                        Tuple randBread = getRandomItem(BREADS);
                        sku = (Integer) randBread.getZero();
                        price = roundTwoDecimal((Double) randBread.getOne() * PRICE_MULT);
                        write(date, custCount, itemsCount, sku, price);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                    }

                    if(!fallThrough) {
                        for ( ; itemsCount < numItems; itemsCount++) {
                            Tuple randAll = getRandomItem();
                            sku = Integer.parseInt((String) randAll.getZero());
                            price = roundTwoDecimal(Double.parseDouble((String) randAll.getOne()) * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, price);
                        }
                    }
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

        timer.stop();
        println(fgCyan, timer);
        println(fgYellow,"Lines of DB: " + total_lines_in_db);
        System.exit(0);
    }
}
