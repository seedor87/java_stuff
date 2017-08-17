import Sorting.DualPivotQuickSort;
import Sorting.QuickSort;
import myUtils.ConsolePrinting;
import myUtils.MyGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static Random.PrimeFactorization.isPerfectPower;
import static myUtils.Equivalence.equal;
import static myUtils.Primes.isPrime;

public class TestBench {

    static long startTime, endTime;
    static double duration;

    public static class test implements Command {
        public void execute(int data) {
            primeTest(data);
        }
    }

    public static void timeIt(Command test, int data) {
        startTime = System.nanoTime();
        test.execute(data);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000000.0;
        ConsolePrinting.println("Runtime: " + duration + "s");
    }


    private static void qsTesting() {
        Integer[] itest1 = MyGenerator.randomInts(100000, 100000);
        Character[] ctest1 = MyGenerator.randomChars(100000);
        long startTime, endTime;
        double duration;

        startTime = System.nanoTime();
        QuickSort.quickSort(itest1);
        ConsolePrinting.println(itest1);
        QuickSort.quickSort(ctest1);
        ConsolePrinting.println(ctest1);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000000.0;
        ConsolePrinting.println("Runtime: " + duration + "s");

        startTime = System.nanoTime();
        DualPivotQuickSort.quickSort(itest1);
        ConsolePrinting.println(itest1);
        DualPivotQuickSort.quickSort(ctest1);
        ConsolePrinting.println(ctest1);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000000.0;
        ConsolePrinting.println("Runtime: " + duration + "s");
    }

    public static void ppTesting() {
        int i, j, lim;
        double result;
        lim = 1000;
        i = 2;
        double thresh = Math.pow(lim, 0.5);
        Set<Double> results = new HashSet<>();

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
        ConsolePrinting.println(res1);
        List<Double> res2 = new ArrayList<>();
        for (i =0; i <= lim; i++) {
            if(isPerfectPower(i)) {
                res2.add((double) i);
            }
        }
        ConsolePrinting.println(res2);
        ConsolePrinting.println(equal(res1,res2));
    }

    public static void primeTest(int lim) {
        List primes = new ArrayList<>();
        for (int j = 0; j < lim; j++) {
            if (isPrime(j)) {
                primes.add(j);
            }
        }
        ConsolePrinting.print("All Primes: ");
        ConsolePrinting.println(primes);
    }


    public static void main(String[] args) {
        timeIt(new TestBench.test(), 1000);
        timeIt(new TestBench.test(), 10000);
        timeIt(new TestBench.test(), 100000);
        timeIt(new TestBench.test(), 1000000);
        timeIt(new TestBench.test(), 10000000);
        timeIt(new TestBench.test(), 100000000);
    }
}
