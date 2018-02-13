package Utils.StreamUtils.VariadicFunctionalInterfaces;

import java.util.List;

@FunctionalInterface
public interface Transformation<T> {
    T execute(T... args);
    default T execute(List<T> arr) {
        return execute((T[]) arr.toArray());
    }
}
