package Utils.StreamUtils.MappingInterfaces;

@FunctionalInterface
public interface TrinaryHomogenousMapping<T> extends NaryHomogenousMapping<T> {
    T execute(T t1, T t2, T t3);
    @Override
    default int getSize() {
        return 3;
    }
    @Override
    default T execute(T... args) {
        return execute(args[0], args[1], args[2]);
    }
}