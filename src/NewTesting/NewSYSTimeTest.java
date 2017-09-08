package NewTesting;

import Utils.Timers.SYSTimer;
import Utils.Timers.AbstractTimer;
import Utils.Collections.Tuple;

import static Utils.ConsolePrinting.println;

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
