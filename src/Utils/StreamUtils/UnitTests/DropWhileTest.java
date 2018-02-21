package Utils.StreamUtils.UnitTests;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StreamUtils.MappingInterfaces.BinaryPredicate;
import Utils.StreamUtils.MappingInterfaces.NullaryPredicate;
import Utils.StreamUtils.MappingInterfaces.TrinaryPredicate;
import Utils.StreamUtils.MappingInterfaces.UnaryPredicate;
import Utils.StreamUtils.Spliterators.IntDropWhileSpliterator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static Utils.StreamUtils.Methods.makeString;
import static junit.framework.TestCase.assertEquals;

public class DropWhileTest {

    @Rule
    public TestRule rule = new TimedRule();

    @Test
    public void test() {
        assertEquals(
                "",
                makeString(
                        StreamSupport.intStream(
                                new IntDropWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (BinaryPredicate<Integer>) (i1, i2) -> i1 < i2
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "0, 1, 2, 3, 4, 5, 6, 7, 8, 9",
                makeString(
                        StreamSupport.intStream(
                                new IntDropWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (BinaryPredicate<Integer>) (i1, i2) -> i2 < i1
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "0, 1, 2, 3, 4, 5, 6, 7, 8, 9",
                makeString(
                        StreamSupport.intStream(
                                new IntDropWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (UnaryPredicate<Integer>) (i) -> i < 0
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "5, 6, 7, 8, 9",
                makeString(
                        StreamSupport.intStream(
                                new IntDropWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (UnaryPredicate<Integer>) (i) -> i < 5
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "",
                makeString(
                        StreamSupport.intStream(
                                new IntDropWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (UnaryPredicate<Integer>) (i) -> i < 10
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "0, 1, 2, 3, 4, 5, 6, 7, 8, 9",
                makeString(
                        StreamSupport.intStream(
                                new IntDropWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (NullaryPredicate<Integer>) () -> false
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "5, 6, 7, 8, 9",
                makeString(
                        StreamSupport.intStream(
                                new IntDropWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (TrinaryPredicate<Integer>) (i1, i2, i3) -> i1 + i2 + i3 < 12
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "6, 7, 8, 9",
                makeString(
                        StreamSupport.intStream(
                                new IntDropWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (TrinaryPredicate<Integer>) (i1, i2, i3) -> i1 < 4
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "5, 6, 7, 8, 9",
                makeString(
                        StreamSupport.intStream(
                                new IntDropWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (TrinaryPredicate<Integer>) (i1, i2, i3) -> i2 < 4
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "4, 5, 6, 7, 8, 9",
                makeString(
                        StreamSupport.intStream(
                                new IntDropWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (TrinaryPredicate<Integer>) (i1, i2, i3) -> i3 < 4
                                ),
                                false
                        ).map(i -> i)
                )
        );
    }
}
