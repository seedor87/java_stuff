package Testing;

import myUtils.Measurement.CPUTimer;
import myUtils.Measurement.Timer;
import myUtils.Tuple;

import static myUtils.ConsolePrinting.println;

public class CPUTimeTest extends AbstractTest {

    public CPUTimeTest(Class cl, String method) {
        super(cl, method);
}

    @Override
    Tuple wrapperFunc(Object[] params) throws Exception {
        Timer timer = new CPUTimer();
        timer.start();
        Tuple ret = new Tuple(myMethod(params));
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }
}
