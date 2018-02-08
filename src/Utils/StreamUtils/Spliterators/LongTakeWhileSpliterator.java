package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.PredicateInterfaces.BinaryPredicate;
import Utils.StreamUtils.PredicateInterfaces.UnaryPredicate;

import java.util.Spliterator;
import java.util.function.BiPredicate;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;

public class LongTakeWhileSpliterator extends AbstractLongTakeWhileSpliterator implements LongConsumer {
    private final BinaryPredicate<? super Long> condition;
    private Long prev;

    public LongTakeWhileSpliterator(Spliterator.OfLong source, BinaryPredicate<? super Long> predicate, Long identity) {
        super(source);
        this.condition = predicate;
        this.prev = identity;
    }

    public LongTakeWhileSpliterator(Spliterator.OfLong source, UnaryPredicate<? super Long> predicate) {
        super(source);
        this.condition = predicate;
    }

    @Override
    public void accept(long e) {
        this.prev = e;
    }

    @Override
    public boolean tryAdvance(LongConsumer action) {
        return (!found.get() &&
            this.getSource().tryAdvance((LongConsumer) (e) -> {
                if (condition.test(prev, e)) {
                    this.accept(e);
                    action.accept(e);
                } else {
                    found.set(true);
                }
            })
        );
    }
}
