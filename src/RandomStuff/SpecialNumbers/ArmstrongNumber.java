package RandomStuff.SpecialNumbers;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StopWatches.AbstractStopwatch;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import static Utils.Console.Printing.*;

public class ArmstrongNumber {

    @Rule
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.MICRO);

    private static boolean cntrlMethod(int n, int pow) {
        List<Integer> arr = new ArrayList<>();
        int temp = n;
        while (temp > 0) {
            arr.add(temp % 10);
            temp /= 10;
        }
        int sum = 0;
        for (int k : arr) {
            sum += Math.pow(k, pow);
        }
        return sum == n;
    }

    @Test
    public void test() {
        int lim = 1000000000;
        int pow = 4;
        for (int i = 1; i < lim; ++i) {
            Assert.assertEquals(isNthArmstrNum(i, pow), cntrlMethod(i, pow));
        }
    }

    public static IntStream parseDigits(int n) {
        int count = 0, temp = n;
        while(temp > 0) { temp /= 10; count++; }
        return IntStream.iterate(n, i -> i /10)
                .map(i -> i % 10)
                .limit(count);
    }

    public static boolean isNthArmstrNum(int n, int pow) {
        return n == parseDigits(n)
                .mapToDouble(i -> Math.pow(i, pow))
                .reduce(0, (x, y) -> x + y);
    }

    public static boolean isArmstrNum(int n) {
        return isNthArmstrNum(n, 3);
    }

    public static void main(String args[]) {
        int lim = 1000000;
        int pow = 7;
        AbstractStopwatch stopwatch = new SYSStopwatch(TimeUnit.MICRO);

        stopwatch.start();
        IntStream.range(2,pow)
                .forEach(i ->
                    IntStream.range(1, lim)
                            .filter((j) -> isNthArmstrNum(j, i))
                            .mapToObj(k -> parseDigits(k)
                                    .mapToObj(l -> l + "^" + i + " -> " + Math.pow(l, i) + ", ")
                                    .collect(StringBuilder::new,
                                            StringBuilder::append,
                                            StringBuilder::append)
                                    + " : " + k)
                            .forEach(Utils.Console.Printing::println)
                );
        stopwatch.stop();
        println(stopwatch, "\n");
    }
}
