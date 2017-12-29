package RandomStuff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Utils.Console.Printing.*;
import TestingUtils.ObsoleteTesting.NewSYSStopwatchTest;
import Utils.Collections.OldTuple.Tuple;

public class PrimeFactorization<T extends Object> extends NewSYSStopwatchTest<T> {

    public static void geekTest(int lim) {
        for (int i = 2; i < lim; i++) {
            if (isPrime2(i)) {
                println("Prime: ");
            } else if(isGeekNumber(i)) {
                println("GEEK :" + i);
            } else {
                println("Neither...");
            }
            println("i: " + i);
            println(primeFactors(i));
            println();
        }
    }

    public static void isPerfectPowerTest(int lim) {
        for (int i = 2; i < lim; i++) {
            if (isPerfectPower(i)) {
                println("PP :" + i);
                println("i: " + i);
                println(primeFactors(i));
                println();
            }
        }
    }

    public static void smithTest(int lim) {
        for (int i = 2; i < lim; i++) {
            if (isSmithNumber(i) && !isPrime2(i)) {
                println("Smith :" + i);
                println("i: " + i);
                print(sumDig(i));
                print(" == ");
                print(sumAll(primeFactors(i)));
                print(" ");
                print(primeFactors(i));
                println();
            }
        }
    }

//    public static int sumDig(int n, int tot) {
//        if (n > 0) {
//            tot += n % 10;
//            return sumDig(n/10, tot) % 10;
//        }
//        return tot;
//    }
//
//    public static int sumDig(int n) {
//        if(n > 0) {
//            return sumDig(n/10, 0);
//        }
//        return 0;
//
////        while(n > 0) {
////            total += n % 10;
////            n /= 10;
////        }
////        return total;
//    }

    public static int sumDig(int n) {
        int total = 0;
        while(n > 0) {
            total += n % 10;
            n /= 10;
        }
        return total;
    }

    public static int sumAll(List<Integer> arr) {
        int total = 0;
        for (Integer elem : arr) {
            total += sumDig(elem);
        }
        return total;
    }

    public static boolean isPrime2(int n) {
        return (primeFactors(n).size() < 2);
    }

    public static boolean isGeekNumber(int n) {
        List<Integer> factors = primeFactors(n);
        int max = Collections.max(factors);
        int[] counts = new int[max+1];
        for (int elem : factors) {
            counts[elem]++;
            if (counts[elem] > 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSmithNumber(int n) {
        return sumDig(n) == sumAll(primeFactors(n));
    }

    public static boolean isPerfectPower(int n) {
        for (int i = 2; i*i <= n; i++) {
            int temp = n;
            while (temp % i == 0) {
                temp /= i;
            }
            if(temp == 1) {
                return true;
            }
        }
        return false;
    }



    public static List<Integer> primeFactors(int n) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            while (n % i == 0) {
                ret.add(i);
                n /= i;
            }
        }
        return ret;
    }
    @Override
    public Tuple runThis(Object... params) {
        return null;
    }

    public static void main(String[] args) {

//        isPerfectPowerTest(1000);

//        println(sumDig(1010));

        geekTest(1000);
    }

}
