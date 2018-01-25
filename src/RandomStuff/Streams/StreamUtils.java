package RandomStuff.Streams;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;
import static Utils.Console.Printing.*;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.PrimitiveIterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


public class StreamUtils {

    @Rule
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.MICROSECONDS);

    public static IntStream takeWhile(IntStream stream, IntPredicate predicate) {
        return StreamSupport.intStream(new PredicateIntSpliterator(stream, predicate), false);
    }

    public static <T> Function<T, Stream<T>> everyNth(int n) {
        return new Function<T, Stream<T>>() {
            int i = 0;

            @Override
            public Stream<T> apply(T t) {
                if (i++ % n == 0) {
                    return Stream.of(t);
                }
                return Stream.empty();
            }
        };
    }


    public static <T> ArrayList<T> reverseCollect(Stream<T> stream) {
        /* TIP: this is a good way to reverse in a stream */
        return stream.collect(
                ArrayList::new,
                (list, e) -> list.add(0, e),
                (list1, list2) -> list1.addAll(0, list2)
        );
    }

    public static ArrayList<Integer> reverseCollect(IntStream stream) {
        return stream.collect(
                ArrayList::new,
                (list, e) -> list.add(0, e),
                (list1, list2) -> list1.addAll(0, list2)
        );
    }

    static IntStream revRange(int from, int to) {
        return IntStream.range(from, to).map(i -> to - i + from - 1);
    }

    private static class PredicateIntSpliterator extends Spliterators.AbstractIntSpliterator {
        private final PrimitiveIterator.OfInt iterator;
        private final IntPredicate predicate;

        public PredicateIntSpliterator(IntStream stream, IntPredicate predicate) {
            super(Long.MAX_VALUE, IMMUTABLE);
            this.iterator = stream.iterator();
            this.predicate = predicate;
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            if (iterator.hasNext()) {
                int value = iterator.nextInt();
                if (predicate.test(value)) {
                    action.accept(value);
                    return true;
                }
            }
            return false;
        }
    }

    @Test
    public void test() {
        println(reverseCollect(IntStream.range(0, 10)));
        println(reverseCollect(Stream.of('a','b','c','d')));
        printlnDelim(", ", IntStream.of(1,2,3,4));
    }
}
