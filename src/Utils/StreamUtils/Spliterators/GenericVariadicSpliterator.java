package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.VariadicFunctionalInterfaces.*;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static Utils.StreamUtils.Spliterators.GenericVariadicSpliterator.Process.SUBDIVIDED;

public class GenericVariadicSpliterator<T> implements Spliterator<T>, Cloneable {

    interface Processable<T> {
        void process(ArrayQueue<T> queue);
    }
    public enum Process {
        SUBDIVIDED(AbstractList::clear),
        NONSUBDVIDED(queue -> queue.remove(0));
        private Processable processable;
        Process(Processable processable) {
            this.processable = processable;
        }
        void apply(ArrayQueue queue) {
            this.processable.process(queue);
        }
    }
    protected Spliterator<T> source;
    protected Transformation<T> transformation;
    protected Process algorithm;
    protected final AtomicBoolean found = new AtomicBoolean();
    protected int transformationSize;
    protected ArrayQueue<T> queue;

    public GenericVariadicSpliterator(Spliterator<T> source, Transformation<T> transformation, Process algorithm) {
        this.source = source;
        this.transformation = transformation;
        this.transformationSize = transformation.getSize();
        this.queue = new ArrayQueue<>(this.transformationSize);
        this.algorithm = algorithm;
    }

    public GenericVariadicSpliterator(Spliterator<T> source, Transformation<T> transformation) {
        this(source, transformation, SUBDIVIDED);
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
                this.algorithm.apply(this.queue);
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