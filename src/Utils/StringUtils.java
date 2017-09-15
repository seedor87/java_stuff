package Utils;
import java.util.Arrays;
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

    public static String padContinuous(int n, char fill, Object... objs) {
        if(objs.length < 2) {
            return padContinuous(n, fill, "", objs[0], "");
        }
        StringBuilder sb = new StringBuilder();
        int totObjsLen = 0;
        for(Object obj : objs) {
            totObjsLen += obj.toString().length();
        }
        int numSpacers = objs.length -1;
        int avgSpacerLen = (n - totObjsLen) / numSpacers;
        int index = 0;
        String[] arr = new String[numSpacers + objs.length];
        for (Object obj : Arrays.copyOfRange(objs, 0, objs.length-1)) {
            arr[index] = obj.toString();
            index++;
            arr[index] = yieldToLength(avgSpacerLen, fill);
            index++;
        }
        arr[index] = objs[objs.length-1].toString();

        int remaining = n - (totObjsLen + (avgSpacerLen * numSpacers));
        if(numSpacers % 2 == 0) {
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
            int i = arr.length/2+2;
            int j = arr.length/2-2;
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
        for(String str : arr) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int len = 50;

        print(fgBlack.str());
        println(padToRight(len, "testing"));
        println(padToLeft(len, "test ing"));
        println(padToRight(len, '*', "test   ing"));
        println(padToLeft(len, '-', "t e s t i n g"));
        println(padContinuous(len, ' ',"left", "right"));
        println(padContinuous(len, '^', "l ef t", "r igh t"));
        println(yieldToLength(len, "123"));
        println(padContinuous(len, ' ',"testing"));
        println(padContinuous(len,'_',"testing"));

        println(fgBlue);
        String q;
        Scanner s = new Scanner(System.in);
        do {
            String[] arr = new String[]{"0th", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th"};
//            String[] arr = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
            for (int i = 0; i < arr.length; i++) {
                println(padContinuous(len, '_', Arrays.copyOfRange(arr, 0, i + 1)));
            }
            for (int i = arr.length - 2; i > -1; i--) {
                println(padContinuous(len, '_', Arrays.copyOfRange(arr, 0, i + 1)));
            }
            q = s.nextLine();
            len++;
        } while(!q.equals("q"));
    }
}

