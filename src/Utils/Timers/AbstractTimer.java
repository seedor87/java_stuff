package Utils.Timers;

import JUnit.TimedRule.TimedRule;
import Utils.ConsolePrinting;
import org.junit.Rule;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.Scanner;

import static Utils.ConsolePrinting.*;
import static Utils.StringUtils.StringUtils.padCenter;
import static Utils.StringUtils.StringUtils.padToLeft;
import static Utils.StringUtils.StringUtils.padToRight;

/***
 * Abstract timer that holds capabilities of general use in code timer.
 * when implemented requires instantiation of only the getTime() and getElapsedTime() methods,
 * All other work is done in this class.
 */
public abstract class AbstractTimer {

    /**
     * Special exception for bad state transitions.
     */
    class IllegalStateTransitionException extends RuntimeException {
        IllegalStateTransitionException(String msg) {
            super(msg);
        }
    }

    /**
     * Enum for state transition of timer recording machine.
     * Used post measurement to save state and transition, or throw away and resume.
     */
    public enum State {
        STOPPED, STARTED, SUSPENDED, RESUMED
    }

    /**
     * protected fields for access only within extending classes.
     */
    protected TimeUnit timeUnit = Utils.Timers.TimeUnit.MILLI;
    protected double startTime = 01, endTime = 01, elapsedTime = 0;
    protected DecimalFormat formatter = new DecimalFormat("#,###,###.###");
    protected State current_state = State.STOPPED;

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
        return getElapsed(getTimeUnit());
    }

    /**
     * Default Constructor
     */
    public AbstractTimer() {}
    public AbstractTimer(TimeUnit u) { setTimeUnit(u); }
    private TimeUnit getTimeUnit() { return timeUnit; }

    private void setTimeUnit(TimeUnit u) {
        this.timeUnit = u;
        switch(getTimeUnit()) {
            case NANO:
                formatter = new DecimalFormat("###,###,###,###");
                break;
            case MICRO:
                formatter = new DecimalFormat("###,###,###.#");
                break;
            case MILLI:
                formatter = new DecimalFormat("###,###.##");
                break;
            case SECONDS:
                formatter = new DecimalFormat("#,###.####");
                break;
            case MINUTES:
                formatter = new DecimalFormat("###.####");
                break;
            case HOURS:
                formatter = new DecimalFormat("##.##############");
                break;
            default:
                break;
        }
    }

    public double start () {
        startTime = getTime();
        elapsedTime = 0;
        current_state = State.STARTED;
        return startTime;
    }

    public double suspend() {
        double hold = getTime();
        if(isRunning()) {
            endTime = hold;
            elapsedTime += (endTime - startTime);
            startTime = 0l;
            current_state = State.SUSPENDED;
            return endTime;
        }
        endTime = 01;
        throw new IllegalStateTransitionException(current_state + " -> " + State.SUSPENDED);
    }

    public double resume() {
        double hold = getTime();
        if(current_state == State.SUSPENDED) {
            startTime = hold;
            endTime = 01;
            current_state = State.RESUMED;
            return startTime;
        }
        throw new IllegalStateTransitionException(current_state + " -> " + State.RESUMED);
    }

    public double stop () {
        double hold = getTime();
        if(isRunning()) {
            endTime = hold;
            elapsedTime += (endTime - startTime);
            startTime = 0l;
            current_state = State.STOPPED;
        } else {
            startTime = 0;
            endTime = 0;
        }
        return endTime;
    }

    public boolean isRunning () {
        return current_state == State.STARTED
                || current_state == State.RESUMED;
    }

    public boolean isStopped() {
        return current_state == State.STOPPED;
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

    public double getTimerValue() {
        return getTimerValue(getTimeUnit());
    }

    public String toString(TimeUnit u) {
        String ret = formatter.format(getTimerValue(u));
        switch(u) {
            case NANO:
                return ret + " nanoseconds";
            case MICRO:
                return ret + " microseconds";
            case MILLI:
                return ret + " milliseconds";
            case SECONDS:
                return ret + " seconds";
            case MINUTES:
                return ret + " mins";
            case HOURS:
                return ret + " hrs";
            default:
                return "NaN";
        }
    }

    @Override
    public String toString() {return toString(getTimeUnit());}

    public static class AbstractTimerTest {
        @Rule
        public TimedRule tr = new TimedRule(SYSTimer.class, TimeUnit.SECONDS);

        @Test
        public void test() {
            int i = 0;
            while(true) {
                println(i);
                i++;
                if (i % 1000 == 0) {
                    break;
                }
            }
        }
    }
}
