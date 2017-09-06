package GradDataWarehousing.HW1;

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
    static final int MAX_ITEMS = 70+1;
    static final int WEEKEND_INCREASE = 50;

    /**
     Rowan Dairy|1.00% Milk|1 gal|Milk|42355001|$3.69
     Rowan Dairy|1.00% Milk|1/2 gal|Milk|42356001|$1.89
     Rowan Dairy|2.00% Milk|1 gal|Milk|42357001|$3.69
     Rowan Dairy|2.00% Milk|1/2 gal|Milk|42358001|$1.89
     Rowan Dairy|Whole Milk Milk|1 gal|Milk|42359001|$3.69
     Rowan Dairy|Whole Milk Milk|1/2 gal|Milk|42360001|$1.89
     */
    static final String MILK_SKU = "XXXXXXX";
    static final String CEREAL_SKU = "0000000";

    public static void readProducts(String pathname) {
        BufferedReader br;
        try {
            File pipeFile = new File (pathname);
            br = new BufferedReader(new FileReader(pipeFile));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                // use pipe as separator
                Tuple tup = new Tuple(line.split("\\|"));
                products.add(new Tuple(tup.get(1), tup.get(4), ((String) tup.get(5)).replace("$", "")));
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

        readProducts("C:\\Users\\rseedorf\\IdeaProjects\\java_stuff\\src\\GradDataWarehousing\\HW1\\Products");

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
                                break;
                            }
                        }
                    }
                    if (true) {
                        //                        yada yada
                    }
                    print("");
                    for (int remainder = itemsCount; remainder < numItems; remainder++) {
                        write(date, custCount, remainder, getRandomItem());
                    }
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
    }

}
