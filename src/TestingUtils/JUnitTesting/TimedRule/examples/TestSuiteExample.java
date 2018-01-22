package TestingUtils.JUnitTesting.TimedRule.examples;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TimedRuleEx1.class,
        TimedRuleEx2.Inner.class,
})
public class TestSuiteExample {}
