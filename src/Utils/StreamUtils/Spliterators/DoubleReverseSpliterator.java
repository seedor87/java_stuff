package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.DoubleConsumer;

public class DoubleReverseSpliterator extends AbstractPrimitiveReverseSpliterator<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble {

    public DoubleReverseSpliterator(OfDouble spliterator) {
        super(spliterator);
    }

    @Override
    public boolean tryAdvance(DoubleConsumer action) {
        while(source.tryAdvance(deque::addFirst));
        if(!deque.isEmpty()) {
            action.accept(deque.remove());
            return true;
        }
        return false;
    }
}
