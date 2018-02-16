package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryPredicate;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class LongTakeWhileSpliterator extends AbstractPrimitiveTakeWhileSpliterator<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {

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
