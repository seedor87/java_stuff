package myUtils;

import myUtils.Measurement.SYSTimer;

import java.lang.reflect.Array;
import java.util.*;

public class ConsolePrinting {

    public static final String ANSI_RESET =     "\u001B[0m";
    public static final String ANSI_BLACK =     "\u001B[30m";
    public static final String ANSI_RED =       "\u001B[31m";
    public static final String ANSI_GREEN =     "\u001B[32m";
    public static final String ANSI_YELLOW =    "\u001B[33m";
    public static final String ANSI_BLUE =      "\u001B[34m";
    public static final String ANSI_PURPLE =    "\u001B[35m";
    public static final String ANSI_CYAN =      "\u001B[36m";
    public static final String ANSI_WHITE =     "\u001B[37m";

    public enum COLOR {
        RESET, BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE
    }

    private static String decypher(COLOR c) {
        switch (c) {
            case BLACK:
                return ANSI_BLACK;
            case RED:
                return ANSI_RED;
            case GREEN:
                return ANSI_GREEN;
            case YELLOW:
                return ANSI_YELLOW;
            case BLUE:
                return ANSI_BLUE;
            case PURPLE:
                return ANSI_PURPLE;
            case CYAN:
                return ANSI_CYAN;
            case WHITE:
                return ANSI_WHITE;
            default:
                return ANSI_RESET;
        }
    }

    public static void print(COLOR c) {
        print(decypher(c));
    }

    public static void println() {
        System.out.println();
    }

    public static <T> String wrap(COLOR c, T... args) {
        StringBuffer sb = new StringBuffer();
        sb.append(decypher(c));
        for (T elem: args) {
            sb.append(elem.toString());
        }
        sb.append(ANSI_RESET);
        return sb.toString();
    }

    private static <T> void printArray(T arr) {
        print("[");
        String delim = "";
        for (int i = 0; i < Array.getLength(arr); i++) {
            Object obj = Array.get(arr, i);
            print(delim);
            print(obj);
            delim = ", ";
        }
        print("]");
    }

    private static <T> void printTuple(T tup) {
        print("<");
        String delim = "";
        for (int i = 0; i < Tuple.class.cast(tup).length; i++) {
            Object obj = Tuple.class.cast(tup).get(i);
            print(delim);
            print(obj);
            delim = ", ";
        }
        print(">");
    }

    private static <T> void printCollection(T col) {
        String delim = "{";
        for(Object elem: (Collection) col) {
            print(delim);
            print(elem);
            delim = ", ";
        }
        print("}");
    }

    public static <T> void print(T o) {
        if(o.getClass().isArray()) {
           printArray(o);
        } else if (Tuple.class.isInstance(o)) {
            printTuple(o);
        } else if (Collection.class.isInstance(o)) {
            printCollection(o);
        } else if (Character.class.isInstance(o)) {
            print("'");
            System.out.print(o);
            print("'");

        } else {
            System.out.print(o);
        }
    }

    public static <T> void print(T... args) {
        String delim = "";
        for (T elem : args) {
            print(delim);
            print(elem);
            delim = ", ";
        }
    }

    public static <T> void println(T... args) {
        print(args);
        println();
    }

    public static <T> void print(COLOR c, T...args) {
        print(c);
        print(args);
        print(ANSI_RESET);
    }

    public static <T> void println(COLOR c, T...args) {
        print(c, args);
        println();
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

        println(COLOR.CYAN, Arrays.asList(1, 47, true, 't', "bob", new Tuple<>(7, '8', "9"), new int[]{2,1,1,2}));
        println(COLOR.WHITE, new char[10]);
        println(COLOR.BLACK, new Character[]{'a','b','c','d','e','f','g','h','i','j'});
        println(COLOR.PURPLE, Arrays.asList(Long.MAX_VALUE, Long.MAX_VALUE+1));
        println(COLOR.BLUE, new char[]{'a','b','c'});
        println(COLOR.YELLOW, new int[]{'a','b','c'});
    }

}
