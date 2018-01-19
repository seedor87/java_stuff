package TestingUtils.JUnitTesting.TimedRule;

import Utils.StopWatches.AbstractStopwatch;
import Utils.StopWatches.SYSStopwatch;
import Utils.StopWatches.TimeUnit;
import java.lang.reflect.Constructor;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

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

    public Description getDescription() {
        return this.description;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        this.base = base;
        this.description = description;
        return new TimedStatement(this.base, this.description, this.type, this.unit);
    }

    public class TimedStatement extends Statement {
        private AbstractStopwatch timer;
        private final Statement base;
        private final Description description;

        public <T extends AbstractStopwatch> TimedStatement(Statement base, Description description, Class<T> type, TimeUnit unit) {
            try {
                Constructor<T> ctor = (Constructor<T>) type.getDeclaredConstructors()[1];
                this.timer = ctor.newInstance(unit);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            this.base = base;
            this.description = description;
        }

        @Override
        public void evaluate() throws Throwable {
            this.timer.start();
            try {
                this.base.evaluate();
            } finally {
                this.timer.stop();
            }
            System.out.println("\u001B[32m" + this.description + " : " + this.timer + "\u001B[0m\n");
        }
    }
}
