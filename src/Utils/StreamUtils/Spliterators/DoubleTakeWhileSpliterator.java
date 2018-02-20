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
        if (!queueFilled) {
            if (queue.size() < transformationSize) {
                return true;
            }
            queueFilled = true;
            if (!condition.execute(queue)) {
                return false;
            }
            for (int i = 0; i < transformationSize-1; i++) {
                action.accept(queue.get(i));
            }
        }
        if (!condition.execute(queue)) {
            return false;
        }
        action.accept(e);
        queue.remove(0);
        return true;
    }
}
