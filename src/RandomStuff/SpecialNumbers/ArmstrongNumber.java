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
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.MICROSECONDS);

    private static boolean ctrlMethod(int n, int pow) {
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

    @Test
    public void test() {
        int lim = 1000000;
        int pow = 8;

        IntStream.range(2,pow)
            .forEach(i -> {
                println("Power:", i);
                IntStream.range(2, lim)
                    .filter((j) -> isNthArmstrNum(j, i))
                    .mapToObj(k -> "\t" + parseDigits(k)
                        .mapToObj(l -> l + "^" + i + " -> " + Math.pow(l, i) + " ")
                        .collect(
                            StringBuilder::new,
                            (sb, s) -> sb.insert(0, s),
                            (sb1, sb2) -> sb1.insert(0, sb2.toString())

                            /* TIP: this is a good way to reverse in a stream */
//                            ArrayList::new,
//                            (list, e) -> list.add(0, e),
//                            (list1, list2) -> list1.addAll(0, list2)

                        )
                        + ": " + k)
                    .forEach(Utils.Console.Printing::println);
            });
        println("\n");
    }

    public static void main(String args[]) {
        new ArmstrongNumber().test();
    }
}
