package JUnit.TimedRule;

import Utils.Timers.AbstractTimer;
import Utils.Timers.SYSTimer;
import Utils.Timers.TimeUnit;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.lang.reflect.Constructor;

public class TimedRule implements TestRule {

    private Statement base;
    private Description description;
    private Class<? extends AbstractTimer> type = SYSTimer.class;
    private TimeUnit unit = TimeUnit.MILLI;

    public TimedRule() { super(); }

    public TimedRule(Class<? extends AbstractTimer> type) {
        this();
        this.type = type;
    }

    public TimedRule(Class<? extends AbstractTimer> type, TimeUnit unit) {
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
        private AbstractTimer timer;

        public <T extends AbstractTimer> TimedStatement(Statement base, Class<T> type, TimeUnit unit) {
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
