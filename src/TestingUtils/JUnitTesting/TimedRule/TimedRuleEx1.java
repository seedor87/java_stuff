package TestingUtils.JUnitTesting.TimedRule;

import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;
import org.junit.Rule;
import org.junit.Test;

public class TimedRuleEx1 {

    @Rule
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.MICRO);

    @Test
    public void test() {
        for (int i=0; i < 10; i++) {
            System.out.println(i);
        }
    }
}
