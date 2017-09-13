package Utils;
import static Utils.ConsolePrinting.*;
public class StringUtils {

    public static String genToLength(int n) {
        String ret = "";
        for(int i = 0; i < n; i++) {
            ret += "_";
        }
        return ret;
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public static String padRight(String s, int n, char fill) {
        String temp = genToLength(s.length());
        return padRight(temp, n)
                .replace(' ', fill)
                .replace(temp, s);
    }

    public static String padLeft(String s, int n, char fill) {
        String temp = genToLength(s.length());
        return padLeft(temp, n)
                .replace(' ', fill)
                .replace(temp, s);
    }

    public static void main(String[] args) {
        println(padLeft("testing", 20));
        println(padRight("test ing", 20));
        println(padLeft("test   ing", 20, '*'));
        println(padRight("t e s t i n g", 20, '@'));
    }
}

