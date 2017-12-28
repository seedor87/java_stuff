package Utils;

import Utils.Timers.AbstractStopwatch;
import Utils.Timers.SYSStopwatch;
import Utils.Timers.TimeUnit;

import static Utils.Console.Printing.println;

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
        if( x <= 0 || y <= 0) {
            return 0;
        }
        while (x != y) {
            while (x > y) {
                x = x - y;
            }
            while (y > x) {
                y = y - x;
            }
        }
        return x;
    }

    public static int fac(int n) {
        if(n-1 <= 0) {
            return 1;
        }
        return n * fac(n-1);
    }

    public static int fib(int n) {
        if (n-1 <= 0) {
            return 1;
        }
        return fib(n-1) + fib(n-2);
    }

    public static void main(String[] args) {

        for(int i = 0 ; i < 20; i++) {
            println(fib(i));
        }


        AbstractStopwatch timer = new SYSStopwatch(TimeUnit.MILLI);
        if(!true) {
            timer. start();
            println(gcdRecur(1, 1234567890));
            timer.stop();
            println(timer);
        } else {
            timer.start();
            println(gcdEuclid(1, 1234567890));
            timer.stop();
            println(timer);
        }
    }
}


