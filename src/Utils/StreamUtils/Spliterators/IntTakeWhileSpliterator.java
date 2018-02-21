package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryPredicate;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

import static Utils.Console.Printing.println;

public class IntTakeWhileSpliterator extends PrimitiveTakeWhileSpliterator<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt {

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
        if (!queueFilled.get()) {
            if (queue.size() < conditionSize) {
                return true;
            }
            queueFilled.set(true);
            if (!condition.execute(queue)) {
                return false;
            }
            for (int i = 0; i < conditionSize -1; i++) {
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