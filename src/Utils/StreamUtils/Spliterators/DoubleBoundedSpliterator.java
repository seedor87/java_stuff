package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.StreamSupport;

public class DoubleBoundedSpliterator implements Spliterator.OfDouble {
    private Double index;
    private DoublePredicate condition;
    private DoubleUnaryOperator increment;

    public DoubleBoundedSpliterator(Double initialization, DoublePredicate termination, DoubleUnaryOperator incrementation) {
        this.index = initialization;
        this.condition = termination;
        this.increment = incrementation;
    }

    @Override
    public boolean tryAdvance(DoubleConsumer action) {
        if (condition.test(index)) {
            action.accept(index);
            index = increment.applyAsDouble(index);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator.OfDouble trySplit() {
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
        double index = 10d;
        StreamSupport.doubleStream(
            new DoubleBoundedSpliterator(
                index,
                i -> i > 0.0,
                i -> i / 2
            ),
            false
        )
        .forEach(Utils.Console.Printing::println);
    }
}
