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
import static Utils.Equivocation.equal;

public class TestBench {

    public static void qsTest(Integer len, Integer max) {

        for(int i = 10; i < len; i*=10) {
            try {
                print("DP Sort: ");
                new SYSTimeTest(DualPivotQuickSort.class, "Test").exe(i,max);
                print("Reg qs: ");
                new SYSTimeTest(QuickSort.class, "Test").exe(i,max);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean ppTest(int lim) {
        boolean result = false;
        try {
            List<Double> res1 = (List) new SYSTimeTest(PerfectNumbers.class, "generatePerfectNumbers").exe(lim).get(0);
            List<Double> res2 = (List) new SYSTimeTest(PerfectNumbers.class, "findPerfectNumbers").exe(lim).get(0);
            result = Equivocation.equal(res1, res2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static List<Integer> primeTest(int lim) {
        Tuple result;
        try {
            result = new CPUTimeTest(Primes.class, "getAllPrimes").exe(lim);
            return (List) result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void mersennePrimeTest(int lim) {
        try {
            new CPUTimeTest(MersennePrimes.class, "mersennePrimeTest").exe(lim);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void testTest1(int lim) throws Exception {
        for (int i = 10; i < lim; i *= 10) {
            new MemUseTest(TestBench.class, "mersennePrimeTest").exe(i);
            new SYSTimeTest(TestBench.class, "mersennePrimeTest").exe(i);
            new CPUTimeTest(TestBench.class, "mersennePrimeTest").exe(i);
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

//        new MemUseTest(KnightsTour.class, "solveKnightTour").exe();
//        new SYSTimeTest(LargeProduct.class, "test1").exe();
//        new CPUTimeTest(LargeProduct.class, "test2").exe();

//        qsTest(100000000,10000);

//        println(ppTest(1000000));

//        println(primeTest(10000000));

//        mersennePrimeTest();

//        new SYSTimeTest(TestBench.class, "notherTest").exe(Arrays.asList("1", "2", "3"));
//        new SYSTimeTest(TestBench.class, "notherTest").exe(new String[] {"1", "2", "3"});

//        new SYSTimeTest(
//                TestBench.class,
//                "testTest1"
//        ).exe(100000000);

//        println(padCenter(35, '_', new LinkedList<>(Arrays.asList("Bob", "Alex", "Star"))));

        int x = 0;
        int z = 0;

        int y = 1; // offset to start at first step;

        for (int i = 0 ; i < x; i++) {  // basically z = x
            z++;
        }

        while(z > 0) {
            if(z != 0) {
                y--;
                z--;
            }
            if(z != 0) {
                y++;
                z--;
            }
        }
        println(y); // y <- y;
    }
}