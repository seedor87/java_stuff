package JUnit;

import Utils.StringUtils;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StringUtils.class,
})
public class JUnitTestSuite {}
