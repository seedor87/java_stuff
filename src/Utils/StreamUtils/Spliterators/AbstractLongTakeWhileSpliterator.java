package Utils.StreamUtils.Spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public abstract class AbstractLongTakeWhileSpliterator implements Spliterator.OfLong, Consumer<Long>, Cloneable {
    private Spliterator.OfLong source;
    protected final AtomicBoolean found = new AtomicBoolean();

    public AbstractLongTakeWhileSpliterator(Spliterator.OfLong source) {
        this.source = source;
    }

    @Override
    public Spliterator.OfLong trySplit() {
        Spliterator.OfLong prefix = source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(found.get()) {
            return Spliterators.emptyLongSpliterator();
        }
        AbstractLongTakeWhileSpliterator clone;
        try {
            clone = (AbstractLongTakeWhileSpliterator) clone();
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
    public Comparator<? super Long> getComparator() {
        return source.getComparator();
    }

    public Spliterator.OfLong getSource() {
        return source;
    }

    public void setSource(Spliterator.OfLong source) {
        this.source = source;
    }

    @Override
    public void accept(Long aDouble) {

    }
}
