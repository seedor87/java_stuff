package Testing;

import RandomStuff.MersennePrimes;
import RandomStuff.PerfectNumbers;
import Sorting.DualPivotQuickSort;
import Sorting.QuickSort;
import Utils.Equivocation;
import Utils.Primes;
import Utils.Collections.Tuple;

import java.util.List;

import static Utils.ConsolePrinting.*;
import static Utils.ConsolePrinting.printlnDelim;

public class TestBench {

    public static void qsTest(Integer len, Integer max) {

        for(int i = 10; i < len; i*=10) {
            try {
                print("DP Sort: ");
                new SYSStopwatchTest(DualPivotQuickSort.class, "DBTest").exe(i,max);
                print("Reg qs: ");
                new SYSStopwatchTest(QuickSort.class, "DBTest").exe(i,max);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean ppTest(int lim) {
        boolean result = false;
        try {
            List<Double> res1 = (List) new SYSStopwatchTest(PerfectNumbers.class, "generatePerfectNumbers").exe(lim).get(0);
            List<Double> res2 = (List) new SYSStopwatchTest(PerfectNumbers.class, "findPerfectNumbers").exe(lim).get(0);
            result = Equivocation.equal(res1, res2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static List<Integer> primeTest(int lim) {
        Tuple result;
        try {
            result = new CPUStopwatchTest(Primes.class, "getAllPrimes").exe(lim);
            return (List) result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void mersennePrimeTest(int lim) {
        try {
            new CPUStopwatchTest(MersennePrimes.class, "mersennePrimeTest").exe(lim);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testTest1(int lim) throws Exception {
        for (int i = 10; i < lim; i *= 10) {
            new SYSSpaceTest(TestBench.class, "mersennePrimeTest").exe(i);
            new SYSStopwatchTest(TestBench.class, "mersennePrimeTest").exe(i);
            new CPUStopwatchTest(TestBench.class, "mersennePrimeTest").exe(i);
        }
    }

    public static void notherTest(List args) {
        printlnDelim(" + ", args);
    }

    public static <T extends  Object> void notherTest(T first, T... rest) {
        println(first);
        notherTest(rest);
    }

    public static void main(String argv[]) throws Exception {

//        new SYSSpaceTest(KnightsTour.class, "solveKnightTour").exe();
//        new SYSStopwatchTest(LargeProduct.class, "test1").exe();
//        new CPUStopwatchTest(LargeProduct.class, "test2").exe();

//        qsTest(100000000,10000);

//        println(ppTest(1000000));

//        println(primeTest(10000000));

//        mersennePrimeTest();

//        new SYSStopwatchTest(TestBench.class, "notherTest").exe(Arrays.asList("1", "2", "3"));
//        new SYSStopwatchTest(TestBench.class, "notherTest").exe(new String[] {"1", "2", "3"});

//        new SYSStopwatchTest(
//                TestBench.class,
//                "testTest1"
//        ).exe(100000000);

//        println(padCenter(35, '_', new LinkedList<>(Arrays.asList("Bob", "Alex", "Star"))));

    }
}