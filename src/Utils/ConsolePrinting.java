package Utils;

import Utils.Collections.Tuple;
import Utils.Timers.AbstractTimer;
import Utils.Timers.SYSTimer;

import java.lang.reflect.Array;
import java.util.*;

public class ConsolePrinting {

    public static final String NL = "\n";
    public static final String RN = "\r";

    public interface Special {
        String str();
    }

    public static Special RESET = () ->             "\u001B[0m";
    public static Special BOLD = () ->              "\u001B[1m";
    public static Special ITAL = () ->              "\u001B[3m";
    public static Special UNDER = () ->             "\u001B[4m";
    public static Special BLINK = () ->             "\u001B[5m";
    public static Special RAPID_BLINK = () ->       "\u001B[6m";
    public static Special INVER = () ->             "\u001B[7m";
    public static Special FG_BLACK = () ->          "\u001B[30m";
    public static Special FG_RED = () ->            "\u001B[31m";
    public static Special FG_GREEN = () ->          "\u001B[32m";
    public static Special FG_YELLOW = () ->         "\u001B[33m";
    public static Special FG_BLUE = () ->           "\u001B[34m";
    public static Special FG_MAGENTA = () ->        "\u001B[35m";
    public static Special FG_CYAN = () ->           "\u001B[36m";
    public static Special FG_GRAY = () ->           "\u001B[37m";
    public static Special BG_BLACK = () ->          "\u001B[40m";
    public static Special BG_RED = () ->            "\u001B[41m";
    public static Special BG_GREEN = () ->          "\u001B[42m";
    public static Special BG_YELLOW = () ->         "\u001B[43m";
    public static Special BG_BLUE = () ->           "\u001B[44m";
    public static Special BG_MAGENTA = () ->        "\u001B[45m";
    public static Special BG_CYAN = () ->           "\u001B[46m";
    public static Special BG_GRAY = () ->           "\u001B[47m";
    public static Special FG_DARK_GRAY = () ->      "\u001B[90m";
    public static Special FG_BRIGHT_RED = () ->     "\u001B[91m";
    public static Special FG_BRIGHT_GREEN = () ->   "\u001B[92m";
    public static Special FG_BRIGHT_YELLOW = () ->  "\u001B[93m";
    public static Special FG_BRIGHT_BLUE = () ->    "\u001B[94m";
    public static Special FG_BRIGHT_MAGENTA = () -> "\u001B[95m";
    public static Special FG_BRIGHT_CYAN = () ->    "\u001B[96m";
    public static Special FG_WHITE = () ->          "\u001B[97m";
    public static Special BG_DARK_GRAY = () ->      "\u001B[100m";
    public static Special BG_BRIGHT_RED = () ->     "\u001B[101m";
    public static Special BG_BRIGHT_GREEN = () ->   "\u001B[102m";
    public static Special BG_BRIGHT_YELLOW = () ->  "\u001B[103m";
    public static Special BG_BRIGHT_BLUE = () ->    "\u001B[104m";
    public static Special BG_BRIGHT_MAGENTA = () -> "\u001B[105m";
    public static Special BG_BRIGHT_CYAN = () ->    "\u001B[106m";
    public static Special BG_WHITE = () ->          "\u001B[107m";

    public static void print(Special c) {
        print(c.str());
    }

    public static void println() {
        print(NL);
    }
    public static void printrn() { print(RN); }

    public static <T> String wrap(Special c, T... args) {
        StringBuffer sb = new StringBuffer();
        sb.append(c.str());
        for (T elem: args) {
            sb.append(elem.toString());
        }
        sb.append(RESET);
        return sb.toString();
    }

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

    public static <T> void print(Special c, T...args) {
        print(c);
        print(args);
        print(RESET);
    }

    public static <T> void println(Special c, T...args) {
        print(c, args);
        println();
    }

    public static <T> void printrn(Special c, T...args) {
        printrn();
        print(c, args);
    }

    public static <T> void print(Special c1, Special c2, T...args) {
        print(c1);
        print(c2);
        print(args);
        print(RESET);
    }

    public static <T> void println(Special c1, Special c2, T...args) {
        print(c1, c2, args);
        println();
    }

    public static <T> void printrn(Special c1, Special c2, T...args) {
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
           AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.MINUTES);
           timer.start();
           boolean s = true;
           boolean s2 = true;
           while(true) {
                if (s) {
                    printrn(FG_RED, BG_BLACK, new Date().toString());
                    s = false;
                } else {
                    if(s2) {
                        printrn(BG_RED, FG_BLACK,"Running for:", timer.toString(AbstractTimer.TimeUnit.SECONDS));
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
