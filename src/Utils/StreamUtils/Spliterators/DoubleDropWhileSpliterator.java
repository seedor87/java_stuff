package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryPredicate;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class DoubleDropWhileSpliterator extends PrimitiveDropWhileSpliterator<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble {

    public DoubleDropWhileSpliterator(Spliterator.OfDouble source, NaryPredicate<Double> predicate) {
        super(source, predicate);
    }

    @Override
    public void actionAccept(DoubleConsumer action, Double e) {
        if (!found.get()) {
            queue.add(e);
            if (!queueFilled.get()) {
                if (queue.size() < conditionSize) {
                    return;
                }
                queueFilled.set(true);
                if (!condition.execute(queue)) {
                    for (int i = 0; i < conditionSize - 1; i++) {
                        action.accept(queue.get(i));
                    }
                }
            }
            if (!condition.execute(queue)) {
                found.set(true);
                action.accept(e);
            }
            queue.remove(0);
            return;
        }
        action.accept(e);
    }

    @Override
    public OfDouble getEmtpySpliterator() {
        return Spliterators.emptyDoubleSpliterator();
    }
}

