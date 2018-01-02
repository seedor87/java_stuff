package Utils.Console;

import Utils.Collections.OldTuple.Tuple;
import Utils.Timers.AbstractStopwatch;
import Utils.Timers.SYSStopwatch;
import Utils.Timers.TimeUnit;

import java.lang.reflect.Array;
import java.util.*;
import static Utils.Console.Special.*;

public class Printing {

    public static String format(String str, Object... args) {
        return System.out.format(str, args).toString();
    }

    public static void print(PrintableSpecial c) {
        print(c.toString());
    }

    public static void println() {
        print(NEW_LINE);
    }
    public static void printrn() { print(CARR_RET); }

    private static <T> void printMap(T obj) {
        print("[");
        String delim = "";
        for(Object o : ((Map) obj).entrySet()) {
            print(delim);
            Map.Entry entry = (Map.Entry) o;
            print("<");
            print(entry.getKey());
            print(" = ");
            print(entry.getValue());
            print(">");
            delim = " , ";
        }
        print("]");
    }

    private static <T> void printArray(T arr) {
        print("{");
        String delim = "";
        for (int i = 0; i < Array.getLength(arr); i++) {
            Object obj = Array.get(arr, i);
            print(delim);
            print(obj);
            delim = ", ";
        }
        print("}");
    }

    private static <T> void printTuple(T tup) {
        print("(");
        String delim = "";
        for (int i = 0; i < Tuple.class.cast(tup).length; i++) {
            Object obj = Tuple.class.cast(tup).get(i);
            print(delim);
            print(obj);
            delim = ", ";
        }
        print(")");
    }

    private static <T> void printIterable(T col) {
        print("[");
        String delim = "";
        for(Object elem: (Iterable<T>) col) {
            print(delim);
            print(elem);
            delim = ", ";
        }
        print("]");
    }

    private static <T> void printChar(T c) {
        print("'");
        System.out.print(c);
        print("'");
    }

    private static void printNull() {
        print(FG_BRIGHT_RED, "NULL");
    }

    public static <T> void print(T o) {
        try {
            if (o.getClass().isArray()) {
                printArray(o);
            } else if (Map.class.isInstance(o)) {
                printMap(o);
            } else if (Tuple.class.isInstance(o)) {
                printTuple(o);
            } else if (Iterable.class.isInstance(o)) {
                printIterable(o);
            } else if (Character.class.isInstance(o)) {
                printChar(o);
            } else {
                System.out.print(o);
            }
        } catch (NullPointerException ex) {
            printNull();
        }
    }

    public static <T> void print(T... args) {
        String delim = "";
        for (T elem : args) {
            print(delim);
            print(elem);
            delim = " ";
        }
    }

    public static <T> void println(T... args) {
        print(args);
        println();
    }

    public static <T> void printrn(T... args) {
        printrn();
        print(args);
    }

    public static <T> void print(PrintableSpecial c, T...args) {
        print(c);
        print(args);
        print(RESET);
    }

    public static <T> void println(PrintableSpecial c, T...args) {
        print(c, args);
        println();
    }

    public static <T> void printrn(PrintableSpecial c, T...args) {
        printrn();
        print(c, args);
    }

    public static <T> void print(PrintableSpecial c1, PrintableSpecial c2, T...args) {
        print(c1);
        print(c2);
        print(args);
        print(RESET);
    }

    public static <T> void println(PrintableSpecial c1, PrintableSpecial c2, T...args) {
        print(c1, c2, args);
        println();
    }

    public static <T> void printrn(PrintableSpecial c1, PrintableSpecial c2, T...args) {
        printrn();
        print(c1, c2, args);
    }

    public static <T> void printDelim(String delim, T... args) {
        String temp = "";
        for (T elem : args) {
            print(temp);
            print(elem);
            temp = delim;
        }
    }

    public static <T> void printlnDelim(String delim, T... args) {
        printDelim(delim, args);
        println();
    }

    public static <T> void printrnDelim(String delim, T... args) {
        printrn();
        printDelim(delim, args);
    }

    public static <T extends Collection<E>, E extends Object> void printDelim(String delim, T args) {
        String temp = "";
        for (E elem : args) {
            print(temp);
            print(elem);
            temp = delim;
        }
    }

    public static <T extends Collection<E>, E extends Object> void printlnDelim(String delim, T args) {
        printDelim(delim, args);
        println();
    }

    public static <T extends Collection<E>, E extends Object> void printrnDelim(String delim, T args) {
        printrn();
        printDelim(delim, args);
    }

   public static void printDelim(String delim, char[] arr) {
        String temp = "";
        for(char c : arr) {
            print(temp);
            print(c);
            temp = delim;
        }
   }

   public static void printlnDelim(String delim, char[] arr) {
        printDelim(delim, arr);
        println();
   }

    public static void printrnDelim(String delim, char[] arr) {
        printrn();
        printDelim(delim, arr);
    }

   public static void main(String[] args) {

        printlnDelim(", ", 1, 47, true, 't', "bob", new Tuple<>(7, '8', "9"), new int[]{2,1,1,2});
        printlnDelim(" + ", Arrays.asList(1,2,3,4));
        printlnDelim(" and ", new Character[]{'a','e','i','o','u'});
        printlnDelim(" & ", new char[]{'a','e','i','o','u'});

        println(Arrays.asList(1,2,3,4));
        println(new long[]{Long.MAX_VALUE, Long.MAX_VALUE+1});
        println(new char[]{'a','b','c'});
        println(new int[]{'a','b','c'});
        println(new String[][]{{"mouse", "cheese"}, {"dog", "bone"}, {"pig", "slop"}});
        println(new int[][][]{{{1},{2},{3}}, {{4,5,6}}, {{7}},{{8}},{{9}}});

        println(FG_BRIGHT_CYAN, Arrays.asList(1, 47, true, 't', "bob", new Tuple<>(7, '8', "9"), new int[]{2,1,1,2}));
        println(FG_WHITE, new char[10]);
        println(FG_DARK_GRAY, new Character[]{'a','b','c','d','e','f','g','h','i','j'});
        println(FG_BRIGHT_MAGENTA, Arrays.asList(Long.MAX_VALUE, Long.MAX_VALUE+1));
        println(FG_BRIGHT_BLUE, BG_BRIGHT_YELLOW, new char[]{'a','b','c'});
        println(FG_BRIGHT_YELLOW, BG_BRIGHT_BLUE, new int[]{'a','b','c'});

        Integer[] iarr = new Integer[5];
        iarr[1] = 1;
        iarr[3] = 3;
        println(iarr);

        char[] carr = new char[5];
        carr[0] = 'a';
        carr[2] = 'c';
        carr[4] = 'e';
        println(carr);

        println(BG_BLACK, FG_WHITE,"TEST");
        println(BG_GRAY, FG_DARK_GRAY,"TEST");
        println(BG_DARK_GRAY, FG_GRAY,"TEST");
        println(BG_WHITE, FG_BLACK,"TEST");

       try {
           AbstractStopwatch timer = new SYSStopwatch(TimeUnit.MINUTES);
           timer.start();
           boolean s = true;
           boolean s2 = true;
           while(true) {
                if (s) {
                    printrn(FG_RED, BG_BLACK, new Date().toString());
                    s = false;
                } else {
                    if(s2) {
                        printrn(BG_RED, FG_BLACK,"Running for:", timer.toString(TimeUnit.SECONDS));
                        s2 = false;
                    } else {
                        printrn(BG_RED, FG_BLACK,"Running for:", timer.toString());
                        s2 = true;
                    }
                    s = true;
                }
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
