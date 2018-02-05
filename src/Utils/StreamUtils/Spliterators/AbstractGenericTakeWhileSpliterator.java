package Utils.StreamUtils.Spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public abstract class AbstractGenericTakeWhileSpliterator<T> implements Spliterator<T>, Consumer<T>, Cloneable {
    private Spliterator<T> source;
    protected final AtomicBoolean found = new AtomicBoolean();

    public AbstractGenericTakeWhileSpliterator(Spliterator<T> source) {
        this.source = source;
    }

    @Override
    public abstract boolean tryAdvance(Consumer<? super T> action);

    @Override
    public Spliterator<T> trySplit() {
        Spliterator<T> prefix = source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(found.get()) {
            return Spliterators.emptySpliterator();
        }
        AbstractGenericTakeWhileSpliterator<T> clone;
        try {
            clone = (AbstractGenericTakeWhileSpliterator<T>) clone();
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
