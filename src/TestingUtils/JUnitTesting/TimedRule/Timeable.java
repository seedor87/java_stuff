package TestingUtils.JUnitTesting.TimedRule;

import org.junit.rules.TestRule;
import org.junit.Rule;

public abstract class Timeable {
    @Rule public TestRule rule = new TimedRule();
}
