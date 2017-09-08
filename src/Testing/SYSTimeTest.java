package Testing;

import Utils.Timers.SYSTimer;
import Utils.Timers.AbstractTimer;
import Utils.Collections.Tuple;

import static Utils.ConsolePrinting.println;

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
