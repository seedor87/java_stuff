package Utils.StreamUtils.Spliterators;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

public class IntReverseSpliterator implements Spliterator.OfInt {
    private OfInt spliterator;
    private final Deque<Integer> deque = new ArrayDeque<>();

    public IntReverseSpliterator(OfInt spliterator) {
        this.spliterator = spliterator;
    }

    @Override
    public OfInt trySplit() {
        OfInt prev = spliterator.trySplit();
        if(prev == null) {
            return null;
        }

        OfInt me = spliterator;
        spliterator = prev;
        return new IntReverseSpliterator(me);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        while(spliterator.tryAdvance((IntConsumer) deque::addFirst));
        if(!deque.isEmpty()) {
            action.accept(deque.remove());
            return true;
        }
        return false;
    }

    @Override
    public long estimateSize() {
        return spliterator.estimateSize();
    }

    @Override
    public int characteristics() {
        return spliterator.characteristics();
    }

    @Override
    public Comparator<? super Integer> getComparator() {
        Comparator<? super Integer> comparator = spliterator.getComparator();
        return (comparator != null) ? comparator.reversed() : null;
    }

    @Override
    public void forEachRemaining(Consumer<? super Integer> action) {
        if(!deque.isEmpty() || tryAdvance(action)) {
            deque.forEach(action);
        }
    }
}
