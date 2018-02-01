package Utils.StreamUtils.Spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TakeWhileSpliterator<T> implements Spliterator<T>, Consumer<T>, Cloneable {
    private final Predicate<? super T> condition;
    private final AtomicBoolean checked = new AtomicBoolean();
    private Spliterator<T> source;

    public TakeWhileSpliterator(Spliterator<T> source, Predicate<? super T> predicate) {
        this.condition = predicate;
        this.source = source;
    }

    @Override
    public void accept(T t) {}

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return (!checked.get() &&
            source.tryAdvance((e) -> {
                if (condition.test(e)) {
                    action.accept(e);
                } else {
                    checked.set(true);
                }
            })
        );
    }

    @Override
    public Spliterator<T> trySplit() {
        Spliterator<T> prefix = source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(checked.get()) {
            return Spliterators.emptySpliterator();
        }
        TakeWhileSpliterator<T> clone;
        try {
            clone = (TakeWhileSpliterator<T>) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        clone.source = prefix;
        return clone;
    }

    @Override
    public long estimateSize() {
        return source.estimateSize();
    }

    @Override
    public int characteristics() {
        return source.characteristics() & (DISTINCT | SORTED | NONNULL);
    }

    @Override
    public Comparator<? super T> getComparator() {
        return source.getComparator();
    }
}