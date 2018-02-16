package Utils.StreamUtils.Spliterators;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Spliterator;
import java.util.function.Consumer;

public class GenericReverseSpliterator<T> implements Spliterator<T>, Cloneable {
    protected Spliterator<T> source;
    protected final Deque<T> deque = new ArrayDeque<>();

    public GenericReverseSpliterator(Spliterator<T> spliterator) {
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
        return new GenericReverseSpliterator(me);
    }

    public Spliterator<T> getSource() {
        return source;
    }

    public void setSource(Spliterator<T> source) {
        this.source = source;
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
