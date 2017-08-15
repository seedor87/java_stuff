package Random;

import Utils.ConsolePrinting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrineFactorization {

    public static void geekTest(int lim) {
        for (int i = 2; i < lim; i++) {
            if (isPrime2(i)) {
                ConsolePrinting.println("Prime: ");
            } else if(isGeekNumber(i)) {
                ConsolePrinting.println("GEEK :" + i);
            } else {
                ConsolePrinting.println("Neither...");
            }
            ConsolePrinting.println("i: " + i);
            ConsolePrinting.println(primeFactors(i));
            ConsolePrinting.println();
        }
    }

    public static void perfectPowerTest(int lim) {
        for (int i = 2; i < lim; i++) {
            if (isPerfectPower(i)) {
                ConsolePrinting.println("PP :" + i);
                ConsolePrinting.println("i: " + i);
                ConsolePrinting.println(primeFactors(i));
                ConsolePrinting.println();
            }
        }
    }

    public static void smithTest(int lim) {
        for (int i = 2; i < lim; i++) {
            if (isSmithNumber(i) && !isPrime2(i)) {
                ConsolePrinting.println("Smith :" + i);
                ConsolePrinting.println("i: " + i);
                ConsolePrinting.print(sumDig(i));
                ConsolePrinting.print(" == ");
                ConsolePrinting.print(sum(primeFactors(i)));
                ConsolePrinting.print(" ");
                ConsolePrinting.print(primeFactors(i));
                ConsolePrinting.println();
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

    public static int sum(List<Integer> arr) {
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
        return sumDig(n) == sum(primeFactors(n));
    }

    //TODO fix this one its bad and we can do it better
    public static boolean isPerfectPower(int n) {
        List<Integer> factors = primeFactors(n);
        int last = factors.get(0);
        for (int elem : primeFactors(n)) {
            if (elem != last) {
                return false;
            }
        }
        return true;
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

    public static void main(String[] args) throws IOException{
        perfectPowerTest(1000);
    }
}
