package Utils.StreamUtils.Interfaces;

import java.util.Collection;

public interface NaryHomogenousMapping<T> extends NaryMapping<T, T> {
    T execute(T... args);
    int getSize();
    default T execute(Collection<T> arr) {
        return execute((T[]) arr.toArray());
    }
}

