package Utils;

import static Utils.ConsolePrinting.println;

public class GCD {

    public static int gcd(int x, int y) {
        if (y == 0) {
            return x;
        }
        return gcd(y, x % y);
    }

    public static void main(String[] args) {
        println(gcd(20, 16));
    }
}


