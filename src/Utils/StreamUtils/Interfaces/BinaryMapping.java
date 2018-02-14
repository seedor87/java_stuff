package Utils.StreamUtils.Interfaces;

@FunctionalInterface
public interface BinaryMapping<S, T> extends NaryMapping<S, T> {
    S execute(T t1, T t2);
    @Override
    default int getSize() {
        return 2;
    }
    @Override
    default S execute(T... args) {
        return execute(args[0], args[1]);
    }
}
