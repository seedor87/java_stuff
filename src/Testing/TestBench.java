package Testing;

import Random.KnightsTour;
import Random.LargeProduct;
import Random.MersennePrimes;
import myUtils.Measurement.CPUTimer;
import myUtils.Measurement.SYSTimer;
import myUtils.Measurement.Spacer;
import myUtils.Measurement.Timer;
import myUtils.Tuple;

import java.lang.reflect.Method;

import static myUtils.ConsolePrinting.*;
import static myUtils.flowControlTools.waitForKey;


public class TestBench<T extends Object>  {

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
                    Spacer spacer = new Spacer();
                    spacer.start();
                    Tuple ret = new Tuple(m.invoke(obj, params));
                    spacer.stop();
                    println("Used: " + spacer);
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
                    Timer timer = new SYSTimer();
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

//        int lim = 1000000000;
//        for (int i = 10; i < lim; i*=10) {
//            cpuTimeIt(MersennePrimes.class, "mersennePrimeTest", i);
//        }

        for (int i = 100; i <= 1000000; i*=10) {
            Tuple results = memUsage(Tests.class, "primeTest", i);
            println(results);
        }

        for (int i = 100; i <= 1000000; i*=10) {
            Tuple results = memUsage(Tests.class, "ppTest", i);
            println(results);
        }

        for (int i = 1; i <= 1000; i*=10) {
            Tuple results = memUsage(Tests.class, "qsTest", i, 1000);
            println(results);
        }

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