package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.MappingInterfaces.*;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class GenericDropWhileSpliterator<T> implements Spliterator<T>, Cloneable {
    protected Spliterator<T> source;
    protected NaryPredicate<T> condition;
    protected final AtomicBoolean found = new AtomicBoolean();
    protected int conditionSize;
    protected ArrayQueue<T> queue;
    protected AtomicBoolean queueFilled = new AtomicBoolean();

    public GenericDropWhileSpliterator(Spliterator<T> source, NaryPredicate<T> predicate) {
        this.source = source;
        this.condition = predicate;
        this.conditionSize = (condition.getSize() > 0) ? condition.getSize() : 1;
        this.queue = new ArrayQueue<>(this.conditionSize);
    }

    public void actionAccept(Consumer<? super T> action, T e) {
        if (!found.get()) {
            queue.add(e);
            if (!queueFilled.get()) {
                if (queue.size() < conditionSize) {
                    return;
                }
                queueFilled.set(true);
                if (!condition.execute(queue)) {
                    for (int i = 0; i < conditionSize - 1; i++) {
                        action.accept(queue.get(i));
                    }
                }
            }
            if (!condition.execute(queue)) {
                found.set(true);
                action.accept(e);
            }
            queue.remove(0);
            return;
        }
        action.accept(e);
    }

    public boolean tryAdvance(Consumer<? super T> action) {
        return this.getSource().tryAdvance(t -> actionAccept(action, t));
    }

    public void forEachRemaining(Consumer<? super T> action) {
        while(!found.get()) if(!tryAdvance(action)) return;
        this.getSource().forEachRemaining(action);
    }

    public Spliterator<T> getEmtpySpliterator() {
        return Spliterators.emptySpliterator();
    }

    @Override
    public Spliterator<T> trySplit() {
        Spliterator<T> prefix = this.getSource().trySplit();
        if(prefix == null) {
            return null;
        }
        if(found.get()) {
            return this.getEmtpySpliterator();
        }
        GenericDropWhileSpliterator<T> clone;
        try {
            clone = (GenericDropWhileSpliterator<T>) clone();
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