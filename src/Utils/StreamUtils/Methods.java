package Utils.StreamUtils;

import static Utils.Console.Printing.*;
import static Utils.StreamUtils.Functions.*;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.Console.Special;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;
import Utils.StreamUtils.Interfaces.IntBiPredicate;
import Utils.StreamUtils.Interfaces.BiPredicate;

import Utils.StreamUtils.Spliterators.*;
import org.junit.Rule;
import org.junit.Test;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Methods {

    @Rule
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.MICROSECONDS);


    public static IntStream takeWhile(IntStream stream, IntPredicate predicate) {
        return StreamSupport.stream(new IntTakeWhileSpliterator(stream.spliterator(), predicate), stream.isParallel()).mapToInt(i -> i);
    }

    public static IntStream takeWhile(IntStream stream, IntBiPredicate predicate, Integer identity) {
        return stream.flatMap(Functions.takeWhile(identity, predicate));
    }

    public static IntStream takeWhile(IntStream stream, IntBiPredicate predicate) {
        return stream.flatMap(Functions.takeWhile(null, predicate));
    }

    static <T> Stream<T> takeWhile(Stream<T> stream, Predicate<? super T> predicate) {
        return StreamSupport.stream(new TakeWhileSpliterator<>(stream.spliterator(), predicate), stream.isParallel());
    }

    public static <T> Stream<T> takeWhile(Stream<T> stream, BiPredicate<T> predicate, T identity) {
        return stream.flatMap(Functions.takeWhile(identity, predicate));
    }

    public static <T> Stream<T> takeWhile(Stream<T> stream, BiPredicate<T> predicate) {
        return stream.flatMap(Functions.takeWhile(null, predicate));
    }


    public static IntStream dropWhile(IntStream stream, IntPredicate predicate) {
        return stream.flatMap(Functions.dropWhile(predicate));
    }

    public static IntStream dropWhile(IntStream stream, IntBiPredicate predicate, Integer identity) {
        return stream.flatMap(Functions.takeWhile(identity, predicate));
    }

    public static IntStream dropWhile(IntStream stream, IntBiPredicate predicate) {
        return stream.flatMap(Functions.dropWhile(null, predicate));
    }

    public static <T> Stream<T> dropWhile(Stream<T> stream, Predicate<T> predicate) {
        return stream.flatMap(Functions.dropWhile(predicate));
    }

    public static <T> Stream<T> dropWhile(Stream<T> stream, BiPredicate<T> predicate, T identity) {
        return stream.flatMap(Functions.dropWhile(identity, predicate));
    }

    public static <T> Stream<T> dropWhile(Stream<T> stream, BiPredicate<T> predicate) {
        return stream.flatMap(Functions.dropWhile(null, predicate));
    }


    public static IntStream takeOnly(IntStream stream, IntPredicate predicate) {
        return stream.flatMap(Functions.intTakeOnly(predicate));
    }

    public static <T> Stream<T> takeOnly(Stream<T> stream, Predicate<T> predicate) {
        return stream.flatMap(Functions.takeOnly(predicate));
    }

    public static IntStream dropOnly(IntStream stream, IntPredicate predicate) {
        return stream.flatMap(Functions.intTakeOnly(predicate.negate()));
    }

    public static <T> Stream<T> dropOnly(Stream<T> stream, Predicate<T> predicate) {
        return stream.flatMap(Functions.takeOnly(predicate.negate()));
    }

    public static IntStream dropN(IntStream stream, long n) {
        return stream.flatMap(Functions.intDropN(n));
    }

    public static <T> Stream<T> dropN(Stream<T> stream, long n) {
        return stream.flatMap(Functions.dropN(n));
    }

    public static IntStream takeN(IntStream stream, long n) {
        return stream.flatMap(Functions.intTakeN(n));
    }

    public static <T> Stream<T> takeN(Stream<T> stream, long n) {
        return stream.flatMap(Functions.takeN(n));
    }

    public static <T> Stream<List<T>> listsOfN(Stream<T> stream, int n) {
        return stream.flatMap(Functions.listsOfN(n));
    }

    public static Stream<List<Integer>> listsOfN(IntStream stream, int n) {
        return stream.boxed().flatMap(Functions.listsOfN(n));
    }

    public static <T> Stream<T[]> arraysOfN(Stream<T> stream, int n) {
        return stream.flatMap(Functions.listsOfN(n)).map(ts -> (T[]) ts.toArray());
    }

    public static Stream<Integer[]> arraysOfN(IntStream stream, int n) {
        return stream.boxed().flatMap(Functions.listsOfN(n)).map(integers -> integers.<Integer>toArray(new Integer[n]));
    }

    public static <T> Stream<T> reverse(Stream<T> stream) {
        return stream.<ArrayDeque<T>>collect(
                ArrayDeque::new,
                ArrayDeque::addFirst,
                ArrayDeque::addAll)
            .stream();
    }

    public static IntStream reverse(IntStream stream) {
        return stream.<ArrayDeque<Integer>>collect(
                ArrayDeque::new,
                ArrayDeque::addFirst,
                ArrayDeque::addAll)
            .stream()
            .mapToInt(Integer::intValue);
    }

    public static <T> String toString(Stream<T> stream) {
        return stream.collect(
            Collector.of(
                StringBuilder::new,
                (sb, s) -> sb.append(s.toString()),
                StringBuilder::append,
                StringBuilder::toString)
            );
    }

    public static <T> String toString(String delim, Stream<T> stream) {
        return stream.collect(
            Collector.of(
                StringBuilder::new,
                new BiConsumer<StringBuilder, T>() {
                    boolean first = true;
                    @Override
                    public void accept(StringBuilder sb, T t) {
                        if (!first) {
                            sb.append(delim);
                            sb.append(t);
                        } else {
                            sb.append(t);
                            first = false;
                        }
                    }
                },
                StringBuilder::append,
                StringBuilder::toString)
        );
    }

    public static <T> Stream<T> iterate(T seed, Predicate<T> predicate, UnaryOperator<T> operator) {
        return StreamSupport.stream(
            new BoundedSpliterator<>(
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

    @Test
    public void test() {

        println(Special.RESET,"toString(\", \", IntStream.range(0, 10).flatMap(intTakeEveryNth(3)).boxed()));\n", Special.FG_BRIGHT_CYAN,
                toString(", ", IntStream.range(0, 10).flatMap(intTakeEveryNth(3)).boxed()));

        println(Special.RESET,"reverse(IntStream.range(0, 10)));\n", Special.FG_BRIGHT_CYAN,
                reverse(IntStream.range(0, 10)));

        println(Special.RESET,"reverse(Stream.of('a','b','c','d')));\n", Special.FG_BRIGHT_CYAN,
                reverse(Stream.of('a','b','c','d')));

        println(Special.RESET,"takeN(IntStream.range(0, 10), 5));\n", Special.FG_BRIGHT_CYAN,
                takeN(IntStream.range(0, 10), 5));

        println(Special.RESET,"dropN(IntStream.range(0, 10), 5));\n", Special.FG_BRIGHT_CYAN,
                dropN(IntStream.range(0, 10), 5));

        println(Special.RESET,"takeWhile(Stream.of(\"one\", \"two\", \"three\", \"four\", \"five\"), s -> !s.contains(\"ee\")));\n", Special.FG_BRIGHT_CYAN,
                takeWhile(Stream.of("one", "two", "three", "four", "five"), s -> !s.contains("ee")));

        println(Special.RESET,"dropWhile(Stream.of(\"one\", \"two\", \"three\", \"four\", \"five\"), s -> !s.contains(\"ee\")));\n", Special.FG_BRIGHT_CYAN,
                dropWhile(Stream.of("one", "two", "three", "four", "five"), s -> !s.contains("ee")));

        println(Special.RESET,"takeWhile(IntStream.of(1,2,3,4,3,2,1), (i1, i2) -> i1 < i2, Integer.MIN_VALUE));\n", Special.FG_BRIGHT_CYAN,
                takeWhile(IntStream.of(1,2,3,4,3,2,1), (i1, i2) -> i1 < i2, Integer.MIN_VALUE));

        println(Special.RESET,"dropWhile(IntStream.of(1,2,3,4,3,2,1), (i1, i2) -> i1 < i2));\n", Special.FG_BRIGHT_CYAN,
                dropWhile(IntStream.of(1,2,3,4,3,2,1), (i1, i2) -> i1 < i2));

        println(Special.RESET,"takeWhile(Stream.of(\"one\", \"two\", \"three\", \"four\", \"five\"), (i1, i2) -> i1.length() <= i2.length(), \"\"));\n", Special.FG_BRIGHT_CYAN,
                takeWhile(Stream.of("one", "two", "three", "four", "five"), (i1, i2) -> i1.length() <= i2.length(), ""));

        println(Special.RESET,"dropWhile(Stream.of(\"one\", \"two\", \"three\", \"four\", \"five\"), (i1, i2) -> i1.length() <= i2.length()));\n", Special.FG_BRIGHT_CYAN,
                dropWhile(Stream.of("one", "two", "three", "four", "five"), (i1, i2) -> i1.length() <= i2.length()));

        println(Special.RESET,"takeOnly(IntStream.range(0, 10), value -> value % 2 == 0));\n", Special.FG_BRIGHT_CYAN,
                takeOnly(IntStream.range(0, 10), value -> value % 2 == 0));

        println(Special.RESET,"dropOnly(IntStream.range(0, 10), value -> value % 2 == 0));\n", Special.FG_BRIGHT_CYAN,
                dropOnly(IntStream.range(0, 10), value -> value % 2 == 0));

        println(Special.RESET,"takeOnly(Stream.of(\"one\", \"two\", \"three\", \"four\", \"five\"), value -> value.length() < 5));\n", Special.FG_BRIGHT_CYAN,
                takeOnly(Stream.of("one", "two", "three", "four", "five"), value -> value.length() < 5));

        println(Special.RESET,"listsOfN(Stream.of(\"one\", \"two\", \"one\", \"two\", \"one\", \"two\"), 2));\n", Special.FG_BRIGHT_CYAN,
                listsOfN(Stream.of("one", "two", "one", "two", "one", "two"), 2));

        println(Special.RESET,"arraysOfN(IntStream.range(0, 100), 3));\n", Special.FG_BRIGHT_CYAN,
                arraysOfN(IntStream.range(0, 100), 3));

        println(Special.RESET,
"            intIterate(\n" +
"                1,\n" +
"                i -> i <= 100,\n" +
"                i -> i + 1\n" +
"            )\n" +
"        );\n", Special.FG_BRIGHT_CYAN,
            intIterate(
                1,
                i -> i <= 100,
                i -> i + 1
            )
        );

        println(Special.RESET,
"            iterate(\n" +
"                'z',\n" +
"                c -> !c.equals((char) ('a' - 1)),\n" +
"                c -> (--c)\n" +
"            )\n" +
"        );\n", Special.FG_BRIGHT_CYAN,
            iterate(
                'z',
                c -> !c.equals((char) ('a' - 1)),
                c -> (--c)
            )
        );

        println(Special.RESET,
"            iterate(\n" +
"                \"1\",\n" +
"                i -> i.length() < 10,\n" +
"                i -> i + (char) (i.charAt(i.length()-1) + 1)\n" +
"            )\n" +
"        );\n", Special.FG_BRIGHT_CYAN,
            iterate(
                "1",
                i -> i.length() < 10,
                i -> i + (char) (i.charAt(i.length()-1) + 1)
            )
        );

        println(Special.RESET,
"            takeWhile(\n" +
"                Stream.generate(\n" +
"                    new Supplier<Character>() {\n" +
"                        int c = ' ';\n" +
"                        @Override\n" +
"                        public Character get() {\n" +
"                            return (char) c++;\n" +
"                        }\n" +
"                    }\n" +
"                ),\n" +
"                i -> i < 1000\n" +
"            )\n" +
"        );\n", Special.FG_BRIGHT_CYAN,
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

        println(Special.RESET,
"            takeWhile(\n" +
"                IntStream.generate(\n" +
"                    new IntSupplier() {\n" +
"                        int i = 0;\n" +
"                        @Override\n" +
"                        public int getAsInt() { return i++; }\n" +
"                    }\n" +
"                ),\n" +
"                i -> i < 1000\n" +
"            )\n" +
"        );\n", Special.FG_BRIGHT_CYAN,
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

        println(Special.RESET,
"               takeWhile(\n" +
"                    IntStream.generate(\n" +
"                        new IntSupplier() {\n" +
"                            int i = 0;\n" +
"                            @Override\n" +
"                            public int getAsInt() { return i++; }\n" +
"                        }\n" +
"                    ),\n" +
"                    i -> i < 1000\n" +
"                )\n", Special.FG_BRIGHT_CYAN,
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
    }
}
