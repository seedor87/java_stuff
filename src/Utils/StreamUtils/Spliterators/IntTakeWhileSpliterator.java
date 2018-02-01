package Utils.StreamUtils.Spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

public class IntTakeWhileSpliterator implements Spliterator<Integer>, Consumer<Integer>, Cloneable {
    private final IntPredicate predicate;
    private final AtomicBoolean checked = new AtomicBoolean();
    private Spliterator<Integer> source;
    private Integer cur;

    public IntTakeWhileSpliterator(Spliterator<Integer> source, IntPredicate predicate) {
        this.predicate = predicate;
        this.source = source;
    }

    @Override
    public void accept(Integer integer) {
        this.cur = integer;
    }

    @Override
    public boolean tryAdvance(Consumer<? super Integer> action) {
        return (!checked.get() &&
            source.tryAdvance((e) -> {
                if (predicate.test(e)) {
                    action.accept(e);
                } else {
                    checked.set(true);
                }
            })
        );
    }

    @Override
    public Spliterator<Integer> trySplit() {
        Spliterator<Integer> prefix = source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(checked.get()) {
            return Spliterators.emptySpliterator();
        }
        IntTakeWhileSpliterator clone;
        try {
            clone = (IntTakeWhileSpliterator) clone();
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
    public Comparator<? super Integer> getComparator() {
        return source.getComparator();
    }
}
