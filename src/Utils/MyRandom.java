package Utils;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StopWatches.AbstractStopwatch;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;
import org.junit.Rule;
import org.junit.Test;
import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static Utils.Console.Printing.println;

public class MyRandom {

    @Rule
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.MICRO);

    private static Random rand = new Random();

    enum Case {
        UPPER(  'A',   26,     0),
        LOWER(  'a',   26,     0),
        MIXED(  'A',   26,     1),
        NONE(   ' ',   128,    0);
        private char base;
        private int range;
        private int appylOffest;
        Case(char base, int range, int appylOffest) {
            this.base = base;
            this.range = range;
            this.appylOffest = appylOffest;
        }
    }
    public static Stream<Integer> randomInts(int n, int max) {
        return randomInts(n, 0, max);
    }

    public static Stream<Integer> randomInts(int n, int min, int max) {
        return IntStream
                .range(0, n)
                .mapToObj(i -> min + new Integer(rand.nextInt(max - min)));
    }

    public static Stream<Character> randomLetters(int n, Case c) {
        return IntStream
                .range(0, n)
                .map(i -> rand.nextInt(c.appylOffest + 1) * 32)
                .mapToObj(l -> (char) (l + c.base + rand.nextInt(c.range)));
    }

    public static Stream<String> randomStrings(int n, Case c, int len) {
        return randomStrings(n , c, len, len);
    }

    public static Stream<String> randomStrings(int n, Case c, int min, int max) {
        return IntStream
                .range(0, n)
                .mapToObj(s -> randomLetters(min + rand.nextInt(max - min), c)
                        .collect(
                                StringBuilder::new,
                                StringBuilder::appendCodePoint,
                                StringBuilder::append
                        )
                        .toString()
                );
    }

    public static DoubleStream randomDouble(int n, int max, int precision) {
        return randomInts(n, max-1)
                .mapToDouble(d -> d +
                        rand.nextInt((int) Math.pow(10, precision))
                                / Math.pow(10, precision)
                );
    }

    private Integer[] randomIntsCtrl(int n, int max) {
        Integer[] ret = new Integer[n];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = new Integer(rand.nextInt(max));
        }
        return ret;
    }

    private Character[] randomLettersCtrl(int n) {
        Character[] ret = new Character[n];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = (char) (rand.nextInt(26) + 'a');
        }
        return ret;
    }

    private Double[] randomDoubleCtrl(int n, int max, int precision) {
        Double[] ret = new Double[n];
        for (int i = 0; i < ret.length; i++) {
            int characteristic = rand.nextInt(max);
            ret[i] = characteristic +
                    (rand.nextInt((int) Math.pow(10, precision))
                    / Math.pow(10, precision));
        }
        return ret;
    }

    @Test
    public void ctrl() {
//        Integer[] res = randomIntsCtrl(1000000, 1000);
//        Character[] res = randomLettersCtrl(1000000);
        Double[] res = randomDoubleCtrl(1000000, 1000, 4);
        for (Object i : res) {
            println(i);
        }
    }

    @Test
    public void test() {
//        randomInts(1000000, 1000).forEach(Utils.Console.Printing::println);
//        randomLetters(1000000, Case.UPPER).forEach(Utils.Console.Printing::println);
        randomDouble(1000000, 1000, 4).forEach(Utils.Console.Printing::println);
    }

    public static void main(String[] args) {
        AbstractStopwatch stp = new SYSStopwatch(TimeUnit.MICRO);

        stp.start();
        randomLetters(100000000, Case.UPPER);
//        printlnDelim(", ",randomLetter(1000000));
        stp.stop();
        println(stp);

        stp.start();
        randomInts(100000000, 100);
//        randomInts(1000000, 100).forEach(p -> Utils.Console.Printing.print(p + " "));
        stp.stop();
//        println();
        println(stp);

        stp.start();
        randomInts(100000000, 50, 100);
//        randomInts(100000000, 50, 100).forEach(p -> Utils.Console.Printing.print(p + " "));
        stp.stop();
//        println();

        println(stp);

        stp.start();
        randomStrings(100000000, Case.UPPER, 5);
        stp.stop();
        println(stp);

        stp.start();
        randomStrings(100000000, Case.UPPER, 1, 10);
//        randomStrings(100000000, Case.UPPER, 1, 10).forEach(p -> Utils.Console.Printing.print(p + " "));
        stp.stop();
        println(stp);

        stp.start();
        randomDouble(100000000, 100, 9);
        stp.stop();
        println(stp);

        stp.start();
//        randomStrings(1000, Case.UPPER, 1, 10);
        randomStrings(1000, Case.NONE, 1, 10).forEach(p -> Utils.Console.Printing.print(p + " "));
        stp.stop();
        println();
        println(stp);
    }
}
