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
        return stream.flatMap(takeWhileFunc(predicate));
    }

    public static IntFunction<? extends IntStream> takeWhileFunc(IntPredicate predicate) {
        return new IntFunction<IntStream>() {
            boolean found = false;

            @Override
            public IntStream apply(int value) {
                if (!found) {
                    if (predicate.test(value)) {
                        return IntStream.of(value);
                    } else {
                        found = true;
                    }
                }
                return IntStream.empty();
            }
        };
    }

    public static IntStream takeWhile(IntStream stream, IntBinaryPredicate predicate, Integer seed) {
        return stream.flatMap(takeWhileFunc(seed, predicate));
    }

    public static IntStream takeWhile(IntStream stream, IntBinaryPredicate predicate) {
        return stream.flatMap(takeWhileFunc(null, predicate));
    }

    public static IntFunction<? extends IntStream> takeWhileFunc(Integer seed, IntBinaryPredicate predicate) {
        return new IntFunction<IntStream>() {
            boolean found = false;
            Integer prev = seed;

            @Override
            public IntStream apply(int value) {
                if (!found) {
                    if (prev != null) {
                        if (!predicate.test(prev, value)) {
                            found = true;
                            return IntStream.empty();
                        }
                    }
                    prev = value;
                    return IntStream.of(value);
                }
                return IntStream.empty();
            }
        };
    }

    public static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<T> predicate) {
        return stream.flatMap(takeWhileFunc(predicate));
    }

    public static <T> Function<T, Stream<T>> takeWhileFunc(Predicate<T> predicate) {
        return new Function<T, Stream<T>>() {
            boolean found = false;

            @Override
            public Stream<T> apply(T t) {
                if (!found) {
                    if (predicate.test(t)) {
                        return Stream.of(t);
                    } else {
                        found = true;
                    }
                }
                return Stream.empty();
            }
        };
    }

    public static <T> Stream<T> takeWhile(Stream<T> stream, BinaryPredicate<T> predicate, T seed) {
        return stream.flatMap(takeWhileFunc(seed, predicate));
    }

    public static <T> Stream<T> takeWhile(Stream<T> stream, BinaryPredicate<T> predicate) {
        return stream.flatMap(takeWhileFunc(null, predicate));
    }

    public static <T> Function<T, Stream<T>> takeWhileFunc(T seed, BinaryPredicate<T> predicate) {
        return new Function<T, Stream<T>>() {
            boolean found = false;
            T prev = seed;

            @Override
            public Stream<T> apply(T t) {
                if (!found) {
                    if (prev != null) {
                        if (!predicate.test(prev, t)) {
                            found = true;
                            return Stream.empty();
                        }
                    }
                    prev = t;
                    return Stream.of(t);
                }
                return Stream.empty();
            }
        };
    }

    public static IntStream dropWhile(IntStream stream, IntPredicate predicate) {
        return stream.flatMap(dropWhileFunc(predicate));
    }

    public static IntFunction<? extends IntStream> dropWhileFunc(IntPredicate predicate) {
        return new IntFunction<IntStream>() {
            boolean found = false;

            @Override
            public IntStream apply(int value) {
                if (!found) {
                    if (predicate.test(value)) {
                        return IntStream.empty();
                    } else {
                        found = true;
                    }
                }
                return IntStream.of(value);
            }
        };
    }

    public static IntStream dropWhile(IntStream stream, IntBinaryPredicate predicate, Integer seed) {
        return stream.flatMap(dropWhileFunc(seed, predicate));
    }

    public static IntStream dropWhile(IntStream stream, IntBinaryPredicate predicate) {
        return stream.flatMap(dropWhileFunc(null, predicate));
    }

    public static IntFunction<? extends IntStream> dropWhileFunc(Integer seed, IntBinaryPredicate predicate) {
        return new IntFunction<IntStream>() {
            boolean found = false;
            Integer prev = seed;

            @Override
            public IntStream apply(int value) {
                if (!found) {
                    if (prev != null) {
                        if (!predicate.test(prev, value)) {
                            found = true;
                            return IntStream.of(value);
                        }
                    }
                    prev = value;
                    return IntStream.empty();
                }
                return IntStream.of(value);
            }
        };
    }

    public static <T> Stream<T> dropWhile(Stream<T> stream, Predicate<T> predicate) {
        return stream.flatMap(dropWhileFunc(predicate));
    }

    public static <T> Function<T, Stream<T>> dropWhileFunc(Predicate<T> predicate) {
        return new Function<T, Stream<T>>() {
            boolean found = false;

            @Override
            public Stream<T> apply(T t) {
                if (!found) {
                    if (predicate.test(t)) {
                        return Stream.empty();
                    } else {
                        found = true;
                    }
                }
                return Stream.of(t);
            }
        };
    }

    public static <T> Stream<T> dropWhile(Stream<T> stream, BinaryPredicate<T> predicate, T seed) {
        return stream.flatMap(dropWhileFunc(seed, predicate));
    }

    public static <T> Stream<T> dropWhile(Stream<T> stream, BinaryPredicate<T> predicate) {
        return stream.flatMap(dropWhileFunc(null, predicate));
    }

    public static <T> Function<T, Stream<T>> dropWhileFunc(T seed, BinaryPredicate<T> predicate) {
        return new Function<T, Stream<T>>() {
            boolean found = false;
            T prev = seed;

            @Override
            public Stream<T> apply(T t) {
                if (!found) {
                    if (prev != null) {
                        if (!predicate.test(prev, t)) {
                            found = true;
                            return Stream.of(t);
                        }
                    }
                    prev = t;
                    return Stream.empty();
                }
                return Stream.of(t);
            }
        };
    }

    public static IntStream reverse(IntStream stream) {
        return StreamSupport.intStream(new IntReverseSpliterator<>(stream.spliterator()), stream.isParallel());
    }

    public static <T> Stream<T> reverse(Stream<T> stream) {
        return StreamSupport.stream(new ReverseSpliterator<>(stream.spliterator()), stream.isParallel());
    }

    public static IntStream intDropN(IntStream stream, long n) {
        return stream.flatMap(intDropN(n));
    }

    public static IntFunction<? extends IntStream> intDropN(long n) {
        return new IntFunction<IntStream>() {
            int i = 0;

            @Override
            public IntStream apply(int value) {
                return (i ++ >= n) ? IntStream.of(value) : IntStream.empty();
            }
        };
    }

    public static <T> Stream<T> dropN(Stream<T> stream, long n) {
        return stream.flatMap(dropN(n));
    }

    public static <T> Function<T, Stream<T>> dropN(long n) {
        return new Function<T, Stream<T>>() {
            int i = 0;

            @Override
            public Stream<T> apply(T value) {
                return (i ++ >= n) ? Stream.of(value) : Stream.empty();
            }
        };
    }

    public static IntStream intTakeN(IntStream stream, long n) {
        return stream.flatMap(intTakeN(n));
    }

    public static IntFunction<? extends IntStream> intTakeN(long n) {
        return new IntFunction<IntStream>() {
            int i = 0;

            @Override
            public IntStream apply(int value) {
                return (i ++ >= n) ? IntStream.empty() : IntStream.of(value);
            }
        };
    }

    public static <T> Stream<T> takeN(Stream<T> stream, long n) {
        return stream.flatMap(takeN(n));
    }

    public static <T> Function<T, Stream<T>> takeN(long n) {
        return new Function<T, Stream<T>>() {
            int i = 0;

            @Override
            public Stream<T> apply(T t) {
                return (i ++ >= n) ? Stream.empty() : Stream.of(t);
            }
        };
    }

    public static IntFunction<? extends IntStream> intTakeEveryNth(long n) {
        return new IntFunction<IntStream>() {
            int i = 0;

            @Override
            public IntStream apply(int value) {
                return (i++ % n == 0) ? IntStream.of(value) : IntStream.empty();
            }
        };
    }

    public static <T> Function<T, Stream<T>> takeEveryNth(long n) {
        return new Function<T, Stream<T>>() {
            int i = 0;

            @Override
            public Stream<T> apply(T t) {
                return (i++ % n == 0)? Stream.of(t) : Stream.empty();
            }
        };
    }

    public static IntFunction<IntStream> intMatchOnly(IntPredicate predicate) {
        return value -> (predicate.test(value)) ? IntStream.of(value) : IntStream.empty();
    }

    public static <T> Function<T, Stream<T>> matchOnly(Predicate<T> predicate) {
        return value -> (predicate.test(value)) ? Stream.of(value) : Stream.empty();
    }

    public static <T> String toString(Stream<T> stream) {
        return stream.collect(
                    StringBuilder::new,
                    (sb, s) -> sb.append(s.toString()),
                    StringBuilder::append)
                .toString();
    }

    public static <T> String toString(String delim, Stream<T> stream) {
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

    public static IntStream intReverseStream(IntStream stream) {
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

    private static class IntReverseSpliterator<T extends Integer> implements Spliterator.OfInt {
        private Spliterator.OfInt spliterator;
        private final Deque<Integer> deque = new ArrayDeque<>();

        private IntReverseSpliterator(Spliterator.OfInt spliterator) {
            this.spliterator = spliterator;
        }


        @Override
        public OfInt trySplit() {
            // After traveling started the spliterator don't contain elements!
            Spliterator.OfInt prev = spliterator.trySplit();
            if(prev == null) {
                return null;
            }

            Spliterator.OfInt me = spliterator;
            spliterator = prev;
            return new IntReverseSpliterator(me);
        }

        @Override
        public boolean tryAdvance(IntConsumer action) {
            while(spliterator.tryAdvance((IntConsumer) deque::addFirst));
            if(!deque.isEmpty()) {
                action.accept(deque.remove());
                return true;
            }
            return false;
        }

        @Override
        public long estimateSize() {
            return spliterator.estimateSize();
        }

        @Override
        public int characteristics() {
            return spliterator.characteristics();
        }
    }

    @Test
    public void test() {
        printlnDelim(", ", IntStream.of(1,2,3,4));

        println(IntStream.range(0, 10).flatMap(intTakeEveryNth(3)));

        println(intReverseStream(IntStream.range(0, 10)));
        println(reverse(Stream.of('a','b','c','d')));

        println(IntStream.range(0, 10).flatMap(intTakeN(5)));
        println(IntStream.range(0, 10).flatMap(intDropN(5)));

        println(takeWhile(Stream.of("1ne", "2wo", "thr33", "4our", "5ive"), s -> !s.contains("3")));
        println(dropWhile(Stream.of("1ne", "2wo", "thr33", "4our", "5ive"), s -> !s.contains("3")));

        println(takeWhile(IntStream.of(1,2,3,4,3,2,1), (i1, i2) -> i1 < i2, Integer.MIN_VALUE));
        println(takeWhile(Stream.of("1", "22", "333", "22", "1"), (i1, i2) -> i1.length() < i2.length(), ""));

        println(dropWhile(IntStream.of(1,2,3,4,3,2,1), (i1, i2) -> i1 < i2));
        println(dropWhile(Stream.of("1", "22", "333", "22", "1"), (i1, i2) -> i1.length() < i2.length()));

        println(IntStream.range(0, 50).flatMap(intMatchOnly(value -> value % 2 == 0)));
        println(Stream.of("1ne", "2wo", "thr33", "4our", "5ive").flatMap(matchOnly(value -> value.length() > 3)));

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
