package TestingUtils.JUnitTesting.TimedRule.examples;

import TestingUtils.JUnitTesting.TimedRule.Testable;
import TestingUtils.JUnitTesting.TimedRule.TimeableClass;
import org.junit.Test;

public class TimedRuleEx1 extends TimeableClass implements Testable {

    public static void doMethod() {
        for (int i=0; i < 10; i++) {
            System.out.println(i);
        }
    }

    @Override
    @Test
    public void test() { TimedRuleEx1.doMethod(); }
}
