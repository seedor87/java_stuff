package GradDataWarehousing.HW4;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import static Utils.ConsolePrinting.*;

/**
 * Created by robertseedorf on 11/2/17.
 */
public class DateDimensionParser {


    static final String OUTPUT_PATH = "." + File.separatorChar + "DateDimensionTable.csv"; //results go here
    static final String START_DATE_STRING = "2017-01-01";
    static final String END_DATE_STRING = "2018-01-01";
    static final String sep = ", ";
    static BufferedWriter bw;

    // federal holidays
    private static final String holidays[] = {
            "2017-01-02",   // New Year's Day
            "2017-01-16",   // G.W.'s B-Day
            "2017-02-20",   // MLK Day
            "2017-05-29",   // Memorial Day
            "2017-07-04",   // The 4th
            "2017-09-04",   // Labour Day
            "2017-10-09",   // Columbus Day
            "2017-11-10",   // Armistice Day
            "2017-11-23",   // Turkey Day
            "2017-12-25",   // Jesus's B-Day
    };

    private static final String seasons[] = {
            "NA",                           // cannot index at position 0
            "Winter", "Winter",             // jan and feb
            "Spring", "Spring", "Spring",   // march, april, may
            "Summer", "Summer", "Summer",   // june, july, august
            "Fall", "Fall", "Fall",         // sep, oct, nov
            "Winter"                        // december
    };

    //date format for day iteration
    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    static LocalDate start;
    static LocalDate end;
    static LocalDate date;

    static int date_key = 0;
    static int day_num_in_month = 0;
    static int day_num_in_year = 0;
    static int week_num_in_year = 0;    // we start on a sunday this year...
    static int n_month = 1;
    static String month = "";
    static int quarter = 1;
    static int year = 2017;
    static int fiscal_year = 2016;
    static boolean is_holiday = true;
    static boolean is_weekend = false;
    static String season = "";

    /**
     *  Method to write particular line to txt file now
     */
    public static void write(Object... args) throws IOException {

        /** Uncomment to print each entry */
//         println(args);

        String delim = "";
        for (Object o : args) {
            bw.write(delim);
            bw.write(o.toString());
            delim = sep;
        }
        bw.write("\n");
    }

    /**
     * Method to check if param date is Weekend day
     */
    public static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    public static boolean isHoliday(LocalDate date) {
        try {
            for (String h : holidays) {
                LocalDate holiday = formatter.parse(h).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                if(date.compareTo(holiday) == 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getSeason(LocalDate date) {
        return seasons[date.getMonth().getValue()];
    }

    public static int getQuarter(LocalDate date) {
        Month month = date.getMonth();
        return (month.compareTo(Month.JANUARY) >= 0 && month.compareTo(Month.MARCH) <= 0     ? 1 :
                (month.compareTo(Month.APRIL) >= 0 && month.compareTo(Month.JUNE) <= 0        ? 2 :
                        (month.compareTo(Month.JULY) >= 0 && month.compareTo(Month.SEPTEMBER) <= 0    ? 3 :
                                4)));
    }

    public static void main(String[] args) {

        try {
            FileOutputStream fos = new FileOutputStream(OUTPUT_PATH);
            bw = new BufferedWriter(new OutputStreamWriter(fos));

            final LocalDate fiscalYearEnd = formatter.parse("2017-07-31").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Date startDate = formatter.parse(START_DATE_STRING);
            Date endDate = formatter.parse(END_DATE_STRING);
            start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            write("Date Key",
                    "Date",
                    "Day Num In Moth",
                    "Day Num In Year",
                    "Week Num In Year",
                    "Month Num",
                    "Month",
                    "Quarter",
                    "Year",
                    "Fiscal Year",
                    "Is Holiday",
                    "Is Weekend",
                    "Season");
            for (date = start; date.isBefore(end); date = date.plusDays(1)) {
                day_num_in_month = date.getDayOfMonth();
                day_num_in_year = date.getDayOfYear();
                if(date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                    week_num_in_year++;
                }
                n_month = date.getMonth().getValue();
                month = date.getMonth().toString();
                quarter = getQuarter(date);
                if(date.isBefore(fiscalYearEnd)) {
                    fiscal_year = 2016;
                } else {
                    fiscal_year = 2017;
                }

                is_holiday = isHoliday(date);

                is_weekend = isWeekend(date);
                season = getSeason(date);
                write(date_key,
                        date,
                        day_num_in_month,
                        day_num_in_year,
                        week_num_in_year,
                        n_month,
                        month,
                        quarter,
                        year,
                        fiscal_year,
                        is_holiday,
                        is_weekend,
                        season
                );
                date_key++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                bw.flush();
                bw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
