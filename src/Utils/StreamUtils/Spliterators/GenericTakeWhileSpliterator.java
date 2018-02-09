package Utils.StreamUtils.Spliterators;


import Utils.StreamUtils.PredicateInterfaces.BinaryPredicate;
import Utils.StreamUtils.PredicateInterfaces.UnaryPredicate;

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
    protected Spliterator<T> source;
    protected BinaryPredicate<? super T> condition;
    protected T prev;
    protected final AtomicBoolean found = new AtomicBoolean();

    public GenericTakeWhileSpliterator(Spliterator<T> source, UnaryPredicate<? super T> predicate) {
        this.source = source;
        this.condition = predicate;
    }

    public GenericTakeWhileSpliterator(Spliterator<T> source, BinaryPredicate<? super T> predicate, T identity) {
        this.source = source;
        this.condition = predicate;
        this.prev = identity;
    }

    public void actionAccept(Consumer<? super T> action, T e) {
        action.accept(e);
    }

    public Spliterator<T> getEmtpySpliterator() {
        return Spliterators.emptySpliterator();
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return (!found.get() &&
            this.getSource().tryAdvance((e) -> {
                if (condition.test(prev, e)) {
                    this.prev = e;
                    this.actionAccept(action, e);
                } else {
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

    public static void main(String[] args) {

        GenericTakeWhileSpliterator<Double> myCPTWS1 = new DoubleTakeWhileSpliterator(
                DoubleStream.of(0, 1, 2, 4, 8, 16, 32, 64, 128).spliterator(),
                (d1, d2) -> d1 + 4d > d2,
                0d);
        println(StreamSupport.doubleStream((DoubleTakeWhileSpliterator) myCPTWS1, false));


        AbstractPrimitiveTakeWhileSpliterator<Integer, IntConsumer, OfInt> myCPTWS2 = new IntTakeWhileSpliterator(
                IntStream.of(0, 1, 2, 4, 8, 16, 32, 64, 128).spliterator(),
                (i) -> i < 3);
        println(StreamSupport.intStream((IntTakeWhileSpliterator) myCPTWS2, false));


        LongTakeWhileSpliterator myCPTWS3 = new LongTakeWhileSpliterator(
                LongStream.of(0, 1, 2, 4, 8, 16, 32, 64, 128).spliterator(),
                (i) -> true);
        println(StreamSupport.longStream(myCPTWS3, false));

        println(takeWhile(Stream.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'), s -> s.compareTo('e') < 0));

        println(takeWhile(Stream.of("one", "two", "three", "four", "five", "six"), (t1, t2) -> t1.length() <= t2.length(), ""));
    }
}
