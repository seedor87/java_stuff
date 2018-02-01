package Utils.StreamUtils.Spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class BiTakeWhileSpliterator<T> implements Spliterator<T>, Consumer<T>, Cloneable {
    private final BiPredicate<? super T, ? super T> condition;
    private final AtomicBoolean checked = new AtomicBoolean();
    private Spliterator<T> source;
    private T prev;

    public BiTakeWhileSpliterator(Spliterator<T> source, BiPredicate<? super T, ? super T> predicate, T identity) {
        this.condition = predicate;
        this.source = source;
        this.prev = identity;
    }

    @Override
    public void accept(T o) { this.prev = o;}

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return (!checked.get() &&
            source.tryAdvance((e) -> {
                if (condition.test(prev, e)) {
                    this.accept(e);
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
        BiTakeWhileSpliterator<T> clone;
        try {
            clone = (BiTakeWhileSpliterator<T>) clone();
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