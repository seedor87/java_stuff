package Utils.StreamUtils.UnitTests;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;


import static Utils.StreamUtils.Methods.concat;
import static Utils.StreamUtils.Methods.intIterate;
import static Utils.StreamUtils.Methods.makeString;
import static junit.framework.TestCase.assertEquals;

public class RandomTest {

    @Rule
    public TestRule rule = new TimedRule();

    @Test
    public void test() {

        assertEquals(
                "0, 1, 2, 3, 4, 5, 6, 7, 8",
                makeString(
                        concat(
                                intIterate(0, i -> i < 3, i -> ++i).boxed(),
                                intIterate(3, i -> i < 6, i -> ++i).boxed(),
                                intIterate(6, i -> i < 9, i -> ++i).boxed()
                        )
                )
        );
    }
}
