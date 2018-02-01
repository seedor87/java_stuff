package Utils.StreamUtils.Spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class BiIntTakeWhileSpliterator implements Spliterator<Integer>, Consumer<Integer>, Cloneable {
    private final BiPredicate<? super Integer, ? super Integer> condition;
    private final AtomicBoolean checked = new AtomicBoolean();
    private Spliterator<Integer> source;
    private Integer prev;

    public BiIntTakeWhileSpliterator(Spliterator<Integer> source, BiPredicate<? super Integer, ? super Integer> predicate, Integer identity) {
        this.condition = predicate;
        this.source = source;
        this.prev = identity;
    }

    @Override
    public void accept(Integer i) { this.prev = i; }

    @Override
    public boolean tryAdvance(Consumer<? super Integer> action) {
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
    public Spliterator<Integer> trySplit() {
        Spliterator<Integer> prefix = source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(checked.get()) {
            return Spliterators.emptySpliterator();
        }
        BiIntTakeWhileSpliterator clone;
        try {
            clone = (BiIntTakeWhileSpliterator) clone();
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
