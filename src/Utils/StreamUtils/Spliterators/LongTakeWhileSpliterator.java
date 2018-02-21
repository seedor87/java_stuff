package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryPredicate;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class LongTakeWhileSpliterator extends PrimitiveTakeWhileSpliterator<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {

    public LongTakeWhileSpliterator(Spliterator.OfLong source, NaryPredicate<Long> predicate) {
        super(source, predicate);
    }

    @Override
    public OfLong getEmtpySpliterator() {
        return Spliterators.emptyLongSpliterator();
    }

    @Override
    public boolean actionAccept(LongConsumer action, Long e) {
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
