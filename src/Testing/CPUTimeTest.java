package Testing;

import Utils.Timers.CPUTimer;
import Utils.Timers.AbstractTimer;
import Utils.Collections.Tuple;

import static Utils.ConsolePrinting.println;

public class CPUTimeTest extends AbstractTest {

    public CPUTimeTest(Class cl, String method) {
        super(cl, method);
}

    @Override
    Tuple wrapperFunc(Object[] params) throws Exception {
        AbstractTimer timer = new CPUTimer();
        timer.start();
        Tuple ret = new Tuple(myMethod(params));
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }
}
