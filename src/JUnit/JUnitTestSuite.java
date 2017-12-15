package JUnit;

import Utils.StringUtils.StringUtils;
import Utils.StringUtils.StringUtilsPlusPlus;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        StringUtils.class,
        StringUtilsPlusPlus.class
})
public class JUnitTestSuite {}
