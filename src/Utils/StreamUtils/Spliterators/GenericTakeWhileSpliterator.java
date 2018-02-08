package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.PredicateInterfaces.BinaryPredicate;
import Utils.StreamUtils.PredicateInterfaces.UnaryPredicate;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class GenericTakeWhileSpliterator<T> implements Spliterator<T>, Consumer<T>, Cloneable {
    private Spliterator<T> source;
    private final BinaryPredicate<? super T> condition;
    private T prev;
    protected final AtomicBoolean found = new AtomicBoolean();

    public GenericTakeWhileSpliterator(Spliterator<T> source, UnaryPredicate<T> predicate) {
        this.source = source;
        this.condition = predicate;
    }

    public GenericTakeWhileSpliterator(Spliterator<T> source, BinaryPredicate<T> predicate, T identity) {
        this.source = source;
        this.condition = predicate;
        this.prev = identity;
    }

    @Override
    public void accept(T e) {
        this.prev = e;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return (!found.get() &&
            this.getSource().tryAdvance((e) -> {
                if (condition.test(prev, e)) {
                    this.accept(e);
                    action.accept(e);
                } else {
                    found.set(true);
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
        if(found.get()) {
            return Spliterators.emptySpliterator();
        }
        GenericTakeWhileSpliterator<T> clone;
        try {
            clone = (GenericTakeWhileSpliterator<T>) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        clone.setSource(prefix);
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

    public Spliterator<T> getSource() {
        return source;
    }

    public void setSource(Spliterator<T> source) {
        this.source = source;
    }
}