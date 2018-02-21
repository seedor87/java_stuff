package Utils.StreamUtils.UnitTests;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StreamUtils.Interfaces.BinaryPredicate;
import Utils.StreamUtils.Spliterators.IntDropWhileSpliterator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static Utils.Console.Printing.println;
import static Utils.StreamUtils.Methods.intIterate;
import static Utils.StreamUtils.Methods.iterate;
import static Utils.StreamUtils.Methods.makeString;
import static junit.framework.TestCase.assertEquals;

public class IterateTest {

    @Rule
    public TestRule rule = new TimedRule();

    @Test
    public void test() {

        assertEquals(
                "1, 2, 3, 4, 5, 6, 7, 8, 9, 10",
                makeString(
                        intIterate(
                                1,
                                i -> i <= 10,
                                i -> i + 1
                        )
                )
        );

        assertEquals(
                "z, y, x, w, v, u, t, s, r, q, p, o, n, m, l, k, j, i, h, g, f, e, d, c, b, a",
                makeString(
                        iterate(
                                'z',
                                c -> !c.equals((char) ('a' - 1)),
                                c -> --c
                        )
                )
        );

        assertEquals(
                "1, 12, 123, 1234, 12345, 123456, 1234567, 12345678, 123456789",
                makeString(
                        iterate(
                                "1",
                                s -> s.length() < 10,
                                s -> s + (char) (s.charAt(s.length()-1) + 1)
                        )
                )
        );
    }
}
