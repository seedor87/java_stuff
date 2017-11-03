package Utils;

import Utils.Collections.Tuple;

import java.lang.reflect.Array;
import java.util.*;

public class ConsolePrinting {

    public static final String NL = "\n";
    public static final String RN = "\r";

    interface Color {
        String str();
    }

    public static Color FGBLACK = () ->    "\u001B[30m";
    public static Color FGRED = () ->      "\u001B[31m";
    public static Color FGGREEN = () ->    "\u001B[32m";
    public static Color FGYELLOW = () ->   "\u001B[33m";
    public static Color FGBLUE = () ->     "\u001B[34m";
    public static Color FGPURPLE = () ->   "\u001B[35m";
    public static Color FGCYAN = () ->     "\u001B[36m";
    public static Color FGWHITE = () ->    "\u001B[37m";
    public static Color BGBLACK = () ->    "\u001B[40m";
    public static Color BGRED = () ->      "\u001B[41m";
    public static Color BGGREEN = () ->    "\u001B[42m";
    public static Color BGYELLOW = () ->   "\u001B[43m";
    public static Color BGBLUE = () ->     "\u001B[44m";
    public static Color BGPURPLE = () ->   "\u001B[45m";
    public static Color BGCYAN = () ->     "\u001B[46m";
    public static Color BGWHITE = () ->    "\u001B[47m";
    public static Color RESET = () ->      "\u001B[0m";

    public static void print(Color c) {
        print(c.str());
    }

    public static void println() {
        print(NL);
    }
    public static void printrn() { print(RN); }

    public static <T> String wrap(Color c, T... args) {
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
        print(FGRED, "NULL");
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

    public static <T> void print(Color c, T...args) {
        print(c);
        print(args);
        print(RESET);
    }

    public static <T> void println(Color c, T...args) {
        print(c, args);
        println();
    }

    public static <T> void printrn(Color c, T...args) {
        printrn();
        print(c, args);
    }

    public static <T> void print(Color c1, Color c2, T...args) {
        print(c1);
        print(c2);
        print(args);
        print(RESET);
    }

    public static <T> void println(Color c1, Color c2, T...args) {
        print(c1, c2, args);
        println();
    }

    public static <T> void printrn(Color c1, Color c2, T...args) {
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

        println(FGCYAN, Arrays.asList(1, 47, true, 't', "bob", new Tuple<>(7, '8', "9"), new int[]{2,1,1,2}));
        println(FGWHITE, new char[10]);
        println(FGBLACK, new Character[]{'a','b','c','d','e','f','g','h','i','j'});
        println(FGPURPLE, Arrays.asList(Long.MAX_VALUE, Long.MAX_VALUE+1));
        println(FGBLUE, BGYELLOW, new char[]{'a','b','c'});
        println(FGYELLOW, BGBLUE, new int[]{'a','b','c'});

        Integer[] iarr = new Integer[5];
        iarr[1] = 1;
        iarr[3] = 3;
        println(iarr);

        char[] carr = new char[5];
        carr[0] = 'a';
        carr[2] = 'c';
        carr[4] = 'e';
        println(carr);

        try {
            while(true) {
                Thread t;
                Date date = new Date();
                printrn(FGRED, date.toString());
                Thread.sleep(2000);
                date = new Date();
                printrn(FGRED, date.toInstant());
                Thread.sleep(2000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
