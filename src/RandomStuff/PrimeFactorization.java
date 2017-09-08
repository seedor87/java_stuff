package RandomStuff;

import java.util.ArrayList;
import java.util.List;

import static Utils.ConsolePrinting.*;
import NewTesting.NewSYSTimeTest;
import Utils.Collections.Tuple;

public class PrimeFactorization<T extends Object> extends NewSYSTimeTest<T> {

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

    public static <T extends Comparable<T>> T max(List<T> arr) {
        T max = arr.get(0);
        for (T elem : arr.subList(1, arr.size())) {
            if (elem.compareTo(max) > 0) {
                max = elem;
            }
        }
        return max;
    }

    public static <T extends Comparable<T>> T min(List<T> arr) {
        T min = arr.get(0);
        for (T elem : arr.subList(1, arr.size())) {
            if (elem.compareTo(min) < 0) {
                min = elem;
            }
        }
        return min;
    }

    public static boolean isPrime2(int n) {
        return (primeFactors(n).size() < 2);
    }

    public static boolean isGeekNumber(int n) {
        List<Integer> factors = primeFactors(n);
        int max = max(factors);
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
        printlnDelim(" + ", params);
        return new Tuple();
    }

    public static void main(String[] args) {
        PrimeFactorization pf = new PrimeFactorization();
        println(pf.test(new String[]{"1", "2", "3"}));
    }
}
