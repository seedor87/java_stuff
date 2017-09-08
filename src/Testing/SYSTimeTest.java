package Testing;

import myUtils.Measurement.SYSTimer;
import myUtils.Measurement.AbstractTimer;
import myUtils.Tuple;

import static myUtils.ConsolePrinting.println;

public class SYSTimeTest extends AbstractTest {

    public SYSTimeTest(Class cl, String method) {
        super(cl, method);
    }

    @Override
    Tuple wrapperFunc(Object[] params) throws Exception {
        AbstractTimer timer = new SYSTimer();
        timer.start();
        Tuple ret = new Tuple(myMethod(params));
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }
}
