package Utils.StreamUtils;

import static Utils.Console.Printing.*;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.StreamUtils.MappingInterfaces.BinaryPredicate;
import Utils.Timing.SYSStopwatch;
import Utils.Timing.TimeUnit;
import Utils.StreamUtils.Spliterators.*;
import Utils.StreamUtils.MappingInterfaces.*;
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
        return StreamSupport.intStream(new IntDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends UnaryPredicate<Integer>> IntStream dropWhile(IntStream stream, S predicate) {
        return StreamSupport.intStream(new IntDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends BinaryPredicate<Integer>> IntStream dropWhile(IntStream stream, S predicate) {
        return StreamSupport.intStream(new IntDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends TrinaryPredicate<Integer>> IntStream dropWhile(IntStream stream, S predicate) {
        return StreamSupport.intStream(new IntDropWhileSpliterator(stream.spliterator(), predicate), false);
    }

    public static <S extends NaryPredicate<Double>> DoubleStream dropWhile(DoubleStream stream, S predicate) {
        return StreamSupport.doubleStream(new DoubleDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends UnaryPredicate<Double>> DoubleStream dropWhile(DoubleStream stream, S predicate) {
        return StreamSupport.doubleStream(new DoubleDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends BinaryPredicate<Double>> DoubleStream dropWhile(DoubleStream stream, S predicate) {
        return StreamSupport.doubleStream(new DoubleDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends TrinaryPredicate<Double>> DoubleStream dropWhile(DoubleStream stream, S predicate) {
        return StreamSupport.doubleStream(new DoubleDropWhileSpliterator(stream.spliterator(), predicate), false);
    }

    public static <S extends NaryPredicate<Long>> LongStream dropWhile(LongStream stream, S predicate) {
        return StreamSupport.longStream(new LongDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends UnaryPredicate<Long>> LongStream dropWhile(LongStream stream, S predicate) {
        return StreamSupport.longStream(new LongDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends BinaryPredicate<Long>> LongStream dropWhile(LongStream stream, S predicate) {
        return StreamSupport.longStream(new LongDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends TrinaryPredicate<Long>> LongStream dropWhile(LongStream stream, S predicate) {
        return StreamSupport.longStream(new LongDropWhileSpliterator(stream.spliterator(), predicate), false);
    }

    public static <S extends NaryPredicate<T>, T> Stream<T> dropWhile(Stream<T> stream, S predicate) {
        return StreamSupport.stream(new GenericDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends UnaryPredicate<T>, T> Stream<T> dropWhile(Stream<T> stream, S predicate) {
        return StreamSupport.stream(new GenericDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends BinaryPredicate<T>, T> Stream<T> dropWhile(Stream<T> stream, S predicate) {
        return StreamSupport.stream(new GenericDropWhileSpliterator(stream.spliterator(), predicate), false);
    }
    public static <S extends TrinaryPredicate<T>, T> Stream<T> dropWhile(Stream<T> stream, S predicate) {
        return StreamSupport.stream(new GenericDropWhileSpliterator(stream.spliterator(), predicate), false);
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

    public static <S extends NaryHomogeneousMapping<Integer>> IntStream variadicMapToObj(IntStream stream, S pred, Process process) {
        return StreamSupport.intStream(new IntVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryHomogeneousMapping<Integer>> IntStream variadicMapToObj(IntStream stream, S pred) {
        return StreamSupport.intStream(new IntVariadicSpliterator(stream.spliterator(), pred), false);
    }

    public static <S extends NaryHomogeneousMapping<Double>> DoubleStream variadicMapToObj(DoubleStream stream, S pred, Process process) {
        return StreamSupport.doubleStream(new DoubleVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryHomogeneousMapping<Double>> DoubleStream variadicMapToObj(DoubleStream stream, S pred) {
        return StreamSupport.doubleStream(new DoubleVariadicSpliterator(stream.spliterator(), pred), false);
    }

    public static <S extends NaryHomogeneousMapping<Long>> LongStream variadicMapToObj(LongStream stream, S pred, Process process) {
        return StreamSupport.longStream(new LongVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryHomogeneousMapping<Long>> LongStream variadicMapToObj(LongStream stream, S pred) {
        return StreamSupport.longStream(new LongVariadicSpliterator(stream.spliterator(), pred), false);
    }

    public static <S extends NaryHomogeneousMapping<T>, T> Stream<T> variadicMapToObj(Stream<T> stream, S pred, Process process) {
        return StreamSupport.stream(new GenericVariadicSpliterator(stream.spliterator(), pred, process), false);
    }

    public static <S extends NaryHomogeneousMapping<T>, T> Stream<T> variadicMapToObj(Stream<T> stream, S pred) {
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
        List<T> list = stream.collect(Collectors.toList());
        int num_lists = (int) (1.0 / n);
        int min_len = (int) (n * list.size());
        int remainder = list.size() % min_len;
        int[] break_pts = new int[num_lists+1];
        final AtomicInteger count = new AtomicInteger(0);
        for (int i = 1; i < break_pts.length; i++) {
            break_pts[i] = min_len;
        }
        while (remainder >= num_lists) {
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
        ).limit(num_lists);
    }

    public static Stream<IntStream> ntiles(IntStream stream, double n) {
        return makeNtiles(stream, n).map(integers -> integers.stream().mapToInt(i -> i));
    }

    public static Stream<DoubleStream> ntiles(DoubleStream stream, double n) {
        return makeNtiles(stream, n).map(doubles -> doubles.stream().mapToDouble(d -> d));
    }

    public static Stream<LongStream> ntiles(LongStream stream, double n) {
        return makeNtiles(stream, n).map(longs -> longs.stream().mapToLong(l -> l));
    }

    public static <T> Stream<Stream<T>> ntiles(Stream<T> stream, double n) {
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
            ntiles(IntStream.range(0,1000), d).forEach(intStream -> print("", intStream.summaryStatistics()));
            println();
        }
    }
}
