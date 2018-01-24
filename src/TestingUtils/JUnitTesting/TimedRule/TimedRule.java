package TestingUtils.JUnitTesting.TimedRule;

import Utils.StopWatches.AbstractStopwatch;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class TimedRule implements TestRule {

    private final Class<? extends AbstractStopwatch> DEFAULT_CLASS = SYSStopwatch.class;
    private final TimeUnit DEFAULT_UNIT = TimeUnit.MICROSECONDS;

    private Statement statement;
    private Description description;
    private Class<? extends AbstractStopwatch> clas = DEFAULT_CLASS;
    private TimeUnit unit = DEFAULT_UNIT;

    public TimedRule() { super(); }

    public TimedRule(Class<? extends AbstractStopwatch> type) {
        this();
        this.clas = type;
    }

    public TimedRule(Class<? extends AbstractStopwatch> type, TimeUnit unit) {
        this(type);
        this.unit = unit;
    }

    public Description getDescription() {
        return this.description;
    }

    public Statement getStatement() { return this.statement; }

    @Override
    public Statement apply(Statement statement, Description description) {
        this.statement = statement;
        this.description = description;
        return new TimedStatement(this.statement, this.description, this.clas, this.unit);
    }
}
