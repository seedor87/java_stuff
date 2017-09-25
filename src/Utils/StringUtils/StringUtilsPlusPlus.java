package Utils.StringUtils;

import static Utils.ConsolePrinting.println;
import static Utils.StringUtils.StringUtils.padToLeft;
import static Utils.StringUtils.StringUtils.padToRight;

public class StringUtilsPlusPlus {

    public static String splitAndJustifyOnSeparator(String separator, char fill, Object... params) {
        return splitAndJustifyOnSeparator(0, separator, fill, params);
    }

    public static String splitAndJustifyOnSeparator(int padding, String separator, char fill, Object... params) {
        int maxl = 0;
        int maxr = 0;
        String[] lefts = new String[params.length];
        String[] rights = new String[params.length];
        for(int i = 0; i < params.length; i++) {
            int div = params[i].toString().lastIndexOf(separator);
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
            sb.append(padToLeft(maxl, fill, lefts[i]));
            sb.append(separator);
            sb.append(padToRight(maxr, fill, rights[i]));
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String splitAndCenterOnSeparator(String separator, char fill, Object... params) {
        int maxl = 0;
        int maxr = 0;
        String[] lefts = new String[params.length];
        String[] rights = new String[params.length];
        for(int i = 0; i < params.length; i++) {
            int div = params[i].toString().lastIndexOf(separator);
            String left = params[i].toString().substring(0, div);
            String right = params[i].toString().substring(div+1);
            lefts[i] = left;
            rights[i] = right;
            if(left.length() > maxl) { maxl = left.length(); }
            if(right.length() > maxr) { maxr = right.length(); }
        }
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < params.length; i++) {
            sb.append(padToRight(maxl, fill, lefts[i]));
            sb.append(separator);
            sb.append(padToLeft(maxr, fill, rights[i]));
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        println(splitAndJustifyOnSeparator(3, "/", ' ',
                "some/abstract/path/to/a_file.py",
                "another/path/to/a_file.py",
                "yet/another/path/to/a_file.py",
                "a/path/to/my_file.py",
                "not/a/path/to/my_file.py"));

        println(splitAndCenterOnSeparator(".", '_',
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
