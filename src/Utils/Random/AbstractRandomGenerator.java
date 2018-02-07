package Utils.Random;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.Timing.SYSStopwatch;
import Utils.Timing.TimeUnit;
import Utils.StreamUtils.Methods;
import org.junit.Rule;

import java.util.Random;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class AbstractRandomGenerator {

    @Rule
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.MICROSECONDS);

    protected Random rand;

    public enum Case {
        UPPER(  'A',   26,     0),
        LOWER(  'a',   26,     0),
        MIXED(  'A',   26,     1),
        NONE(   ' ',   128,    0);
        public char base;
        public int range;
        public int appylOffest;

        Case(char base, int range, int appylOffest) {
            this.base = base;
            this.range = range;
            this.appylOffest = appylOffest;
        }
    }

    public IntStream randomInts(int n, int max) {
        return randomInts(n, 0, max);
    }

    public IntStream randomInts(int n, int min, int max) {
        return IntStream
            .generate(() -> min + this.rand.nextInt(max - min))
            .limit(n);
    }

    public Stream<Character> randomLetters(int n, Case c) {
        return Stream
            .generate(() -> (char) ((this.rand.nextInt(c.appylOffest + 1) * 32) + c.base + this.rand.nextInt(c.range)))
            .limit(n);
    }

    public Stream<String> randomStrings(int n, Case c, int len) {
        return randomStrings(n , c, len, len+1);
    }

    public Stream<String> randomStrings(int n, Case c, int min, int max) {
        return Stream
            .generate(() ->
                Methods.makeString(
                    randomLetters(min + this.rand.nextInt(max - min), c)
                )
            )
            .limit(n);
    }

    public DoubleStream randomDouble(int n, int max, int precision) {
        return randomInts(n, max-1)
            .mapToDouble(d -> d +
                    this.rand.nextInt((int) Math.pow(10, precision)) / Math.pow(10, precision)
            );
    }
}
