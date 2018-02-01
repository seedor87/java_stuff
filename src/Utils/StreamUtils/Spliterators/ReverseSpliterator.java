package Utils.StreamUtils.Spliterators;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ReverseSpliterator <T> implements Spliterator<T> {
    private Spliterator<T> spliterator;
    private final Deque<T> deque = new ArrayDeque<>();

    public ReverseSpliterator(Spliterator<T> spliterator) {
        this.spliterator = spliterator;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        while(spliterator.tryAdvance(deque::addFirst));
        if(!deque.isEmpty()) {
            action.accept(deque.remove());
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<T> trySplit() {
        Spliterator<T> prev = spliterator.trySplit();
        if(prev == null) {
            return null;
        }

        Spliterator<T> me = spliterator;
        spliterator = prev;
        return new ReverseSpliterator(me);
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
    public Comparator<? super T> getComparator() {
        Comparator<? super T> comparator = spliterator.getComparator();
        return (comparator != null) ? comparator.reversed() : null;
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        if(!deque.isEmpty() || tryAdvance(action)) {
            deque.forEach(action);
        }
    }
}
