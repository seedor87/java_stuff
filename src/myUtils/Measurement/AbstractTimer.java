package myUtils.Measurement;

import myUtils.ConsolePrinting;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Scanner;

public abstract class AbstractTimer {

    public enum TimeUnit {
        NANO, MICRO, MILLI, SECONDS, MINUTES, HOURS
    }
    private TimeUnit unit = TimeUnit.MILLI;
    double startTime = 01, endTime = 01, elapsedTime = 0;
    DecimalFormat formatter = new DecimalFormat("#,###,###.###");

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
        return startTime;
    }

    public double suspend() {
        endTime = getTime();
        if (!isRunning() ) {
            endTime = 01;
        } else {
            elapsedTime += (endTime - startTime);
            startTime = 0l;
        }
        return endTime;
    }

    public double resume() {
        double temp = getTime();
        if(!isRunning() && endTime != 01) {
            startTime = temp;
        }
        return temp;
    }

    public double stop () {
        endTime = getTime();
        if(!isRunning()) {
            elapsedTime = 0;
        } else {
            elapsedTime += (endTime - startTime);
            startTime = 0l;
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
        ConsolePrinting.COLOR printCol = ConsolePrinting.COLOR.RED;
        int input;
        AbstractTimer timer = new SYSTimer(TimeUnit.SECONDS);
        String lastAction = "timer application";
        do {
            ConsolePrinting.println(printCol, timer + " : " + lastAction);
            ConsolePrinting.println(key);
            ConsolePrinting.print(">> ");
            String s = reader.next();
            input = Integer.parseInt(s);
            switch (input) {
                case 1:
                    timer.start();
                    lastAction = "started";
                    printCol = ConsolePrinting.COLOR.GREEN;
                    break;
                case 2:
                    timer.suspend();
                    lastAction = "suspended";
                    printCol = ConsolePrinting.COLOR.YELLOW;
                    break;
                case 3:
                    timer.resume();
                    lastAction = "resumed";
                    printCol = ConsolePrinting.COLOR.CYAN;
                    break;
                case 4:
                    timer.stop();
                    lastAction = "stopped";
                    printCol = ConsolePrinting.COLOR.RED;
                    break;
                default:
                    break;
            }
        } while(0 < input && input < 5);
        ConsolePrinting.print("quiting...");
        reader.close();
    }
}
