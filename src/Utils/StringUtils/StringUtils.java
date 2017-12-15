package Utils.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static Utils.ConsolePrinting.*;

public class StringUtils {

    public static String padToLength(int len) {
        return padToLength(len, ' ');
    }

    public static String padToLength(int len, char fill) {
        return padToLength(len, "" + fill);
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

    @Test
    public void testPaddingUtils() {
        int len = 30;
        Assert.assertEquals("padToRightTest",
                "                       testing",
                padToRight(len, "testing"));
        Assert.assertEquals("padToLeftTest",
                "t e s t i n g__$____$____$____",
                padToLeft(len, "__$__", "t e s t i n g"));
        Assert.assertEquals("padJustifyTest",
                "           testing            ",
                padJustify(len, ' ', "testing"));
        Assert.assertEquals("padJustifyTest",
                "l ef t^^^^^^^^^^^^^^^^^r igh t",
                padJustify(len, '^', "l ef t", "r igh t"));
        Assert.assertEquals("pasToLengthTest",
                "123412341234123412341234123412",
                padToLength(len, "1234"));

    }

    public static void main(String[] args) {

        int len = 60;
        print(FG_BLUE);

        Character[] carr = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q'};
//        arr = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        for (int i = 0; i < carr.length; i++) {
            println(FG_BLUE, padJustify(len, '_', Arrays.copyOfRange(carr, 0, i + 1)));
            println(FG_RED, padCenter(len, '_', Arrays.copyOfRange(carr, 0, i + 1)));
        }

        String[] sarr = new String[]{"0th", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th"};
        for (int i = 0; i < sarr.length; i++) {
            println(FG_CYAN, padJustify(len, '-', Arrays.copyOfRange(sarr, 0, i + 1)));
        }
        for (int i = sarr.length - 2; i > -1; i--) {
            println(FG_CYAN, padJustify(len, '-', Arrays.copyOfRange(sarr, 0, i + 1)));
        }

        for (int i = 0; i < sarr.length; i++) {
            println(FG_YELLOW, padCenter(len, '-', Arrays.copyOfRange(sarr, 0, i + 1)));
        }
        for (int i = sarr.length - 2; i > -1; i--) {
            println(FG_YELLOW, padCenter(len, '-', Arrays.copyOfRange(sarr, 0, i + 1)));
        }
    }
}
