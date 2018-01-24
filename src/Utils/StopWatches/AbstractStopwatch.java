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

    private final long ZERO = 0L;
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
    public interface Enactable<T> {
        double transition(T timer);
    }

    /**
     * Enum for state transition of timer recording machine.
     * Used post measurement to save state and transition, or throw away and resume.
     */
    public enum StopwatchState implements Enactable<AbstractStopwatch> {
        /*                         Started   Suspended     Resumed     Stopped     */
        STARTED( new boolean[]      {true,      true,       false,      true},      "Started",     (timer) -> timer.start()),
        SUSPENDED( new boolean[]    {false,     false,      true,       true},      "Suspended",   (timer) -> timer.suspend()),
        RESUMED( new boolean[]      {true,      true,       false,      true},      "Resumed",     (timer) -> timer.resume()),
        STOPPED( new boolean[]      {true,      false,      false,      true},      "Stopped",     (timer) -> timer.stop());

        public String name;
        public boolean[] transitions;
        private Enactable<AbstractStopwatch> enactable;

        StopwatchState(boolean[] transitions, String name, Enactable<AbstractStopwatch> enactable) {
            this.name = name; this.transitions = transitions; this.enactable = enactable;
        }

        @Override
        public double transition(AbstractStopwatch timer) {
            return enactable.transition(timer);
        }
    }

    /**
     * protected fields for access only within extending classes.
     */
    protected TimeUnit timeUnit = Utils.StopWatches.TimeUnit.MILLISECONDS;
    protected double startTime = ZERO, endTime = ZERO, elapsedTime = 0;
    protected DecimalFormat formatter = new DecimalFormat(this.getTimeUnit().stringFormat);
    protected StopwatchState current = StopwatchState.STOPPED;

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
        current = StopwatchState.STARTED;
        return startTime;
    }

    public double suspend() {
        double hold = getTime();
        if(current.transitions[1]) {
            endTime = hold;
            elapsedTime += (endTime - startTime);
            startTime = ZERO;
            current = StopwatchState.SUSPENDED;
            return endTime;
        }
        endTime = ZERO;
        throw new IllegalStateTransitionException(current + " -> " + StopwatchState.SUSPENDED);
    }

    public double resume() {
        double hold = getTime();
        if(current.transitions[2]) {
            startTime = hold;
            endTime = ZERO;
            current = StopwatchState.RESUMED;
            return startTime;
        }
        throw new IllegalStateTransitionException(current + " -> " + StopwatchState.RESUMED);
    }

    public double stop () {
        double hold = getTime();
        if(isRunning()) {
            endTime = hold;
            elapsedTime += (endTime - startTime);
            startTime = ZERO;
        } else {
            startTime = 0;
            endTime = 0;
        }
        current = StopwatchState.STOPPED;
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
        return current == StopwatchState.STARTED ||
                current == StopwatchState.RESUMED;
    }

    public double getTimerValue() {
        return getTimerValue(getTimeUnit());
    }

    public String toString(TimeUnit u) {
        return formatter.format(getTimerValue(u)) + " " + u.name;
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
                if (i % 100000 == 0) {
                    println();
                    break;
                }
            }
        }
    }
}
