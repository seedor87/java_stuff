package Utils.StreamUtils.Spliterators;


import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StreamUtils.Interfaces.BinaryPredicate;
import Utils.StreamUtils.Interfaces.NaryPredicate;
import Utils.StreamUtils.Interfaces.UnaryPredicate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.sun.jmx.remote.internal.ArrayQueue;
import java.util.Comparator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.*;

import static Utils.Console.Printing.println;
import static Utils.StreamUtils.Methods.takeWhile;

public class GenericTakeWhileSpliterator<T> implements Spliterator<T>, Cloneable {
    private Spliterator<T> source;
    protected NaryPredicate<T> condition;
    protected final AtomicBoolean found = new AtomicBoolean();
    protected int transformationSize;
    protected ArrayQueue<T> queue;

    public GenericTakeWhileSpliterator(Spliterator<T> source, NaryPredicate<T> predicate) {
        this.source = source;
        this.condition = predicate;
        this.transformationSize = condition.getSize();
        this.queue = new ArrayQueue<>(this.transformationSize);
    }

    public boolean actionAccept(Consumer<? super T> action, T e) {
        queue.add(e);
        if(queue.size() >= transformationSize) {
            if (!condition.execute(queue)) {
                return false;
            }
            queue.remove(0);
        }
        action.accept(e);
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

    public static class TestingClass {

        @Rule
        public TestRule rule = new TimedRule();

        @Test
        public void test() {
            GenericTakeWhileSpliterator<Double> myGTWS1 = new DoubleTakeWhileSpliterator(
                DoubleStream.of(0, 1, 2, 4, 8, 16, 32, 64, 128).spliterator(),
                    (BinaryPredicate<Double>) (d1, d2) -> d1 + 8d > d2);
            println(StreamSupport.doubleStream((DoubleTakeWhileSpliterator) myGTWS1, false));


            AbstractPrimitiveTakeWhileSpliterator<Integer, IntConsumer, OfInt> myGTWS2 = new IntTakeWhileSpliterator(
                IntStream.of(0, 1, 2, 4, 8, 16, 32, 64, 128).spliterator(),
                    (UnaryPredicate<Integer>) (i) -> i < 16);
            println(StreamSupport.intStream((IntTakeWhileSpliterator) myGTWS2, false));


            LongTakeWhileSpliterator myGTWS3 = new LongTakeWhileSpliterator(
                LongStream.of(0, 1, 2, 4, 8, 16, 32, 64, 128).spliterator(),
                (UnaryPredicate<Long>) (i) -> true);
            println(StreamSupport.longStream(myGTWS3, false));

            println(takeWhile(Stream.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'), (UnaryPredicate<Character>) s -> s.compareTo('e') < 0));

            println(takeWhile(Stream.of("one", "two", "three", "four", "five", "six"), (BinaryPredicate<String>) (t1, t2) -> t1.length() <= t2.length()));
        }
    }
}
