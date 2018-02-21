package Utils.StreamUtils.MappingInterfaces;

@FunctionalInterface
public interface QuaternaryPredicate<T> extends NaryPredicate<T> {
    Boolean execute(T t1, T t2, T t3, T t4);
    @Override
    default int getSize() {
        return 4;
    }
    @Override
    default Boolean execute(T... args) {
        return execute(args[0], args[1], args[2], args[3]);
    }
}
