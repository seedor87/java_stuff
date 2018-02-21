package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;
import java.util.function.*;

public abstract class PrimitiveBoundedSpliterator<T, U, V extends Spliterator.OfPrimitive<T, U, V>> extends GenericBoundedSpliterator<T> implements Spliterator.OfPrimitive<T, U, V> {

    public abstract boolean tryAdvance(U action);

    public PrimitiveBoundedSpliterator(T initialization, Predicate<T> termination, UnaryOperator<T> incrementation) {
        super(initialization, termination, incrementation);
    }

    @Override
    public V trySplit() {
        V prefix = this.trySplit();
        if(prefix == null) {
            return null;
        }
        PrimitiveBoundedSpliterator<T, U, V> clone;
        try {
            clone = (PrimitiveBoundedSpliterator<T, U, V>) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        return (V) clone;
    }
}
