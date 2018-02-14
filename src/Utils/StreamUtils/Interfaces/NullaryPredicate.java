package Utils.StreamUtils.Interfaces;

public interface NullaryPredicate<T> extends NaryPredicate<T> {
    Boolean execute();
    @Override
    default int getSize() {
        return 0;
    }
    @Override
    default Boolean execute(T... args) {
        return execute();
    }
}