package Utils.StreamUtils;

import Utils.StreamUtils.Interfaces.BiPredicate;
import Utils.StreamUtils.Interfaces.IntBiPredicate;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

public class Functions {

    public static IntFunction<? extends IntStream> takeWhile(IntPredicate predicate) {
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

    public static IntFunction<? extends IntStream> takeWhile(Integer identity, IntBiPredicate predicate) {
        return new IntFunction<IntStream>() {
            boolean found = false;
            Integer prev = identity;
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

    public static <T> Function<T, Stream<T>> takeWhile(Predicate<T> predicate) {
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

    public static <T> Function<T, Stream<T>> takeWhile(T identity, BiPredicate<T> predicate) {
        return new Function<T, Stream<T>>() {
            boolean found = false;
            T prev = identity;
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

    public static IntFunction<? extends IntStream> dropWhile(IntPredicate predicate) {
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

    public static IntFunction<? extends IntStream> dropWhile(Integer identity, IntBiPredicate predicate) {
        return new IntFunction<IntStream>() {
            boolean found = false;
            Integer prev = identity;
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

    public static <T> Function<T, Stream<T>> dropWhile(Predicate<T> predicate) {
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

    public static <T> Function<T, Stream<T>> dropWhile(T identity, BiPredicate<T> predicate) {
        return new Function<T, Stream<T>>() {
            boolean found = false;
            T prev = identity;

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

    public static IntFunction<? extends IntStream> intDropN(long n) {
        return new IntFunction<IntStream>() {
            int i = 0;
            @Override
            public IntStream apply(int value) {
                return (i ++ >= n) ? IntStream.of(value) : IntStream.empty();
            }
        };
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

    public static IntFunction<? extends IntStream> intTakeN(long n) {
        return new IntFunction<IntStream>() {
            int i = 0;
            @Override
            public IntStream apply(int value) {
                return (i ++ >= n) ? IntStream.empty() : IntStream.of(value);
            }
        };
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

    public static IntFunction<IntStream> intTakeOnly(IntPredicate predicate) {
        return value -> (predicate.test(value)) ? IntStream.of(value) : IntStream.empty();
    }

    public static <T> Function<T, Stream<T>> takeOnly(Predicate<T> predicate) {
        return value -> (predicate.test(value)) ? Stream.of(value) : Stream.empty();
    }
}
