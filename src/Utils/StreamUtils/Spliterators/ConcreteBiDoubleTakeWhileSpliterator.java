package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.BiPredicate;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;

public class ConcreteBiDoubleTakeWhileSpliterator extends AbstractDoubleTakeWhileSpliterator {
    private final BiPredicate<? super Double, ? super Double> condition;
    private Double prev;

    public ConcreteBiDoubleTakeWhileSpliterator(Spliterator.OfDouble source, BiPredicate<? super Double, ? super Double> predicate, Double identity) {
        super(source);
        this.condition = predicate;
        this.prev = identity;
    }

    @Override
    public void accept(Double d) {
        this.prev = d;
    }

    @Override
    public boolean tryAdvance(DoubleConsumer action) {
        return (!found.get() &&
                this.getSource().tryAdvance((DoubleConsumer) (e) -> {
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
