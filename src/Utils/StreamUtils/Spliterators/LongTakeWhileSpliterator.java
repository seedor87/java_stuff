package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.PredicateInterfaces.BinaryPredicate;
import Utils.StreamUtils.PredicateInterfaces.UnaryPredicate;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class LongTakeWhileSpliterator extends AbstractPrimitiveTakeWhileSpliterator<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {

    public LongTakeWhileSpliterator(Spliterator.OfLong source, UnaryPredicate<? super Long> predicate) {
        super(source, predicate);
    }

    public LongTakeWhileSpliterator(Spliterator.OfLong source, BinaryPredicate<? super Long> predicate, Long identity) {
        super(source, predicate, identity);
    }

    @Override
    public OfLong getEmtpySpliterator() {
        return Spliterators.emptyLongSpliterator();
    }

    @Override
    public void actionAccept(LongConsumer action, Long e) {
        action.accept(e);
    }
}
