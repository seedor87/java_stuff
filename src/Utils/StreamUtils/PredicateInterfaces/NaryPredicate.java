package Utils.StreamUtils.PredicateInterfaces;

public interface NaryPredicate<T> {
    default boolean test() { return true; }
    default boolean test(T t) { return test(); }
    default boolean test(T t1, T t2) { return test(t2); }
    default boolean test(T t1, T t2, T t3) { return test(t2, t3); }
    boolean test(T... ts);
}
