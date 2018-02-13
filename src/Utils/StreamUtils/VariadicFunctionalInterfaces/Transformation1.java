package Utils.StreamUtils.VariadicFunctionalInterfaces;

@FunctionalInterface
public interface Transformation1<T> extends Transformation<T> {
    T execute(T t);
    default int getSize() {
        return Integer.MAX_VALUE;
    }
    @Override
    default T execute(T... args) {
        return execute(args[0]);
    }
}
