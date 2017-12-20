package JUnit.TimedRule;

import Utils.Timers.SYSTimer;
import Utils.Timers.TimeUnit;
import org.junit.Rule;
import org.junit.Test;

public class TimedRuleTest2 {

    public static class Inner {
        @Rule
        public TimedRule jcr = new TimedRule(SYSTimer.class, TimeUnit.NANO);

        @Test
        public void test() {
            for (int i=0; i < 10; i++) {
                System.out.println(i);
            }
        }
    }
}
