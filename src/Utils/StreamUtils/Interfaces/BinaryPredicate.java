package Utils.StreamUtils.Interfaces;

public interface BinaryPredicate<T> extends NaryPredicate<T> {
    Boolean execute(T t1, T t2);
    @Override
    default int getSize() {
        return 2;
    }
    @Override
    default Boolean execute(T... args) {
        return execute(args[0], args[1]);
    }
}
