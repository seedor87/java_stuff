package Utils.StreamUtils.Spliterators;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

public class LongReverseSpliterator implements Spliterator.OfLong {
    private OfLong source;
    private final Deque<Long> deque = new ArrayDeque<>();

    public LongReverseSpliterator(OfLong spliterator) {
        this.source = spliterator;
    }

    @Override
    public OfLong trySplit() {
        OfLong prev = source.trySplit();
        if(prev == null) {
            return null;
        }

        OfLong me = source;
        source = prev;
        return new LongReverseSpliterator(me);
    }

    @Override
    public boolean tryAdvance(LongConsumer action) {
        while(source.tryAdvance((LongConsumer) deque::addFirst));
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
    public Comparator<? super Long> getComparator() {
        Comparator<? super Long> comparator = source.getComparator();
        return (comparator != null) ? comparator.reversed() : null;
    }

    @Override
    public void forEachRemaining(Consumer<? super Long> action) {
        if(!deque.isEmpty() || tryAdvance(action)) {
            deque.forEach(action);
        }
    }
}
