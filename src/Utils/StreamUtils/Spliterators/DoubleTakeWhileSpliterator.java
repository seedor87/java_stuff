package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.*;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class DoubleTakeWhileSpliterator extends AbstractPrimitiveTakeWhileSpliterator<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble {

    public DoubleTakeWhileSpliterator(Spliterator.OfDouble source, NaryPredicate<Double> predicate) {
        super(source, predicate);
    }

    @Override
    public OfDouble getEmtpySpliterator() {
        return Spliterators.emptyDoubleSpliterator();
    }

    @Override
    public boolean actionAccept(DoubleConsumer action) {
        if(queue.size() >= transformationSize) {
            if (!condition.execute(queue)) {
                if (queue.size() > 1) {
                    for (int i = 0; i < transformationSize-1; i++) {
                        action.accept(queue.remove(0));
                    }
                }
                return false;
            }
            action.accept(queue.remove(0));
        }
        return true;
    }
}
