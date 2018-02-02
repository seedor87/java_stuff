package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.BiPredicate;
import java.util.function.IntConsumer;

public class ConcreteBiIntTakeWhileSpliterator extends AbstractIntTakeWhileSpliterator {
    private final BiPredicate<? super Integer, ? super Integer> condition;
    private Integer prev;

    public ConcreteBiIntTakeWhileSpliterator(Spliterator.OfInt source, BiPredicate<? super Integer, ? super Integer> predicate, Integer identity) {
        super(source);
        this.condition = predicate;
        this.prev = identity;
    }

    @Override
    public void accept(Integer i) {
        this.prev = i;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        return (!found.get() &&
            this.getSource().tryAdvance((IntConsumer) (e) -> {
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
