package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsolePrinting {

    public static <T extends Object> void print(T o) {
        System.out.print(o.toString());
    }
    public static <T extends Object> void println(T o) {
        System.out.println(o.toString());
    }
    public static void println() {
        System.out.println();
    }

    public static <T> void print(T... args) {
        String delim = "";
        for (T elem : args) {
            print(delim + elem);
            delim = " ";
        }
        delim = (delim.equals("")) ? "{}" : "";
        print(delim);
    }

    public static <T> void println(T... args) {
        String delim = "";
        for (T elem : args) {
            print(delim + elem);
            delim = " ";
        }
        delim = (delim.equals("")) ? "{}" : "";
        print(delim);
        println();
    }

    public static <T> void printDelim(String delim, T... args) {
        String temp = "";
        for (T elem : args) {
            print(temp + elem);
            temp = delim;
        }
    }

    public static <T> void printlnDelim(String delim, T... args) {
        print(delim, args);
        println();
    }

    public static <T extends Iterable<E>, E> void printDelim(String delim, T args) {
        String temp = "";
        for (E elem : args) {
            print(temp + elem);
            temp = delim;
        }
    }

    public static <T extends Iterable<E>, E> void printlnDelim(String delim, T args) {
        print(delim, args);
        println();
    }

    public static <T extends Iterable> void printDelim(String delim, T[] args) {
        String temp = "";
        for (T elem : args) {
            print(temp + elem);
            temp = delim;
        }
    }

    public static <T extends Iterable> void printlnDelim(String delim, T[] args) {
        print(delim, args);
        println();
    }
    public static <T extends Iterable<E>, E> void print(T o) {
        String delim = "{";
        for(E elem: o) {
            print(delim + elem);
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static <T extends Iterable<E>, E> void println(T o) {
        print(o);
        println();
    }

    public static <T extends Iterable> void print(T[] o) {
        String delim = "{";
        for (T elem : o) {
            print(delim + elem);
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static <T extends Iterable> void println(T[] o) {
        print(o);
        println();
    }

    public static void print(char[] o) {
        String delim = "{";
        for(char elem: o) {
            print(delim + "'" + elem + "'");
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static void println(char[] o) {
        print(o);
        println();
    }

    public static void print(Character[] o) {
        String delim = "{";
        for(char elem: o) {
            print(delim + "'" + elem + "'");
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static void println(Character[] o) {
        print(o);
        println();
    }

    public static void print(int[] arr) {
        Integer[] temp = Arrays.stream(arr).boxed().toArray( Integer[]::new );
        print(temp);
    }

    public static void println(int[] arr) {
        Integer[] temp = Arrays.stream(arr).boxed().toArray( Integer[]::new );
        println(temp);
    }

    public static void printDelim(String delim, int[] arr) {
        Integer[] temp = Arrays.stream(arr).boxed().toArray( Integer[]::new );
        printDelim(delim, temp);
    }

    public static void printlnDelim(String delim, int[] arr) {
        Integer[] temp = Arrays.stream(arr).boxed().toArray( Integer[]::new );
        printlnDelim(delim, temp);
    }

    public static void print(long[] arr) {
        Long[] temp = Arrays.stream(arr).boxed().toArray( Long[]::new );
        print(temp);
    }

    public static void println(long[] arr) {
        Long[] temp = Arrays.stream(arr).boxed().toArray( Long[]::new );
        println(temp);
    }

    public static void printDelim(String delim, long[] arr) {
        Long[] temp = Arrays.stream(arr).boxed().toArray( Long[]::new );
        printDelim(delim, temp);
    }

    public static void printlnDelim(String delim, long[] arr) {
        Long[] temp = Arrays.stream(arr).boxed().toArray( Long[]::new );
        printlnDelim(delim, temp);
    }

    public static void print(double[] arr) {
        Double[] temp = Arrays.stream(arr).boxed().toArray( Double[]::new );
        print(temp);
    }

    public static void println(double[] arr) {
        Double[] temp = Arrays.stream(arr).boxed().toArray( Double[]::new );
        println(temp);
    }

    public static void printDelim(String delim, double[] arr) {
        Double[] temp = Arrays.stream(arr).boxed().toArray( Double[]::new );
        printDelim(delim, temp);
    }

    public static void printlnDelim(String delim, double[] arr) {
        Double[] temp = Arrays.stream(arr).boxed().toArray( Double[]::new );
        printlnDelim(delim, temp);
    }

    public static void main(String[] args) {
        printDelim(", ", 1, 47, true, 't', "bob");
        println();
        printDelim(", ", Arrays.asList(1,2,3,4));
        println();
        print(Arrays.asList(1,2,3,4));
        println();
        printDelim(" - ", new int[]{1,2,3});
        println();
        print(new int[]{1,2,3});
        println();
        printDelim(". ", new Integer[]{1,2,3});
        println();
        print(new Character[]{'a','b','c'});
        println();
    }

}
