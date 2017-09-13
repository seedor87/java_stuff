package Utils;
import static Utils.ConsolePrinting.*;
public class StringUtils {

    public static String genToLength(int n) {
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < n; i++) {
            ret.append('_');
        }
        return ret.toString();
    }

    public static String genToLength(int n, char fill) {
        StringBuilder ret = new StringBuilder();
        for(int i = 0; i < n; i++) {
            ret.append(fill);
        }
        return ret.toString();
    }

    public static String genToLength(int n, String fill) {
        StringBuilder ret = new StringBuilder();
        while(ret.length() < n) {
           ret.append(fill);
            if(ret.length() >= n-fill.length()) {
                ret.append(fill.substring(0, n-ret.length()));
            }
        }
        return ret.toString();
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }
    public static String padCenter(String l, String r, int n) {
        return String.format(l + "%1$" + (n-l.length()) + "s", r);
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

    public static String padCenter(Object l, Object r, int n, char fill) {
        String ltemp = genToLength(l.toString().length(), '0');
        String rtemp = genToLength(r.toString().length(), '1');
        return padCenter(ltemp, rtemp, n)
                .replace(' ', fill)
                .replace(ltemp, l.toString())
                .replace(rtemp, r.toString());
    }

    public static void main(String[] args) {
        println(padLeft("testing", 20));
        println(padRight("test ing", 20));
        println(padLeft("test   ing", 20, '*'));
        println(padRight("t e s t i n g", 20, '-'));
        println(padCenter("left", "right", 20));
        println(padCenter("l ef t", "r igh t", 20, '^'));
        println(genToLength(20, "<^>"));
    }
}

