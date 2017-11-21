package Utils.Timers;

import Utils.ConsolePrinting;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

import static Utils.ConsolePrinting.*;
import static Utils.StringUtils.StringUtils.padCenter;

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
     * Enum for usable units of time that can be registered, the abstract methods be aware of this
     */
    public enum TimeUnit {
        NANO, MICRO, MILLI, SECONDS, MINUTES, HOURS
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
    protected TimeUnit timeUnit = TimeUnit.MILLI;
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
    public abstract double getElapsedTime(TimeUnit u);

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
                formatter = new DecimalFormat("###.########");
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

    public double getElapsedTime() {
        return getElapsedTime(getTimeUnit());
    }

    public String getTimeString() {
        String ret = formatter.format(getElapsedTime());
        switch(timeUnit) {
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
    public String toString() {
        return getTimeString();
    }

    private static boolean validate(int input) {
        return 0 < input && input < 5;
    }

    private static State getState(int input) {
        switch (input) {
            case 1:
                return State.STARTED;
            case 2:
                return State.SUSPENDED;
            case 3:
                return State.RESUMED;
            case 4:
                return State.STOPPED;
            default:
                break;
        }
        return State.STOPPED;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String commands;
        TimeUnit unit = TimeUnit.SECONDS;
        AbstractTimer timer;
        String s = "";
        int input = 3;

        commands = padCenter(20, ' ', "[1] Nanoseconds") + padCenter(20, ' ', "[4] Seconds") + "\n" +
                padCenter(20, ' ', "[2] Microseconds") + padCenter(20, ' ', "[5] Minutes") + "\n" +
                padCenter(20, ' ', "[3] Milliseconds") + padCenter(20, ' ', "[6] hours");
        do {
            ConsolePrinting.println("Please enter a number between 1 and 5");
            ConsolePrinting.println(commands);
            ConsolePrinting.print(">> ");
            try {
                s = reader.next();
                input = Integer.parseInt(s);
            } catch(NumberFormatException ex) {
                println(FG_BRIGHT_BLUE, "Cannot Parse input: " + s);
                continue;
            }
            switch(input) {
                case 1:
                    unit = TimeUnit.NANO;
                    break;
                case 2:
                    unit = TimeUnit.MICRO;
                    break;
                case 3:
                    unit = TimeUnit.MILLI;
                    break;
                case 4:
                    unit = TimeUnit.SECONDS;
                    break;
                case 5:
                    unit = TimeUnit.MINUTES;
                    break;
                case 6:
                    unit = TimeUnit.HOURS;
                    break;
                default:
                    unit = TimeUnit.SECONDS;
                    break;
            }
        } while(input > 6 || input < 1);
        timer = new SYSTimer(unit);

        commands = "start[1] suspend[2] resume[3] stop[4] quit[~]";
        s = "";
        input = 4;
        String lastAction = "timer application";
        Special color = FG_BRIGHT_RED;
        do {
            try {
                switch (getState(input)) {
                    case STARTED:
                        timer.start();
                        lastAction = "started";
                        color = FG_BRIGHT_GREEN;
                        break;
                    case SUSPENDED:
                        timer.suspend();
                        lastAction = "suspended";
                        color = FG_BRIGHT_YELLOW;
                        break;
                    case RESUMED:
                        timer.resume();
                        lastAction = "resumed";
                        color = FG_BRIGHT_CYAN;
                        break;
                    case STOPPED:
                        timer.stop();
                        lastAction = "stopped";
                        color = FG_BRIGHT_RED;
                        break;
                    default:
                        break;
                }
            } catch (IllegalStateTransitionException ex) {
                lastAction = ex.toString();
                color = FG_BRIGHT_MAGENTA;
            }
            ConsolePrinting.println(color, lastAction, ":", timer);
            ConsolePrinting.println(commands);
            ConsolePrinting.print(">> ");
            try {
                s = reader.next();
                input = Integer.parseInt(s);
            } catch(NumberFormatException ex) {
                println(FG_BRIGHT_BLUE, "Cannot Parse input: " + s);
            }
        } while(validate(input));
        ConsolePrinting.print("quiting...");
        reader.close();
        System.exit(0);
    }
}
