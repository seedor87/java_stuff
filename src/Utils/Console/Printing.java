package Utils.Console;

import Utils.Collections.OldTuple.Tuple;
import Utils.Timing.AbstractStopwatch;
import Utils.Timing.SYSStopwatch;
import Utils.Timing.TimeUnit;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;

import static Utils.Console.Special.*;

public class Printing {

    private static String DEFAULT_DELIM = ", ";

    public static String format(CharSequence str, Object... args) {
        return System.out.format(str.toString(), args).toString();
    }

    public static void println() {
        print(NEW_LINE);
    }
    public static void printrn() { print(CARR_RET); }

    public static void print(PrintableSpecial c) {
        print(c.toString());
    }

    public static void print(Object o) {
        try {
            if (CharSequence.class.isInstance(o)) {
                System.out.print(o);
            } else if (o.getClass().isArray()) {
                print("{");
                CharSequence delim = "";
                for (int i = 0; i < Array.getLength(o); i++) {
                    Object obj = Array.get(o, i);
                    print(delim);
                    print(obj);
                    delim = ", ";
                }
                print("}");
            } else if (Map.class.isInstance(o)) {
                printMap(
                        DEFAULT_DELIM, (Map) o);
            } else if (Tuple.class.isInstance(o)) {
                printTuple(
                        DEFAULT_DELIM, (Tuple) o);
            } else if (Iterable.class.isInstance(o)) {
                printIterable(
                        DEFAULT_DELIM, (Iterable) o);
            } else if (BaseStream.class.isInstance(o)) {
                printStream(
                        DEFAULT_DELIM, (BaseStream) o);
            } else if (Character.class.isInstance(o)) {
                printChar((Character) o);
            } else {
                System.out.print(o);
            }
        } catch (NullPointerException ex) {
            printNull();
        }
    }

    private static <T extends Character> void printChar(T car) {
        print("'");
        System.out.print(car);
        print("'");
    }

    private static void printNull() {
        print(FG_BRIGHT_RED, "NULL");
    }

    private static <T extends Iterable<E>, E> void printIterable(CharSequence delim, T col) {
        print("[");
        CharSequence temp = "";
        for(E elem: col) {
            print(temp);
            print(elem);
            temp = delim;
        }
        print("]");
    }

    private static <T extends Map<K, V>, K, V> void printMap(CharSequence delim, T map) {
        print("[");
        CharSequence temp = "";
        for(Map.Entry<K, V> o : map.entrySet()) {
            print(temp);
            print("<");
            print(o.getKey());
            print(" = ");
            print(o.getValue());
            print(">");
            temp = delim;
        }
        print("]");
    }

    private static <T extends Tuple> void printTuple(CharSequence delim, T tup) {
        print("(");
        CharSequence temp = "";
        for (int i = 0; i < Tuple.class.cast(tup).length; i++) {
            Object obj = Tuple.class.cast(tup).get(i);
            print(temp);
            print(obj);
            temp = delim;
        }
        print(")");
    }

    private static <T extends BaseStream<E, T>, E> void printStream(CharSequence delim, T stm) {
        stm.iterator().forEachRemaining(
            new Consumer<E>() {
                boolean first = true;
                @Override
                public void accept(E o) {
                    if (first) {
                        print(o);
                        first = false;
                    } else {
                        print(delim);
                        print(o);
                    }
                }
            }
        );
    }

    public static void print(Object... args) {
        CharSequence delim = "";
        for (Object elem : args) {
            print(delim);
            print(elem);
            delim = " ";
        }
    }

    public static void println(Object... args) {
        print(args);
        println();
    }

    public static void printrn(Object... args) {
        printrn();
        print(args);
    }

    public static void print(PrintableSpecial c, Object...args) {
        print(c);
        print(args);
        print(RESET);
    }

    public static void println(PrintableSpecial c, Object...args) {
        print(c, args);
        println();
    }

    public static void printrn(PrintableSpecial c, Object...args) {
        printrn();
        print(c, args);
    }

    public static void print(PrintableSpecial c1, PrintableSpecial c2, Object...args) {
        print(c1);
        print(c2);
        print(args);
        print(RESET);
    }

    public static void println(PrintableSpecial c1, PrintableSpecial c2, Object...args) {
        print(c1, c2, args);
        println();
    }

    public static void printrn(PrintableSpecial c1, PrintableSpecial c2, Object...args) {
        printrn();
        print(c1, c2, args);
    }

    public static void printDelim(CharSequence delim, Object... args) {
        CharSequence temp = "";
        for (Object elem : args) {
            print(temp);
            print(elem);
            temp = delim;
        }
    }

    public static void printlnDelim(CharSequence delim, Object... args) {
        printDelim(delim, args);
        println();
    }

    public static void printrnDelim(CharSequence delim, Object... args) {
        printrn();
        printDelim(delim, args);
    }

    public static <T extends Collection<E>, E> void printDelim(CharSequence delim, T args) {
        printIterable(delim, args);
    }

    public static <T extends Collection<E>, E> void printlnDelim(CharSequence delim, T args) {
        printDelim(delim, args);
        println();
    }

    public static <T extends Collection<E>, E> void printrnDelim(CharSequence delim, T args) {
        printrn();
        printDelim(delim, args);
    }

    public static <T extends BaseStream<E, T>, E> void printDelim(CharSequence delim, T args) {
        printStream(delim, args);
    }

    public static <T extends BaseStream<E, T>, E> void printlnDelim(CharSequence delim, T args) {
        printDelim(delim, args);
        println();
    }

    public static <T extends BaseStream<E, T>, E> void printrnDelim(CharSequence delim, T args) {
        printrn();
        printDelim(delim, args);
    }

   public static void printDelim(CharSequence delim, char[] arr) {
        CharSequence temp = "";
        for(char c : arr) {
            print(temp);
            print(c);
            temp = delim;
        }
   }

   public static void printlnDelim(CharSequence delim, char[] arr) {
        printDelim(delim, arr);
        println();
   }

    public static void printrnDelim(CharSequence delim, char[] arr) {
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

        printlnDelim(", ", IntStream.of(1,2,3,4));
        printlnDelim(" - ", Arrays.stream(new String[4]));
        println(FG_BLUE, IntStream.of(5,4,3,2,1));

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
