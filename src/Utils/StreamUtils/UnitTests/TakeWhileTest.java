package Utils.StreamUtils.UnitTests;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StreamUtils.MappingInterfaces.BinaryPredicate;
import Utils.StreamUtils.MappingInterfaces.NullaryPredicate;
import Utils.StreamUtils.MappingInterfaces.TrinaryPredicate;
import Utils.StreamUtils.MappingInterfaces.UnaryPredicate;
import Utils.StreamUtils.Spliterators.DoubleTakeWhileSpliterator;
import Utils.StreamUtils.Spliterators.GenericTakeWhileSpliterator;
import Utils.StreamUtils.Spliterators.IntTakeWhileSpliterator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static Utils.StreamUtils.Methods.makeString;
import static junit.framework.TestCase.assertEquals;

public class TakeWhileTest {

    @Rule
    public TestRule rule = new TimedRule();

    @Test
    public void test() {
        assertEquals(
                "0, 1, 2, 3, 4, 5, 6, 7, 8, 9",
                makeString(
                        StreamSupport.intStream(
                                new IntTakeWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (BinaryPredicate<Integer>) (i1, i2) -> i1 < i2
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "",
                makeString(
                        StreamSupport.intStream(
                                new IntTakeWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (BinaryPredicate<Integer>) (i1, i2) -> i2 < i1
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "",
                makeString(
                        StreamSupport.intStream(
                                new IntTakeWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (UnaryPredicate<Integer>) (i) -> i < 0
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "0, 1, 2, 3, 4",
                makeString(
                        StreamSupport.intStream(
                                new IntTakeWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (UnaryPredicate<Integer>) (i) -> i < 5
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "0, 1, 2, 3, 4, 5, 6, 7, 8, 9",
                makeString(
                        StreamSupport.intStream(
                                new IntTakeWhileSpliterator(
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
                                new IntTakeWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (NullaryPredicate<Integer>) () -> true
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "0, 1, 2, 3, 4",
                makeString(
                        StreamSupport.intStream(
                                new IntTakeWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (TrinaryPredicate<Integer>) (i1, i2, i3) -> i1 + i2 + i3 < 12
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "0, 1, 2, 3, 4, 5",
                makeString(
                        StreamSupport.intStream(
                                new IntTakeWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (TrinaryPredicate<Integer>) (i1, i2, i3) -> i1 < 4
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "0, 1, 2, 3, 4",
                makeString(
                        StreamSupport.intStream(
                                new IntTakeWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (TrinaryPredicate<Integer>) (i1, i2, i3) -> i2 < 4
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "0, 1, 2, 3",
                makeString(
                        StreamSupport.intStream(
                                new IntTakeWhileSpliterator(
                                        IntStream.range(0,10).spliterator(),
                                        (TrinaryPredicate<Integer>) (i1, i2, i3) -> i3 < 4
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z",
                makeString(
                        StreamSupport.stream(
                                new GenericTakeWhileSpliterator<>(
                                        Stream.generate(
                                                new Supplier<Character>() {
                                                    int c = 'a';
                                                    @Override
                                                    public Character get() {
                                                        return (char) c++;
                                                    }
                                                }
                                        )
                                        .spliterator(),
                                        (UnaryPredicate<Character>) c -> c < 26 + 'a'
                                ),
                                false
                        )
                )
        );

        assertEquals(
                "0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0",
                makeString(
                        StreamSupport.doubleStream(
                                new DoubleTakeWhileSpliterator(
                                        DoubleStream.generate(
                                                new DoubleSupplier() {
                                                    double d = 0;
                                                    @Override
                                                    public double getAsDouble() { return d += 0.5; }
                                                }
                                        )
                                        .spliterator(),
                                        (UnaryPredicate<Double>) d -> d <= 5.0
                                ),
                                false
                        )
                )
        );
    }
}
