package Utils.StreamUtils.PredicateInterfaces;

public interface TernaryPredicate<T> extends NaryPredicate<T> {
    @Override
    boolean test(T t1, T t2, T t3);
    default boolean test(T... ts) {
        int len = ts.length;
        return test(
            ts[len-3],
            ts[len-2],
            ts[len-1]
        );
    }

}
