package TestingUtils.ObsoleteTesting;

import Utils.StopWatches.SYSSpacer;
import Utils.Collections.OldTuple.Tuple;

import static Utils.Console.Printing.println;

public class SYSSpaceTest extends AbstractTest {

    public SYSSpaceTest(Class cl, String method) {
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
