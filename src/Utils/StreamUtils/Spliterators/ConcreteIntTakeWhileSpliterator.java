package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

public class ConcreteIntTakeWhileSpliterator extends AbstractIntTakeWhileSpliterator {
    private final IntPredicate condition;

    public ConcreteIntTakeWhileSpliterator(Spliterator.OfInt source, IntPredicate predicate) {
        super(source);
        this.condition = predicate;
    }

    @Override
    public boolean tryAdvance(IntConsumer action) {
        return (!found.get() &&
            this.getSource().tryAdvance((IntConsumer) (e) -> {
                if (condition.test(e)) {
                    action.accept(e);
                } else {
                    found.set(true);
                }
            })
        );
    }
}

