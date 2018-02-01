package RandomStuff.SpecialNumbers;

import Utils.StreamUtils.Methods;
import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.Console.Special;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;
import org.junit.Rule;
import org.junit.Test;

import java.util.*;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

import static Utils.Console.Printing.*;
import static Utils.Console.Special.*;
import static Utils.StringUtils.wrap;

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

    /* NOTE: maintains order of parsed digits */
    public static IntStream parseDigits(int n) {
        return
            Methods.takeWhile(
                IntStream.iterate(n, i -> i / 10),
                (IntPredicate) i -> i > 0
            )
            .map(j -> j % 10);
    }

    public static boolean isNthArmstrNum(int n, int pow) {
        return n == parseDigits(n)
                .mapToDouble(i -> Math.pow(i, pow))
                .reduce(0, (x, y) -> x + y);
    }

    public static boolean isArmstrNum(int n) {
        return isNthArmstrNum(n, 3);
    }

    enum DigitColors {
        ZERO(FG_RED),
        ONE(FG_GREEN),
        TWO(FG_BRIGHT_RED),
        THREE(FG_YELLOW),
        FOUR(FG_MAGENTA),
        FIVE(FG_BRIGHT_GREEN),
        SIX(FG_BRIGHT_YELLOW),
        SEVEN(FG_CYAN),
        EIGHT(FG_BRIGHT_MAGENTA),
        NINE(FG_BRIGHT_CYAN);
        private Special color;
        DigitColors(Special color) {
            this.color = color;
        }
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
                        .forEach(k -> {
                            parseDigits(k)
                                .<ArrayList<String>>collect(
                                    ArrayList::new,
                                    (list, e) -> {
                                        list.add(wrap(DigitColors.values()[e].color, e) + "^" + i + " = " + Math.pow(e, i));
                                        list.add((list.size()/2), wrap(DigitColors.values()[e].color, e));
                                        /* Used for reverse-ordered digits */
//                                        (list).add(0, "" + wrap(DigitColors.values()[e].color, e));
//                                        (list).add((list.size()/2)+1, wrap(DigitColors.values()[e].color, e) + "^" + i + " = " + Math.pow(e, i));
                                    },
                                    (list1, list2) -> (list1).addAll(list2)
                                ).forEach(s -> {
                                        if (s.length() < 11) {
                                            print(s);
                                        } else {
                                            print("", s);
                                        }
                                    });
                            println();
                        });
            });
        println();
    }

    public static void main(String args[]) {
     Utils.Console.Printing.print(parseDigits(1234));
//        new ArmstrongNumber().test();
    }
}