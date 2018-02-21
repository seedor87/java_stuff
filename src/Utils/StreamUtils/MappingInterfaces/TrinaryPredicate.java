package Utils.StreamUtils.MappingInterfaces;

@FunctionalInterface
public interface TrinaryPredicate<T> extends NaryPredicate<T> {
    Boolean execute(T t1, T t2, T t3);
    @Override
    default int getSize() {
        return 3;
    }
    @Override
    default Boolean execute(T... args) {
        return execute(args[0], args[1], args[2]);
    }
}
