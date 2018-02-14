package Utils.StreamUtils.Interfaces;

public interface QuaternaryMapping<S, T> extends NaryMapping<S, T> {
    S execute(T t1, T t2, T t3, T t4);
    @Override
    default int getSize() {
        return 4;
    }
    @Override
    default S execute(T... args) {
        return execute(args[0], args[1], args[2], args[3]);
    }
}
