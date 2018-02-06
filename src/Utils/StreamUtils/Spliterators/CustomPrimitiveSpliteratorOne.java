package Utils.StreamUtils.Spliterators;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.Predicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static Utils.Console.Printing.println;

public class CustomPrimitiveSpliteratorOne<T, U, V extends Spliterator.OfPrimitive<T, U, V>> implements Spliterator.OfPrimitive<T, U, V> {
    private V source;
    protected final AtomicBoolean found = new AtomicBoolean();
    private final Predicate<T> condition;

    public CustomPrimitiveSpliteratorOne(V source, Predicate<T> predicate) {
        this.source = source;
        this.condition = predicate;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        return (!found.get() &&
            this.getSource().tryAdvance((e) -> {
                if (condition.test(e)) {
                    action.accept(e);
                } else {
                    found.set(true);
                }
            })
        );
    }

    @Override
    public long estimateSize() {
        return source.estimateSize();
    }

    @Override
    public int characteristics() {
        return source.characteristics() & (DISTINCT | SORTED | NONNULL);
    }

    @Override
    public Comparator<? super T> getComparator() {
        return source.getComparator();
    }

    public V getSource() {
        return source;
    }

    public void setSource(V source) {
        this.source = source;
    }

    @Override
    public V trySplit() {
        return null;
    }

    @Override
    public boolean tryAdvance(U action) {
       return tryAdvance((Consumer<? super T>) action);
    }

    public static void main(String[] args) {
        CustomPrimitiveSpliteratorOne<Double, DoubleConsumer, OfDouble> myCPS1 = new CustomPrimitiveSpliteratorOne<>(DoubleStream.of(1,2,3,4).spliterator(), (d) -> d < 3);
        println(StreamSupport.stream(myCPS1, false).mapToDouble(d -> d).summaryStatistics());

        CustomPrimitiveSpliteratorOne<Integer, IntConsumer, OfInt> myCPS2 = new CustomPrimitiveSpliteratorOne<>(IntStream.of(1,2,3,4).spliterator(), (i) -> i < 3);
        println(StreamSupport.stream(myCPS2, false).mapToInt(i -> i).summaryStatistics());
    }
}
