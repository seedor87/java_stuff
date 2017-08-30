package Testing;

import myUtils.ConsolePrinting;
import myUtils.Measurement.SYSTimer;
import myUtils.Measurement.Timer;
import myUtils.Tuple;

import java.lang.reflect.Method;

import static myUtils.ConsolePrinting.println;

public abstract class superWrapper<T extends Object> {

    protected interface VarArgs<T> {
        Tuple<T> varArgs(T... params);
    }

    public <T> Tuple<T> test(T... args) {
        Timer timer = new SYSTimer();
        timer.start();
        Tuple ret = this.exe.varArgs(args);
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }

    public static <T> Tuple<T> test(VarArgs<T> lam, T... args) {
        Timer timer = new SYSTimer();
        timer.start();
        Tuple ret = lam.varArgs(args);
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }

    public abstract <T> Tuple<T> runIt(T... params);

    public VarArgs<Object> exe = (Object...params) -> runIt(params);
}
