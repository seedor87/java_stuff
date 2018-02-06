package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.DoubleConsumer;
import java.util.function.DoublePredicate;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

public class ConcreteDoubleTakeWhileSpliterator extends AbstractDoubleTakeWhileSpliterator {
    private final DoublePredicate condition;

    public ConcreteDoubleTakeWhileSpliterator(Spliterator.OfDouble source, DoublePredicate predicate) {
        super(source);
        this.condition = predicate;
    }

    @Override
    public boolean tryAdvance(DoubleConsumer action) {
        return (!found.get() &&
                this.getSource().tryAdvance((DoubleConsumer) (e) -> {
                    if (condition.test(e)) {
                        action.accept(e);
                    } else {
                        found.set(true);
                    }
                })
        );
    }
}

