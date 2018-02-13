package Utils.StreamUtils.VariadicFunctionalInterfaces;

@FunctionalInterface
public interface Transformation1<T> extends Transformation<T> {
    T execute(T t);
    @Override
    default T execute(T... args) {
        return execute(args[0]);
    }
}
