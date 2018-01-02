package RandomStuff.SpecialNumbers;

import java.util.*;
import java.util.stream.IntStream;

import static Utils.Console.Printing.*;
import static Utils.Primes.isPrime;
import static Utils.Primes.primeFactors;

import TestingUtils.ObsoleteTesting.NewSYSStopwatchTest;
import Utils.Collections.OldTuple.Tuple;
import Utils.Console.Printing;

public class SpecialNumbers<T extends Object> extends NewSYSStopwatchTest<T> {

    public static void geekTest(int lim) {
        for (int i = 2; i < lim; i++) {
            if (isPrime(i)) {
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
            if (isSmithNumber(i) && !isPrime(i)) {
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
        return sumDig(n, 0);
    }

    public static int sumDig(int n, int  i) {
        if (n > 0) {
            i += n % 10;
            n /= 10;
            return sumDig(n, i);
        }
        return i;

//        int i = 0;
//        while(n > 0) {
//            i += n % 10;
//            n /= 10;
//        }
//        return i;
    }

    public static int sumAll(List<Integer> arr) {
        int total = 0;
        for (Integer elem : arr) {
            total += sumDig(elem);
        }
        return total;
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

    public static Collection<Double> generatePerfectNumbers(int lim) {
        int i = 2, j;
        double result, thresh = Math.pow(lim, 0.5);
        Set<Double> results = new HashSet<>();
        while (i <= thresh) {
            j = 2;
            while (j <= thresh) {
                result = Math.pow(i, j);
                if (result > lim) {
                    break;
                }
                results.add(result);
                j++;
            }
            i++;
        }
        return results;
    }

    public static List findPerfectNumbers(int lim) {
        List<Double> res = new ArrayList<>();
        for (int i = 0; i <= lim; i++) {
            if (isPerfectPower(i)) {
                res.add((double) i);
            }
        }
        return res;
    }

    public static void isPerfectNumberTest(int lim) {
        for (int i = 2; i < lim; i++) {
            if (isPerfectNumber(i)) {
                println("\tDude Perfect :" + i);
            }
        }
    }

    public static int sum(List<Integer> arr) {
        int total = 0;
        for (Integer elem : arr) {
            total += elem;
        }
        return total;
    }

    public static boolean isPerfectNumber(int n) {
        return sum(allFactors(n)) == n;
    }

    public static List<Integer> allFactors(int n) {
        List<Integer> ret = new ArrayList<>();
        ret.add(1);
        for (int i = 2; i*i < n; i++) {
            if(n % i == 0) {
                ret.add(i);
                ret.add(n/i);
            }
        }
        println(sum(ret));
        return ret;
    }

    public static long fermatNumber(int p) {
        return (long) Math.pow(2, Math.pow(2, p)) + 1;
    }


    @Override
    public Tuple runThis(Object... params) {
        return null;
    }

    public static void main(String[] args) {

//        isPerfectPowerTest(1000);

//        println(sumDig(1010));

//        isPerfectNumberTest(1000);

//        IntStream.range(0, 7)
//                .forEach(i -> Printing.println(fermatNumber(i)));

        geekTest(1000);
    }

}
