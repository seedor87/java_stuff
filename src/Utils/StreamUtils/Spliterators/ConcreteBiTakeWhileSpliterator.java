package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class ConcreteBiTakeWhileSpliterator<T> extends AbstractTakeWhileSpliterator<T> {
    private final BiPredicate<? super T, ? super T> condition;
    private T prev;

    public ConcreteBiTakeWhileSpliterator(Spliterator<T> source, BiPredicate<? super T, ? super T> predicate, T identity) {
        super(source);
        this.condition = predicate;
        this.prev = identity;
    }

    @Override
    public void accept(T e) { this.prev = e; }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return (!found.get() &&
            this.getSource().tryAdvance((e) -> {
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