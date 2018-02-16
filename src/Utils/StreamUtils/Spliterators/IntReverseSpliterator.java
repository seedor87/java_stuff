package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.IntConsumer;

public class IntReverseSpliterator extends AbstractPrimitiveReverseSpliterator<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt {

    public IntReverseSpliterator(OfInt spliterator) {
        super(spliterator);
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        while(source.tryAdvance(deque::addFirst));
        if(!deque.isEmpty()) {
            action.accept(deque.remove());
            return true;
        }
        return false;
    }
}