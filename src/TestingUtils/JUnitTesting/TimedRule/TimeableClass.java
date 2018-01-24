package TestingUtils.JUnitTesting.TimedRule;

import org.junit.rules.TestRule;
import org.junit.Rule;

public abstract class TimeableClass {
    @Rule public TestRule rule = new TimedRule();
}
