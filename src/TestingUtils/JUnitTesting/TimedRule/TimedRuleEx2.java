package TestingUtils.JUnitTesting.TimedRule;

import org.junit.Rule;
import org.junit.Test;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;

public class TimedRuleEx2 {

    public static class Inner {
        @Rule
        public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.NANO);

        @Test
        public void test() {
            for (int i=0; i < 10; i++) {
                System.out.println(i);
            }
        }
    }
}
