package Utils.StreamUtils.Interfaces;

import java.util.Collection;

public interface NaryPredicate<T> extends NaryMapping<Boolean, T> {
    Boolean execute(T... args);
    int getSize();
    default Boolean execute(Collection<T> arr) {
        return execute((T[]) arr.toArray());
    }
}
