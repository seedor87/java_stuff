package Testing;

import Sorting.DualPivotQuickSort;
import Sorting.QuickSort;
import myUtils.MyGenerator;
import myUtils.Tuple;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import static Random.PrimeFactorization.isPerfectPower;
import static myUtils.ConsolePrinting.println;
import static myUtils.Equivalence.equal;
import static myUtils.Primes.isPrime;

public class Tests<T extends Object> {

    public static void qsTest(Integer len, Integer max) {
        Integer[] itest1 = MyGenerator.randomInts(len, max);
        Character[] ctest1 = MyGenerator.randomChars(len);
        long startTime, endTime;
        double duration;

        startTime = System.nanoTime();
        QuickSort.quickSort(itest1);
        println(itest1);
        QuickSort.quickSort(ctest1);
        println(ctest1);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000000.0;
        println("Runtime: " + duration + "s");

        startTime = System.nanoTime();
        DualPivotQuickSort.quickSort(itest1);
        println(itest1);
        DualPivotQuickSort.quickSort(ctest1);
        println(ctest1);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000000.0;
        println("Runtime: " + duration + "s");
    }

    public static boolean ppTest(int lim) {
        int i, j;
        double result;
        i = 2;
        double thresh = Math.pow(lim, 0.5);
        Set<Double> results = new HashSet<>();
        //generate all perfect powers
        while(i <= thresh) {
            j = 2;
            while (j <= thresh) {
                result = Math.pow(i, j);
                if(result > lim) {
                    break;
                }
                results.add(result);
                j++;
            }
            i++;
        }
        List<Double> res1 = new ArrayList<>(results);
        println(res1);

        //test each each number under limit against the generated list
        List<Double> res2 = new ArrayList<>();
        for (i =0; i <= lim; i++) {
            if(isPerfectPower(i)) {
                res2.add((double) i);
            }
        }
        println(res2);

        return equal(res1,res2);
    }

    public static List<Integer> primeTest(int lim) {
        List primes = new ArrayList<>();
        for (int j = 0; j < lim; j++) {
            if (isPrime(j)) {
                primes.add(j);
            }
        }
        return primes;
    }
}
