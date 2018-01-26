package Utils.Streams;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;
import static Utils.Console.Printing.*;
import org.junit.Rule;
import org.junit.Test;

import java.util.*;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    @Rule
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.MICROSECONDS);

    public static IntStream takeWhile(IntStream stream, IntPredicate predicate) {
        return StreamSupport.intStream(new TakeIntSpliterator(stream, predicate), stream.isParallel());
    }

    public static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<T> predicate) {
        return StreamSupport.<T>stream(new TakeSpliterator<T>(stream, predicate), stream.isParallel());
    }

    public static IntStream dropWhile(IntStream stream, IntPredicate predicate) {
        return StreamSupport.intStream(new DropIntSpliterator(stream, predicate), stream.isParallel());
    }

    public static <T> Stream<T> dropWhile(Stream<T> stream, Predicate<T> predicate) {
        return StreamSupport.<T>stream(new DropSpliterator<T>(stream, predicate), stream.isParallel());
    }

    public static <T> Stream<T> reverse(Stream<T> stream) {
        return StreamSupport.stream(new ReverseSpliterator<>(stream.spliterator()), stream.isParallel());
    }

    public static <T> Function<T, Stream<T>> everyNth(int n) {
        return new Function<T, Stream<T>>() {
            int i = 0;

            @Override
            public Stream<T> apply(T t) {
                if (i++ % n == 0) {
                    return Stream.of(t);
                }
                return Stream.empty();
            }
        };
    }

    public static IntFunction<? extends IntStream> everyNthInt(int n) {
        return new IntFunction<IntStream>() {
            int i = 0;

            @Override
            public IntStream apply(int value) {
                if (i++ % n == 0) {
                    return IntStream.of(value);
                }
                return IntStream.empty();
            }
        };
    }

    public static <T> String toString(Stream<T> stream) {
        return stream.collect(
                    StringBuilder::new,
                    (sb, s) -> sb.append(s.toString()),
                    StringBuilder::append)
                .toString();
    }

    public static <T> Stream<T> reverseStream(Stream<T> stream) {
        return stream.<ArrayDeque<T>>collect(
                    ArrayDeque::new,
                    ArrayDeque::addFirst,
                    ArrayDeque::addAll)
                .stream();
    }

    public static IntStream reverseIntStream(IntStream stream) {
        return stream.<ArrayDeque<Integer>>collect(
                    ArrayDeque::new,
                    ArrayDeque::addFirst,
                    ArrayDeque::addAll)
                .stream()
                .mapToInt(Integer::intValue);
    }

    private static class ReverseSpliterator<T> implements Spliterator<T> {
        private Spliterator<T> spliterator;
        private final Deque<T> deque = new ArrayDeque<>();

        private ReverseSpliterator(Spliterator<T> spliterator) {
            this.spliterator = spliterator;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            while(spliterator.tryAdvance(deque::addFirst));
            if(!deque.isEmpty()) {
                action.accept(deque.remove());
                return true;
            }
            return false;
        }

        @Override
        public Spliterator<T> trySplit() {
            // After traveling started the spliterator don't contain elements!
            Spliterator<T> prev = spliterator.trySplit();
            if(prev == null) {
                return null;
            }

            Spliterator<T> me = spliterator;
            spliterator = prev;
            return new ReverseSpliterator(me);
        }

        @Override
        public long estimateSize() {
            return spliterator.estimateSize();
        }

        @Override
        public int characteristics() {
            return spliterator.characteristics();
        }

        @Override
        public Comparator<? super T> getComparator() {
            Comparator<? super T> comparator = spliterator.getComparator();
            return (comparator != null) ? comparator.reversed() : null;
        }

        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            // Ensure that tryAdvance is called at least once
            if(!deque.isEmpty() || tryAdvance(action)) {
                deque.forEach(action);
            }
        }
    }

    private static class DropIntSpliterator extends Spliterators.AbstractIntSpliterator {
        private final PrimitiveIterator.OfInt iterator;
        private final IntPredicate predicate;
        private boolean found;

        public DropIntSpliterator(IntStream stream, IntPredicate predicate) {
            super(Long.MAX_VALUE, IMMUTABLE);
            this.iterator = stream.iterator();
            this.predicate = predicate;
            this.found = false;
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            if (iterator.hasNext()) {
                int value = iterator.next();
                if (found || !predicate.test(value)) {
                    found = true;
                    action.accept(value);
                }
                return true;
            }
            return false;
        }
    }

    private static class DropSpliterator<T> extends Spliterators.AbstractSpliterator {
        private final Iterator<T> iterator;
        private final Predicate<T> predicate;
        private boolean found;

        public DropSpliterator(Stream<T> stream, Predicate<T> predicate) {
            super(Long.MAX_VALUE, IMMUTABLE);
            this.iterator = stream.iterator();
            this.predicate = predicate;
            this.found = false;
        }

        @Override
        public boolean tryAdvance(Consumer action) {
            if (iterator.hasNext()) {
                T value = iterator.next();
                if (found || !predicate.test(value)) {
                    found = true;
                    action.accept(value);
                }
                return true;
            }
            return false;
        }
    }

    private static class TakeIntSpliterator extends Spliterators.AbstractIntSpliterator {
        private final PrimitiveIterator.OfInt iterator;
        private final IntPredicate predicate;

        public TakeIntSpliterator(IntStream stream, IntPredicate predicate) {
            super(Long.MAX_VALUE, IMMUTABLE);
            this.iterator = stream.iterator();
            this.predicate = predicate;
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            if (iterator.hasNext()) {
                int value = iterator.nextInt();
                if (predicate.test(value)) {
                    return false;
                }
                action.accept(value);
            }
            return true;
        }
    }

    private static class TakeSpliterator<T> extends Spliterators.AbstractSpliterator {
        private final Iterator<T> iterator;
        private final Predicate<T> predicate;

        public TakeSpliterator(Stream<T> stream, Predicate<T> predicate) {
            super(Long.MAX_VALUE, IMMUTABLE);
            this.iterator = stream.iterator();
            this.predicate = predicate;
        }

        @Override
        public boolean tryAdvance(Consumer action) {
            if (iterator.hasNext()) {
                T value = iterator.next();
                if (predicate.test(value)) {
                    action.accept(value);
                    return true;
                }
            }
            return false;
        }
    }

    @Test
    public void test() {
        printlnDelim(", ", IntStream.of(1,2,3,4));

        println(reverseIntStream(IntStream.range(0, 10)));

        println(reverseStream(Stream.of('a','b','c','d')));

        println(reverse(Stream.of('a','b','c','d')));

        println(takeWhile(Stream.of("1ne", "2wo", "thr33", "4our"), s -> !s.contains("3")));

        println(dropWhile(Stream.of("1ne", "2wo", "thr33", "4our"), s -> !s.contains("3")));

        println(IntStream.range(0, 10).flatMap(everyNthInt(3)));

        println(Stream.of('a','b','c','d')
        .flatMap(new Function<Character, Stream<?>>() {
            int count = 0;

            @Override
            public Stream<?> apply(Character character) {
                return Stream.of("" + character + count++);
            }
        }));
    }
}
