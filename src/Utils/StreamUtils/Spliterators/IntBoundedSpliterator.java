package Utils.StreamUtils.Spliterators;

import static Utils.Console.Printing.println;

import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.StreamSupport;

public class IntBoundedSpliterator implements Spliterator.OfInt {
    private Integer index;
    private IntPredicate condition;
    private IntUnaryOperator increment;

    public IntBoundedSpliterator(Integer initialization, IntPredicate termination, IntUnaryOperator incrementation) {
        this.index = initialization;
        this.condition = termination;
        this.increment = incrementation;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (condition.test(index)) {
            action.accept(index);
            index = increment.applyAsInt(index);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator.OfInt trySplit() {
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
        int index = 10;
        StreamSupport.stream(
            new IntBoundedSpliterator(
                index,
                i -> i > -1,
                i -> i /2
            ) ,
            false
        )
        .mapToInt(Character::getNumericValue)
        .forEach(Utils.Console.Printing::println);
        println("\n", index);
    }
}
