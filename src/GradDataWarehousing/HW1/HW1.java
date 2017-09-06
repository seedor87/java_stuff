package GradDataWarehousing.HW1;

import myUtils.Tuple;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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

    static FileWriter writer;
    static List<Tuple> collection;

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    static LocalDate start;
    static LocalDate end;
    static Date startDate;
    static Date endDate;

    static String startString = "2017-01-01";
    static String endString = "2018-01-01";

    static final int custLow = 980;
    static final int custHi = 1020;
    static final double priceMult = 1.1;
    static final int MAX_ITEMS = 70;
    static final int WEEKEND_INCREASE = 50;

    static final String MILK_SKU = "XXXXXXX";
    static final String CEREAL_SKU = "0000000";

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
//        String delim = "";
//        try {
//            for (Object o : args) {
//                writer.write(delim);
//                writer.write(o.toString());
//                delim = " | ";
//            }
//            writer.write("\n");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
        collection.add(new Tuple(args));
    }

    public static void main(String[] args) {

        collection = new ArrayList<>();
        File file;

        try {
            file = new File("./foo.txt");
            writer = new FileWriter(file);
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

        int i = 0;
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            int custCount = randRange(custLow, custHi);
            if(isWeekend(date)) {
                custCount += WEEKEND_INCREASE;
            }

            for (int j = 0; j < custCount; j++) {

                int numItems = randRange(1, MAX_ITEMS);
                int itemsCount = 0;
                if (randPct() <= 70) {
                    write(date, j, MILK_SKU);
                    itemsCount++;
                    if (itemsCount >= numItems) {
                        break;
                    }
                    if (randPct() <= 50) {
                        write(date, j, CEREAL_SKU);
                        itemsCount++;
                        if(itemsCount >= numItems) {
                            break;
                        }
                    }
                } else {
                    if (randPct() <= 5) {
                        write(date, j, CEREAL_SKU);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            break;
                        }
                    }
                }
                if (true) {
//                        yada yada
                }

                for (int remainder = itemsCount; remainder < numItems; remainder++) {
                    write(date, j, getRandomItem());
                }
            }
        }

        println(collection);
    }

}
