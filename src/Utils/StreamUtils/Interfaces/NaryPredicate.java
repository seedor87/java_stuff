package Utils.StreamUtils.Interfaces;

import java.util.Collection;

public interface NaryPredicate<T> extends NaryMapping<Boolean, T> {
    Boolean execute(T... args);
    default Boolean execute(Collection<T> arr) {
        return execute((T[]) arr.toArray());
    }
}
