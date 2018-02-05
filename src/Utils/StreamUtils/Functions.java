package Utils.StreamUtils;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Functions {

    public static IntFunction<? extends IntStream> dropWhile(IntPredicate condition) {
        return new IntFunction<IntStream>() {
            final AtomicBoolean found = new AtomicBoolean();
            @Override
            public IntStream apply(int value) {
                if (!found.get()) {
                    if (condition.test(value)) {
                        return IntStream.empty();
                    }
                    found.set(true);
                }
                return IntStream.of(value);
            }
        };
    }

    public static IntFunction<? extends IntStream> dropWhile(Integer identity, BiPredicate<? super Integer, ? super Integer> condition) {
        return new IntFunction<IntStream>() {
            final AtomicBoolean found = new AtomicBoolean();
            Integer prev = identity;
            @Override
            public IntStream apply(int value) {
                if (!found.get()) {
                    if (condition.test(prev, value)) {
                        prev = value;
                        return IntStream.empty();
                    }
                    found.set(true);
                }
                return IntStream.of(value);
            }
        };
    }

    public static DoubleFunction<? extends DoubleStream> dropWhile(DoublePredicate condition) {
        return new DoubleFunction<DoubleStream>() {
            final AtomicBoolean found = new AtomicBoolean();
            @Override
            public DoubleStream apply(double value) {
                if (!found.get()) {
                    if (condition.test(value)) {
                        return DoubleStream.empty();
                    }
                    found.set(true);
                }
                return DoubleStream.of(value);
            }
        };
    }

    public static DoubleFunction<? extends DoubleStream> dropWhile(Double identity, BiPredicate<? super Double, ? super Double> condition) {
        return new DoubleFunction<DoubleStream>() {
            final AtomicBoolean found = new AtomicBoolean();
            Double prev = identity;
            @Override
            public DoubleStream apply(double value) {
                if (!found.get()) {
                    if (condition.test(prev, value)) {
                        prev = value;
                        return DoubleStream.empty();
                    }
                    found.set(true);
                }
                return DoubleStream.of(value);
            }
        };
    }

    public static <T> Function<T, Stream<T>> dropWhile(Predicate<T> condition) {
        return new Function<T, Stream<T>>() {
            final AtomicBoolean found = new AtomicBoolean();
            @Override
            public Stream<T> apply(T t) {
                if (!found.get()) {
                    if (condition.test(t)) {
                        return Stream.empty();
                    }
                    found.set(true);
                }
                return Stream.of(t);
            }
        };
    }

    public static <T> Function<T, Stream<T>> dropWhile(T identity, BiPredicate<? super T, ? super T> condition) {
        return new Function<T, Stream<T>>() {
            final AtomicBoolean found = new AtomicBoolean();
            T prev = identity;

            @Override
            public Stream<T> apply(T t) {
                if (!found.get()) {
                    if (condition.test(prev, t)) {
                        prev = t;
                        return Stream.empty();
                    }
                    found.set(true);
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

    public static <T> Function<T, Stream<T>> takeOnly(Predicate<T> condition) {
        return value -> (condition.test(value)) ? Stream.of(value) : Stream.empty();
    }
}
