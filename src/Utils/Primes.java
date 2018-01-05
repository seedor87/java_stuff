package Utils;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StopWatches.AbstractStopwatch;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.awt.Toolkit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static Utils.Console.Printing.*;
import static Utils.Console.Special.FG_CYAN;

public class Primes {

    @Rule
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.SECONDS);

    /**
     * Simple method to wrap the use of a stop watch for ease of reuse
     */
    public static void test(int lim) {
        AbstractStopwatch stpw = new SYSStopwatch(TimeUnit.SECONDS);
        stpw.start();
        print("All Primes under, " + NumberFormat.getIntegerInstance().format(lim) + ": ");
        getAllPrimes(lim);
        stpw.stop();
        println(FG_CYAN,stpw);
    }

    /**
     * Utility method to get all prime numbers under the param lim
     */
    public static List<Integer> getAllPrimes(int lim) {
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 0; i <= lim; i++) {
            if (isPrime4(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    /**
     * Primary method to discovery primality of a number. Uses traditional exterior looping to solve
     */
    public static boolean isPrime(int n) {
        for (int i = 2; i <= (int) Math.sqrt(n); i++) {
            if( n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Secondary method to discover primality, uses recursion to solve
     */
    public static boolean isPrime2(int n) {
        return isPrime2(n, 2);
    }

    /**
     * Helper method of isPrime2. For use only in the recursive implementation of isPrime2.
     */
    private static boolean isPrime2(int n, int i) {
        if (i <= (int) Math.sqrt(n)) {
            return (n % i != 0 && isPrime2(n, i+1));
        }
        return true;
    }

    /**
     * Tertiary method to discover primality, uses NON-PARALLEL stream to solve
     */
    public static boolean isPrime3(int n) {
        return IntStream
                .rangeClosed(2, (int) Math.sqrt(n))
                .noneMatch(i -> n % i == 0);
    }

    /**
     * Quaternary method to discover primality, uses the primeFactors method to generate a list of factors.
     * If this list is of size 3 or greater ? false : true
     */
    public static boolean isPrime4(int n) {
        return (primeFactors(n).size() < 2);
    }

    /**
     * Stream based method to yield prime factors of a param, n
     */
    public static IntStream primeFactorsStream(final int n) {
        return IntStream.range(2, n)
                .filter(x -> n % x == 0)
                .mapToObj(x -> IntStream.concat(IntStream.of(x), primeFactorsStream(n / x)))
                .findFirst()
                .orElse(IntStream.of(n));
    }

    /**
     * List based method to yield prime factors of param, n
     */
    public static List<Integer> primeFactors(int n) {
        return primeFactorsStream(n).mapToObj(Integer::new).collect(Collectors.toList());

//        List<Integer> ret = new ArrayList<>();
//        for (int i = 2; i <= n; i++) {
//            while (n % i == 0) {
//                ret.add(i);
//                n /= i;
//            }
//        }
//        return ret;
    }

    /**
     * Method to determine Mersenne primality of number, n
     * where isMersenne(x) = { if x is a prime number of the form x = (2^n) - 1 ? true : false}
     */
    public static boolean isMersenne(int n) {
        int i = 0;
        if (isPrime(n)) {
            while(Math.pow(2, i) <= n+1) {
                if (Math.pow(2, i) -1 == n) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    /**
     * Special method to test isMersenne method
     */
    public static void mersennePrimeTest(int lim) {
        for (int i = 1; i < lim; i++) {
            if (isMersenne(i)) {
                println("found @ " + i + " ");
            }
        }
    }


    /**
     * JUnit test to validate success of various isPrime methods
     */
    @Test
    public void test() {
        IntStream
                .range(2, 1000000)
                .forEach(i -> {
                    boolean res_one, res_two, res_three, res_four;
                    res_one = isPrime(i);
                    res_two = isPrime2(i);
                    res_three = isPrime3(i);
                    res_four = isPrime4(i);
                    Assert.assertEquals(res_one, res_two);
                    Assert.assertEquals(res_one, res_three);
                    Assert.assertEquals(res_one, res_four);
                });
    }

    /**
     * Main method
     */
    public static void main(String[] args) {

//        mersennePrimeTest(1000000);

        test(100000);
        test(1000000);
        test(10000000);
        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.beep();
    }
}
