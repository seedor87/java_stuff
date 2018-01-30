package Utils.StreamUtils;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Spliterator;
import java.util.function.IntConsumer;

public class IntReverseSpliterator<T extends Integer> implements Spliterator.OfInt {
    private OfInt spliterator;
    private final Deque<Integer> deque = new ArrayDeque<>();

    public IntReverseSpliterator(OfInt spliterator) {
        this.spliterator = spliterator;
    }

    @Override
    public OfInt trySplit() {
        // After traveling started the spliterator don't contain elements!
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
}
