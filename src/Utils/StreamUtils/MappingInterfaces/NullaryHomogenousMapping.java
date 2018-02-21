package Utils.StreamUtils.MappingInterfaces;

@FunctionalInterface
public interface NullaryHomogenousMapping<T> extends NaryMapping<T, T> {
    T execute();
    @Override
    default int getSize() {
        return 0;
    }
    @Override
    default T execute(T... args) {
        return execute();
    }
}