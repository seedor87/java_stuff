package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryPredicate;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class IntTakeWhileSpliterator extends AbstractPrimitiveTakeWhileSpliterator<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt {

    public IntTakeWhileSpliterator(Spliterator.OfInt source, NaryPredicate<Integer> predicate) {
        super(source, predicate);
    }

    @Override
    public OfInt getEmtpySpliterator() {
        return Spliterators.emptyIntSpliterator();
    }

    @Override
    public boolean actionAccept(IntConsumer action, Integer e) {
        queue.add(e);
        if (queue.size() >= transformationSize) {
            if (!condition.execute(queue)) {
                return false;
            }
            queue.remove(0);
        }
        action.accept(e);
        return true;
    }
}