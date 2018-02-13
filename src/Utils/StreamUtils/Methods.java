package Utils.StreamUtils;

import static Utils.Console.Printing.*;
import static Utils.StreamUtils.Functions.*;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.Console.Special;
import Utils.StreamUtils.VariadicFunctionalInterfaces.*;
import Utils.Timing.SYSStopwatch;
import Utils.Timing.TimeUnit;
import Utils.StreamUtils.Spliterators.*;
import Utils.StreamUtils.PredicateInterfaces.*;

import org.junit.Rule;
import org.junit.Test;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;


public class Methods {

    @Rule
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.MILLISECONDS);

    public static <T, U extends BaseStream<T, U>> String makeString(BaseStream<T, U> stream) {
        return makeString(", ", stream);
    }

    public static <T, U extends BaseStream<T, U>> String makeString(CharSequence delim, BaseStream<T, U> stream) {
        final StringBuilder sb = new StringBuilder();
        stream.iterator().forEachRemaining(new Consumer<T>() {
            boolean first = true;

            @Override
            public void accept(T e) {
                if (!first) {
                    sb.append(delim);
                } else {
                    first = false;
                }
                sb.append(e);
            }
        });
        return sb.toString();
    }

    public static <T, U extends BaseStream<T, U>> Stream<T> reverse(BaseStream<T, U> stream) {
        return StreamSupport.stream(new GenericReverseSpliterator<>(stream.spliterator()), stream.isParallel());
    }

    public static IntStream reverse(IntStream stream) {
        return StreamSupport.intStream(new IntReverseSpliterator(stream.spliterator()), stream.isParallel());
    }

    public static DoubleStream reverse(DoubleStream stream) {
        return StreamSupport.doubleStream(new DoubleReverseSpliterator(stream.spliterator()), stream.isParallel());
    }

    public static LongStream reverse(LongStream stream) {
        return StreamSupport.longStream(new LongReverseSpliterator(stream.spliterator()), stream.isParallel());
    }

    public static <T> Stream<T> iterate(T seed, Predicate<T> predicate, UnaryOperator<T> operator) {
        return StreamSupport.stream(
            new GenericBoundedSpliterator<>(
                seed,
                predicate,
                operator
            ),
            false
        );
    }

    public static IntStream intIterate(Integer seed, IntPredicate predicate, IntUnaryOperator operator) {
        return StreamSupport.intStream(
            new IntBoundedSpliterator(
                seed,
                predicate,
                operator
            ),
            false
        );
    }

    public static DoubleStream doubleIterate(Double seed, DoublePredicate predicate, DoubleUnaryOperator operator) {
        return StreamSupport.doubleStream(
            new DoubleBoundedSpliterator(
                    seed,
                    predicate,
                    operator
            ),
            false
        );
    }

    public static LongStream longIterate(Long seed, LongPredicate predicate, LongUnaryOperator operator) {
        return StreamSupport.longStream(
            new LongBoundedSpliterator(
                    seed,
                    predicate,
                    operator
            ),
            false
        );
    }

    public static IntStream takeWhile(IntStream stream, UnaryPredicate<Integer> predicate) {
        return StreamSupport.intStream(new IntTakeWhileSpliterator(stream.spliterator(), predicate), stream.isParallel());
    }

    public static IntStream takeWhile(IntStream stream, BinaryPredicate<Integer> predicate, Integer identity) {
        return StreamSupport.intStream(new IntTakeWhileSpliterator(stream.spliterator(), predicate, identity), stream.isParallel());
    }

    public static DoubleStream takeWhile(DoubleStream stream, UnaryPredicate<Double> predicate) {
        return StreamSupport.doubleStream(new DoubleTakeWhileSpliterator(stream.spliterator(), predicate), stream.isParallel());
    }

    public static DoubleStream takeWhile(DoubleStream stream, BinaryPredicate<Double> predicate, Double identity) {
        return StreamSupport.doubleStream(new DoubleTakeWhileSpliterator(stream.spliterator(), predicate, identity), stream.isParallel());
    }

    public static LongStream takeWhile(LongStream stream, UnaryPredicate<Long> predicate) {
        return StreamSupport.longStream(new LongTakeWhileSpliterator(stream.spliterator(), predicate), stream.isParallel());
    }

    public static LongStream takeWhile(LongStream stream, BinaryPredicate<Long> predicate, Long identity) {
        return StreamSupport.longStream(new LongTakeWhileSpliterator(stream.spliterator(), predicate, identity), stream.isParallel());
    }

    public static <T> Stream<T> takeWhile(Stream<T> stream, UnaryPredicate<? super T> predicate) {
        return StreamSupport.stream(new GenericTakeWhileSpliterator(stream.spliterator(), predicate), stream.isParallel());
    }

    public static <T> Stream<T> takeWhile(Stream<T> stream, BinaryPredicate<? super T> predicate, T identity) {
        return StreamSupport.stream(new GenericTakeWhileSpliterator(stream.spliterator(), predicate, identity), stream.isParallel());
    }


    public static IntStream dropWhile(IntStream stream, IntPredicate predicate) {
        return stream.flatMap(Functions.dropWhile(predicate));
    }

    public static IntStream dropWhile(IntStream stream, BiPredicate<? super Integer, ? super Integer> predicate, Integer identity) {
        return stream.flatMap(Functions.dropWhile(identity, predicate));
    }

    public static DoubleStream dropWhile(DoubleStream stream, DoublePredicate predicate) {
        return stream.flatMap(Functions.dropWhile(predicate));
    }

    public static DoubleStream dropWhile(DoubleStream stream, BiPredicate<? super Double, ? super Double> predicate, Double identity) {
        return stream.flatMap(Functions.dropWhile(identity, predicate));
    }

    public static LongStream dropWhile(LongStream stream, LongPredicate predicate) {
        return stream.flatMap(Functions.dropWhile(predicate));
    }

    public static LongStream dropWhile(LongStream stream, BiPredicate<? super Long, ? super Long> predicate, Long identity) {
        return stream.flatMap(Functions.dropWhile(identity, predicate));
    }

    public static <T> Stream<T> dropWhile(Stream<T> stream, Predicate<T> predicate) {
        return stream.flatMap(Functions.dropWhile(predicate));
    }

    public static <T> Stream<T> dropWhile(Stream<T> stream, BiPredicate<? super T, ? super T> predicate, T identity) {
        return stream.flatMap(Functions.dropWhile(identity, predicate));
    }


    public static IntStream takeOnly(IntStream stream, IntPredicate predicate) {
        return stream.flatMap(Functions.intTakeOnly(predicate));
    }

    public static DoubleStream takeOnly(DoubleStream stream, DoublePredicate predicate) {
        return stream.flatMap(Functions.doubleTakeOnly(predicate));
    }

    public static LongStream takeOnly(LongStream stream, LongPredicate predicate) {
        return stream.flatMap(Functions.longTakeOnly(predicate));
    }

    public static <T> Stream<T> takeOnly(Stream<T> stream, Predicate<T> predicate) {
        return stream.flatMap(Functions.takeOnly(predicate));
    }

    public static IntStream dropOnly(IntStream stream, IntPredicate predicate) {
        return stream.flatMap(Functions.intTakeOnly(predicate.negate()));
    }

    public static DoubleStream dropOnly(DoubleStream stream, DoublePredicate predicate) {
        return stream.flatMap(Functions.doubleTakeOnly(predicate.negate()));
    }

    public static LongStream dropOnly(LongStream stream, LongPredicate predicate) {
        return stream.flatMap(Functions.longTakeOnly(predicate.negate()));
    }

    public static <T> Stream<T> dropOnly(Stream<T> stream, Predicate<T> predicate) {
        return stream.flatMap(Functions.takeOnly(predicate.negate()));
    }

    public static IntStream dropN(IntStream stream, long n) {
        return stream.flatMap(Functions.intDropN(n));
    }

    public static DoubleStream dropN(DoubleStream stream, long n) {
        return stream.flatMap(Functions.doubleDropN(n));
    }

    public static LongStream dropN(LongStream stream, long n) {
        return stream.flatMap(Functions.longDropN(n));
    }

    public static <T> Stream<T> dropN(Stream<T> stream, long n) {
        return stream.flatMap(Functions.dropN(n));
    }

    public static IntStream takeN(IntStream stream, long n) {
        return stream.flatMap(Functions.intTakeN(n));
    }

    public static DoubleStream takeN(DoubleStream stream, long n) {
        return stream.flatMap(Functions.doubleTakeN(n));
    }

    public static LongStream takeN(LongStream stream, long n) {
        return stream.flatMap(Functions.longTakeN(n));
    }

    public static <T> Stream<T> takeN(Stream<T> stream, long n) {
        return stream.flatMap(Functions.takeN(n));
    }

    public static Stream<List<Integer>> listsOfN(IntStream stream, int n) {
        return stream.boxed().flatMap(Functions.listsOfN(n));
    }

    public static Stream<List<Double>> listsOfN(DoubleStream stream, int n) {
        return stream.boxed().flatMap(Functions.listsOfN(n));
    }

    public static Stream<List<Long>> listsOfN(LongStream stream, int n) {
        return stream.boxed().flatMap(Functions.listsOfN(n));
    }

    public static <T> Stream<List<T>> listsOfN(Stream<T> stream, int n) {
        return stream.flatMap(Functions.listsOfN(n));
    }

    public static Stream<Integer[]> arraysOfN(IntStream stream, int n) {
        return stream.boxed().flatMap(Functions.listsOfN(n)).map(integers -> integers.<Integer>toArray(new Integer[n]));
    }

    public static Stream<Double[]> arraysOfN(DoubleStream stream, int n) {
        return stream.boxed().flatMap(Functions.listsOfN(n)).map(doubles -> doubles.<Double>toArray(new Double[n]));
    }

    public static Stream<Long[]> arraysOfN(LongStream stream, int n) {
        return stream.boxed().flatMap(Functions.listsOfN(n)).map(longs -> longs.<Long>toArray(new Long[n]));
    }

    public static <T> Stream<T[]> arraysOfN(Stream<T> stream, int n) {
        return stream.flatMap(Functions.listsOfN(n)).map(ts -> (T[]) ts.toArray());
    }

    public static <T extends Transformation<Double>> DoubleStream variadicMapToObj(DoubleStream stream, T pred) {
        return StreamSupport.doubleStream(new DoubleVariadicSpliterator(stream.spliterator(), pred), false);
    }

    @Test
    public void test() {
        println(
            takeWhile(LongStream.iterate(1L, (l) -> l+l), (l) -> l < Long.MAX_VALUE/2)
        );

//        println(
//            longIterate(1L, (l) -> l < Long.MAX_VALUE/2, (l) -> l+l)
//        );
    }

    public static void main(String[] args) {

        println(Special.FG_BRIGHT_CYAN, makeString(", ", IntStream.range(0, 10).flatMap(intTakeEveryNth(3))));
        println(
            Special.FG_BRIGHT_CYAN,
            takeWhile(
                DoubleStream.generate(
                    new DoubleSupplier() {
                        double d = 10d;

                        @Override
                        public double getAsDouble() {
                            return d *= 0.1;
                        }
                    }
                ),
                (d) -> d >= 0.000001
            )
            .reduce((left, right) -> left + right
            )
        );

        println(Special.FG_BRIGHT_CYAN, reverse(IntStream.range(0, 10)));

        println(Special.FG_BRIGHT_CYAN, reverse(Stream.of('a','b','c','d')));

        println(Special.FG_BRIGHT_CYAN, takeN(IntStream.range(0, 10), 5));

        println(Special.FG_BRIGHT_CYAN, dropN(IntStream.range(0, 10), 5));

        println(Special.FG_BRIGHT_CYAN, takeWhile(Stream.of("one", "two", "three", "four", "five", "six"), s -> !s.contains("our")));

        println(Special.FG_BRIGHT_CYAN, dropWhile(Stream.of("one", "two", "three", "four", "five", "six"), s -> !s.contains("our")));

        println(Special.FG_BRIGHT_CYAN, takeWhile(IntStream.range(0, 10), (i1, i2) -> i1 < i2, Integer.MIN_VALUE));

        println(Special.FG_BRIGHT_CYAN, dropWhile(IntStream.range(0, 10), (i1, i2) -> i1 < i2, Integer.MIN_VALUE));

        println(Special.FG_BRIGHT_CYAN, takeWhile(Stream.of("one", "two", "three", "four", "five", "six"), (i1, i2) -> i1.length() <= i2.length(), ""));

        println(Special.FG_BRIGHT_CYAN, dropWhile(Stream.of("one", "two", "three", "four", "five", "six"), (i1, i2) -> i1.length() <= i2.length(), ""));

        println(Special.FG_BRIGHT_CYAN, takeOnly(IntStream.range(0, 10), value -> value % 2 == 0));

        println(Special.FG_BRIGHT_CYAN, dropOnly(IntStream.range(0, 10), value -> value % 2 == 0));

        println(Special.FG_BRIGHT_CYAN, takeOnly(Stream.of("one", "two", "three", "four", "five", "six"), value -> value.length() < 5));

        println(Special.FG_BRIGHT_CYAN, listsOfN(Stream.of("one", "two", "three", "four", "five", "six"), 3));

        println(Special.FG_BRIGHT_CYAN, arraysOfN(IntStream.range(0, 100), 3));

        println(Special.FG_RED, takeWhile(DoubleStream.of(1,2,3,4,3,3,1), (d) -> d < 4));

        println(Special.FG_RED, takeWhile(DoubleStream.of(1,2,3,4,3,3,1), (d1, d2) -> d1 < d2, Double.MIN_VALUE));

        println(
            intIterate(
                1,
                i -> i <= 100,
                i -> i + 1
            )
        );

        println(
            iterate(
                'z',
                c -> !c.equals((char) ('a' - 1)),
                c -> (--c)
            )
        );

        println(
            iterate(
                "1",
                i -> i.length() < 10,
                i -> i + (char) (i.charAt(i.length()-1) + 1)
            )
        );

        println(
            takeWhile(
                Stream.generate(
                    new Supplier<Character>() {
                        int c = ' ';
                        @Override
                        public Character get() {
                            return (char) c++;
                        }
                    }
                ),
                i -> i < 1000
            )
        );

        println(
            takeWhile(
                IntStream.generate(
                    new IntSupplier() {
                        int i = 0;
                        @Override
                        public int getAsInt() { return i++; }
                    }
                ),
                i -> i < 1000
            )
        );

        println(
            takeWhile(
                Stream.<Double>generate(
                    new Supplier<Double>() {
                        double i = 0.1;
                        @Override
                        public Double get() { return i *= 1.5; }
                    }
                ),
                i -> i < 100
            )
        );

        println(
            takeWhile(
                DoubleStream.generate(
                    new DoubleSupplier() {
                        double d = 1;
                        @Override
                        public double getAsDouble() { return d *= 1.000001; }
                    }
                ),
                (o) -> o < 100
            ).summaryStatistics()
        );

        println(
            takeWhile(
                variadicMapToObj(
                    DoubleStream.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
                    (Transformation2<Double>) (d1, d2) -> (d1 * d2)
                ).map(d -> d),
                (d -> d < 200)
            )
        );
    }
}
