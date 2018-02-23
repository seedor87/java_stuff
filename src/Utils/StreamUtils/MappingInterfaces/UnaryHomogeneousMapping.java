package Utils.StreamUtils.MappingInterfaces;

public interface UnaryHomogeneousMapping<T> extends NaryHomogeneousMapping<T> {
    T execute(T t);
    default int getSize() {
        return 1;
    }
    @Override
    default T execute(T... ts) {
        return execute(ts[0]);
    }
}