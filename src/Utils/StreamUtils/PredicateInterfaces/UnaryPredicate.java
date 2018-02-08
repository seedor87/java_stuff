package Utils.StreamUtils.PredicateInterfaces;

public interface UnaryPredicate<T> extends BinaryPredicate<T> {
    @Override
    boolean test(T t);
    default boolean test(T t1, T t2) { return test(t2); }
}
