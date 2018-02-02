package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class ConcreteTakeWhileSpliterator<T> extends AbstractTakeWhileSpliterator<T> {
    private final Predicate<? super T> condition;

    public ConcreteTakeWhileSpliterator(Spliterator<T> source, Predicate<? super T> predicate) {
        super(source);
        this.condition = predicate;
    }

    @Override
    public void accept(T o) {}

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return (!found.get() &&
            this.getSource().tryAdvance((e) -> {
                if (condition.test(e)) {
                    action.accept(e);
                } else {
                    found.set(true);
                }
            })
        );
    }
}