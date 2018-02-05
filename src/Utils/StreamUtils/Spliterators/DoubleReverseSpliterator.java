package Utils.StreamUtils.Spliterators;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;

public class DoubleReverseSpliterator implements Spliterator.OfDouble {
    private OfDouble source;
    private final Deque<Double> deque = new ArrayDeque<>();

    public DoubleReverseSpliterator(OfDouble spliterator) {
        this.source = spliterator;
    }

    @Override
    public OfDouble trySplit() {
        OfDouble prev = source.trySplit();
        if(prev == null) {
            return null;
        }

        OfDouble me = source;
        source = prev;
        return new DoubleReverseSpliterator(me);
    }

    @Override
    public boolean tryAdvance(DoubleConsumer action) {
        while(source.tryAdvance((DoubleConsumer) deque::addFirst));
        if(!deque.isEmpty()) {
            action.accept(deque.remove());
            return true;
        }
        return false;
    }

    @Override
    public long estimateSize() {
        return source.estimateSize();
    }

    @Override
    public int characteristics() {
        return source.characteristics();
    }

    @Override
    public Comparator<? super Double> getComparator() {
        Comparator<? super Double> comparator = source.getComparator();
        return (comparator != null) ? comparator.reversed() : null;
    }

    @Override
    public void forEachRemaining(Consumer<? super Double> action) {
        if(!deque.isEmpty() || tryAdvance(action)) {
            deque.forEach(action);
        }
    }
}
