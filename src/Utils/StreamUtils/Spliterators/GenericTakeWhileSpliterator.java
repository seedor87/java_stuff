package Utils.StreamUtils.Spliterators;


import Utils.StreamUtils.MappingInterfaces.*;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class GenericTakeWhileSpliterator<T> implements Spliterator<T>, Cloneable {
    private Spliterator<T> source;
    protected NaryPredicate<T> condition;
    protected final AtomicBoolean found = new AtomicBoolean();
    protected int conditionSize;
    protected ArrayQueue<T> queue;
    protected AtomicBoolean queueFilled = new AtomicBoolean();

    public GenericTakeWhileSpliterator(Spliterator<T> source, NaryPredicate<T> predicate) {
        this.source = source;
        this.condition = predicate;
        this.conditionSize = (condition.getSize() > 0) ? condition.getSize() : 1;
        this.queue = new ArrayQueue<>(this.conditionSize);
    }

    public boolean actionAccept(Consumer<? super T> action, T e) {
        queue.add(e);
        if (!queueFilled.get()) {
            if (queue.size() < conditionSize) { return true; }
            queueFilled.set(true);
            if (!condition.execute(queue)) { return false; }
            for (int i = 0; i < conditionSize; i++) { action.accept(queue.get(i)); }
            queue.remove(0);
            return true;
        }
        if (!condition.execute(queue)) { return false; }
        action.accept(e);
        queue.remove(0);
        return true;
    }

    public Spliterator<T> getEmtpySpliterator() {
        return Spliterators.emptySpliterator();
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return (!found.get() &&
            this.getSource().tryAdvance((e) -> {
                if (!this.actionAccept(action, e)) {
                    found.set(true);
                }
            })
        );
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
