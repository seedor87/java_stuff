package Utils.Timing;

import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import org.junit.Rule;
import org.junit.Test;

import java.text.DecimalFormat;

import static Utils.Console.Printing.*;
import static Utils.Timing.AbstractStopwatch.StopwatchState.RESUMED;
import static Utils.Timing.AbstractStopwatch.StopwatchState.SUSPENDED;

/***
 * Abstract timer that holds capabilities of general use in code timer.
 * when implemented requires instantiation of only the getTime() and getElapsedTime() methods,
 * All other work is done in this class.
 */
public abstract class AbstractStopwatch {

    private final long ZERO = 0L;   // final ZERO long value

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
    protected long startTime = ZERO, endTime = ZERO, elapsedTime = ZERO;
    protected TimeUnit timeUnit = TimeUnit.NANOSECONDS;
    protected DecimalFormat formatter = new DecimalFormat(this.getTimeUnit().stringFormat);
    protected StopwatchState currentState = StopwatchState.STOPPED;

    /**
     * abstract methods to be overridden.
     * @return double - str present time per method of measurement, and currentState value of elapsed time, respectively.
     */
    public abstract long getTime();

    /**
     * abstract methods to be overridden.
     * @return double - str present time per method of measurement, and currentState value of elapsed time, respectively.
     */
    public abstract double getElapsed(TimeUnit u);
    public double getElapsed() {
        return this.getElapsed(this.getTimeUnit());
    }

    /**
     * Default Constructor
     */
    public AbstractStopwatch() {}
    public AbstractStopwatch(TimeUnit u) { this.setTimeUnit(u); }
    private TimeUnit getTimeUnit() { return this.timeUnit; }

    private void setTimeUnit(TimeUnit u) {
        this.timeUnit = u;
        this.formatter = new DecimalFormat(this.getTimeUnit().stringFormat);
    }

    public double start () {
        this.startTime = this.getTime();
        this.elapsedTime = 0;
        this.currentState = StopwatchState.STARTED;
        return this.startTime;
    }

    public double suspend() {
        long hold = this.getTime();
        if(this.currentState.transitions[StopwatchState.SUSPENDED.ordinal()]) {
            this.endTime = hold;
            this.elapsedTime += (this.endTime - this.startTime);
            this.startTime = this.ZERO;
            this.currentState = StopwatchState.SUSPENDED;
            return endTime;
        }
        this.endTime = this.ZERO;
        throw new IllegalStateTransitionException(this.currentState + " -> " + StopwatchState.SUSPENDED);
    }

    public double resume() {
        long hold = this.getTime();
        if(this.currentState.transitions[StopwatchState.RESUMED.ordinal()]) {
            this.startTime = hold;
            this.endTime = ZERO;
            this.currentState = StopwatchState.RESUMED;
            return startTime;
        }
        throw new IllegalStateTransitionException(this.currentState + " -> " + StopwatchState.RESUMED);
    }

    public double stop () {
        long hold = this.getTime();
        if(this.isRunning()) {
            this.endTime = hold;
            this.elapsedTime += (this.endTime - this.startTime);
            this.startTime = this.ZERO;
        } else {
            this.startTime = 0;
            this.endTime = 0;
        }
        this.currentState = StopwatchState.STOPPED;
        return endTime;
    }

    public double getTimerValue(TimeUnit u) {
        long hold = this.getTime();
        if(isRunning()) {
            this.endTime = hold;
            this.elapsedTime += (this.endTime - this.startTime);
            this.startTime = hold;
        }
        return getElapsed(u);
    }

    public boolean isRunning() {
        return this.currentState == StopwatchState.STARTED ||
                this.currentState == StopwatchState.RESUMED;
    }

    public double getTimerValue() {
        return getTimerValue(this.getTimeUnit());
    }

    public String toString(TimeUnit u) {
        return this.formatter.format(this.getTimerValue(u)) + " " + u.name;
    }

    @Override
    public String toString() { return toString(this.getTimeUnit()); }

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
