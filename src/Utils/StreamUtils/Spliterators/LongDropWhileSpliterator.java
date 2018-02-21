package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryPredicate;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class LongDropWhileSpliterator extends PrimitiveDropWhileSpliterator<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {

    public LongDropWhileSpliterator(Spliterator.OfLong source, NaryPredicate<Long> predicate) {
        super(source, predicate);
    }

    @Override
    public void actionAccept(LongConsumer action, Long e) {
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
    public OfLong getEmtpySpliterator() {
        return Spliterators.emptyLongSpliterator();
    }
}

