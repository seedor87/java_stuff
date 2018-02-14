package Utils.StreamUtils.Interfaces;

@FunctionalInterface
public interface TrinaryMapping<S, T> extends NaryMapping<S, T> {
    S execute(T t1, T t2, T t3);
    @Override
    default int getSize() {
        return 3;
    }
    @Override
    default S execute(T... args) {
        return execute(args[0], args[1], args[2]);
    }
}
