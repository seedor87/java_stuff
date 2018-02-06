package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import java.util.function.LongConsumer;
import java.util.function.LongPredicate;

public class ConcreteLongTakeWhileSpliterator extends AbstractLongTakeWhileSpliterator {
    private final LongPredicate condition;

    public ConcreteLongTakeWhileSpliterator(Spliterator.OfLong source, LongPredicate predicate) {
        super(source);
        this.condition = predicate;
    }

    @Override
    public boolean tryAdvance(LongConsumer action) {
        return (!found.get() &&
                this.getSource().tryAdvance((LongConsumer) (e) -> {
                    if (condition.test(e)) {
                        action.accept(e);
                    } else {
                        found.set(true);
                    }
                })
        );
    }
}
