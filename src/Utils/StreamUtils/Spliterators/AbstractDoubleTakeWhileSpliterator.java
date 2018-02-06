package Utils.StreamUtils.Spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractDoubleTakeWhileSpliterator implements Spliterator.OfDouble, Cloneable {
    private Spliterator.OfDouble source;
    protected final AtomicBoolean found = new AtomicBoolean();

    public AbstractDoubleTakeWhileSpliterator(Spliterator.OfDouble source) {
        this.source = source;
    }

    @Override
    public Spliterator.OfDouble trySplit() {
        Spliterator.OfDouble prefix = source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(found.get()) {
            return Spliterators.emptyDoubleSpliterator();
        }
        AbstractDoubleTakeWhileSpliterator clone;
        try {
            clone = (AbstractDoubleTakeWhileSpliterator) clone();
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
    public Comparator<? super Double> getComparator() {
        return source.getComparator();
    }

    public Spliterator.OfDouble getSource() {
        return source;
    }

    public void setSource(Spliterator.OfDouble source) {
        this.source = source;
    }
}
