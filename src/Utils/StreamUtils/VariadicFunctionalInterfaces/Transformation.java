package Utils.StreamUtils.VariadicFunctionalInterfaces;

import java.util.Collection;

public interface Transformation<T> {
    T execute(T... args);
    int getSize();
    default T execute(Collection<T> arr) {
        return execute((T[]) arr.toArray());
    }
}
