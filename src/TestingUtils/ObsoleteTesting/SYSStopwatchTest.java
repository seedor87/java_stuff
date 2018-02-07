package TestingUtils.ObsoleteTesting;

import Utils.Timing.SYSStopwatch;
import Utils.Timing.AbstractStopwatch;
import Utils.Collections.OldTuple.Tuple;

import static Utils.Console.Printing.println;

public class SYSStopwatchTest extends AbstractTest {

    public SYSStopwatchTest(Class cl, String method) {
        super(cl, method);
    }

    @Override
    Tuple wrapperFunc(Object[] params) throws Exception {
        AbstractStopwatch timer = new SYSStopwatch();
        timer.start();
        Tuple ret = new Tuple(myMethod(params));
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }
}
