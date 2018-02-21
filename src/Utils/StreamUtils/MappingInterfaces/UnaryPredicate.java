package Utils.StreamUtils.MappingInterfaces;

public interface UnaryPredicate<T> extends NaryPredicate<T> {
    Boolean execute(T t);
    @Override
    default int getSize() {
        return 1;
    }
    @Override
    default Boolean execute(T... args) {
        return execute(args[0]);
    }
}
