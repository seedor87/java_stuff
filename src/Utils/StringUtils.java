package Utils;
import Utils.Console.PrintableSpecial;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

import static Utils.Console.Special.*;

import static Utils.Console.Printing.*;

/**
 * This class contains some methods for string manipulation that are lacking in the String suite of tools.
 * Key:
 *      padCenter({1, 2, 3})    =  ..1..2..3...
 *      padJustify({1, 2, 3})   =  1....2.....3
 */
public class StringUtils {

    /**
     * Interface for extension of index-named-lambda pattern for ease of use in string split methods
     */
    public interface SplitLambda {
        int splitOn(String s, String delim);
    }

    public static SplitLambda firstIndex = String::indexOf;
    public static SplitLambda secondIndex = (String s, String delim) -> s.indexOf(delim, firstIndex.splitOn(s, delim)+1);
    public static SplitLambda thirdIndex = (String s, String delim) -> s.indexOf(delim, secondIndex.splitOn(s, delim)+1);
    public static SplitLambda fourthIndex = (String s, String delim) -> s.indexOf(delim, thirdIndex.splitOn(s, delim)+1);
    public static SplitLambda fifthIndex = (String s, String delim) -> s.indexOf(delim, fourthIndex.splitOn(s, delim)+1);
    public static SplitLambda sixthIndex = (String s, String delim) -> s.indexOf(delim, fifthIndex.splitOn(s, delim)+1);
    public static SplitLambda lastIndex = String::lastIndexOf;
    public static SplitLambda secondLastIndex = (String s, String delim) -> s.lastIndexOf(delim, lastIndex.splitOn(s, delim)-1);
    public static SplitLambda thirdLastIndex = (String s, String delim) -> s.lastIndexOf(delim, secondLastIndex.splitOn(s, delim)-1);
    public static SplitLambda fourthLastIndex = (String s, String delim) -> s.lastIndexOf(delim, thirdLastIndex.splitOn(s, delim)-1);
    public static SplitLambda fifthLastIndex = (String s, String delim) -> s.lastIndexOf(delim, fourthLastIndex.splitOn(s, delim)-1);
    public static SplitLambda sixthLastIndex = (String s, String delim) -> s.lastIndexOf(delim, fifthLastIndex.splitOn(s, delim)-1);

    /**
     * Method to generate and return string of default char (' ') to length, len
     */
    public static String padToLength(int len) {
        return padToLength(len, ' ');
    }

    /**
     * Method to generate and return string of specified char, fill, to length, len
     */
    public static String padToLength(int len, char fill) {
        return padToLength(len, "" + fill);
    }

    /**
     * Method to generate and return string of substring, fill, to length, len
     */
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

    /**
     * Method to generate and return left justified string of object, s, to length len.
     */
    public static String padToLeft(int len, Object s) {
        return padToLeft(len, ' ', s);
    }

    /**
     * Method to generate and return right justified string of object, s, to length len.
     */
    public static String padToRight(int len, Object s) {
        return padToRight(len, ' ', s);
    }

    /**
     * Method to generate and return left justified string of object, s, padded with char, fill, to length len.
     */
    public static String padToLeft(int len, char fill, Object s) {
        String temp = padToLength(len - s.toString().length(), fill);
        return s.toString() + temp;
    }

    /**
     * Method to generate and return right justified string of object, s, padded with char, fill, to length len.
     */
    public static String padToRight(int len, char fill, Object s) {
        return padToLength(len - s.toString().length(), fill) + s.toString();
    }

    /**
     * Method to generate and return left justified string of object, s, padded with string, fill, to length len.
     */
    public static String padToLeft(int len, String fill, Object s) {
        return s.toString() + padToLength(len - s.toString().length(), fill);
    }

    /**
     * Method to generate and return left justified string of object, s, padded with string, fill, to length len.
     */
    public static String padToRight(int len, String fill, Object s) {
        String temp = padToLength(len - s.toString().length(), fill);
        return temp + s.toString();
    }

    /**
     * Method to generate and return center justified string of Collection, objs, padded with char, fill, to length len.
     */
    public static String padJustify(int len, char fill, Collection<Object> objs) {
        return padJustify(len, fill, objs.toArray());
    }

