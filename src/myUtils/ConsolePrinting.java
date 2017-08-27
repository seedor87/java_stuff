package myUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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

    public static <T> String wrap(COLOR c, T... args) {
        StringBuffer sb = new StringBuffer();
        sb.append(decypher(c));
        for (T elem: args) {
            sb.append(elem.toString());
        }
        sb.append(ANSI_RESET);
        return sb.toString();
    }

    public static void print(COLOR c) {
        print(decypher(c));
    }

    public static <T> void print(T... args) {
        for (T elem : args) {
            System.out.print(elem);
        }
    }

    public static <T> void println(T... args) {
        print(args);
        System.out.println();
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

    public static <T extends Iterable<E>, E extends Serializable> void printDelim(String delim, T args) {
        String temp = "";
        for (E elem : args) {
            print(temp);
            print(elem);
            temp = delim;
        }
    }

    public static <T extends Iterable<E>, E extends Serializable> void printlnDelim(String delim, T args) {
        printDelim(delim, args);
        println();
    }

    public static <T extends Iterable> void printDelim(String delim, T[] args) {
        String temp = "";
        for (T elem : args) {
            print(temp);
            print(elem);
            temp = delim;
        }
    }

    public static <T extends Iterable> void printlnDelim(String delim, T[] args) {
        print(delim, args);
        println();
    }

    public static <T extends Iterable<E>, E extends Serializable> void print(T o) {
        String delim = "{";
        for(E elem: o) {
            print(delim);
            print(elem);
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static <T extends Iterable<E>, E extends Serializable> void println(T o) {
        print(o);
        println();
    }

    public static <T extends Iterable<E>, E extends Serializable> void print(COLOR c, T o) {
        print(c);
        print(o);
        print(ANSI_RESET);
    }

    public static <T extends Iterable<E>, E extends Serializable> void println(COLOR c, T o) {
        print(c, o);
        println();
    }

    public static <T extends Serializable> void print(T[] o) {
        String delim = "{";
        for (T elem : o) {
            print(delim);
            print(elem);
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static <T extends Serializable> void println(T[] o) {
        print(o);
        println();
    }

    public static <T extends Serializable> void print(COLOR c, T[] o) {
        print(c);
        print(o);
    }

    public static <T extends Serializable> void println(COLOR c, T[] o) {
        print(c, o);
        println();
    }

    public static void print(char[] o) {
        String delim = "{";
        for(char elem: o) {
            print(delim + "'");
            print(elem);
            print("'");
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static void println(char[] o) {
        print(o);
        println();
    }

    public static void print(COLOR c, char[] o) {
        print(c);
        print(o);
    }

    public static void println(COLOR c, char[] o) {
        print(c, o);
        println();
    }

    public static void print(int[] o) {
        String delim = "{";
        for(int elem: o) {
            print(delim);
            print(elem);
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static void println(int[] o) {
       print(o);
       println();
    }

    public static void print(COLOR c, int[] o) {
        print(c);
        print(o);
    }

    public static void println(COLOR c, int[] o) {
        print(c, o);
        println();
    }

    public static void print(double[] o) {
        String delim = "{";
        for(double elem: o) {
            print(delim);
            print(elem);
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static void println(double[] o) {
        print(o);
        println();
    }

    public static void print(COLOR c, double[] o) {
        print(c);
        print(o);
    }

    public static void println(COLOR c, double[] o) {
        print(c, o);
        println();
    }

    public static void print(long[] o) {
        String delim = "{";
        for(long elem: o) {
            print(delim);
            print(elem);
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static void println(long[] o) {
        print(o);
        println();
    }

    public static void print(COLOR c, long[] o) {
        print(c);
        print(o);
    }

    public static void println(COLOR c, long[] o) {
        print(c, o);
        println();
    }

    public static void print(Character[] o) {
        String delim = "{";
        for(char elem: o) {
            print(delim + "'");
            print(elem);
            print("'");
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        print(delim);
    }

    public static void println(Character[] o) {
        print(o);
        println();
    }

    public static void print(COLOR c, Character[] o) {
        print(c);
        print(o);
    }

    public static void println(COLOR c, Character[] o) {
        print(c, o);
        println();
    }

    public static void printDelim(String delim, int[] arr) {
        Integer[] temp = Arrays.stream(arr).boxed().toArray( Integer[]::new );
        printDelim(delim, temp);
    }

    public static void printlnDelim(String delim, int[] arr) {
        Integer[] temp = Arrays.stream(arr).boxed().toArray( Integer[]::new );
        printlnDelim(delim, temp);
    }

    public static void printDelim(String delim, long[] arr) {
        Long[] temp = Arrays.stream(arr).boxed().toArray( Long[]::new );
        printDelim(delim, temp);
    }

    public static void printlnDelim(String delim, long[] arr) {
        Long[] temp = Arrays.stream(arr).boxed().toArray( Long[]::new );
        printlnDelim(delim, temp);
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
        printlnDelim(", ", 1, 47, true, 't', "bob", new Tuple<>());
        printlnDelim(" + ", Arrays.asList(1,2,3,4));
        println(Arrays.asList(1,2,3,4));
        println(new long[]{Long.MAX_VALUE, Long.MAX_VALUE+1});
        printlnDelim(". ", new Integer[]{1,2,3});
        println(new Character[]{'a','b','c'});
        println(new int[]{'a','b','c'});

        print(COLOR.CYAN);
        printlnDelim(", ", 1, 47, true, 't', "bob", new Tuple<>());
        print(COLOR.RESET);

        println(COLOR.BLACK, Arrays.asList(1,2,3,4));
        println(COLOR.PURPLE, new long[]{Long.MAX_VALUE, Long.MAX_VALUE+1});
        println(COLOR.YELLOW, new Character[]{'a','b','c'});
        println(COLOR.BLUE, new int[]{'a','b','c'});

    }

}
