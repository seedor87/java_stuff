package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.PredicateInterfaces.BinaryPredicate;
import Utils.StreamUtils.PredicateInterfaces.UnaryPredicate;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntConsumer;

public class IntTakeWhileSpliterator implements Spliterator.OfInt, IntConsumer, Cloneable {
    private Spliterator.OfInt source;
    private final BinaryPredicate<? super Integer> condition;
    private Integer prev;
    protected final AtomicBoolean found = new AtomicBoolean();

    public IntTakeWhileSpliterator(Spliterator.OfInt source, BinaryPredicate<? super Integer> predicate, Integer identity) {
        this.source = source;
        this.condition = predicate;
        this.prev = identity;
    }

    public IntTakeWhileSpliterator(Spliterator.OfInt source, UnaryPredicate<? super Integer> predicate) {
        this.source = source;
        this.condition = predicate;
    }

    @Override
    public void accept(int e) {
        this.prev = e;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        return (!found.get() &&
            this.getSource().tryAdvance((IntConsumer) (e) -> {
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
    public Spliterator.OfInt trySplit() {
        Spliterator.OfInt prefix = source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(found.get()) {
            return Spliterators.emptyIntSpliterator();
        }
        IntTakeWhileSpliterator clone;
        try {
            clone = (IntTakeWhileSpliterator) clone();
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
