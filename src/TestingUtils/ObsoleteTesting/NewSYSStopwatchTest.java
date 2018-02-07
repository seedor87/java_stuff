package TestingUtils.ObsoleteTesting;

import Utils.Timing.SYSStopwatch;
import Utils.Timing.AbstractStopwatch;
import Utils.Collections.OldTuple.Tuple;

import static Utils.Console.Printing.println;

public abstract class NewSYSStopwatchTest<T extends Object> extends NewAbstractTest {

    @Override
    public Tuple test(Object... args) {
        return test(this.exe, args);
    }

    @Override
    public Tuple test (VarArgs lam, Object... args)  {
        AbstractStopwatch timer = new SYSStopwatch();
        timer.start();
        Tuple ret = lam.varArgs(args);
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }

    public abstract Tuple runThis(Object... params);
}
