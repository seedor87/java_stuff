package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.StreamSupport;

import static Utils.Console.Printing.println;

public class DoubleBoundedSpliterator extends AbstractPrimitiveBoundedSpliterator<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble {

    public DoubleBoundedSpliterator(Double initialization, Predicate<Double> termination, UnaryOperator<Double> incrementation) {
        super(initialization, termination, incrementation);
    }

    @Override
    public boolean tryAdvance(DoubleConsumer action) {
        if (condition.test(index)) {
            action.accept(index);
            index = increment.apply(index);
            return true;
        }
        return false;
    }

    @Override
    public void forEachRemaining(DoubleConsumer action) {

    }

    public static void main(String[] args) {
        double index = 10d;
        StreamSupport.stream(
            new DoubleBoundedSpliterator(
                index,
                i -> i > 0.0,
                i -> i / 2
            ) ,
            false
        )
        .forEach(Utils.Console.Printing::println);
        println("\n", index);
    }
}
