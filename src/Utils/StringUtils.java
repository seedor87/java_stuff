package Utils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import static Utils.Console.Special.*;

import static Utils.Console.Printing.*;

public class StringUtils {

    public interface SplitLambda {
        int splitOn(String s, String sep);
    }

    public static SplitLambda firstIndex = (String s, String sep) -> s.indexOf(sep);
    public static SplitLambda secondIndex = (String s, String sep) -> s.indexOf(sep, firstIndex.splitOn(s, sep)+1);
    public static SplitLambda thirdIndex = (String s, String sep) -> s.indexOf(sep, secondIndex.splitOn(s, sep)+1);
    public static SplitLambda fourthIndex = (String s, String sep) -> s.indexOf(sep, thirdIndex.splitOn(s, sep)+1);
    public static SplitLambda fifthIndex = (String s, String sep) -> s.indexOf(sep, fourthIndex.splitOn(s, sep)+1);
    public static SplitLambda sixthIndex = (String s, String sep) -> s.indexOf(sep, fifthIndex.splitOn(s, sep)+1);
    public static SplitLambda lastIndex = (String s, String sep) -> s.lastIndexOf(sep);
    public static SplitLambda secondLastIndex = (String s, String sep) -> s.lastIndexOf(sep, lastIndex.splitOn(s, sep)-1);
    public static SplitLambda thirdLastIndex = (String s, String sep) -> s.lastIndexOf(sep, secondLastIndex.splitOn(s, sep)-1);
    public static SplitLambda fourthLastIndex = (String s, String sep) -> s.lastIndexOf(sep, thirdLastIndex.splitOn(s, sep)-1);
    public static SplitLambda fifthLastIndex = (String s, String sep) -> s.lastIndexOf(sep, fourthLastIndex.splitOn(s, sep)-1);
    public static SplitLambda sixthLastIndex = (String s, String sep) -> s.lastIndexOf(sep, fifthLastIndex.splitOn(s, sep)-1);

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

    public static String splitAndJustifyOnSeparator(int padding, String separator, char fill, Object... params) {
        return splitAndJustifyOnSeparator(lastIndex, padding, separator, fill, params);
    }

    public static String splitAndJustifyOnSeparator(SplitLambda lamb, int padding, String separator, char fill, Object... params) {
        int maxl = 0;
        int maxr = 0;
        String[] lefts = new String[params.length];
        String[] rights = new String[params.length];
        int fulcrum = 0;
        for (int i = 0; i < params.length; i++) {
            fulcrum = lamb.splitOn(params[i].toString(), separator);
            String left = params[i].toString().substring(0, fulcrum);
            String right = params[i].toString().substring(fulcrum+separator.length());
            lefts[i] = left;
            rights[i] = right;
            if (left.length() > maxl) {
                maxl = left.length();
            }
            if (right.length() > maxr) {
                maxr = right.length();
            }
        }
        maxl += padding;
        maxr += padding;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for ( ; i < params.length-1; i++) {
            sb.append(padToLeft(maxl, fill, lefts[i]));
            sb.append(separator);
            sb.append(padToRight(maxr, fill, rights[i]));
            sb.append("\n");
        }
        sb.append(padToLeft(maxl, fill, lefts[i]));
        sb.append(separator);
        sb.append(padToRight(maxr, fill, rights[i]));
        return sb.toString();
    }

    public static String splitAndCenterOnSeparator(int padding, String separator, char fill, Object... params) {
        return splitAndCenterOnSeparator(lastIndex, padding, separator, fill, params);
    }

