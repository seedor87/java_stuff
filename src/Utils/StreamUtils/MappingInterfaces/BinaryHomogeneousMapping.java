package Utils.StreamUtils.MappingInterfaces;

@FunctionalInterface
public interface BinaryHomogeneousMapping<T> extends NaryHomogeneousMapping<T> {
    T execute(T t1, T t2);
    @Override
    default int getSize() {
        return 2;
    }
    @Override
    default T execute(T... ts) {
        return execute(ts[0], ts[1]);
    }
}
