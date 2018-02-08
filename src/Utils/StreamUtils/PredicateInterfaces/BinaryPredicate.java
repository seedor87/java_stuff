package Utils.StreamUtils.PredicateInterfaces;

public interface BinaryPredicate<T> extends TernaryPredicate<T> {
    @Override
    boolean test(T t1, T t2);
    default boolean test(T t1, T t2, T t3) { return test(t2, t3); }
}