    public static String splitAndCenterOnSeparator(SplitLambda lamb, int padding, String separator, char fill, Object... params) {
        int maxl = 0;
        int maxr = 0;
        String[] lefts = new String[params.length];
        String[] rights = new String[params.length];
        int fulcrum = 0;
        for(int i = 0; i < params.length; i++) {
            fulcrum = lamb.splitOn(params[i].toString(), separator);
            String left = params[i].toString().substring(0, fulcrum);
            String right = params[i].toString().substring(fulcrum+separator.length());
            lefts[i] = left;
            rights[i] = right;
            if(left.length() > maxl) { maxl = left.length(); }
            if(right.length() > maxr) { maxr = right.length(); }
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for( ; i < params.length-1; i++) {
            sb.append(padToRight(maxl, fill, lefts[i]));
            sb.append(padToLength(padding, fill));
            sb.append(separator);
            sb.append(padToLength(padding, fill));
            sb.append(padToLeft(maxr, fill, rights[i]));
            sb.append("\n");
        }
        sb.append(padToRight(maxl, fill, lefts[i]));
        sb.append(padToLength(padding, fill));
        sb.append(separator);
        sb.append(padToLength(padding, fill));
        sb.append(padToLeft(maxr, fill, rights[i]));
        return sb.toString();
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

        String[] someFiles = new String[]{
                "some/path/to/a_file.py",
                "another/path/to/a_file.py",
                "yet/another/path/to/a_file.py",
                "a/path/to/my_file.py",
                "not/a/path/to/my_file.py"
        };
        println(splitAndJustifyOnSeparator(
                firstIndex,
                3,
                "/",
                '.',
                someFiles));
        println(splitAndJustifyOnSeparator(
                secondIndex,
                3,
                "/",
                '.',
                someFiles));
        println(splitAndJustifyOnSeparator(
                thirdIndex,
                3,
                "/",
                '.',
                someFiles));

        println(padToLength(40, "="));

        println(splitAndCenterOnSeparator(
                3,
                "/",
                '.',
                someFiles));
        println(splitAndCenterOnSeparator(
                secondLastIndex,
                3,
                "/",
                '.',
                someFiles));
        println(splitAndCenterOnSeparator(
                thirdLastIndex,
                3,
                "/",
                '.',
                someFiles));
        String[] someMoreFiles = new String[]{
                "some//abstract//path//to//a_file.py",
                "another//path//to//a_file.py",
                "yet//another//path/to//a_file.py",
                "a/path/to//my_file.py",
                "not//a//path/to/my_file.py"
        };
        println(splitAndCenterOnSeparator(
                lastIndex,
                3,
                "//",
                '.',
                someMoreFiles));
        println(splitAndJustifyOnSeparator(
                firstIndex,
                3,
                "//",
                '.',
                someMoreFiles));


        String[] someEmailAddresses = new String[] {
                "me@hotmail.com",
                "you@apple.com",
                "someone@yahoo.com",
                "DBTest@gmail.om",
                "no.reply@domain.name.com"
        };
        println(splitAndJustifyOnSeparator(2, "@", '_', someEmailAddresses));
        println(splitAndCenterOnSeparator(2, "@", '_', someEmailAddresses));


        Double[] someDoubles = new Double[] {
                12.09,
                3.59,
                1.3,
                0.75,
                .69,
                10.5,
                .1,
                999.99
        };
        println(splitAndCenterOnSeparator(firstIndex, 1,".", ' ',someDoubles));
        println(splitAndCenterOnSeparator(firstIndex, 0,".", '0',someDoubles));


        String[] someMoreDoubles = new String[]{
                "0....0",
                "1....1",
                "2....2"
        };
        println(splitAndCenterOnSeparator(firstIndex, 1, "..", ' ', someMoreDoubles));
        println(splitAndJustifyOnSeparator(secondIndex, 1, "..", ' ', someMoreDoubles));
        println(splitAndCenterOnSeparator(thirdIndex, 1, "..", ' ', someMoreDoubles));
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
        String[] someFiles = new String[]{
                "some/path/to/a_file.py",
                "another/path/to/a_file.py",
                "yet/another/path/to/a_file.py",
                "a/path/to/my_file.py",
                "not/a/path/to/my_file.py"
        };
        Assert.assertEquals("splitAndJustifySeparator",
                "some....../...........path/to/a_file.py\n" +
                        "another.../...........path/to/a_file.py\n" +
                        "yet......./...another/path/to/a_file.py\n" +
                        "a........./..........path/to/my_file.py\n" +
                        "not......./........a/path/to/my_file.py",
                splitAndJustifyOnSeparator(
                        firstIndex,
                        3,
                        "/",
                        '.',
                        someFiles));
    }
}
