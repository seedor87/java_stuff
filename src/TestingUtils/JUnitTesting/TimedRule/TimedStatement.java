package TestingUtils.JUnitTesting.TimedRule;

import Utils.StopWatches.AbstractStopwatch;
import Utils.StopWatches.TimeUnit;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import java.lang.reflect.Constructor;

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
    public String toString() {
        StringBuilder message = new StringBuilder("\n" + this.description + " : " + this.timer);
        int len = message.length() - 1;
        for (int i = 0; i < len; i++) {
            message.insert(0, "*");
        }
        return message.toString();
    }

    @Override
    public void evaluate() throws Throwable {
        this.timer.start();
        try {
            this.base.evaluate();
        } finally {
            this.timer.stop();
        }

        System.out.println(this.toString());
    }
}
