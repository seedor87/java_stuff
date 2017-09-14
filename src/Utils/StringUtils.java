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

    public static String padRight(Object s, int n) {
        return String.format("%1$-" + n + "s", s.toString());
    }
    public static String padLeft(Object s, int n) {
        return String.format("%1$" + n + "s", s.toString());
    }
    public static String padCenter(Object l, Object r, int n) {
        return String.format(l + "%1$" + (n-l.toString().length()) + "s", r.toString());
    }
    public static String padLeftRight(Object s, int n) {
        return padLeftRight(s, n, ' ');
    }

    public static String padRight(Object s, int n, char fill) {
        String temp = genToLength(s.toString().length());
        return padRight(temp, n)
                .replace(' ', fill)
                .replace(temp, s.toString());
    }
    public static String padLeft(Object s, int n, char fill) {
        String temp = genToLength(s.toString().length());
        return padLeft(temp, n)
                .replace(' ', fill)
                .replace(temp, s.toString());
    }
    public static String padCenter(Object l, Object r, int n, char fill) {
        int clen = n - (l.toString().length() + r.toString().length());
        return l.toString() +
                genToLength(clen, fill) +
                r.toString();
    }
    public static String padLeftRight(Object s, int n, char fill) {
        int len = s.toString().length();
        int rside = (n-len) / 2;
        int lside = rside;
        if(n % len != 0) {
            lside += 1;
        }
        return genToLength(lside, fill) +
                s.toString() +
                genToLength(rside, fill);
    }

    public static void main(String[] args) {
        println(padLeft("testing", 20));
        println(padRight("test ing", 20));
        println(padLeft("test   ing", 20, '*'));
        println(padRight("t e s t i n g", 20, '-'));
        println(padCenter("left", "right", 20));
        println(padCenter("l ef t", "r igh t", 20, '^'));
        println(genToLength(20, "<^>"));
        println(padLeftRight("testing", 20));
        println(padLeftRight("testing", 20, '_'));
    }
}

