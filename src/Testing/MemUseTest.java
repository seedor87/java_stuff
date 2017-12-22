package Testing;

import Utils.Timers.SYSSpacer;
import Utils.Collections.Tuple;

import static Utils.ConsolePrinting.println;

public class MemUseTest extends AbstractTest {

    public MemUseTest(Class cl, String method) {
        super(cl, method);
    }

    @Override
    Tuple wrapperFunc(Object[] params) throws Exception {
        SYSSpacer spacer = new SYSSpacer();
        spacer.start();
        Tuple ret = new Tuple(myMethod(params));
        spacer.stop();
        println("Used: " + spacer);
        return ret;
    }
}
