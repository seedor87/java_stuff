package Utils;

import java.util.Arrays;

public class ConsolePrinting {

    public static void print(Object o) {
        System.out.print(o.toString());
    }

    public static void println(Object o) {
        System.out.println(o.toString());
    }

    public static void println() {
        System.out.println();
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
        String delim = "{";
        for(E elem: o) {
            print(delim + elem);
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
        println();
    }

    public static <T> void print(T[] o) {
        String delim = "{";
        for (T elem : o) {
            print(delim + elem);
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static <T> void println(T[] o) {
        String delim = "{";
        for (T elem : o) {
            print(delim + elem);
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
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

    public static void print(long[] arr) {
        Long[] temp = Arrays.stream(arr).boxed().toArray( Long[]::new );
        print(temp);
    }

    public static void println(long[] arr) {
        Long[] temp = Arrays.stream(arr).boxed().toArray( Long[]::new );
        println(temp);
    }

    public static void print(double[] arr) {
        Double[] temp = Arrays.stream(arr).boxed().toArray( Double[]::new );
        print(temp);
    }

    public static void println(double[] arr) {
        Double[] temp = Arrays.stream(arr).boxed().toArray( Double[]::new );
        println(temp);
    }

}
