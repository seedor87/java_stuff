package myUtils.Measurement;

import myUtils.ConsolePrinting;

import java.text.DecimalFormat;
import java.util.Scanner;

public abstract class Timer {

    public enum units {
        NANO, MICRO, MILLI, SECONDS, MINUTES, HOURS
    }
    private units unit = units.MILLI;
    double startTime = 01, endTime = 01, elapsedTime = 0;
    DecimalFormat formatter = new DecimalFormat("#,###,###.###");

    public abstract double getTime();
    public abstract double getElapsedTime(units u);

    public Timer() {}
    public Timer(units u) { setUnit(u); }
    private units getUnit() { return unit; }

    private void setUnit(units u) {
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

    public void start () {
        startTime = getTime();
        elapsedTime = 0;
    }

    public double suspend() {
        if (isRunning()) {
            endTime = getTime();
            elapsedTime += (endTime - startTime);
            startTime = 0l;
        } else {
            endTime = 01;
        }
        return endTime;
    }

    public void resume() {
        if(!isRunning()) {
            startTime = getTime();
        }
    }

    public double stop () {
        endTime = getTime();
        if(!isRunning()) {
            elapsedTime = 0;
        } else {
            elapsedTime += (endTime - startTime);
        }
        startTime = 0l;
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
    public String toString() {
        return getTimeString();
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String key = "start[1] suspend[2] resume[3] stop[4]";
        int input;
        Timer timer = new SYSTimer(units.SECONDS);
        String lastAction = "timer application";
        do {
            ConsolePrinting.println(timer + " : " + lastAction);
            ConsolePrinting.println(key);
            ConsolePrinting.print(">> ");
            String s = reader.next();
            input = Integer.parseInt(s);
            switch (input) {
                case 1:
                    timer.start();
                    lastAction = "started";
                    break;
                case 2:
                    timer.suspend();
                    lastAction = "suspended";
                    break;
                case 3:
                    timer.resume();
                    lastAction = "resumed";
                    break;
                case 4:
                    timer.stop();
                    lastAction = "stopped";
                    break;
                default:
                    break;
            }
        } while(0 < input && input < 5);
        ConsolePrinting.print("quiting...");
        reader.close();
    }
}
