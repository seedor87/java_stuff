package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryPredicate;

import java.util.Spliterator;

public abstract class PrimitiveTakeWhileSpliterator<T, U, V extends Spliterator.OfPrimitive<T, U, V>> extends GenericTakeWhileSpliterator<T> implements Spliterator.OfPrimitive<T, U, V> {
    @Override
    public abstract V getEmtpySpliterator();

    public PrimitiveTakeWhileSpliterator(V source, NaryPredicate<T> predicate) {
        super(source, predicate);
    }

    public abstract boolean actionAccept(U action, T e);

    public boolean tryAdvance(U action) {
        return (!found.get() &&
            this.getSource().tryAdvance((e) -> {
                if (!this.actionAccept(action, e)) {
                    found.set(true);
                }
            })
        );
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
        PrimitiveTakeWhileSpliterator clone;
        try {
            clone = (PrimitiveTakeWhileSpliterator) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        clone.setSource(prefix);
        return (V) clone;
    }
}
