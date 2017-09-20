package GradDataWarehousing.HW1;

// 

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

public class HW1 {

    static String outputPath = "." + File.separatorChar + "output.txt";
    static String allProductsPath = "C:\\Users\\joe\\IdeaProjects\\java_stuff\\src\\GradDataWarehousing\\HW1\\myProducts";

    static final String START_DATE_STRING = "2017-01-01";
    static final String END_DATE_STRING = "2018-01-01";
    static final int CUST_LOW = 980;
    static final int CUST_HI = 1020;
    static final int WEEKEND_INCREASE = 50;
    static final double PRICE_MULT = 1.1;
    static final int MAX_ITEMS = 60;

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    static ArrayList<Tuple> allMyProdcuts = new ArrayList<>();
    static int total_lines_in_db = 0;

    static BufferedWriter writer;
    static LocalDate start;
    static LocalDate end;
    static Date startDate;
    static Date endDate;

    public static void constructAllProducts(String pathname) {
        try {
            FileInputStream fs= new FileInputStream(pathname);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line;
            while((line = br.readLine()) != null) {
                allMyProdcuts.add(new Tuple(line.split(", ")));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int randPct() {
        return randRange(1, 101);
    }

    public static int randRange(int low, int hi) {
        return new Random().nextInt(hi-low) + low;
    }

    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static void write(Object... args) {
        // println(args);
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

    public static Tuple getRandomItem() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(allMyProdcuts.size());
        return allMyProdcuts.get(randomIndex);
    }

    public static Tuple getRandomItem(Tuple[] arr) {
        return arr[new Random().nextInt(arr.length)];
    }

    public static double roundTwoDecimal(double num) {
        num = Math.round(num * 100);
        num = num/100;
        return num;
    }

    public static void main(String[] args) {

        println(fgPurple, "Creation Started");
        println("Params: ", START_DATE_STRING, END_DATE_STRING, CUST_LOW, CUST_HI,
                PRICE_MULT, MAX_ITEMS, WEEKEND_INCREASE);
        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();

        constructAllProducts(allProductsPath);
        println("All Products List constructed from...");
        println(allProductsPath);

        try {
            startDate = formatter.parse(START_DATE_STRING);
            endDate = formatter.parse(END_DATE_STRING);
            start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        try {
            println("working...");
            File file = new File(outputPath);
            writer = new BufferedWriter(new FileWriter(file));
            Integer sku;
            double num;

            for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
                int numCust = randRange(CUST_LOW, CUST_HI);
                if (isWeekend(date)) {
                    numCust += WEEKEND_INCREASE;
                }

                for (int custCount = 0; custCount < numCust; custCount++) {
                    int numItems = randRange(1, MAX_ITEMS+1);
                    int itemsCount = 0;
                    boolean fallThrough = false;
                    if (!fallThrough && randPct() <= 70) {
                        Tuple randMilk = getRandomItem(MILKS);
                        sku = (Integer) randMilk.getZero();
                        num = roundTwoDecimal((Double) randMilk.getOne() * PRICE_MULT);
                        write(date, custCount, itemsCount, sku, num);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 50) {
                            Tuple randCereal = getRandomItem(CEREALS);
                            sku = (Integer) randCereal.getZero();
                            num = roundTwoDecimal((Double) randCereal.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, num);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            Tuple randCereal = getRandomItem(CEREALS);
                            sku = (Integer) randCereal.getZero();
                            num = roundTwoDecimal((Double) randCereal.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, num);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 20) {
                        Tuple randBaby = getRandomItem(BABY_FOODS);
                        sku = (Integer) randBaby.getZero();
                        num = roundTwoDecimal((Double) randBaby.getOne() * PRICE_MULT);
                        write(date, custCount, itemsCount, sku, num);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 80) {
                            Tuple randDiaper = getRandomItem(DIAPERS);
                            sku = (Integer) randDiaper.getZero();
                            num = roundTwoDecimal((Double) randDiaper.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, num);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 1) {
                            Tuple randDiaper = getRandomItem(DIAPERS);
                            sku = (Integer) randDiaper.getZero();
                            num = roundTwoDecimal((Double) randDiaper.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, num);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 10) {
                        Tuple randPeanut = getRandomItem(PEANUT_BUTTERS);
                        sku = (Integer) randPeanut.getZero();
                        num = roundTwoDecimal((Double) randPeanut.getOne() * PRICE_MULT);
                        write(date, custCount, itemsCount, sku, num);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 90) {
                            Tuple randJJ = getRandomItem(JAM_JELLIES);
                            sku = (Integer) randJJ.getZero();
                            num = roundTwoDecimal((Double) randJJ.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, num);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            Tuple randJJ = getRandomItem(JAM_JELLIES);
                            sku = (Integer) randJJ.getZero();
                            num = roundTwoDecimal((Double) randJJ.getOne() * PRICE_MULT);
                            write(date, custCount, itemsCount, sku, num);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if(!fallThrough && randPct() < 50) {
                        Tuple randBread = getRandomItem(BREADS);
                        sku = (Integer) randBread.getZero();
                        num = roundTwoDecimal((Double) randBread.getOne() * PRICE_MULT);
                        write(date, custCount, itemsCount, sku, num);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                    }

                    if(!fallThrough) {
                        for (int remainder = itemsCount; remainder < numItems; remainder++) {
                            Tuple randAll = getRandomItem();
                            sku = Integer.parseInt((String) randAll.getZero());
                            num = roundTwoDecimal(Double.parseDouble((String) randAll.getOne()) * PRICE_MULT);
                            write(date, custCount, remainder, sku, num);
                        }
                    }
                    total_lines_in_db += numItems;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        timer.stop();
        println(fgCyan, timer);
        println(fgYellow,"Lines of DB: " + total_lines_in_db);
    }

}
