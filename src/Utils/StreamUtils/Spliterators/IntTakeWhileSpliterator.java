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
    public boolean actionAccept(IntConsumer action) {
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