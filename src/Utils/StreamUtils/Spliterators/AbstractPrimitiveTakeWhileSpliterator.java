package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.PredicateInterfaces.BinaryPredicate;
import Utils.StreamUtils.PredicateInterfaces.UnaryPredicate;

import java.util.Spliterator;

public abstract class AbstractPrimitiveTakeWhileSpliterator<T, U, V extends Spliterator.OfPrimitive<T, U, V>> extends GenericTakeWhileSpliterator<T> {

    public abstract V getEmtpySpliterator();
    public abstract void actionAccept(U action, T e);

    public AbstractPrimitiveTakeWhileSpliterator(V source, UnaryPredicate<? super T> predicate) {
        super(source, predicate);
    }

    public AbstractPrimitiveTakeWhileSpliterator(V source, BinaryPredicate<? super T> predicate, T identity) {
        super(source, predicate, identity);
    }

    public boolean tryAdvance(U action) {
        return (!found.get() &&
            this.getSource().tryAdvance((e) -> {
                if (condition.test(prev, e)) {
                    this.prev = e;
                    this.actionAccept(action, e);
                } else {
                    found.set(true);
                }
            })
        );
    }

    @Override
    public V trySplit() {
        V prefix = (V) source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(found.get()) {
            return this.getEmtpySpliterator();
        }
        AbstractPrimitiveTakeWhileSpliterator clone;
        try {
            clone = (DoubleTakeWhileSpliterator) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        clone.setSource(prefix);
        return (V) clone;
    }
}
