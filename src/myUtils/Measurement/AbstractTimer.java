package myUtils.Measurement;

import myUtils.ConsolePrinting;

import java.text.DecimalFormat;
import java.util.Scanner;

public abstract class AbstractTimer {

    class IllegalStateTransitionException extends RuntimeException {

        IllegalStateTransitionException(String msg) {
            super(msg);
        }
    }

    public enum TimeUnit {
        NANO, MICRO, MILLI, SECONDS, MINUTES, HOURS
    }

    public enum State {
        STOPPED, STARTED, SUSPENDED, RESUMMED
    }

    private TimeUnit timeUnit = TimeUnit.MILLI;
    double startTime = 01, endTime = 01, elapsedTime = 0;
    DecimalFormat formatter = new DecimalFormat("#,###,###.###");
    private State current_state = State.STOPPED;

    public abstract double getTime();
    public abstract double getElapsedTime(TimeUnit u);

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
            current_state = State.RESUMMED;
            return startTime;
        }
        throw new IllegalStateTransitionException(current_state + " -> " + State.RESUMMED);
    }

    public double stop () {
        double hold = getTime();
        if(isRunning()) {
            endTime = hold;
            elapsedTime += (endTime - startTime);
            startTime = 0l;
            current_state = State.STOPPED;
        } else {
            elapsedTime = 0;
            startTime = 0;
            endTime = 0;
        }
        return endTime;
    }

    public boolean isRunning () {
        return current_state == State.STARTED
                || current_state == State.RESUMMED;
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
                return ret + " mircroseconds";
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

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String commands = "start[1] suspend[2] resume[3] stop[4] quit[~]";
        int input;
        AbstractTimer timer = new SYSTimer(TimeUnit.SECONDS);
        String lastAction = "timer application";
        ConsolePrinting.println(ConsolePrinting.COLOR.RED, timer + " : " + lastAction);
        do {
            ConsolePrinting.println(commands);
            ConsolePrinting.print(">> ");
            String s = reader.next();
            input = Integer.parseInt(s);
            try {
                switch (input) {
                    case 1:
                        timer.start();
                        lastAction = "started";
                        ConsolePrinting.println(ConsolePrinting.COLOR.GREEN, timer + " : " + lastAction);
                        break;
                    case 2:
                        timer.suspend();
                        lastAction = "suspended";
                        ConsolePrinting.println(ConsolePrinting.COLOR.YELLOW, timer + " : " + lastAction);
                        break;
                    case 3:
                        timer.resume();
                        lastAction = "resumed";
                        ConsolePrinting.println(ConsolePrinting.COLOR.CYAN, timer + " : " + lastAction);

                        break;
                    case 4:
                        timer.stop();
                        lastAction = "stopped";
                        ConsolePrinting.println(ConsolePrinting.COLOR.RED, timer + " : " + lastAction);
                        break;
                    default:
                        break;
                }
            } catch (IllegalStateTransitionException ex) {
                ConsolePrinting.println(ConsolePrinting.COLOR.PURPLE, ex.toString());
            }
        } while(0 < input && input < 5);
        ConsolePrinting.print("quiting...");
        reader.close();
        System.exit(0);
    }
}
