package Utils;
import java.util.Arrays;
import java.util.Collection;

import static Utils.ConsolePrinting.*;
public class StringUtils {

    public static String padToLength(int len) {
        return padToLength(len, ' ');
    }

    public static String padToLength(int n, char fill) {
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < n; i++) {
            ret.append(fill);
        }
        return ret.toString();
    }

    public static String padToLength(int len, String fill) {
        StringBuilder ret = new StringBuilder();
        while(ret.length() < len) {
           ret.append(fill);
            if(ret.length() >= len-fill.length()) {
                ret.append(fill.substring(0, len-ret.length()));
            }
        }
        return ret.toString();
    }

    public static String padToLeft(int len, Object s) {
        return padToLeft(len, ' ', s);
    }
    public static String padToRight(int len, Object s) {
        return padToRight(len, ' ', s);
    }

    public static String padToLeft(int len, char fill, Object s) {
        String temp = padToLength(len - s.toString().length(), fill);
        return s.toString() + temp;
    }

    public static String padToRight(int len, char fill, Object s) {
        String temp = padToLength(len - s.toString().length(), fill);
        return temp + s.toString();
    }

    public static String padToLeft(int len, String fill, Object s) {
        String temp = padToLength(len - s.toString().length(), fill);
        return s.toString() + temp;
    }

    public static String padToRight(int len, String fill, Object s) {
        String temp = padToLength(len - s.toString().length(), fill);
        return temp + s.toString();
    }

    public static String padJustify(int len, char fill, Collection<Object> objs) {
        return padJustify(len, fill, objs.toArray());
    }

    public static String padJustify(int len, char fill, Object... objs) {
        if(objs.length < 2) {
            return padJustify(len, fill, "", objs[0], "");
        }
        StringBuilder sb = new StringBuilder();
        int totObjsLen = 0;
        for(Object obj : objs) {
            totObjsLen += obj.toString().length();
        }
        int numSpacers = objs.length - 1;
        String[] arr = new String[numSpacers + objs.length];
        int avgSpacerLen = (len - totObjsLen) / numSpacers;

        int index = 0;
        for (Object obj : Arrays.copyOfRange(objs, 0, objs.length-1)) {
            arr[index++] = obj.toString();
            arr[index++] = padToLength(avgSpacerLen, fill);
        }
        arr[index] = objs[objs.length-1].toString();

        int remaining = len - (totObjsLen + (avgSpacerLen * numSpacers));
        arr = fillRemainder(remaining, numSpacers, fill, arr);

        for(String str : arr) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String padCenter(int len, char fill, Collection<Object> objs) {
        return padCenter(len, fill, objs.toArray());
    }

    public static String padCenter(int len, char fill, Object... objs) {
        StringBuilder sb = new StringBuilder();
        int totObjsLen = 0;
        for(Object obj : objs) {
            totObjsLen += obj.toString().length();
        }

        int numSpacers = objs.length + 1;
        String[] arr = new String[numSpacers + objs.length];
        int avgPerObj = (len - totObjsLen) / numSpacers;

        int index = 0;
        arr[index++] = padToLength(avgPerObj, fill);
        for (Object obj : Arrays.copyOfRange(objs, 0, objs.length)) {
            arr[index++] = obj.toString();
            arr[index++] = padToLength(avgPerObj, fill);
        }

        int remaining = len - (totObjsLen + (avgPerObj * numSpacers));
        arr = fillRemainder(remaining, numSpacers, fill, arr);

        for(String str : arr) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static String padAroundChar(int padding, String sep, Object... params) {
        int maxl = 0;
        int maxr = 0;
        String[] lefts = new String[params.length];
        String[] rights = new String[params.length];
        for(int i = 0; i < params.length; i++) {
            int div = params[i].toString().lastIndexOf(sep);
            String left = params[i].toString().substring(0, div);
            String right = params[i].toString().substring(div+1);
            lefts[i] = left;
            rights[i] = right;
            if(left.length() > maxl) { maxl = left.length(); }
            if(right.length() > maxr) { maxr = right.length(); }
        }
        maxl += padding;
        maxr += padding;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < params.length; i++) {
            sb.append(padToRight(maxl, ' ', lefts[i]));
            sb.append(sep);
            sb.append(padToLeft(maxr, ' ', rights[i]));
            sb.append("\n");
        }
        return sb.toString();

//        int div = s.lastIndexOf(sep);
//        if(div >= 0 && div <= s.length()) {
//            String left = s.substring(0, div);
//            String right = s.substring(div+1);
//            return padToLeft(l, ' ', left) +
//                    sep +
//                    padToRight(r, ' ', right);
//        }
//        throw new RuntimeException("delimiter not found");
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
        println(padToLeft(len, "__$__", "t e s t i n g"));
        println(padJustify(len, ' ',"left", "right"));
        println(padJustify(len, '^', "l ef t", "r igh t"));
        println(padToLength(len, "123"));
        println(padJustify(len, ' ',"testing"));
        println(padJustify(len,'_',"testing"));

        Character[] carr = new Character[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q'};
//        arr = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        for (int i = 0; i < carr.length; i++) {
            println(fgBlue, padJustify(len, '_', Arrays.copyOfRange(carr, 0, i + 1)));
            println(fgRed, padCenter(len, '_', Arrays.copyOfRange(carr, 0, i + 1)));
        }


        String[] sarr = new String[]{"0th","1st","2nd","3rd","4th","5th","6th","7th","8th","9th"};
        for (int i = 0; i < sarr.length; i++) {
            println(fgCyan, padJustify(len, '-', Arrays.copyOfRange(sarr, 0, i + 1)));
        }
        for (int i = sarr.length - 2; i > -1; i--) {
            println(fgCyan, padJustify(len, '-', Arrays.copyOfRange(sarr, 0, i + 1)));
        }

        for (int i = 0; i < sarr.length; i++) {
            println(fgYellow, padCenter(len, '-', Arrays.copyOfRange(sarr, 0, i + 1)));
        }
        for (int i = sarr.length - 2; i > -1; i--) {
            println(fgYellow, padCenter(len, '-', Arrays.copyOfRange(sarr, 0, i + 1)));
        }

        println(padAroundChar(3, "/",
                "some/abstract/path/to/a_file.py",
                "another/path/to/a_file.py",
                "yet/another/path/to/a_file.py",
                "a/path/to/my_file.py",
                "not/a/path/to/my_file.py"));
        println(padAroundChar(0, ".",
                "12.09",
                "3.59",
                "1.3",
                "0.75",
                ".69",
                "10.5",
                ".1"));
    }
}

