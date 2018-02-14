package Utils.StreamUtils.Interfaces;

public interface UnaryHomogenousMapping<T> extends NaryMapping<T, T> {
    T execute(T t);
    default int getSize() {
        return 1;
    }
    @Override
    default T execute(T... args) {
        return execute(args[0]);
    }
}