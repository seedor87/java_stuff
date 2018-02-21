package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryHomogenousMapping;
import Utils.StreamUtils.Interfaces.NaryMapping;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static Utils.StreamUtils.Spliterators.GenericVariadicSpliterator.Process.SUBDIVIDED;

public class GenericVariadicSpliterator<T> implements Spliterator<T>, Cloneable {

    interface Processable {
        void process(ArrayQueue queue);
    }

    /**
     * The enum Process represents the fine detail of the algorithm that consumes elements of the stream in a specified manner.
     * SUBDIVIDED works by consuming the entire queue of size n after each successful pass of the mapping's execution.
     * NONSUBDIVIDED works by rolling the queue with each new element and (aside from the onset) the queue is never empty.
     *
     * E.G.
     * NONSUBDIVIDED {
     *     input of [1,2,3,4,5] with a Binary Mapper ->
     *          0th: [1,2],
     *          1st: [2,3],
     *          2nd: [3,4],
     *          3rd: [4,5]
     * }
     *  SUBDIVIDED iteration {
     *      input of [1,2,3,4,5] with a BinaryMapper ->
     *          0th: [1,2],
     *          1st: [3,4]
     *          2nd: will not exec as len([5]) < BinaryMapper.size()
     *  }
     *
     */
    public enum Process {
        SUBDIVIDED(ArrayQueue::clear),
        NONSUBDVIDED(queue -> queue.remove(0));
        private Processable processable;
        Process(Processable processable) {
            this.processable = processable;
        }
        void apply(ArrayQueue queue) {
            this.processable.process(queue);
        }
    }
    private Spliterator<T> source;
    protected NaryHomogenousMapping<T> mapping;
    protected Process algorithm;
    protected final AtomicBoolean found = new AtomicBoolean();
    protected int transformationSize;
    protected ArrayQueue<T> queue;

    public GenericVariadicSpliterator(Spliterator<T> source, NaryHomogenousMapping<T> mapping, Process algorithm) {
        this.source = source;
        this.mapping = mapping;
        this.transformationSize = mapping.getSize();
        this.queue = new ArrayQueue<>(this.transformationSize);
        this.algorithm = algorithm;
    }

    public GenericVariadicSpliterator(Spliterator<T> source, NaryHomogenousMapping<T> mapping) {
        this(source, mapping, SUBDIVIDED);
    }

    public boolean actionAccept(Consumer<? super T> action) {
        if(queue.size() >= transformationSize) {
            action.accept(mapping.execute(queue));
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