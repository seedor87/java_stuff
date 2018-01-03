package TestingUtils.JUnitTesting.TimedRule;

import Utils.Timers.SYSStopwatch;
import Utils.Timers.TimeUnit;
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
