package Utils.StreamUtils.Spliterators;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Spliterator;
import java.util.function.Consumer;

public class ReverseSpliterator <T> implements Spliterator<T> {
    private Spliterator<T> source;
    private final Deque<T> deque = new ArrayDeque<>();

    public ReverseSpliterator(Spliterator<T> spliterator) {
        this.source = spliterator;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        while(source.tryAdvance(deque::addFirst));
        if(!deque.isEmpty()) {
            action.accept(deque.remove());
            return true;
        }
        return false;
    }

    @Override
    public Spliterator<T> trySplit() {
        Spliterator<T> prev = source.trySplit();
        if(prev == null) {
            return null;
        }

        Spliterator<T> me = source;
        source = prev;
        return new ReverseSpliterator(me);
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
    public Comparator<? super T> getComparator() {
        Comparator<? super T> comparator = source.getComparator();
        return (comparator != null) ? comparator.reversed() : null;
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        if(!deque.isEmpty() || tryAdvance(action)) {
            deque.forEach(action);
        }
    }
}
