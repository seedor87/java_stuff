package Testing;

import Random.LargeProduct;
import myUtils.Tuple;

import java.lang.reflect.Method;

import static myUtils.ConsolePrinting.*;
import static myUtils.flowControlTools.waitForKey;

public class TestBench {

    private static long startTime, endTime;
    private static double duration;

    public static <T extends Object> Tuple timeIt(Class cl, String method, T... params) throws Exception {
        Object obj;
        try {
            obj = cl.newInstance();
        } catch (Exception ex) {
            throw ex;
        }
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method)) {
                try {
                    startTime = System.nanoTime();
                    Tuple ret = new Tuple(m.invoke(obj, params));
                    endTime = System.nanoTime();
                    duration = (endTime - startTime) / 1000000000.0;
                    println("Runtime: " + duration + "s");
                    return ret;
                }
                catch(IllegalArgumentException ex) {} // try next overload
            }
        }
        throw new IllegalArgumentException("no matching argument list found");
    }

    public static void main(String argv[]) throws Exception {
        for (int i = 100; i <= 1000000; i*=10) {
            Tuple results = timeIt(Tests.class, "primeTest", i);
            println(results);
        }

        for (int i = 100; i <= 1000000; i*=10) {
            Tuple results = timeIt(Tests.class, "ppTest", i);
            println(results);
        }

        for (int i = 1; i <= 1000; i*=10) {
            Tuple results = timeIt(Tests.class, "qsTest", i, 1000);
            println(results);
        }

        waitForKey();

        println(timeIt(KnightsTour.class, "solveKnightTour"));

        waitForKey();

        println(timeIt(LargeProduct.class, "test1"));
        println(timeIt(LargeProduct.class, "test2"));
    }
}