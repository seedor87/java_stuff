package Utils.StreamUtils.Interfaces;

@FunctionalInterface
public interface NullaryMapping<S, T> extends NaryMapping<S, T> {
    S execute();
    @Override
    default int getSize() {
        return 0;
    }
    @Override
    default S execute(T... args) {
        return execute();
    }
}