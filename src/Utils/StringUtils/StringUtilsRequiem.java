package Utils.StringUtils;

import static Utils.StringUtils.StringUtils.padToLeft;
import static Utils.ConsolePrinting.*;

public class StringUtilsRequiem {

    public static String remove(String str, char oldCh) {
        return replace(str, oldCh, '\0');
    }

    public static String replace(String str, String subString, String newString) {
        return str.replace(subString, newString);
    }

    public static String replace(String str, char oldCh, char newCh) {
        StringBuilder sb = new StringBuilder();
        for(char ch: str.toCharArray()) {
            sb.append((ch != oldCh) ? ch : newCh);
        }
        return sb.toString();
    }

    public static String trim(String str, int len) {
        if(str.length() > len) {
            return padToLeft(len, '.', str.substring(0, len-3));
        }
        return padToLeft(len, ' ', str);
    }

    public static void main(String[] args) {

        println(replace("test\ntest", '\n', '-'));
        for (int i = 0; i < 256; i++) {
            System.out.println(i + " \u001B[" + i + "mText goes here\u001B[0m");
        }
    }
}
