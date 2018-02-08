package Utils.StreamUtils.PredicateInterfaces;

public interface NullaryPredicate<T> extends UnaryPredicate<T> {
    @Override
    boolean test();
    default boolean test(T t) { return test(); }
}