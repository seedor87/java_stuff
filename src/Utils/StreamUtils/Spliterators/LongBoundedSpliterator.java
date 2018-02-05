package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;
import java.util.function.LongUnaryOperator;
import java.util.stream.StreamSupport;

public class LongBoundedSpliterator implements Spliterator.OfLong {
    private Long index;
    private LongPredicate condition;
    private LongUnaryOperator increment;

    public LongBoundedSpliterator(Long initialization, LongPredicate termination, LongUnaryOperator incrementation) {
        this.index = initialization;
        this.condition = termination;
        this.increment = incrementation;
    }

    @Override
    public boolean tryAdvance(LongConsumer action) {
        if (condition.test(index)) {
            action.accept(index);
            index = increment.applyAsLong(index);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator.OfLong trySplit() {
        return this;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return 0;
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
