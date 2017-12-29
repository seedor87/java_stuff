package TestingUtils.JUnitTesting.TimedRule;

import Utils.Timers.AbstractStopwatch;
import Utils.Timers.SYSStopwatch;
import Utils.Timers.TimeUnit;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.reflect.Constructor;

public class TimedRule implements TestRule {

    private Statement base;
    private Description description;
    private Class<? extends AbstractStopwatch> type = SYSStopwatch.class;
    private TimeUnit unit = TimeUnit.MILLI;

    public TimedRule() { super(); }

    public TimedRule(Class<? extends AbstractStopwatch> type) {
        this();
        this.type = type;
    }

    public TimedRule(Class<? extends AbstractStopwatch> type, TimeUnit unit) {
        this(type);
        this.unit = unit;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        this.base = base;
        this.description = description;
        return new TimedStatement(this.base, this.type, this.unit);
    }

    public class TimedStatement extends Statement {
        private final Statement base;
        private AbstractStopwatch timer;

        public <T extends AbstractStopwatch> TimedStatement(Statement base, Class<T> type, TimeUnit unit) {
            try {
                Constructor<T> ctor = (Constructor<T>) type.getDeclaredConstructors()[1];
                this.timer = ctor.newInstance(unit);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            this.base = base;
        }

        @Override
        public void evaluate() throws Throwable {
            timer.start();
            try {
                base.evaluate();
            } finally {
                timer.stop();
            }
            System.out.println(timer);
        }
    }
}
