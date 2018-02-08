package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.PredicateInterfaces.*;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.DoubleConsumer;

public class DoubleTakeWhileSpliterator implements Spliterator.OfDouble, DoubleConsumer, Cloneable {
    private Spliterator.OfDouble source;
    private final BinaryPredicate<? super Double> condition;
    private Double prev;
    protected final AtomicBoolean found = new AtomicBoolean();

    public DoubleTakeWhileSpliterator(Spliterator.OfDouble source, UnaryPredicate<? super Double> predicate) {
        this.source = source;
        this.condition = predicate;
    }

    public DoubleTakeWhileSpliterator(Spliterator.OfDouble source, BinaryPredicate<? super Double> predicate, Double identity) {
        this.source = source;
        this.condition = predicate;
        this.prev = identity;
    }

    @Override
    public void accept(double e) {
        this.prev = e;
    }

    @Override
    public boolean tryAdvance(DoubleConsumer action) {
        return (!found.get() &&
            this.getSource().tryAdvance((DoubleConsumer) (e) -> {
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
    public Spliterator.OfDouble trySplit() {
        Spliterator.OfDouble prefix = source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(found.get()) {
            return Spliterators.emptyDoubleSpliterator();
        }
        DoubleTakeWhileSpliterator clone;
        try {
            clone = (DoubleTakeWhileSpliterator) clone();
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
