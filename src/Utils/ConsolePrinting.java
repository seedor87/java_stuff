package Utils;

import Utils.Collections.Tuple;

import java.lang.reflect.Array;
import java.util.*;

public class ConsolePrinting {

    interface Color {
        String str();
    }

    public static Color fgBlack = () ->    "\u001B[30m";
    public static Color fgRed = () ->      "\u001B[31m";
    public static Color fgGreen = () ->    "\u001B[32m";
    public static Color fgYellow = () ->   "\u001B[33m";
    public static Color fgBlue = () ->     "\u001B[34m";
    public static Color fgPurple = () ->   "\u001B[35m";
    public static Color fgCyan = () ->     "\u001B[36m";
    public static Color fgWhite = () ->    "\u001B[37m";

    public static Color bgBlack = () ->    "\u001B[40m";
    public static Color bgRed = () ->      "\u001B[41m";
    public static Color bgGreen = () ->    "\u001B[42m";
    public static Color bgYellow = () ->   "\u001B[43m";
    public static Color bgBlue = () ->     "\u001B[44m";
    public static Color bgPurple = () ->   "\u001B[45m";
    public static Color bgCyan = () ->     "\u001B[46m";
    public static Color bgWhite = () ->    "\u001B[47m";

    public static Color reset = () ->      "\u001B[0m";

    public static void print(Color c) {
        print(c.str());
    }

    public static void println() {
        System.out.println();
    }

    public static <T> String wrap(Color c, T... args) {
        StringBuffer sb = new StringBuffer();
        sb.append(c.str());
        for (T elem: args) {
            sb.append(elem.toString());
        }
        sb.append(reset);
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

    private static <T> void printIterable(T col) {
        String delim = "{";
        for(Object elem: (Iterable<? extends Object>) col) {
            print(delim);
            print(elem);
            delim = ", ";
        }
        print("}");
    }

    private static <T> void printChar(T c) {
        print("'");
        System.out.print(c);
        print("'");
    }

    public static <T> void print(T o) {
        if(o.getClass().isArray()) {
           printArray(o);
        } else if (Tuple.class.isInstance(o)) {
            printTuple(o);
        } else if(Iterable.class.isInstance(o)) {
            printIterable(o);
        } else if (Character.class.isInstance(o)) {
            printChar(o);
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

    public static <T> void print(Color c, T...args) {
        print(c);
        print(args);
        print(reset);
    }

    public static <T> void println(Color c, T...args) {
        print(c, args);
        println();
    }

    public static <T> void print(Color c1, Color c2, T...args) {
        print(c1);
        print(c2);
        print(args);
        print(reset);
    }

    public static <T> void println(Color c1, Color c2, T...args) {
        print(c1, c2, args);
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

        println(fgCyan, Arrays.asList(1, 47, true, 't', "bob", new Tuple<>(7, '8', "9"), new int[]{2,1,1,2}));
        println(fgWhite, new char[10]);
        println(fgBlack, new Character[]{'a','b','c','d','e','f','g','h','i','j'});
        println(fgPurple, Arrays.asList(Long.MAX_VALUE, Long.MAX_VALUE+1));
        println(fgBlue, bgYellow, new char[]{'a','b','c'});
        println(fgYellow, bgBlue, new int[]{'a','b','c'});
    }
}
