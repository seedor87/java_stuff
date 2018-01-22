package TestingUtils.JUnitTesting.TimedRule.examples;

import TestingUtils.JUnitTesting.TimedRule.Testable;
import TestingUtils.JUnitTesting.TimedRule.Timeable;
import org.junit.Test;

public abstract class TimedRuleEx2 extends Timeable implements Testable {

    /*
    The method we want to test, does not have to be static
     */
    public static void doMethod() {
        for (int i=0; i < 10; i++) {
            System.out.println(i);
        }
    }

    /*
    Valid compilation but will fail as we CANNOT instantiate an abstract class
     */
    @Test
    public void test() {
        doMethod();
    }

    /*
    To get around this, we use the following dynamic to work, make a static
    inner class that has a valid default constructor and test that instead
     */
    public static class Inner extends Timeable implements Testable {
        @Override
        @Test
        public void test() {
            TimedRuleEx2.doMethod();
        }
    }


}
