package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.VariadicFunctionalInterfaces.*;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class GenericVariadicSpliterator<T> implements Spliterator<T>, Cloneable {

    protected Spliterator<T> source;
    protected Transformation<T> transformation;
    protected final AtomicBoolean found = new AtomicBoolean();
    protected int transformationSize;
    protected ArrayQueue<T> queue;

    public GenericVariadicSpliterator(Spliterator<T> source, Transformation<T> transformation) {
        this.source = source;
        this.transformation = transformation;
        this.transformationSize = transformation.getSize();
        this.queue = new ArrayQueue<>(this.transformationSize);
    }

    public boolean actionAccept(Consumer<? super T> action) {
        if(queue.size() >= transformationSize) {
            action.accept(transformation.execute(queue));
            return true;
        }
       return false;
    }

    public Spliterator<T> getEmtpySpliterator() {
        return Spliterators.emptySpliterator();
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return (this.getSource().tryAdvance((e) -> {
            queue.add(e);
            if (this.actionAccept(action)) {
                queue.remove(0);
//                queue.clear();
            }
        }));
    }

    @Override
    public Spliterator<T> trySplit() {
        Spliterator<T> prefix = source.trySplit();
        if(prefix == null) {
            return null;
        }
        if(found.get()) {
            return this.getEmtpySpliterator();
        }
        GenericTakeWhileSpliterator<T> clone;
        try {
            clone = (GenericTakeWhileSpliterator<T>) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        clone.setSource(prefix);
        return clone;
    }

    public Spliterator<T> getSource() {
        return source;
    }

    public void setSource(Spliterator<T> source) {
        this.source = source;
    }

    @Override
    public long estimateSize() {
        return source.estimateSize();
    }

    @Override
    public int characteristics() {
        return source.characteristics();
    }

    @Override
    public Comparator<? super T> getComparator() {
        return source.getComparator();
    }
}