package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.PredicateInterfaces.*;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class DoubleTakeWhileSpliterator extends AbstractPrimitiveTakeWhileSpliterator<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble, Cloneable {

    public DoubleTakeWhileSpliterator(Spliterator.OfDouble source, UnaryPredicate<? super Double> predicate) {
        super(source, predicate);
    }

    public DoubleTakeWhileSpliterator(Spliterator.OfDouble source, BinaryPredicate<? super Double> predicate, Double identity) {
        super(source, predicate, identity);
    }

    @Override
    public OfDouble getEmtpySpliterator() {
        return Spliterators.emptyDoubleSpliterator();
    }

    @Override
    public void actionAccept(DoubleConsumer action, Double e) {
        action.accept(e);
    }
}
