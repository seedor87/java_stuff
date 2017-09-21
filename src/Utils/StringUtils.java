package Utils;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

import static Utils.ConsolePrinting.*;
public class StringUtils {

    public static String yieldToLength(int n) {
        return yieldToLength(n, ' ');
    }

    public static String yieldToLength(int n, char fill) {
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < n; i++) {
            ret.append(fill);
        }
        return ret.toString();
    }

    public static String yieldToLength(int n, String fill) {
        StringBuilder ret = new StringBuilder();
        while(ret.length() < n) {
           ret.append(fill);
            if(ret.length() >= n-fill.length()) {
                ret.append(fill.substring(0, n-ret.length()));
            }
        }
        return ret.toString();
    }

    public static String padToLeft(int n, Object s) {
        return padToLeft(n, ' ', s);
    }
    public static String padToRight(int n, Object s) {
        return padToRight(n, ' ', s);
    }

    public static String padToLeft(int n, char fill, Object s) {
        String temp = yieldToLength(n - s.toString().length(), fill);
        return s.toString() + temp;
    }

    public static String padToRight(int n, char fill, Object s) {
        String temp = yieldToLength(n - s.toString().length(), fill);
        return temp + s.toString();
    }

    public static String padToLeft(int n, String fill, Object s) {
        String temp = yieldToLength(n - s.toString().length(), fill);
        return s.toString() + temp;
    }

    public static String padToRight(int n, String fill, Object s) {
        String temp = yieldToLength(n - s.toString().length(), fill);
        return temp + s.toString();
    }

    public static String padJustify(int n, char fill, Collection<Object> objs) {
        return padJustify(n, fill, objs.toArray());
    }

    public static String padJustify(int n, char fill, Object... objs) {
        if(objs.length < 2) {
            return padJustify(n, fill, "", objs[0], "");
        }
        StringBuilder sb = new StringBuilder();
        int totObjsLen = 0;
        for(Object obj : objs) {
            totObjsLen += obj.toString().length();
        }
        int numSpacers = objs.length - 1;
        String[] arr = new String[numSpacers + objs.length];
        int avgSpacerLen = (n - totObjsLen) / numSpacers;

        int index = 0;
        for (Object obj : Arrays.copyOfRange(objs, 0, objs.length-1)) {
            arr[index++] = obj.toString();
            arr[index++] = yieldToLength(avgSpacerLen, fill);
        }
        arr[index] = objs[objs.length-1].toString();

        int remaining = n - (totObjsLen + (avgSpacerLen * numSpacers));
        arr = fillRemainder(remaining, numSpacers, fill, arr);

        for(String str : arr) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String padCenter(int n, char fill, Collection<Object> objs) {
        return padCenter(n, fill, objs.toArray());
    }

    public static String padCenter(int n, char fill, Object... objs) {
        StringBuilder sb = new StringBuilder();
        int totObjsLen = 0;
        for(Object obj : objs) {
            totObjsLen += obj.toString().length();
        }

        int numSpacers = objs.length + 1;
        String[] arr = new String[numSpacers + objs.length];
        int avgPerObj = (n - totObjsLen) / numSpacers;

        int index = 0;
        arr[index++] = yieldToLength(avgPerObj, fill);
        for (Object obj : Arrays.copyOfRange(objs, 0, objs.length)) {
            arr[index++] = obj.toString();
            arr[index++] = yieldToLength(avgPerObj, fill);
        }

        int remaining = n - (totObjsLen + (avgPerObj * numSpacers));
        arr = fillRemainder(remaining, numSpacers, fill, arr);

        for(String str : arr) {
            sb.append(str);
        }
        return sb.toString();
    }

    private static String[] fillRemainder(int remaining, int numSlots, char fill, String[] arr) {
        if(numSlots % 2 == 0) {
            int i = 1;
            int j = arr.length-2;
            while(remaining > 0) {
                if (remaining % 2 == 0) {
                    arr[i] += fill;
                    i += 2;
                } else {
                    arr[j] += fill;
                    j -= 2;
                }
                remaining--;
            }
        } else {
            int i = (arr.length/2)+2;
            int j = (arr.length/2)-2;
            while(remaining > 0) {
                if (remaining % 2 == 0) {
                    arr[i] += fill;
                    i += 2;
                } else {
                    arr[j] += fill;
                    j -= 2;
                }
                remaining--;
            }
        }
        return arr;
    }

    public static void main(String[] args) {

        int len = 60;
        print(fgBlack.str());
        println(padToRight(len, "testing"));
        println(padToLeft(len, "test ing"));
        println(padToRight(len, '*', "test   ing"));
        println(padToLeft(len, '-', "t e s t i n g"));
        println(padJustify(len, ' ',"left", "right"));
        println(padJustify(len, '^', "l ef t", "r igh t"));
        println(yieldToLength(len, "123"));
        println(padJustify(len, ' ',"testing"));
        println(padJustify(len,'_',"testing"));

        String[] arr = new String[]{"0th", "1st", "2nd", "3rd", "4th", "5th", "6th",
                "7th", "8th", "9th", "10th", "11th", "12th", "13th", "14th", "15th", "16th"};
//        arr = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        for (int i = 0; i < arr.length; i++) {
            println(fgBlue, padJustify(len, '_', Arrays.copyOfRange(arr, 0, i + 1)));
            println(fgRed, padCenter(len, '_', Arrays.copyOfRange(arr, 0, i + 1)));
            println();
        }

//        for (int i = arr.length - 2; i > -1; i--) {
//            println(fgBlue, padJustify(len, '_', Arrays.copyOfRange(arr, 0, i + 1)));
//            println(fgRed, padCenter(len, '_', Arrays.copyOfRange(arr, 0, i + 1)));
//            println();
//        }
    }
}

