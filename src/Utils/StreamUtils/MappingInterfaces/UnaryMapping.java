package Utils.StreamUtils.MappingInterfaces;

@FunctionalInterface
public interface UnaryMapping<S, T> extends NaryMapping<S, T> {
    S execute(T t);
    default int getSize() {
        return 1;
    }
    @Override
    default S execute(T... args) {
        return execute(args[0]);
    }
}
