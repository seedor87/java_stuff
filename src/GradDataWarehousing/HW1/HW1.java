package GradDataWarehousing.HW1;

import myUtils.ConsolePrinting;
import myUtils.Measurement.AbstractTimer;
import myUtils.Measurement.SYSTimer;
import myUtils.Tuple;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static myUtils.ConsolePrinting.print;
import static myUtils.ConsolePrinting.println;
import static myUtils.ConsolePrinting.printlnDelim;

public class HW1 {

    static BufferedWriter writer;
    static List<Tuple> products = new ArrayList<>();

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    static LocalDate start;
    static LocalDate end;
    static Date startDate;
    static Date endDate;

    static String startString = "2017-01-01";
    static String endString = "2018-01-01";

    static final int custLow = 1140;
    static final int custHi = 1180;
    static final double priceMult = 1.1;
    static final int MAX_ITEMS = 70 + 1;
    static final int WEEKEND_INCREASE = 50;

    static final String MILK_SKU = "XXXXXXX";
    static final String CEREAL_SKU = "0000000";
    static final String BABY_FOOD_SKU = "^^^^^^^";
    static final String DIAPERS_SKU = "8888888";
    static final String PEANUTBUTTER_SKU = "PPPPPPP";
    static final String JAM_JELLY_SKU = "JJJJJJJJ";
    static final String BREAD_SKU = "#######";


    public static void readProducts(String pathname) {
        BufferedReader br;
        try {
            File pipeFile = new File (pathname);
            br = new BufferedReader(new FileReader(pipeFile));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                // use pipe as separator
                String[] fields = line.split("\\|");
                String brand = fields[0];
                String name = fields[1];
                String weight = fields[2];
                String category = fields[3];
                String sku = fields[4];
                Double price = Double.parseDouble(fields[5].replace("$", ""));
                products.add(new Tuple(brand, name, weight, category, sku, price));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        println(products);
    }

    public static String getRandomItem() {
        int temp = new Random().nextInt(4);
        switch (temp) {
            case 0:
                return "@@@@@@@";
            case 1:
                return "%%%%%%%";
            case 2:
                return "*******";
            default:
                return "ZZZZZZZ";
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
        println(args);
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

    public static void main(String[] args) {

        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();

        int TOTAL_LINES_IN_DB = 0;

//        readProducts("C:\\Users\\rseedorf\\IdeaProjects\\java_stuff\\src\\GradDataWarehousing\\HW1\\Products");

        File file;
        try {
            file = new File("." + File.separatorChar + "foo.txt");
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // comment this out if you want to inspect the files afterward
//            file.delete();
        }

        try {
            startDate = formatter.parse(startString);
            endDate = formatter.parse(endString);
            start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        try {
            for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
                int numCust = randRange(custLow, custHi);
                if (isWeekend(date)) {
                    numCust += WEEKEND_INCREASE;
                }

                for (int custCount = 0; custCount < numCust; custCount++) {
                    int numItems = randRange(1, MAX_ITEMS);
                    int itemsCount = 0;
                    boolean fallThrough = false;
                    if (!fallThrough && randPct() <= 70) {
                        write(date, custCount, itemsCount, MILK_SKU);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 50) {
                            write(date, custCount, itemsCount, CEREAL_SKU);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            write(date, custCount, itemsCount, CEREAL_SKU);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 20) {
                        write(date, custCount, itemsCount, BABY_FOOD_SKU);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 80) {
                            write(date, custCount, itemsCount, DIAPERS_SKU);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 1) {
                            write(date, custCount, itemsCount, DIAPERS_SKU);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 10) {
                        write(date, custCount, itemsCount, PEANUTBUTTER_SKU);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 90) {
                            write(date, custCount, itemsCount, JAM_JELLY_SKU);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            write(date, custCount, itemsCount, JAM_JELLY_SKU);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if(fallThrough && randPct() < 50) {
                        write(date, custCount, itemsCount, BREAD_SKU);
                    }

                    for (int remainder = itemsCount; remainder < numItems; remainder++) {
                        write(date, custCount, remainder, getRandomItem());
                    }
                    TOTAL_LINES_IN_DB += numItems;
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
        println(ConsolePrinting.COLOR.CYAN, timer);
        println("Lines of DB: " + TOTAL_LINES_IN_DB);
    }

}
