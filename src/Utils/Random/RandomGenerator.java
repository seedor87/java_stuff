package Utils.Random;

import Utils.Timing.AbstractStopwatch;
import Utils.Timing.SYSStopwatch;
import Utils.Timing.TimeUnit;
import org.junit.Test;
import java.util.Random;

import static Utils.Console.Printing.println;

public class RandomGenerator extends AbstractRandomGenerator {

    public RandomGenerator(Random rand) {
        this.rand = rand;
    }

    public RandomGenerator() {
        this.rand = new Random();
    }

    @Test
    public void ctrl() {
        println(randomInts(10000, 0, 1000));
    }

    @Test
    public void test() {
        println(randomStrings(10000, Case.UPPER, 5));

    }

    public static void main(String[] args) {
        AbstractStopwatch stp = new SYSStopwatch(TimeUnit.MICROSECONDS);

        stp.start();
        println(new RandomGenerator().randomLetters(1000, Case.UPPER));
        stp.stop();
        println(stp);

        stp.start();
        println(new RandomGenerator().randomInts(1000, 100));
        stp.stop();
        println(stp);

        stp.start();
        println(new RandomGenerator().randomInts(1000, 50, 100));
        stp.stop();
        println(stp);

        stp.start();
        println(new RandomGenerator().randomStrings(1000, Case.UPPER, 5));
        stp.stop();
        println(stp);

        stp.start();
        println(new RandomGenerator().randomStrings(1000, Case.UPPER, 1, 10));
        stp.stop();
        println(stp);

        stp.start();
        println(new RandomGenerator().randomDouble(1000, 100, 9));
        stp.stop();
        println(stp);

        stp.start();
        println(new RandomGenerator().randomStrings(1000, Case.NONE, 1, 4));
        stp.stop();
        println(stp);
    }
}
