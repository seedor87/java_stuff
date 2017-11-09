package Utils.StringUtils;

import static Utils.StringUtils.StringUtils.padToLeft;
import static Utils.ConsolePrinting.*;

public class StringUtilsRequiem {

    public static String replaceChar(String str, char oldCh) {
        return replaceChar(str, oldCh, '\0');
    }

    public static String replaceChar(String str, char oldCh, char newCh) {
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
        println(replaceChar("test\ntest", '\n', '-'));
    }
}
