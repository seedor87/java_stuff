package Utils.StreamUtils.Interfaces;

import java.util.Collection;

public interface NaryMapping<S, T> {
    S execute(T... args);
    int getSize();
    default S execute(Collection<T> arr) {
        return execute((T[]) arr.toArray());
    }
}
