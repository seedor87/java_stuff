package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.StreamSupport;

public class LongBoundedSpliterator extends PrimitiveBoundedSpliterator<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {

    public LongBoundedSpliterator(Long initialization, Predicate<Long> termination, UnaryOperator<Long> incrementation) {
        super(initialization, termination, incrementation);
    }

    public boolean tryAdvance(LongConsumer action) {
        if (condition.test(index)) {
            action.accept(index);
            index = increment.apply(index);
            return true;
        }
        return false;
    }

    @Override
    public void forEachRemaining(LongConsumer action) {

    }

    public static void main(String[] args) {
        long index = 1000000000000000000L;
        StreamSupport.longStream(
            new LongBoundedSpliterator(
                index,
                i -> i > 0,
                i -> i / 2
            ) ,
            false
        )
        .forEach(Utils.Console.Printing::println);
    }
}
