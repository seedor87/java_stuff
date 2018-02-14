package Utils.StreamUtils;

import static Utils.Console.Printing.*;
import static Utils.StreamUtils.Functions.*;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.Console.Special;
import Utils.Timing.SYSStopwatch;
import Utils.Timing.TimeUnit;
import Utils.StreamUtils.Spliterators.*;
import Utils.StreamUtils.Interfaces.*;
import Utils.StreamUtils.Spliterators.GenericVariadicSpliterator.Process;

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
        return StreamSupport.stream(new GenericReverseSpliterator<>(stream.spliterator()), false);
    }

    public static IntStream reverse(IntStream stream) {
        return StreamSupport.intStream(new IntReverseSpliterator(stream.spliterator()), false);
    }

    public static DoubleStream reverse(DoubleStream stream) {
        return StreamSupport.doubleStream(new DoubleReverseSpliterator(stream.spliterator()), false);
    }

    public static LongStream reverse(LongStream stream) {
        return StreamSupport.longStream(new LongReverseSpliterator(stream.spliterator()), false);
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

    public static IntStream takeWhile(IntStream stream, NaryPredicate<Integer> predicate) {
        return StreamSupport.intStream(new IntTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }

    public static DoubleStream takeWhile(DoubleStream stream, NaryPredicate<Double> predicate) {
        return StreamSupport.doubleStream(new DoubleTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }

    public static LongStream takeWhile(LongStream stream, NaryPredicate<Long> predicate) {
        return StreamSupport.longStream(new LongTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }

    public static <T> Stream<T> takeWhile(Stream<T> stream, NaryPredicate<? super T> predicate) {
        return StreamSupport.stream(new GenericTakeWhileSpliterator(stream.spliterator(), predicate), false);
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

    public static <S extends NaryMapping<Integer, Integer>> IntStream variadicMapToObj(IntStream stream, S pred, Process process) {
        return StreamSupport.intStream(new IntVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryMapping<Integer, Integer>> IntStream variadicMapToObj(IntStream stream, S pred) {
        return StreamSupport.intStream(new IntVariadicSpliterator(stream.spliterator(), pred), false);
    }

    public static <S extends NaryMapping<Double, Double>> DoubleStream variadicMapToObj(DoubleStream stream, S pred, Process process) {
        return StreamSupport.doubleStream(new DoubleVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryMapping<Double, Double>> DoubleStream variadicMapToObj(DoubleStream stream, S pred) {
        return StreamSupport.doubleStream(new DoubleVariadicSpliterator(stream.spliterator(), pred), false);
    }

    public static <S extends NaryMapping<Long, Long>> LongStream variadicMapToObj(LongStream stream, S pred, Process process) {
        return StreamSupport.longStream(new LongVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryMapping<Long, Long>> LongStream variadicMapToObj(LongStream stream, S pred) {
        return StreamSupport.longStream(new LongVariadicSpliterator(stream.spliterator(), pred), false);
    }

    public static <S extends NaryMapping<T, T>, T> Stream<T> variadicMapToObj(Stream<T> stream, S pred, Process process) {
        return StreamSupport.stream(new GenericVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryMapping<T, T>, T> Stream<T> variadicMapToObj(Stream<T> stream, S pred) {
        return StreamSupport.stream(new GenericVariadicSpliterator(stream.spliterator(), pred), false);
    }

    @Test
    public void test() {
        println(
            takeWhile(LongStream.iterate(1L, (l) -> l+l), (UnaryPredicate<Long>) (l) -> l < Long.MAX_VALUE/2)
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
                    (UnaryPredicate<Double>) (d) -> d >= 0.000001
            )
            .reduce((left, right) -> left + right)
        );

        println(Special.FG_BRIGHT_CYAN, reverse(IntStream.range(0, 10)));

        println(Special.FG_BRIGHT_CYAN, reverse(Stream.of('a','b','c','d')));

        println(Special.FG_BRIGHT_CYAN, takeN(IntStream.range(0, 10), 5));

        println(Special.FG_BRIGHT_CYAN, dropN(IntStream.range(0, 10), 5));

        println(Special.FG_BRIGHT_CYAN, takeWhile(Stream.of("one", "two", "three", "four", "five", "six"), (UnaryPredicate<String>) s -> !s.contains("our")).map(s -> s));

        println(Special.FG_BRIGHT_CYAN, dropWhile(Stream.of("one", "two", "three", "four", "five", "six"), s -> !s.contains("our")));

        println(Special.FG_BRIGHT_CYAN, takeWhile(IntStream.range(0, 10), (BinaryPredicate<Integer>) (i1, i2) -> i1 < i2).map(i -> i));

        println(Special.FG_BRIGHT_CYAN, dropWhile(IntStream.range(0, 10), (i) -> i < 10));

        println(Special.FG_BRIGHT_CYAN, takeWhile(Stream.of("one", "two", "three", "four", "five", "six"), (TernaryPredicate<String>) (i1, i2, i3) -> i1.length() <= i2.length() && i2.length() < i3.length()).map(i -> i));

        println(Special.FG_BRIGHT_CYAN, dropWhile(Stream.of("one", "two", "three", "four", "five", "six"), (i1, i2) -> i1.length() <= i2.length(), ""));

        println(Special.FG_BRIGHT_CYAN, takeOnly(IntStream.range(0, 10), value -> value % 2 == 0));

        println(Special.FG_BRIGHT_CYAN, dropOnly(IntStream.range(0, 10), value -> value % 2 == 0));

        println(Special.FG_BRIGHT_CYAN, takeOnly(Stream.of("one", "two", "three", "four", "five", "six"), value -> value.length() < 5));

        println(Special.FG_BRIGHT_CYAN, listsOfN(Stream.of("one", "two", "three", "four", "five", "six"), 3));

        println(Special.FG_BRIGHT_CYAN, arraysOfN(IntStream.range(0, 100), 3));

        println(Special.FG_RED, takeWhile(DoubleStream.of(1,2,3,4,3,2,1), (UnaryPredicate<Double>) (d) -> d < 4));

        println(Special.FG_RED, takeWhile(DoubleStream.of(1,2,3,4,3,2,1), (BinaryPredicate<Double>) (d1, d2) -> d1 < d2).mapToObj(d -> d));

        println(Special.FG_RED, dropWhile(DoubleStream.of(1,2,3,4,3,2,1), (BiPredicate<Double, Double>) (d1, d2) -> d1 < d2, Double.MIN_VALUE).mapToObj(d -> d));

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
                (UnaryPredicate<Character>) i -> i < 1000
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
                (UnaryPredicate<Integer>) i -> i < 1000
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
                (UnaryPredicate<Double>) i -> i < 100
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
                (UnaryPredicate<Double>) (o) -> o < 100
            ).summaryStatistics()
        );

        println(
            variadicMapToObj(
                IntStream.range(0,1000),
                (TernaryMapping<Integer, Integer>) (i1, i2, i3) -> i1 * i2 * i3,
                Process.NONSUBDVIDED
            ).mapToObj(i -> i + "  ")
        );

        println(
            variadicMapToObj(
                doubleIterate(0d, d -> d < 1000, d -> ++d),
                (TernaryMapping<Double, Double>) (d1, d2, d3) -> d1 * d2 * d3,
                Process.NONSUBDVIDED
            ).mapToObj(d -> String.format("%.1f", d))
        );
    }
}
