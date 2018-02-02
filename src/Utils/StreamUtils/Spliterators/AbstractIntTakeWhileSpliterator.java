package Utils.StreamUtils.Spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public abstract class AbstractIntTakeWhileSpliterator implements Spliterator.OfInt, Consumer<Integer>, Cloneable {
    private Spliterator.OfInt source;
    protected final AtomicBoolean found = new AtomicBoolean();

    public AbstractIntTakeWhileSpliterator(Spliterator.OfInt source) {
        this.source = source;
    }

    @Override
    public Spliterator.OfInt trySplit() {
        Spliterator.OfInt prefix = source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(found.get()) {
            return Spliterators.emptyIntSpliterator();
        }
        AbstractIntTakeWhileSpliterator clone;
        try {
            clone = (AbstractIntTakeWhileSpliterator) clone();
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
    public Comparator<? super Integer> getComparator() {
        return source.getComparator();
    }

    public Spliterator.OfInt getSource() {
        return source;
    }

    public void setSource(Spliterator.OfInt source) {
        this.source = source;
    }

}
