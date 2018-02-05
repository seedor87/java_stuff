package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.StreamSupport;

public class GenericBoundedSpliterator<T> implements Spliterator<T> {
    private T index;
    private Predicate<T> condition;
    private UnaryOperator<T> increment;

    public GenericBoundedSpliterator(T initialization, Predicate<T> termination, UnaryOperator<T> incrementation) {
        this.index = initialization;
        this.condition = termination;
        this.increment = incrementation;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        if (condition.test(index)) {
            action.accept(index);
            index = increment.apply(index);
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<T> trySplit() {
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
        StreamSupport.stream(
            new GenericBoundedSpliterator<>(
                'a',
                c -> !c.equals('d'),
                c -> ++c
            ) ,
            false
        )
        .mapToInt(Character::getNumericValue)
        .forEach(Utils.Console.Printing::println);
    }
}
