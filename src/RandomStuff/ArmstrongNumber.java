package RandomStuff;

import java.util.stream.IntStream;

public class ArmstrongNumber {

    public static IntStream parseDigits(int n) {
        int count = 0, temp = n;
        while(temp > 0) { temp /= 10; count++; }
        return IntStream.iterate(n, i -> i /10)
                .map(i -> i % 10)
                .limit(count);
    }

    public static boolean isArmstrNum(int i) {
        return i == parseDigits(i)
                .mapToDouble(x -> Math.pow(x, 3))
                .reduce(0, (x, y) -> x + y);
    }

    public static boolean isNthPowNum(int i, int n) {
        return i == parseDigits(i)
                .mapToDouble(x -> Math.pow(x, n))
                .reduce(0, (x, y) -> x + y);
    }

    public static void armstrNumTest(int lim, int n) {
        IntStream.range(0, lim)
                .filter((x) -> isNthPowNum(x, n))
                .mapToObj(r -> parseDigits(r)
                        .mapToObj(i -> i + "^" + n + " -> " + Math.pow(i, n) + ", ")
                        .collect(StringBuilder::new,
                                StringBuilder::append,
                                StringBuilder::append)
                        + " : " + r)
                .forEach(Utils.Console.Printing::println);
    }

    public static void main(String args[]) {
        int lim = 1000000;
        IntStream.range(2,10)
                .forEach(n -> armstrNumTest(lim, n));
    }
}
