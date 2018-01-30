package Utils.StreamUtils.Interfaces;

import java.util.function.Predicate;

public interface BiPredicate<T> extends Predicate<T> {
    boolean test(T prev, T curr);

    @Override
    default boolean test(T value) {
        return true;
    }
}
