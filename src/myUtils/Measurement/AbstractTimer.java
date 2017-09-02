package myUtils.Measurement;

import myUtils.ConsolePrinting;

import java.text.DecimalFormat;
import java.util.Scanner;

public abstract class AbstractTimer {

    class IllegalStateTransitionExcpetion extends RuntimeException {

        IllegalStateTransitionExcpetion(String msg) {
            super(msg);
        }
    }

    public enum TimeUnit {
        NANO, MICRO, MILLI, SECONDS, MINUTES, HOURS
    }

    public enum State {
        STOPPED, STARTED, SUSPENDED, RESUMMED
    }

    private TimeUnit unit = TimeUnit.MILLI;
    double startTime = 01, endTime = 01, elapsedTime = 0;
    DecimalFormat formatter = new DecimalFormat("#,###,###.###");
    private State current = State.STOPPED;

    public abstract double getTime();
    public abstract double getElapsedTime(TimeUnit u);

    public AbstractTimer() {}
    public AbstractTimer(TimeUnit u) { setUnit(u); }
    private TimeUnit getUnit() { return unit; }

    private void setUnit(TimeUnit u) {
        this.unit = u;
        switch(unit) {
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
        current = State.STARTED;
        return startTime;
    }

    public double suspend() {
        double hold = getTime();
        if(current == State.STARTED || current == State.RESUMMED) {
            endTime = hold;
            elapsedTime += (endTime - startTime);
            startTime = 0l;
            current = State.SUSPENDED;
            return endTime;
        }
        endTime = 01;
        throw new IllegalStateTransitionExcpetion(current + " -> " + State.SUSPENDED);
    }

    public double resume() {
        double hold = getTime();
        if(current == State.SUSPENDED) {
            startTime = hold;
            endTime = 01;
            current = State.RESUMMED;
            return startTime;
        }
        throw new IllegalStateTransitionExcpetion(current + " -> " + State.RESUMMED);
    }

    public double stop () {
        double hold = getTime();
        if(current == State.RESUMMED || current == State.STARTED) {
            endTime = hold;
            elapsedTime += (endTime - startTime);
            startTime = 0l;
            current = State.STOPPED;
        } else {
            elapsedTime = 0;
            startTime = 0;
            endTime = 0;
        }
        return endTime;
    }

    public boolean isRunning () {
        return startTime != 0l;
    }

    public double getElapsedTime() {
        return getElapsedTime(getUnit());
    }

    public String getTimeString() {
        String ret = formatter.format(getElapsedTime());
        switch(unit) {
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
        String key = "start[1] suspend[2] resume[3] stop[4]";
        int input = 4;
        AbstractTimer timer = new SYSTimer(TimeUnit.SECONDS);
        String lastAction = "timer application";
        ConsolePrinting.println(ConsolePrinting.COLOR.RED, timer + " : " + lastAction);
        do {
            ConsolePrinting.println(key);
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
            } catch (IllegalStateTransitionExcpetion ex) {
                ConsolePrinting.println(ConsolePrinting.COLOR.PURPLE, ex.toString());
            }
        } while(0 < input && input < 5);
        ConsolePrinting.print("quiting...");
        reader.close();
    }
}
