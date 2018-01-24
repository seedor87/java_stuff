package Utils;

import TestingUtils.JUnitTesting.TimedRule.Testable;
import TestingUtils.JUnitTesting.TimedRule.TimeableClass;
import org.junit.Test;

import static Utils.Console.Printing.println;

public class GCD extends TimeableClass implements Testable {

    /**
     * Short hand recursive style to fins greatest common divisor
     * @param x first param
     * @param y second param
     * @return the greatest common divisor of x and y
     */
    public static int gcdRecur(int x, int y) {
        return (y == 0) ? x : gcdRecur(y, x % y);
    }

    /**
     * Euclidean method that relies only on subtraction
     * @param x first param
     * @param y second param
     * @return the greatest common divisor of x and y
     */
    public static int gcdEuclid(int x, int y) {
        if (x > 0 || y > 0) {
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
        return 0;
    }

    public static int fac(int n) {
        return (n-1 <=0) ? 1 : n * fac(n-1);
    }

    public static int fib(int n) {
        return (n-1 <= 0) ? 1 : fib(n-1) + fib(n-2);
    }

    @Test
    public void test() {
        println(fib(4));
        println(fac(4));
        println(gcdRecur(1, 1234567890));
        println(gcdEuclid(1, 1234567890));
    }
}


