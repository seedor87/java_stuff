package Utils.StreamUtils.Spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.StreamSupport;

public class GenericBoundedSpliterator<T> implements Spliterator<T>, Cloneable {
    protected T index;
    protected Predicate<T> condition;
    protected UnaryOperator<T> increment;

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
        Spliterator<T> prefix = this.trySplit();
        if(prefix == null) {
            return null;
        }
        GenericBoundedSpliterator<T> clone;
        try {
            clone = (GenericBoundedSpliterator<T>) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        return clone;
    }

    @Override
    public long estimateSize() {
        return Long.MAX_VALUE;
    }

    @Override
    public int characteristics() {
        return 0;
    }

    @Override
    public boolean hasCharacteristics(int characteristics) {
        return false;
    }

    @Override
    public long getExactSizeIfKnown() {
        return 0;
    }

    @Override
    public Comparator<? super T> getComparator() {
        return null;
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        this.tryAdvance(action);
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
