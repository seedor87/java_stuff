package Utils.StreamUtils.Interfaces;

@FunctionalInterface
public interface BinaryHomogenousMapping<T> extends NaryMapping<T, T> {
    T execute(T t1, T t2);
    @Override
    default int getSize() {
        return 2;
    }
    @Override
    default T execute(T... args) {
        return execute(args[0], args[1]);
    }
}
