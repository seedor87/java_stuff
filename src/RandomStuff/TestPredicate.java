package RandomStuff;

import com.sun.jmx.remote.internal.ArrayQueue;

import static Utils.Console.Printing.println;

interface NaryPred<T> {
    default boolean test() { return true; }
    default boolean test(T t) { return test(); }
    default boolean test(T t1, T t2) { return test(t1); }
    default boolean test(T t1, T t2, T t3) { return test(t1, t2); }
            boolean test(T... ts);
}

interface TernaryPred<T> extends NaryPred<T> {
    @Override
            boolean test(T t1, T t2, T t3);
    default boolean test(T... ts) { return test(ts[0], ts[1], ts[2]); }
}

interface BinaryPred<T> extends TernaryPred<T> {
    @Override
            boolean test(T t1, T t2);
    default boolean test(T t1, T t2, T t3) { return test(t1, t2); }
}

interface UnaryPred<T> extends BinaryPred<T> {
    @Override
            boolean test(T t);
    default boolean test(T t1, T t2) { return test(t1); }
}

interface NullaryPred<T> extends UnaryPred<T> {
    @Override
            boolean test();
    default boolean test(T t) { return test(); }
}

public class TestPredicate {

    public static <T extends Comparable> boolean eval(NaryPred<T> pred) {
        return pred.test();
    }

    public static void main(String[] args) {
        ArrayQueue<Integer> q = new ArrayQueue<>(4);
        q.add(4);
        println(q);
        q.add(3);
        println(q);

        Integer[] ints = new Integer[]{0,1,2,2};

        NaryPred<Integer> naryPred = integers -> {
            int i = 0;
            while (i+1 < integers.length) {
                if (integers[i] >= integers[++i]) { return false; }
            }
            return true;
        };

        println(TestPredicate.eval(naryPred));

        println(TestPredicate.eval((TernaryPred<Integer>) (t1, t2, t3) -> t1.compareTo(t2) < 0 && t2.compareTo(t3) < 0));

        println(TestPredicate.eval((BinaryPred<Integer>) (t1, t2) -> t1 < t2));

        println(TestPredicate.eval((UnaryPred<Integer>) (t) -> t < 50));

        println(TestPredicate.eval((NullaryPred<Integer>) () -> 1 < 2));
    }
}
