package Testing;

import Random.MersennePrimes;
import myUtils.Measurement.CPUTimer;
import myUtils.Measurement.SYSTimer;
import myUtils.Measurement.Timer;
import myUtils.Tuple;

import java.lang.reflect.Method;

import static myUtils.ConsolePrinting.*;


public class TestBench<T extends Object>  {

    private static long memoryBefore, memoryAfter;
    private static double consumed;
    private static Runtime runtime = Runtime.getRuntime();

    public static <T> Tuple cpuTimeIt(Class cl, String method, T... params) throws Exception {
        Object obj;
        try {
            obj = cl.newInstance();
        } catch (Exception ex) {
            throw ex;
        }
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method)) {
                try {
                    Timer timer = new CPUTimer();
                    timer.start();
                    Tuple ret = new Tuple(m.invoke(obj, params));
                    timer.stop();
                    println("Runtime: " + timer);
                    return ret;
                }
                catch(IllegalArgumentException ex) {} // try next overload
            }
        }
        throw new IllegalArgumentException("no matching argument list found");
    }

    public static <T> Tuple memUsage(Class cl, String method, T... params) throws Exception {
        Object obj;
        try {
            obj = cl.newInstance();
        } catch (Exception ex) {
            throw ex;
        }
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method)) {
                try {
                    System.gc();
                    memoryBefore = runtime.totalMemory() - runtime.freeMemory();
                    Tuple ret = new Tuple(m.invoke(obj, params));
                    memoryAfter = runtime.totalMemory() - runtime.freeMemory();
                    consumed = memoryAfter - memoryBefore;
                    println("Used: " + consumed /1000000.0 + " Mb");
                    return ret;
                }
                catch(IllegalArgumentException ex) {} // try next overload
            }
        }
        throw new IllegalArgumentException("no matching argument list found");
    }

    public static <T> Tuple timeIt(Class cl, String method, T... params) throws Exception {
        Object obj;
        try {
            obj = cl.newInstance();
        } catch (Exception ex) {
            throw ex;
        }
        for (Method m : cl.getMethods()) {
            if (m.getName().equals(method)) {
                try {
                    SYSTimer timer = new SYSTimer();
                    timer.start();
                    Tuple ret = new Tuple(m.invoke(obj, params));
                    timer.stop();
                    println("Runtime: " + timer);
                    return ret;
                }
                catch(IllegalArgumentException ex) {} // try next overload
            }
        }
        throw new IllegalArgumentException("no matching argument list found");
    }

    public static void main(String argv[]) throws Exception {

        int lim = 1000000000;
        for (int i = 10; i < lim; i*=10) {
            cpuTimeIt(MersennePrimes.class, "mersennePrimeTest", i);
        }

//        for (int i = 100; i <= 1000000; i*=10) {
//            Tuple results = timeIt(Tests.class, "primeTest", i);
//            println(results);
//        }
//
//        for (int i = 100; i <= 1000000; i*=10) {
//            Tuple results = timeIt(Tests.class, "ppTest", i);
//            println(results);
//        }
//
//        for (int i = 1; i <= 1000; i*=10) {
//            Tuple results = timeIt(Tests.class, "qsTest", i, 1000);
//            println(results);
//        }
//
//        waitForKey();
//
//        println(timeIt(KnightsTour.class, "solveKnightTour"));
//
//        waitForKey();
//
//        println(timeIt(LargeProduct.class, "test1"));
//        println(timeIt(LargeProduct.class, "test2"));
    }
}