package Utils;

import Utils.Timers.AbstractStopwatch;
import Utils.Timers.SYSStopwatch;
import Utils.Timers.TimeUnit;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static Utils.Console.Printing.println;
import static Utils.Console.Printing.printlnDelim;

public abstract class MyRandom {

    private static Random generator = new Random();

    public static Stream<Object> randomInts(int n, int max) {
        return IntStream
                .range(0, n)
                .mapToObj( i -> new Integer(generator.nextInt(max)));
    }

    public static Character[] randomChars(int n) {
        Character[] ret = new Character[n];
        for (int i = 0; i < n; i++) {
            ret[i] = (char)(generator.nextInt(26) + 'a');
        }
        return ret;
    }

    public static void main(String[] args) {
        AbstractStopwatch stp = new SYSStopwatch(TimeUnit.MICRO);
        stp.start();
        printlnDelim(", ",randomChars(1000000));
        stp.stop();
        println(stp);

        stp.start();
        randomInts(1000000, 100).forEach(p -> Utils.Console.Printing.print(p + " "));
        println();
        stp.stop();
        println(stp);
    }
}
