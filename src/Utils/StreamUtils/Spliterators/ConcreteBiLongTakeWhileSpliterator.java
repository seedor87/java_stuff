package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.BiPredicate;
import java.util.function.LongConsumer;

public class ConcreteBiLongTakeWhileSpliterator extends AbstractLongTakeWhileSpliterator {
    private final BiPredicate<? super Long, ? super Long> condition;
    private Long prev;

    public ConcreteBiLongTakeWhileSpliterator(Spliterator.OfLong source, BiPredicate<? super Long, ? super Long> predicate, Long identity) {
        super(source);
        this.condition = predicate;
        this.prev = identity;
    }

    @Override
    public boolean tryAdvance(LongConsumer action) {
        return (!found.get() &&
                this.getSource().tryAdvance((LongConsumer) (e) -> {
                    if (condition.test(prev, e)) {
                        this.prev = e;
                        action.accept(e);
                    } else {
                        found.set(true);
                    }
                })
        );
    }
}
