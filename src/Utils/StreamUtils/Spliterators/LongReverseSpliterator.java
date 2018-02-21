package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.LongConsumer;

public class LongReverseSpliterator extends PrimitiveReverseSpliterator<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {

    public LongReverseSpliterator(OfLong spliterator) {
        super(spliterator);
    }

    @Override
    public boolean tryAdvance(LongConsumer action) {
        while(source.tryAdvance(deque::addFirst));
        if(!deque.isEmpty()) {
            action.accept(deque.remove());
            return true;
        }
        return false;
    }
}