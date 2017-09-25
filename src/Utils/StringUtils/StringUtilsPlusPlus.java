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
        for (int i = 0; i < params.length; i++) {
            sb.append(padToLeft(maxl, fill, lefts[i]));
            sb.append(separator);
            sb.append(padToRight(maxr, fill, rights[i]));
            sb.append("\n");
        }
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
        for(int i = 0; i < params.length; i++) {
            sb.append(padToRight(maxl, fill, lefts[i]));
            sb.append(padToLength(padding, fill));
            sb.append(separator);
            sb.append(padToLength(padding, fill));
            sb.append(padToLeft(maxr, fill, rights[i]));
            sb.append("\n");
        }
        return sb.toString();
    }

    public interface SplitLambda {
        int splitOn(String s, String sep);
    }

    public static SplitLambda lastIndex = (String s, String sep) -> s.lastIndexOf(sep);
    public static SplitLambda firstIndex = (String s, String sep) -> s.indexOf(sep);
    public static SplitLambda secondIndex = (String s, String sep) -> s.indexOf(sep, firstIndex.splitOn(s, sep)+sep.length());
    public static SplitLambda thirdIndex = (String s, String sep) -> s.indexOf(sep, secondIndex.splitOn(s, sep)+sep.length());
    public static SplitLambda fourthIndex = (String s, String sep) -> s.indexOf(sep, thirdIndex.splitOn(s, sep)+sep.length());
    public static SplitLambda fifthIndex = (String s, String sep) -> s.indexOf(sep, fourthIndex.splitOn(s, sep)+sep.length());
    public static SplitLambda sixthIndex = (String s, String sep) -> s.indexOf(sep, fifthIndex.splitOn(s, sep)+sep.length());

    public static void main(String[] args) {

        String[] someFiles = new String[]{
                "some/path/to/a_file.py",
                "another/path/to/a_file.py",
                "yet/another/path/to/a_file.py",
                "a/path/to/my_file.py",
                "not/a/path/to/my_file.py"
        };

        println(splitAndCenterOnSeparator(
                3,
                "/",
                '.',
                someFiles));

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
                "test@gmail.om",
                "no.reply@domain.name.com"
        };

        println(splitAndJustifyOnSeparator(2, "@", '_', someEmailAddresses));
        println(splitAndCenterOnSeparator(2, "@", '_', someEmailAddresses));


        println(splitAndCenterOnSeparator(lastIndex, 1,".", ' ',
                12.09,
                3.59,
                1.3,
                0.75,
                .69,
                10.5,
                .1,
                999.999));

        println(splitAndCenterOnSeparator(lastIndex, 0,".", ' ',
                12.09,
                3.59,
                1.3,
                0.75,
                .69,
                10.5,
                .1,
                999.999));
    }
}