    /**
     * Method to generate and return center justified string of Array, objs, padded with char, fill, to length len.
     */
    public static String padJustify(int len, char fill, Object... objs) {
        if(objs.length < 2) {
            return padJustify(len, fill, "", objs[0], "");
        }
        StringBuilder sb = new StringBuilder();
        int totalLenObjs = 0;
        for(Object obj : objs) {
            totalLenObjs += obj.toString().length();
        }
        int numSpacers = objs.length - 1;
        String[] arr = new String[numSpacers + objs.length];
        int avgSpacerLen = (len - totalLenObjs) / numSpacers;

        int index = 0;
        for (Object obj : Arrays.copyOfRange(objs, 0, objs.length-1)) {
            arr[index++] = obj.toString();
            arr[index++] = padToLength(avgSpacerLen, fill);
        }
        arr[index] = objs[objs.length-1].toString();

        int remaining = len - (totalLenObjs + (avgSpacerLen * numSpacers));
        arr = fillRemainder(remaining, numSpacers, fill, arr);

        for(String str : arr) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * Method to generate and return centered string of Collection, objs, padded with char, fill, to length len.
     */
    public static String padCenter(int len, char fill, Collection<Object> objs) {
        return padCenter(len, fill, objs.toArray());
    }

    /**
     * Method to generate and return centered string of Array, objs, padded with char, fill, to length len.
     */
    public static String padCenter(int len, char fill, Object... objs) {
        StringBuilder sb = new StringBuilder();
        int totalLenObjs = 0;
        for(Object obj : objs) {
            totalLenObjs += obj.toString().length();
        }

        int numSpacers = objs.length + 1;
        String[] arr = new String[numSpacers + objs.length];
        int avgPerObj = (len - totalLenObjs) / numSpacers;

        int index = 0;
        arr[index++] = padToLength(avgPerObj, fill);
        for (Object obj : Arrays.copyOfRange(objs, 0, objs.length)) {
            arr[index++] = obj.toString();
            arr[index++] = padToLength(avgPerObj, fill);
        }

        int remaining = len - (totalLenObjs + (avgPerObj * numSpacers));
        arr = fillRemainder(remaining, numSpacers, fill, arr);

        for(String str : arr) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * Method to populate a number of remaining slots, remaining, of a total number of slots, numSlots, of an Array, arr, padded with char, fill,
     */
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

    /**
     * Method to split and justify Array, objs, on default index, lastIndex, of string separator, to length, padding, and fill with char, fill
     */
    public static String splitAndJustifyOnSeparator(int padding, String separator, char fill, Object... params) {
        return splitAndJustifyOnSeparator(lastIndex, padding, separator, fill, params);
    }

    /**
     * Method to split and justify Array, objs, on specified index, index, of string separator, to length, padding, and fill with char, fill
     */
    public static String splitAndJustifyOnSeparator(SplitLambda index, int padding, String separator, char fill, Object... params) {
        int maxL = 0, maxR = 0, fulcrum = 0;
        String[] lefts = new String[params.length], rights = new String[params.length];
        for (int i = 0; i < params.length; i++) {
            fulcrum = index.splitOn(params[i].toString(), separator);
            String left = params[i].toString().substring(0, fulcrum);
            String right = params[i].toString().substring(fulcrum+separator.length());
            lefts[i] = left;
            rights[i] = right;
            if (left.length() > maxL) {
                maxL = left.length();
            }
            if (right.length() > maxR) {
                maxR = right.length();
            }
        }
        maxL += padding;
        maxR += padding;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for ( ; i < params.length-1; i++) {
            sb.append(padToLeft(maxL, fill, lefts[i]));
            sb.append(separator);
            sb.append(padToRight(maxR, fill, rights[i]));
            sb.append("\n");
        }
        sb.append(padToLeft(maxL, fill, lefts[i]));
        sb.append(separator);
        sb.append(padToRight(maxR, fill, rights[i]));
        return sb.toString();
    }

    /**
     * Method to split and center Array, objs, on default index, lastIndex, of string separator, to length, padding, and fill with char, fill
     */
    public static String splitAndCenterOnSeparator(int padding, String separator, char fill, Object... params) {
        return splitAndCenterOnSeparator(lastIndex, padding, separator, fill, params);
    }

    /**
     * Method to split and center Array, objs, on specified index, index, of string separator, to length, padding, and fill with char, fill
     */
    public static String splitAndCenterOnSeparator(SplitLambda index, int padding, String separator, char fill, Object... params) {
        int maxL = 0, maxR = 0, fulcrum = 0;
        String[] lefts = new String[params.length], rights = new String[params.length];
        for(int i = 0; i < params.length; i++) {
            fulcrum = index.splitOn(params[i].toString(), separator);
            String left = params[i].toString().substring(0, fulcrum);
            String right = params[i].toString().substring(fulcrum+separator.length());
            lefts[i] = left;
            rights[i] = right;
            if(left.length() > maxL) { maxL = left.length(); }
            if(right.length() > maxR) { maxR = right.length(); }
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for( ; i < params.length-1; i++) {
            sb.append(padToRight(maxL, fill, lefts[i]));
            sb.append(padToLength(padding, fill));
            sb.append(separator);
            sb.append(padToLength(padding, fill));
            sb.append(padToLeft(maxR, fill, rights[i]));
            sb.append("\n");
        }
        sb.append(padToRight(maxL, fill, lefts[i]));
        sb.append(padToLength(padding, fill));
        sb.append(separator);
        sb.append(padToLength(padding, fill));
        sb.append(padToLeft(maxR, fill, rights[i]));
        return sb.toString();
    }

    /**
     * Method to wrap the given objects, args (via toString), with the Special character, c, and ensure the string is terminated by a RESET char.
     */
    public static <T> String wrap(PrintableSpecial c, T... args) {
        StringBuffer sb = new StringBuffer();
        sb.append(c.toString());
        for (T elem: args) {
            sb.append(elem.toString());
        }
        sb.append(RESET);
        return sb.toString();
    }

    public static void main(String[] args) {

        Integer[] iArr = {1,2,3};
        println(FG_MAGENTA, iArr, padCenter(12, '.', iArr));
        println(FG_MAGENTA, iArr, padJustify(12, '.', iArr));

        int len = 60;
        Character[] cArr = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q'};
        IntStream.range(0, cArr.length)
                .forEach(i -> {
                    println(FG_BLUE, padJustify(len, '_', Arrays.copyOfRange(cArr, 0, i + 1)));
                    println(FG_RED, padCenter(len, '_', Arrays.copyOfRange(cArr, 0, i + 1)));
                });

        String[] sArr = new String[]{"0th", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th"};
        int from = 0, to = sArr.length;
        IntStream.range(from, to)
            .forEach(i ->
                println(FG_CYAN, padJustify(len, '-', Arrays.copyOfRange(sArr, 0, i + 1))
            ));
        IntStream.range(from, to)
            .map(i -> to - i + from -1)
            .forEach(i ->
                println(FG_CYAN, padJustify(len, '-', Arrays.copyOfRange(sArr, 0, i + 1))
            ));
        IntStream.range(from, to)
            .forEach(i ->
                    println(FG_YELLOW, padCenter(len, '-', Arrays.copyOfRange(sArr, 0, i + 1))
                    ));
        IntStream.range(from, to)
            .map(i -> to - i + from -1)
            .forEach(i ->
                    println(FG_YELLOW, padCenter(len, '-', Arrays.copyOfRange(sArr, 0, i + 1))
                    ));

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
        println(splitAndCenterOnSeparator(1,".", ' ',someDoubles));
        println(splitAndCenterOnSeparator(0,".", '0',someDoubles));


        String[] someChords = new String[]{
                "0 **** 0",
                "1 **** 1",
                "2 **** 2"
        };
        println(splitAndCenterOnSeparator(firstIndex, 1, "**", ' ', someChords));
        println(splitAndJustifyOnSeparator(secondIndex, 1, "**", ' ', someChords));
        println(splitAndCenterOnSeparator(thirdIndex, 1, "**", ' ', someChords));
        println(splitAndCenterOnSeparator((s, delim) -> (s.length()/2)-1, 1, "*", ' ', someChords));
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
        Assert.assertEquals("padToLengthTest",
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
