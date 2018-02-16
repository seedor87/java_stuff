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
    public boolean actionAccept(DoubleConsumer action, Double e) {
        queue.add(e);
        if(queue.size() >= transformationSize) {
            if (!condition.execute(queue)) {
                return false;
            }
            queue.remove(0);
        }
        action.accept(e);
        return true;
    }
}
