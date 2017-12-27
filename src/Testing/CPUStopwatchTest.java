package Testing;

import Utils.Timers.CPUStopwatch;
import Utils.Timers.AbstractStopwatch;
import Utils.Collections.Tuple;

import static Utils.ConsolePrinting.println;

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
