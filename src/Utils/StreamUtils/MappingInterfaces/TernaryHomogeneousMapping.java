package Utils.StreamUtils.MappingInterfaces;

@FunctionalInterface
public interface TernaryHomogeneousMapping<T> extends NaryHomogeneousMapping<T> {
    T execute(T t1, T t2, T t3);
    @Override
    default int getSize() {
        return 3;
    }
    @Override
    default T execute(T... ts) {
        return execute(ts[0], ts[1], ts[2]);
    }
}