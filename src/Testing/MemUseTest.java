package Testing;

import myUtils.Measurement.Spacer;
import myUtils.Tuple;

import static myUtils.ConsolePrinting.println;

public class MemUseTest extends AbstractTest {

    public MemUseTest(Class cl, String method) {
        super(cl, method);
    }

    @Override
    Tuple wrapperFunc(Object[] params) throws Exception {
        Spacer spacer = new Spacer();
        spacer.start();
        Tuple ret = new Tuple(myMethod(params));
        spacer.stop();
        println("Used: " + spacer);
        return ret;
    }
}
