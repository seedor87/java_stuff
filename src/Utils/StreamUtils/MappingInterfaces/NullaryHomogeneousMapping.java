package Utils.StreamUtils.MappingInterfaces;

@FunctionalInterface
public interface NullaryHomogeneousMapping<T> extends NaryHomogeneousMapping<T> {
    T execute();
    @Override
    default int getSize() {
        return 0;
    }
    @Override
    default T execute(T... ts) {
        return execute();
    }
}