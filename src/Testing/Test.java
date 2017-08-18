package Testing;

import myUtils.Tuple;

import java.util.concurrent.Callable;
import static Testing.TestBench.primeTest;

public class Test<T> implements Callable {

    private Tuple data;

    public <T extends Object> Test(T... args) {
        data = new Tuple<T>(args);
    }

    public void call(Tuple<T> args) {
        primeTest((Integer) args.get(0));
    }

    public void call(T... args) {
        primeTest((Integer) args[0]);
    }

    @Override
    public Object call() {
        primeTest((int) data.get(0));
        return null;
    }
}
