package Utils;

import static Utils.ConsolePrinting.println;

public class GCD {

    /**
     * Short hand recursive style to fins greatest common divisor
     * @param x first param
     * @param y second param
     * @return the greatest common divisor of x and y
     */
    public static int gcdRecur(int x, int y) {
        if (y == 0) {
            return x;
        }
        return gcdRecur(y, x % y);
    }

    /**
     * Euclidean method that relies only on subtraction
     * @param x first param
     * @param y second param
     * @return the greatest common divisor of x and y
     */
    public static int gcdEuclid(int x, int y) {
        if( x == 0 || y == 0) {
            return 0;
        }
        while(x != y) {
            while (x > y) {
                x = x - y;
            }
            while( y > x) {
                y = y - x;
            }
        }
        return x;
    }

    public static void main(String[] args) {

        println(gcdRecur(630, 135));
        println(gcdEuclid(630, 135));

    }
}


