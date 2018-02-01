package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Spliterators.BoundedSpliterator;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.StreamSupport;

public class UnboundedSpliterator<T> implements Spliterator<T>{
    private T index;
    private Predicate<T> condition;
    private UnaryOperator<T> increment;

    public UnboundedSpliterator(T seed, Predicate<T> predicate, UnaryOperator<T> operator) {
        this.index = seed;
        this.increment = operator;
        this.condition = predicate;
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
        return this.SORTED;
    }

    public static void main(String[] args) {
        StreamSupport.stream(
            new BoundedSpliterator<>(
                'a',
                character -> !character.equals('d'),
                character -> (char) (character + 1)
            ) ,
            false
        )
        .mapToInt(Character::getNumericValue)
        .forEach(Utils.Console.Printing::println);
    }
}
