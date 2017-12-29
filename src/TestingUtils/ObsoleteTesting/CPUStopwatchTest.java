package TestingUtils.ObsoleteTesting;

import Utils.Timers.CPUStopwatch;
import Utils.Timers.AbstractStopwatch;
import Utils.Collections.OldTuple.Tuple;

import static Utils.Console.Printing.println;

public class CPUStopwatchTest extends AbstractTest {

    public CPUStopwatchTest(Class cl, String method) {
        super(cl, method);
}

    @Override
    Tuple wrapperFunc(Object[] params) throws Exception {
        AbstractStopwatch timer = new CPUStopwatch();
        timer.start();
        Tuple ret = new Tuple(myMethod(params));
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }
}
