package NewTesting;

import myUtils.Measurement.SYSTimer;
import myUtils.Measurement.AbstractTimer;
import myUtils.Tuple;

import static myUtils.ConsolePrinting.println;

public abstract class NewSYSTimeTest<T extends Object> extends NewAbstractTest {

    @Override
    public Tuple test(Object... args) {
        return test(this.exe, args);
    }

    @Override
    public Tuple test (VarArgs lam, Object... args)  {
        AbstractTimer timer = new SYSTimer();
        timer.start();
        Tuple ret = lam.varArgs(args);
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }

    public abstract Tuple runThis(Object... params);
}
