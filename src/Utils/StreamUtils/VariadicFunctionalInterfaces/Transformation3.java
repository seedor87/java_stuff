package Utils.StreamUtils.VariadicFunctionalInterfaces;

@FunctionalInterface
public interface Transformation3<T> extends Transformation<T> {
    T execute(T t1, T t2, T t3);
    @Override
    default T execute(T... args) {
        return execute(args[0], args[1], args[2]);
    }
}
