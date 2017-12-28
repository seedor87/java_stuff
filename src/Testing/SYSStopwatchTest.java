package Testing;

import Utils.Timers.SYSStopwatch;
import Utils.Timers.AbstractStopwatch;
import Utils.Collections.Tuple;

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
