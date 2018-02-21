package Utils.StreamUtils.Spliterators;

import java.util.Spliterator;

public abstract class PrimitiveReverseSpliterator<T, U, V extends Spliterator.OfPrimitive<T, U, V>> extends GenericReverseSpliterator<T> implements Spliterator.OfPrimitive<T, U, V> {

    public abstract boolean tryAdvance(U action);

    public PrimitiveReverseSpliterator(Spliterator<T> source) {
        super(source);
    }

    @Override
    public V trySplit() {
        V prefix = this.trySplit();
        if(prefix == null) {
            return null;
        }
        PrimitiveReverseSpliterator<T, U, V> clone;
        try {
            clone = (PrimitiveReverseSpliterator<T, U, V>) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        return (V) clone;
    }
}

