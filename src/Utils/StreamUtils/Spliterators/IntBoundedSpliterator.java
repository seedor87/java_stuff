package Utils.StreamUtils.Spliterators;

import static Utils.Console.Printing.println;

import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.StreamSupport;

public class IntBoundedSpliterator extends PrimitiveBoundedSpliterator<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt {

    public IntBoundedSpliterator(Integer initialization, Predicate<Integer> termination, UnaryOperator<Integer> incrementation) {
        super(initialization, termination, incrementation);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        if (condition.test(index)) {
            action.accept(index);
            index = increment.apply(index);
            return true;
        }
        return false;
    }

    @Override
    public void forEachRemaining(IntConsumer action) {

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
