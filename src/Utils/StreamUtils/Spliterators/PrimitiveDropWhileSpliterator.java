package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.MappingInterfaces.NaryPredicate;

import java.util.Spliterator;

public abstract class PrimitiveDropWhileSpliterator<T, U, V extends Spliterator.OfPrimitive<T, U, V>> extends GenericDropWhileSpliterator<T> implements Spliterator.OfPrimitive<T, U, V> {
    @Override
    public abstract V getEmtpySpliterator();

    public PrimitiveDropWhileSpliterator(V source, NaryPredicate<T> predicate) {
        super(source, predicate);
    }

    public abstract void actionAccept(U action, T e);

    @Override
    public boolean tryAdvance(U action) {
        return this.getSource().tryAdvance(t -> actionAccept(action, t));
    }

    @Override
    public V trySplit() {
        V prefix = (V) this.getSource().trySplit();
        if(prefix == null) {
            return null;
        }
        if(found.get()) {
            return this.getEmtpySpliterator();
        }
        PrimitiveDropWhileSpliterator clone;
        try {
            clone = (PrimitiveDropWhileSpliterator) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        clone.setSource(prefix);
        return (V) clone;
    }
}
