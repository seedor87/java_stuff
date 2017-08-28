package Testing;

import myUtils.Measurement.SYSTimer;
import myUtils.Measurement.Timer;
import myUtils.Tuple;

import static myUtils.ConsolePrinting.println;

public class SYSTimeTest extends AbstractTest {

    public SYSTimeTest(Class cl, String method) {
        super(cl, method);
    }

    @Override
    Tuple wrapperFunc(Object[] params) throws Exception {
        Timer timer = new SYSTimer(Timer.TimeUnit.NANO);
        timer.start();
        Tuple ret = new Tuple(m.invoke(obj, params));
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }
}
