package Utils.StreamUtils.Interfaces;

@FunctionalInterface
public interface UnaryMapping<S, T> extends NaryMapping<S, T> {
    S execute(T t);
    default int getSize() {
        return Integer.MAX_VALUE;
    }
    @Override
    default S execute(T... args) {
        return execute(args[0]);
    }
}
