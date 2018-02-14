package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryMapping;

import java.util.Spliterator;

public abstract class AbstractPrimitiveVariadicSpliterator<T, U, V extends Spliterator.OfPrimitive<T, U, V>> extends GenericVariadicSpliterator<T> {

    @Override
    public abstract V getEmtpySpliterator();

    public abstract boolean actionAccept(U action);

    public AbstractPrimitiveVariadicSpliterator(V source, NaryMapping<T, T> mapping, Process process) {
        super(source, mapping, process);
    }

    public AbstractPrimitiveVariadicSpliterator(V source, NaryMapping<T, T> mapping) {
        super(source, mapping);
    }

    public boolean tryAdvance(U action) {
        return (this.getSource().tryAdvance((e) -> {
            queue.add(e);
            if (this.actionAccept(action)) {
                this.algorithm.apply(this.queue);
            }
        }));
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
        AbstractPrimitiveVariadicSpliterator clone;
        try {
            clone = (AbstractPrimitiveVariadicSpliterator) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        clone.setSource(prefix);
        return (V) clone;
    }
}
