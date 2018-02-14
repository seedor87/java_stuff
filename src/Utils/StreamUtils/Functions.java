package Utils.StreamUtils;

import Utils.StreamUtils.Interfaces.BinaryPredicate;
import Utils.StreamUtils.Interfaces.NaryPredicate;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Functions {

    public static IntFunction<? extends IntStream> intDropWhile(NaryPredicate<Integer> condition) {
        return new IntFunction<IntStream>() {
            final AtomicBoolean found = new AtomicBoolean();
            int transformationSize = condition.getSize();
            ArrayQueue<Integer> queue = new ArrayQueue<>(transformationSize);

            @Override
            public IntStream apply(int t) {
                if (!found.get()) {
                    queue.add(t);
                    if(queue.size() >= transformationSize) {
                        if (condition.execute(queue)) {
                            queue.remove(0);
                            return IntStream.empty();
                        }
                        found.set(true);
                        return IntStream.of(t);
                    }
                    return IntStream.empty();
                }
                return IntStream.of(t);
            }
        };
    }

    public static DoubleFunction<? extends DoubleStream> doubleDropWhile(NaryPredicate<Double> condition) {
        return new DoubleFunction<DoubleStream>() {
            final AtomicBoolean found = new AtomicBoolean();
            int transformationSize = condition.getSize();
            ArrayQueue<Double> queue = new ArrayQueue<>(transformationSize);

            @Override
            public DoubleStream apply(double t) {
                if (!found.get()) {
                    queue.add(t);
                    if(queue.size() >= transformationSize) {
                        if (condition.execute(queue)) {
                            queue.remove(0);
                            return DoubleStream.empty();
                        }
                        found.set(true);
                        return DoubleStream.of(t);
                    }
                    return DoubleStream.empty();
                }
                return DoubleStream.of(t);
            }
        };
    }

    public static LongFunction<? extends LongStream> longDropWhile(NaryPredicate<Long> condition) {
        return new LongFunction<LongStream>() {
            final AtomicBoolean found = new AtomicBoolean();
            int transformationSize = condition.getSize();
            ArrayQueue<Long> queue = new ArrayQueue<>(transformationSize);

            @Override
            public LongStream apply(long t) {
                if (!found.get()) {
                    queue.add(t);
                    if(queue.size() >= transformationSize) {
                        if (condition.execute(queue)) {
                            queue.remove(0);
                            return LongStream.empty();
                        }
                        found.set(true);
                        return LongStream.of(t);
                    }
                    return LongStream.empty();
                }
                return LongStream.of(t);
            }
        };
    }

    public static <T> Function<T, Stream<T>> dropWhile(NaryPredicate<T> condition) {
        return new Function<T, Stream<T>>() {
            final AtomicBoolean found = new AtomicBoolean();
            int transformationSize = condition.getSize();
            ArrayQueue<T> queue = new ArrayQueue<>(transformationSize);

            @Override
            public Stream<T> apply(T t) {
                if (!found.get()) {
                    queue.add(t);
                    if(queue.size() >= transformationSize) {
                        if (condition.execute(queue)) {
                            queue.clear();
                            return Stream.empty();
                        }
                        found.set(true);
                        return Stream.of(t);
                    }
                    return Stream.empty();
                }
                return Stream.of(t);
            }
        };
    }

    public static IntFunction<? extends IntStream> intDropN(long n) {
        return new IntFunction<IntStream>() {
            long i = 0;
            @Override
            public IntStream apply(int value) {
                return (i++ >= n) ? IntStream.of(value) : IntStream.empty();
            }
        };
    }

    public static DoubleFunction<? extends DoubleStream> doubleDropN(long n) {
        return new DoubleFunction<DoubleStream>() {
            long i = 0;
            @Override
            public DoubleStream apply(double value) {
                return (i++ >= n) ? DoubleStream.of(value) : DoubleStream.empty();
            }
        };
    }

    public static LongFunction<? extends LongStream> longDropN(long n) {
        return new LongFunction<LongStream>() {
            long i = 0;
            @Override
            public LongStream apply(long value) {
                return (i++ >= n) ? LongStream.of(value) : LongStream.empty();
            }
        };
    }

    public static <T> Function<T, Stream<T>> dropN(long n) {
        return new Function<T, Stream<T>>() {
            long i = 0;
            @Override
            public Stream<T> apply(T value) {
                return (i++ >= n) ? Stream.of(value) : Stream.empty();
            }
        };
    }

    public static IntFunction<? extends IntStream> intTakeN(long n) {
        return new IntFunction<IntStream>() {
            long i = 0;
            @Override
            public IntStream apply(int value) {
                return (i++ >= n) ? IntStream.empty() : IntStream.of(value);
            }
        };
    }

    public static DoubleFunction<? extends DoubleStream> doubleTakeN(long n) {
        return new DoubleFunction<DoubleStream>() {
            long i = 0;
            @Override
            public DoubleStream apply(double value) {
                return (i++ >= n) ? DoubleStream.empty() : DoubleStream.of(value);
            }
        };
    }

    public static LongFunction<? extends LongStream> longTakeN(long n) {
        return new LongFunction<LongStream>() {
            long i = 0;
            @Override
            public LongStream apply(long value) {
                return (i++ >= n) ? LongStream.empty() : LongStream.of(value);
            }
        };
    }

    public static <T> Function<T, Stream<T>> takeN(long n) {
        return new Function<T, Stream<T>>() {
            long i = 0;
            @Override
            public Stream<T> apply(T t) {
                return (i++ >= n) ? Stream.empty() : Stream.of(t);
            }
        };
    }

    public static IntFunction<? extends IntStream> intTakeEveryNth(long n) {
        return new IntFunction<IntStream>() {
            long i = 0;
            @Override
            public IntStream apply(int value) {
                return (i++ % n == 0) ? IntStream.of(value) : IntStream.empty();
            }
        };
    }

    public static DoubleFunction<? extends DoubleStream> doubleTakeEveryNth(long n) {
        return new DoubleFunction<DoubleStream>() {
            long i = 0;
            @Override
            public DoubleStream apply(double value) {
                return (i++ % n == 0) ? DoubleStream.of(value) : DoubleStream.empty();
            }
        };
    }

    public static LongFunction<? extends LongStream> longTakeEveryNth(long n) {
        return new LongFunction<LongStream>() {
            long i = 0;
            @Override
            public LongStream apply(long value) {
                return (i++ % n == 0) ? LongStream.of(value) : LongStream.empty();
            }
        };
    }

    public static <T> Function<T, Stream<T>> takeEveryNth(long n) {
        return new Function<T, Stream<T>>() {
            long i = 0;
            @Override
            public Stream<T> apply(T t) {
                return (i++ % n == 0)? Stream.of(t) : Stream.empty();
            }
        };
    }

    public static <T> Function<T, Stream<List<T>>> listsOfN(int n) {
        return new Function<T, Stream<List<T>>>() {
            List<T> arr = new ArrayList<>();
            @Override
            public Stream<List<T>> apply(T t) {
                arr.add(t);
                if (arr.size() < n) {
                    return Stream.empty();
                }
                List<T> temp = arr;
                arr = new ArrayList<>();
                return Stream.of(temp);
            }
        };
    }

    public static IntFunction<IntStream> intTakeOnly(IntPredicate condition) {
        return value -> (condition.test(value)) ? IntStream.of(value) : IntStream.empty();
    }

    public static DoubleFunction<DoubleStream> doubleTakeOnly(DoublePredicate condition) {
        return value -> (condition.test(value)) ? DoubleStream.of(value) : DoubleStream.empty();
    }

    public static LongFunction<LongStream> longTakeOnly(LongPredicate condition) {
        return value -> (condition.test(value)) ? LongStream.of(value) : LongStream.empty();
    }

    public static <T> Function<T, Stream<T>> takeOnly(Predicate<T> condition) {
        return value -> (condition.test(value)) ? Stream.of(value) : Stream.empty();
    }
}
