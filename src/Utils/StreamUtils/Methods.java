package Utils.StreamUtils;

import static Utils.Console.Printing.*;
import static Utils.StreamUtils.Functions.*;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.Console.Special;
import Utils.StreamUtils.Interfaces.BinaryPredicate;
import Utils.Timing.SYSStopwatch;
import Utils.Timing.TimeUnit;
import Utils.StreamUtils.Spliterators.*;
import Utils.StreamUtils.Interfaces.*;
import Utils.StreamUtils.Spliterators.GenericVariadicSpliterator.Process;

import org.junit.Rule;
import org.junit.Test;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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

    public static IntStream reverse(IntStream stream) {
        return StreamSupport.intStream(new IntReverseSpliterator(stream.spliterator()), false);
    }

    public static DoubleStream reverse(DoubleStream stream) {
        return StreamSupport.doubleStream(new DoubleReverseSpliterator(stream.spliterator()), false);
    }

    public static LongStream reverse(LongStream stream) {
        return StreamSupport.longStream(new LongReverseSpliterator(stream.spliterator()), false);
    }

    public static <T, U extends BaseStream<T, U>> Stream<T> reverse(BaseStream<T, U> stream) {
        return StreamSupport.stream(new GenericReverseSpliterator<>(stream.spliterator()), false);
    }

    public static IntStream intIterate(Integer seed, Predicate<Integer> predicate, UnaryOperator<Integer> operator) {
        return StreamSupport.intStream(
            new IntBoundedSpliterator(
                seed,
                predicate,
                operator
            ),
            false
        );
    }

    public static DoubleStream doubleIterate(double seed, Predicate<Double> predicate, UnaryOperator<Double> operator) {
        return StreamSupport.doubleStream(
            new DoubleBoundedSpliterator(
                    seed,
                    predicate,
                    operator
            ),
            false
        );
    }

    public static LongStream longIterate(Long seed, Predicate<Long> predicate, UnaryOperator<Long> operator) {
        return StreamSupport.longStream(
            new LongBoundedSpliterator(
                    seed,
                    predicate,
                    operator
            ),
            false
        );
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

    public static <S extends NaryPredicate<Integer>> IntStream takeWhile(IntStream stream, S predicate) {
        return StreamSupport.intStream(new IntTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends UnaryPredicate<Integer>> IntStream takeWhile(IntStream stream, S predicate) {
        return StreamSupport.intStream(new IntTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends BinaryPredicate<Integer>> IntStream takeWhile(IntStream stream, S predicate) {
        return StreamSupport.intStream(new IntTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends TrinaryPredicate<Integer>> IntStream takeWhile(IntStream stream, S predicate) {
        return StreamSupport.intStream(new IntTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }

    public static <S extends NaryPredicate<Double>> DoubleStream takeWhile(DoubleStream stream, S predicate) {
        return StreamSupport.doubleStream(new DoubleTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends UnaryPredicate<Double>> DoubleStream takeWhile(DoubleStream stream, S predicate) {
        return StreamSupport.doubleStream(new DoubleTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends BinaryPredicate<Double>> DoubleStream takeWhile(DoubleStream stream, S predicate) {
        return StreamSupport.doubleStream(new DoubleTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends TrinaryPredicate<Double>> DoubleStream takeWhile(DoubleStream stream, S predicate) {
        return StreamSupport.doubleStream(new DoubleTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }

    public static <S extends NaryPredicate<Long>> LongStream takeWhile(LongStream stream, S predicate) {
        return StreamSupport.longStream(new LongTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends UnaryPredicate<Long>> LongStream takeWhile(LongStream stream, S predicate) {
        return StreamSupport.longStream(new LongTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends BinaryPredicate<Long>> LongStream takeWhile(LongStream stream, S predicate) {
        return StreamSupport.longStream(new LongTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends TrinaryPredicate<Long>> LongStream takeWhile(LongStream stream, S predicate) {
        return StreamSupport.longStream(new LongTakeWhileSpliterator(stream.spliterator(), predicate), false);
    }

    public static <S extends NaryPredicate<T>, T> Stream<T> takeWhile(Stream<T> stream, S predicate) {
        return StreamSupport.stream(new GenericTakeWhileSpliterator<>(stream.spliterator(), predicate), false);
    }
    public static <S extends UnaryPredicate<T>, T> Stream<T> takeWhile(Stream<T> stream, S predicate) {
        return StreamSupport.stream(new GenericTakeWhileSpliterator<>(stream.spliterator(), predicate), false);
    }
    public static <S extends BinaryPredicate<T>, T> Stream<T> takeWhile(Stream<T> stream, S predicate) {
        return StreamSupport.stream(new GenericTakeWhileSpliterator<>(stream.spliterator(), predicate), false);
    }
    public static <S extends TrinaryPredicate<T>, T> Stream<T> takeWhile(Stream<T> stream, S predicate) {
        return StreamSupport.stream(new GenericTakeWhileSpliterator<>(stream.spliterator(), predicate), false);
    }

    public static <S extends NaryPredicate<Integer>> IntStream dropWhile(IntStream stream, S predicate) {
        return stream.flatMap(Functions.intDropWhile(predicate));
    }
    public static <S extends UnaryPredicate<Integer>> IntStream dropWhile(IntStream stream, S predicate) {
        return stream.flatMap(Functions.intDropWhile(predicate));
    }
    public static <S extends BinaryPredicate<Integer>> IntStream dropWhile(IntStream stream, S predicate) {
        return stream.flatMap(Functions.intDropWhile(predicate));
    }
    public static <S extends TrinaryPredicate<Integer>> IntStream dropWhile(IntStream stream, S predicate) {
        return stream.flatMap(Functions.intDropWhile(predicate));
    }

    public static <S extends NaryPredicate<Double>> DoubleStream dropWhile(DoubleStream stream, S predicate) {
        return stream.flatMap(Functions.doubleDropWhile(predicate));
    }
    public static <S extends UnaryPredicate<Double>> DoubleStream dropWhile(DoubleStream stream, S predicate) {
        return stream.flatMap(Functions.doubleDropWhile(predicate));
    }
    public static <S extends BinaryPredicate<Double>> DoubleStream dropWhile(DoubleStream stream, S predicate) {
        return stream.flatMap(Functions.doubleDropWhile(predicate));
    }
    public static <S extends TrinaryPredicate<Double>> DoubleStream dropWhile(DoubleStream stream, S predicate) {
        return stream.flatMap(Functions.doubleDropWhile(predicate));
    }

    public static <S extends NaryPredicate<Long>> LongStream dropWhile(LongStream stream, S predicate) {
        return stream.flatMap(Functions.longDropWhile(predicate));
    }
    public static <S extends UnaryPredicate<Long>> LongStream dropWhile(LongStream stream, S predicate) {
        return stream.flatMap(Functions.longDropWhile(predicate));
    }
    public static <S extends BinaryPredicate<Long>> LongStream dropWhile(LongStream stream, S predicate) {
        return stream.flatMap(Functions.longDropWhile(predicate));
    }
    public static <S extends TrinaryPredicate<Long>> LongStream dropWhile(LongStream stream, S predicate) {
        return stream.flatMap(Functions.longDropWhile(predicate));
    }

    public static <S extends NaryPredicate<T>, T> Stream<T> dropWhile(Stream<T> stream, S predicate) {
        return stream.flatMap(Functions.dropWhile(predicate));
    }
    public static <S extends UnaryPredicate<T>, T> Stream<T> dropWhile(Stream<T> stream, S predicate) {
        return stream.flatMap(Functions.dropWhile(predicate));
    }
    public static <S extends BinaryPredicate<T>, T> Stream<T> dropWhile(Stream<T> stream, S predicate) {
        return stream.flatMap(Functions.dropWhile(predicate));
    }
    public static <S extends TrinaryPredicate<T>, T> Stream<T> dropWhile(Stream<T> stream, S predicate) {
        return stream.flatMap(Functions.dropWhile(predicate));
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

    public static <S extends NaryHomogenousMapping<Integer>> IntStream variadicMapToObj(IntStream stream, S pred, Process process) {
        return StreamSupport.intStream(new IntVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryHomogenousMapping<Integer>> IntStream variadicMapToObj(IntStream stream, S pred) {
        return StreamSupport.intStream(new IntVariadicSpliterator(stream.spliterator(), pred), false);
    }

    public static <S extends NaryHomogenousMapping<Double>> DoubleStream variadicMapToObj(DoubleStream stream, S pred, Process process) {
        return StreamSupport.doubleStream(new DoubleVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryHomogenousMapping<Double>> DoubleStream variadicMapToObj(DoubleStream stream, S pred) {
        return StreamSupport.doubleStream(new DoubleVariadicSpliterator(stream.spliterator(), pred), false);
    }

    public static <S extends NaryHomogenousMapping<Long>> LongStream variadicMapToObj(LongStream stream, S pred, Process process) {
        return StreamSupport.longStream(new LongVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryHomogenousMapping<Long>> LongStream variadicMapToObj(LongStream stream, S pred) {
        return StreamSupport.longStream(new LongVariadicSpliterator(stream.spliterator(), pred), false);
    }

    public static <S extends NaryHomogenousMapping<T>, T> Stream<T> variadicMapToObj(Stream<T> stream, S pred, Process process) {
        return StreamSupport.stream(new GenericVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryHomogenousMapping<T>, T> Stream<T> variadicMapToObj(Stream<T> stream, S pred) {
        return StreamSupport.stream(new GenericVariadicSpliterator(stream.spliterator(), pred), false);
    }

    public static Stream<List<Integer>> makeNtiles(IntStream stream, double n) {
        return makeNtiles(stream.boxed(), n);
    }

    public static Stream<List<Double>> makeNtiles(DoubleStream stream, double n) {
        return makeNtiles(stream.boxed(), n);
    }

    public static Stream<List<Long>> makeNtiles(LongStream stream, double n) {
        return makeNtiles(stream.boxed(), n);
    }

    public static <T> Stream<List<T>> makeNtiles(Stream<T> stream, double n) {
        if (n > 1.0 || n <= 0.0) {
            throw new IllegalArgumentException("n: " + n + ", must be between 0 and 1");
        }
        final List<T> list = stream.collect(Collectors.toList());
        final int amount = (int) (1.0 / n);
        final int len = (int) (n * list.size());
        final AtomicInteger count = new AtomicInteger(0);
        int remainder = list.size() % len;
        int[] break_pts = new int[amount+1];
        for (int i = 1; i < break_pts.length; i++) {
            break_pts[i] = len;
        }
        while (remainder >= amount) {
            for (int i = 1; i < break_pts.length; i++) {
                break_pts[i] += 1;
                remainder--;
            }
        }
        while(remainder > 0) {
            break_pts[remainder--] += 1;
        }
        for (int i = 1; i < break_pts.length; i++) {
            break_pts[i] = break_pts[i-1] + break_pts[i];
        }
        return Stream.generate(() ->
            list.subList(
                break_pts[count.get()],
                break_pts[count.incrementAndGet()]
            )
        ).limit(amount);
    }

    public static Stream<IntStream> Ntiles (IntStream stream, double n) {
        return makeNtiles(stream, n).map(integers -> integers.stream().mapToInt(i -> i));
    }

    public static Stream<DoubleStream> Ntiles (DoubleStream stream, double n) {
        return makeNtiles(stream, n).map(doubles -> doubles.stream().mapToDouble(d -> d));
    }

    public static Stream<LongStream> Ntiles (LongStream stream, double n) {
        return makeNtiles(stream, n).map(longs -> longs.stream().mapToLong(l -> l));
    }

    public static <T> Stream<Stream<T>> Ntiles (Stream<T> stream, double n) {
        return makeNtiles(stream, n).map(Collection::stream);
    }

    public static IntStream concat(IntStream... streams) {
        return Stream.of(streams).reduce(IntStream.empty(), IntStream::concat);
    }
    public static DoubleStream concat(DoubleStream... streams) {
        return Stream.of(streams).reduce(DoubleStream.empty(), DoubleStream::concat);
    }
    public static LongStream concat(LongStream... streams) {
        return Stream.of(streams).reduce(LongStream.empty(), LongStream::concat);
    }
    public static <T> Stream<T> concat(Stream<T>... streams) {
        return Stream.of(streams).reduce(Stream.empty(), Stream::concat);
    }

    @Test
    public void test() {
        double[] percentiles = new double[]{.01,.02,.03,.04,.05,.06,.07,.08,.09,.10,.11,.12,.13,.14,.15,.16,.17,.18,.19,.2,.21,.22,.23,.24,.25};
        for (double d : percentiles) {
            println(d);
            Ntiles(IntStream.range(0,1000), d).forEach(intStream -> print("", intStream.summaryStatistics()));
            println();
        }
    }

    public static void main(String[] args) {

        println(takeWhile(IntStream.range(0,10), (UnaryPredicate<Integer>) (i) -> i < 10));
        println(takeWhile(IntStream.range(0,10), (UnaryPredicate<Integer>) (i) -> i < 5));
        println(takeWhile(IntStream.range(0,10), (UnaryPredicate<Integer>) (i) -> i < 0));

        println(takeWhile(IntStream.of(1,2,3,4,3,2,1), (BinaryPredicate<Integer>) (i1, i2) -> i1 <= i2));
        println(takeWhile(IntStream.of(1,2,3,4,3,2,1), (BinaryPredicate<Integer>) (i1, i2) -> true));
        println(takeWhile(IntStream.of(1,2,3,4,3,2,1), (BinaryPredicate<Integer>) (i1, i2) -> i1 > i2));


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
                    (UnaryPredicate<Double>) (d) -> d >= .000001
            )
            .reduce((left, right) -> left + right)
        );

        println(Special.FG_BRIGHT_CYAN, reverse(IntStream.range(0, 10)));

        println(Special.FG_BRIGHT_CYAN, reverse(Stream.of('a','b','c','d')));

        println(Special.FG_BRIGHT_CYAN, IntStream.range(0, 10).limit(5));

        println(Special.FG_BRIGHT_CYAN, dropN(IntStream.range(0, 10), 5));

        println(Special.FG_BRIGHT_CYAN, takeWhile(Stream.of("one", "two", "three", "four", "five", "six"), (UnaryPredicate<String>) s -> !s.contains("our")));

        println(Special.FG_BRIGHT_CYAN, dropWhile(Stream.of("one", "two", "three", "four", "five", "six"), (UnaryPredicate<String>) s -> !s.contains("our")));

        println(Special.FG_BRIGHT_CYAN, takeWhile(IntStream.range(0, 10), (BinaryPredicate<Integer>) (i1, i2) -> i1 < i2).map(i -> i));

        println(Special.FG_BRIGHT_CYAN, dropWhile(IntStream.range(0, 10), (BinaryPredicate<Integer>) (i1, i2) -> i1 > i2));

        println(Special.FG_BRIGHT_CYAN, takeWhile(Stream.of("one", "two", "three", "four", "five", "six"), (TrinaryPredicate<String>) (i1, i2, i3) -> i1.length() <= i2.length() && i2.length() < i3.length()).map(i -> i));

        println(Special.FG_BRIGHT_CYAN, dropWhile(Stream.of("one", "two", "three", "four", "five", "six"), (BinaryPredicate<String>) (i1, i2) -> i1.length() <= i2.length()));

        println(Special.FG_BRIGHT_CYAN, takeOnly(IntStream.range(0, 10), value -> value % 2 == 0));

        println(Special.FG_BRIGHT_CYAN, dropOnly(IntStream.range(0, 10), value -> value % 2 == 0));

        println(Special.FG_BRIGHT_CYAN, takeOnly(Stream.of("one", "two", "three", "four", "five", "six"), value -> value.length() < 5));

        println(Special.FG_BRIGHT_CYAN, listsOfN(Stream.of("one", "two", "three", "four", "five", "six"), 3));

        println(Special.FG_BRIGHT_CYAN, arraysOfN(IntStream.range(0, 100), 3));

        println(Special.FG_RED, takeWhile(DoubleStream.of(1,2,3,4,3,2,1), (UnaryPredicate<Double>) (d) -> d < 4));

        println(Special.FG_RED, takeWhile(DoubleStream.of(1,2,3,4,3,2,1), (BinaryPredicate<Double>) (d1, d2) -> d1 < d2).boxed());


        println(Special.FG_RED, dropWhile(DoubleStream.of(1,2,3,4,3,2,1), (UnaryPredicate<Double>) (d) -> d < 4));

        println(Special.FG_RED, dropWhile(DoubleStream.of(1,2,3,4,3,2,1), (BinaryPredicate<Double>) (d1, d2) -> d1 < d2));

        println(Special.FG_RED, dropWhile(DoubleStream.of(1,2,3,4,3,2,1), (TrinaryPredicate<Double>) (d1, d2, d3) -> d1 < d2));

        println(Special.FG_RED, dropWhile(DoubleStream.of(1,2,3,4,3,2,1), (QuaternaryPredicate<Double>) (d1, d2, d3, d4) -> d1 < d2));

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
                (TrinaryHomogenousMapping<Integer>) (i1, i2, i3) -> i1 * i2 * i3,
                Process.NONSUBDVIDED
            ).mapToObj(i -> i + "  ")
        );

        println(
            variadicMapToObj(
                doubleIterate(0d, d -> d < 1000, d -> ++d),
                (TrinaryHomogenousMapping<Double>) (d1, d2, d3) -> d1 * d2 * d3,
                Process.NONSUBDVIDED
            ).mapToObj(d -> String.format("%.1f", d))
        );

        println(
            concat(
                intIterate(0, i -> i < 3, i -> ++i).boxed(),
                intIterate(3, i -> i < 6, i -> ++i).boxed(),
                intIterate(6, i -> i < 9, i -> ++i).boxed()
            )
        );
    }
}
