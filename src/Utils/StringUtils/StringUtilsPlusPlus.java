package Utils.StringUtils;

import static Utils.ConsolePrinting.println;
import static Utils.StringUtils.StringUtils.padToLeft;
import static Utils.StringUtils.StringUtils.padToLength;
import static Utils.StringUtils.StringUtils.padToRight;

public class StringUtilsPlusPlus {

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


    public static void main(String[] args) {

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


        String[] someMoreDoubles = new String[]{
                "0....0",
                "1....1",
                "2....2"
        };
        println(splitAndCenterOnSeparator(firstIndex, 1, "..", ' ', someMoreDoubles));
        println(splitAndJustifyOnSeparator(secondIndex, 1, "..", ' ', someMoreDoubles));
        println(splitAndCenterOnSeparator(thirdIndex, 1, "..", ' ', someMoreDoubles));
    }
}
