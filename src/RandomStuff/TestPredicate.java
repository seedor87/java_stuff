package RandomStuff;

import java.util.Arrays;
import java.util.Collection;

import static Utils.Console.Printing.println;

interface NaryPred<R, S> {
            R test(S... ts);
    default R test(Collection<S> ts) { return test((S[]) ts.toArray()); }
}

interface TernaryPred<R, S> extends NaryPred<R, S> {
            R test(S t1, S t2, S t3);
    @Override
    default R test(S... ts) { return test(ts[0], ts[1], ts[2]); }
}

interface BinaryPred<R, S> extends NaryPred<R, S> {
            R test(S t1, S t2);
    @Override
    default R test(S...ts) { return test(ts[0], ts[1]); }
}

interface UnaryPred<R, S> extends NaryPred<R, S> {
            R test(S t);
    @Override
    default R test(S... ts) { return test(ts[0]); }
}

interface NullaryPred<R, S> extends NaryPred<R, S> {
            R test();
    @Override
    default R test(S... ts) { return test(); }
}

public class TestPredicate {

    public static <R, S> R eval(NaryPred<R, S> pred, S... ts) {
        return pred.test(ts);
    }

    public static <R, S> R eval(NaryPred<R, S> pred, Collection<S> ts) {
        return pred.test(ts);
    }

    public static void main(String[] args) {

        println(TestPredicate.eval((NullaryPred<Boolean, Integer>) () -> 1 < 2));
        println(TestPredicate.eval((NullaryPred<Boolean, Integer>) () -> 1  < 2, Arrays.asList()));

        println(TestPredicate.eval((UnaryPred<Boolean, Integer>) (t) -> t < 50, 1));
        println(TestPredicate.eval((UnaryPred<Boolean, Integer>) (t) -> t < 50, Arrays.asList(1)));

        println(TestPredicate.eval((BinaryPred<Boolean, Integer>) (t1, t2) -> t1 < t2, 1, 2));
        println(TestPredicate.eval((BinaryPred<Boolean, Integer>) (t1, t2) -> t1 < t2, Arrays.asList(1,2)));

        println(TestPredicate.eval((TernaryPred<Boolean, Integer>) (t1, t2, t3) -> t1 < t2 && t2 < t3, 1,2,3));
        println(TestPredicate.eval((TernaryPred<Boolean, Integer>) (t1, t2, t3) -> t1 < t2 && t2 < t3, Arrays.asList(1,2,3)));

        println(TestPredicate.eval((NaryPred<Boolean, Integer>) ts -> {
            int i = 0;
            while(i+1 < ts.length) {
                if (ts[i] >= ts[++i]) { return false; }
            }
            return true;
        }, 1,2,3,4));
        println(TestPredicate.eval((NaryPred<Boolean, Integer>) ts -> {
            int i = 0;
            while(i+1 < ts.length) {
                if (ts[i] >= ts[++i]) { return false; }
            }
            return true;
        }, Arrays.asList(1,2,3,4)));

    }
}
