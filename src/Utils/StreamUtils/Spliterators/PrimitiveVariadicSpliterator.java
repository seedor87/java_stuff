package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.MappingInterfaces.NaryHomogenousMapping;

import java.util.Spliterator;

public abstract class PrimitiveVariadicSpliterator<T, U, V extends Spliterator.OfPrimitive<T, U, V>> extends GenericVariadicSpliterator<T> implements Spliterator.OfPrimitive<T, U, V> {

    @Override
    public abstract V getEmtpySpliterator();

    public abstract boolean actionAccept(U action);

    public PrimitiveVariadicSpliterator(V source, NaryHomogenousMapping<T> mapping, Process process) {
        super(source, mapping, process);
    }

    public PrimitiveVariadicSpliterator(V source, NaryHomogenousMapping<T> mapping) {
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
        PrimitiveVariadicSpliterator clone;
        try {
            clone = (PrimitiveVariadicSpliterator) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        clone.setSource(prefix);
        return (V) clone;
    }
}
