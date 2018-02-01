package Utils.StreamUtils.Spliterators;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class IntReverseSpliterator implements Spliterator.OfInt {
    private OfInt source;
    private final Deque<Integer> deque = new ArrayDeque<>();

    public IntReverseSpliterator(OfInt spliterator) {
        this.source = spliterator;
    }

    @Override
    public OfInt trySplit() {
        OfInt prev = source.trySplit();
        if(prev == null) {
            return null;
        }

        OfInt me = source;
        source = prev;
        return new IntReverseSpliterator(me);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        while(source.tryAdvance((IntConsumer) deque::addFirst));
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
    public Comparator<? super Integer> getComparator() {
        Comparator<? super Integer> comparator = source.getComparator();
        return (comparator != null) ? comparator.reversed() : null;
    }

    @Override
    public void forEachRemaining(Consumer<? super Integer> action) {
        if(!deque.isEmpty() || tryAdvance(action)) {
            deque.forEach(action);
        }
    }
}
