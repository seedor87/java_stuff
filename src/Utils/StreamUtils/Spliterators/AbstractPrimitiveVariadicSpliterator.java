package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.VariadicFunctionalInterfaces.Transformation;

import java.util.Spliterator;
import java.util.function.Consumer;

public abstract class AbstractPrimitiveVariadicSpliterator<T, U, V extends Spliterator.OfPrimitive<T, U, V>> extends GenericVariadicSpliterator<T> {

    @Override
    public abstract V getEmtpySpliterator();

    public abstract boolean actionAccept(U action);

    public AbstractPrimitiveVariadicSpliterator(V source, Transformation<T> transformation) {
        super(source, transformation);
    }

    public boolean tryAdvance(U action) {
        return (this.getSource().tryAdvance((e) -> {
            queue.add(e);
            if (this.actionAccept(action)) {
                queue.remove(0);
//                queue.clear();
            }
        }));
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
