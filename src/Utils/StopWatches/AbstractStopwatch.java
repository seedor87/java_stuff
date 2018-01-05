package Utils.StopWatches;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import org.junit.Rule;
import org.junit.Test;

import java.text.DecimalFormat;

import static Utils.Console.Printing.*;

/***
 * Abstract timer that holds capabilities of general use in code timer.
 * when implemented requires instantiation of only the getTime() and getElapsedTime() methods,
 * All other work is done in this class.
 */
public abstract class AbstractStopwatch {

    /**
     * Special exception for bad state transitions.
     */
    class IllegalStateTransitionException extends RuntimeException {
        IllegalStateTransitionException(String msg) {
            super(msg);
        }
    }

    /**
     * Interface for state machine method leveraging
     */
    public interface EnactableState<T> {
        double enact(T timer);
    }

    /**
     * Enum for state transition of timer recording machine.
     * Used post measurement to save state and transition, or throw away and resume.
     */
    public enum State implements EnactableState<AbstractStopwatch> {
        /*                         Started   Suspended     Resumed     Stopped     */
        STARTED( new boolean[]      {true,      true,       false,      true},      "Started",     (timer) -> timer.start()),
        SUSPENDED( new boolean[]    {false,     false,      true,       true},      "Suspended",   (timer) -> timer.suspend()),
        RESUMED( new boolean[]      {true,      true,       false,      true},      "Resumed",     (timer) -> timer.resume()),
        STOPPED( new boolean[]      {true,      false,      false,      true},      "Stopped",     (timer) -> timer.stop());

        public String name;
        public boolean[] transitions;
        private EnactableState<AbstractStopwatch> op;

        State(boolean[] transitions, String name, EnactableState<AbstractStopwatch> op) {
            this.name = name; this.transitions = transitions; this.op = op;
        }

        @Override
        public double enact(AbstractStopwatch timer) {
            return op.enact(timer);
        }
    }

    /**
     * protected fields for access only within extending classes.
     */
    protected TimeUnit timeUnit = Utils.StopWatches.TimeUnit.MILLI;
    protected double startTime = 01, endTime = 01, elapsedTime = 0;
    protected DecimalFormat formatter;
    protected State current = State.STOPPED;

    /**
     * abstract methods to be overridden.
     * @return double - str present time per method of measurement, and current value of elapsed time, respectively.
     */
    public abstract double getTime();

    /**
     * abstract methods to be overridden.
     * @return double - str present time per method of measurement, and current value of elapsed time, respectively.
     */
    public abstract double getElapsed(TimeUnit u);
    public double getElapsed() {
        return getElapsed(this.getTimeUnit());
    }

    /**
     * Default Constructor
     */
    public AbstractStopwatch() {}
    public AbstractStopwatch(TimeUnit u) { setTimeUnit(u); }
    private TimeUnit getTimeUnit() { return timeUnit; }

    private void setTimeUnit(TimeUnit u) {
        this.timeUnit = u;
        formatter = new DecimalFormat(this.getTimeUnit().stringFormat);
    }

    public double start () {
        startTime = getTime();
        elapsedTime = 0;
        current = State.STARTED;
        return startTime;
    }

    public double suspend() {
        double hold = getTime();
        if(current.transitions[1]) {
            endTime = hold;
            elapsedTime += (endTime - startTime);
            startTime = 0l;
            current = State.SUSPENDED;
            return endTime;
        }
        endTime = 01;
        throw new IllegalStateTransitionException(current + " -> " + State.SUSPENDED);
    }

    public double resume() {
        double hold = getTime();
        if(current.transitions[2]) {
            startTime = hold;
            endTime = 01;
            current = State.RESUMED;
            return startTime;
        }
        throw new IllegalStateTransitionException(current + " -> " + State.RESUMED);
    }

    public double stop () {
        double hold = getTime();
        if(isRunning()) {
            endTime = hold;
            elapsedTime += (endTime - startTime);
            startTime = 0l;
        } else {
            startTime = 0;
            endTime = 0;
        }
        current = State.STOPPED;
        return endTime;
    }

    public double getTimerValue(TimeUnit u) {
        double hold = getTime();
        if(isRunning()) {
            endTime = hold;
            elapsedTime += (endTime - startTime);
            startTime = hold;
        }
        return getElapsed(u);
    }

    public boolean isRunning() {
        return current == State.STARTED ||
                current == State.RESUMED;
    }

    public double getTimerValue() {
        return getTimerValue(getTimeUnit());
    }

    public String toString(TimeUnit u) {
        String ret = formatter.format(getTimerValue(u)) + " " + u.name;
        return ret;
    }

    @Override
    public String toString() {return toString(getTimeUnit());}

    public static class AbstractStopwatchTest {
        @Rule
        public TimedRule tr = new TimedRule(SYSStopwatch.class, TimeUnit.SECONDS);

        @Test
        public void test() {
            int i = 0;
            while(true) {
                printrn(i);
                i++;
                if (i % 1000 == 0) {
                    break;
                }
            }
        }
    }
}
