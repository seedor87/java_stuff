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
import java.util.Random;

import static GradDataWarehousing.HW1.HW1Resources.*;
import static myUtils.ConsolePrinting.println;

public class HW1 {

    static BufferedWriter writer;
    static ArrayList<Tuple> ALL = new ArrayList<>();

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

    public static void readTest(String pathname) {
        try {
            FileInputStream fs= new FileInputStream(pathname);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String line;
            while((line = br.readLine()) != null) {
                ALL.add(new Tuple(line.split(", ")));
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

    public static Tuple getRandomItem() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(ALL.size());
        return ALL.get(randomIndex);
    }

    public static Tuple getRandomItem(Tuple[] arr) {
        return arr[new Random().nextInt(arr.length)];
    }

    public static void main(String[] args) {

        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();

        int TOTAL_LINES_IN_DB = 0;

        readTest("C:\\Users\\Bob S\\IdeaProjects\\java_stuff\\src\\GradDataWarehousing\\HW1\\myProducts");

        File file;
        try {
            file = new File("." + File.separatorChar + "foo.txt");
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // comment this out if you want to inspect the files afterward
            // file.delete();
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
                        Tuple randMilk = getRandomItem(MILKS);
                        write(date, custCount, itemsCount, randMilk.getZero(), (Double) randMilk.getOne() * priceMult);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 50) {
                            Tuple randCereal = getRandomItem(CEREALS);
                            write(date, custCount, itemsCount, randCereal.getZero(), (Double) randCereal.getOne() * priceMult);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            Tuple randCereal = getRandomItem(CEREALS);
                            write(date, custCount, itemsCount, randCereal.getZero(), (Double) randCereal.getOne() * priceMult);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 20) {
                        Tuple randBaby = getRandomItem(BABY_FOODS);
                        write(date, custCount, itemsCount, randBaby.getZero(), (Double) randBaby.getOne() * priceMult);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 80) {
                            Tuple randDiaper = getRandomItem(DIAPERS);
                            write(date, custCount, itemsCount, randDiaper.getZero(), (Double) randDiaper.getOne() * priceMult);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 1) {
                            Tuple randDiaper = getRandomItem(DIAPERS);
                            write(date, custCount, itemsCount, randDiaper.getZero(), (Double) randDiaper.getOne() * priceMult);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if (!fallThrough && randPct() <= 10) {
                        Tuple randPeanut = getRandomItem(PEANUT_BUTTERS);
                        write(date, custCount, itemsCount, randPeanut.getZero(), (Double) randPeanut.getOne() * priceMult);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                        if (!fallThrough && randPct() <= 90) {
                            Tuple randJJ = getRandomItem(JAM_JELLIES);
                            write(date, custCount, itemsCount, randJJ.getZero(), (Double) randJJ.getOne() * priceMult);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    } else {
                        if (!fallThrough && randPct() <= 5) {
                            Tuple randJJ = getRandomItem(JAM_JELLIES);
                            write(date, custCount, itemsCount, randJJ.getZero(), (Double) randJJ.getOne() * priceMult);
                            itemsCount++;
                            if (itemsCount >= numItems) {
                                fallThrough = true;
                            }
                        }
                    }

                    if(!fallThrough && randPct() < 50) {
                        Tuple randBread = getRandomItem(BREADS);
                        write(date, custCount, itemsCount, randBread.getZero(), (Double) randBread.getOne() * priceMult);
                        itemsCount++;
                        if (itemsCount >= numItems) {
                            fallThrough = true;
                        }
                    }

                    for (int remainder = itemsCount; remainder < numItems; remainder++) {
                        Tuple randAll = getRandomItem();
                        write(date, custCount, remainder, randAll.getZero(), Double.parseDouble((String) randAll.getOne()) * priceMult);
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
