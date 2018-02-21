package Utils.StreamUtils.UnitTests;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StreamUtils.MappingInterfaces.TrinaryHomogenousMapping;
import Utils.StreamUtils.Spliterators.GenericVariadicSpliterator;
import Utils.StreamUtils.Spliterators.IntVariadicSpliterator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static Utils.StreamUtils.Methods.makeString;
import static junit.framework.TestCase.assertEquals;

public class VariadicMapperTest {

    @Rule
    public TestRule rule = new TimedRule();

    @Test
    public void test() {
        assertEquals(
                "3, 12, 21",
                makeString(
                        StreamSupport.intStream(
                                new IntVariadicSpliterator(
                                        IntStream.range(0, 10).spliterator(),
                                        (TrinaryHomogenousMapping<Integer>) (i1, i2, i3) -> i1 + i2 + i3,
                                        GenericVariadicSpliterator.Process.SUBDIVIDED
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "3, 6, 9, 12, 15, 18, 21, 24",
                makeString(
                        StreamSupport.intStream(
                                new IntVariadicSpliterator(
                                        IntStream.range(0, 10).spliterator(),
                                        (TrinaryHomogenousMapping<Integer>) (i1, i2, i3) -> i1 + i2 + i3,
                                        GenericVariadicSpliterator.Process.NONSUBDVIDED
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "2, 8, 14",
                makeString(
                        StreamSupport.intStream(
                                new IntVariadicSpliterator(
                                        IntStream.range(0, 10).spliterator(),
                                        (TrinaryHomogenousMapping<Integer>) (i1, i2, i3) -> i1 + i3,
                                        GenericVariadicSpliterator.Process.SUBDIVIDED
                                ),
                                false
                        ).map(i -> i)
                )
        );

        assertEquals(
                "2, 4, 6, 8, 10, 12, 14, 16",
                makeString(
                        StreamSupport.intStream(
                                new IntVariadicSpliterator(
                                        IntStream.range(0, 10).spliterator(),
                                        (TrinaryHomogenousMapping<Integer>) (i1, i2, i3) -> i1 + i3,
                                        GenericVariadicSpliterator.Process.NONSUBDVIDED
                                ),
                                false
                        ).map(i -> i)
                )
        );
    }
}
