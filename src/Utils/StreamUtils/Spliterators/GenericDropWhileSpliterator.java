package Utils.StreamUtils.Spliterators;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StreamUtils.Interfaces.BinaryPredicate;
import Utils.StreamUtils.Interfaces.NaryPredicate;
import Utils.StreamUtils.Interfaces.UnaryPredicate;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static Utils.Console.Printing.println;

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
        this.conditionSize = condition.getSize();
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

    public static class TestingClass {

        @Rule
        public TestRule rule = new TimedRule();

        @Test
        public void test() {
            println(StreamSupport.stream(new GenericDropWhileSpliterator<>(IntStream.of(1,2,3,4).boxed().spliterator(), (UnaryPredicate<Integer>) i -> i < 3), false).map(i -> i));

            println(StreamSupport.stream(new GenericDropWhileSpliterator<>(IntStream.of(1,2,3,4).boxed().spliterator(), (BinaryPredicate<Integer>) (i1, i2) -> i1 < i2), false).map(i -> i));

            println(StreamSupport.stream(new GenericDropWhileSpliterator<>(IntStream.of(1,2,3,4).boxed().spliterator(), (BinaryPredicate<Integer>) (i1, i2) -> i1 > i2), false).map(i -> i));

            println(StreamSupport.stream(new GenericDropWhileSpliterator<>(IntStream.of(1,2,4,4).boxed().spliterator(), (BinaryPredicate<Integer>) (i1, i2) -> i1 < i2), false).map(i -> i));
        }
    }
}