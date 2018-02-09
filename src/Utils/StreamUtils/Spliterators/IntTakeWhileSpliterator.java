package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.PredicateInterfaces.BinaryPredicate;
import Utils.StreamUtils.PredicateInterfaces.UnaryPredicate;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class IntTakeWhileSpliterator extends AbstractPrimitiveTakeWhileSpliterator<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt, Cloneable {

    public IntTakeWhileSpliterator(Spliterator.OfInt source, UnaryPredicate<? super Integer> predicate) {
        super(source, predicate);
    }

    public IntTakeWhileSpliterator(Spliterator.OfInt source, BinaryPredicate<? super Integer> predicate, Integer identity) {
        super(source, predicate, identity);
    }

    @Override
    public OfInt getEmtpySpliterator() {
        return Spliterators.emptyIntSpliterator();
    }

    @Override
    public void actionAccept(IntConsumer action, Integer e) {
        action.accept(e);
    }
}