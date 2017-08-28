package Testing;

import Random.KnightsTour;
import Random.LargeProduct;
import Sorting.DualPivotQuickSort;
import Sorting.QuickSort;
import myUtils.MyGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Random.MersennePrimes.isMersenne;
import static Random.PrimeFactorization.isPerfectPower;
import static myUtils.ConsolePrinting.print;
import static myUtils.ConsolePrinting.println;
import static myUtils.Equivalence.equal;
import static myUtils.Primes.isPrime;

public class TestBench {

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
        List<Double> res1 = new ArrayList<>(results);
        println(res1);

        //test each each number under limit against the generated list
        List<Double> res2 = new ArrayList<>();
        for (i = 0; i <= lim; i++) {
            if (isPerfectPower(i)) {
                res2.add((double) i);
            }
        }
        println(res2);

        return equal(res1, res2);
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

    public static void mersennePrimeTest(int lim) {
        for (int i = 1; i < lim; i++) {
            if (isMersenne(i)) {
                print("found @ " + i + " ");
            }
        }
        println();
    }

    public static void testTest1(int lim) throws Exception {
        for (int i = 10; i < lim; i *= 10) {
            new MemUseTest(TestBench.class, "mersennePrimeTest").exe(i);
            new SYSTimeTest(TestBench.class, "mersennePrimeTest").exe(i);
            new CPUTimeTest(TestBench.class, "mersennePrimeTest").exe(i);
        }
    }

    public static void testTest2() throws Exception {
        for (int i = 100; i <= 1000000; i*=10) {
            new SYSTimeTest(TestBench.class, "primeTest").exe(i);
        }

        for (int i = 100; i <= 1000000; i*=10) {
            new SYSTimeTest(TestBench.class, "ppTest").exe(i);
        }

        for (int i = 1; i <= 1000; i*=10) {
            new SYSTimeTest(TestBench.class, "qsTest").exe(i, 1000);
        }
    }

    public static void main(String argv[]) throws Exception {

        new MemUseTest(KnightsTour.class, "solveKnightTour").exe();
        new SYSTimeTest(LargeProduct.class, "test1").exe();
        new CPUTimeTest(LargeProduct.class, "test2").exe();

        new SYSTimeTest(
                TestBench.class,
                "testTest1"
        ).exe(100000000);

        new CPUTimeTest(
                TestBench.class,
                "testTest2"
        ).exe();

    }
}