package Utils.Timers;

import Utils.ConsolePrinting;

import java.util.Scanner;

import static Utils.ConsolePrinting.*;
import static Utils.StringUtils.StringUtils.padToLeft;

public class StopWatchApp {

    private static boolean validate(int input) {
        return 0 < input && input < 5;
    }

    private static AbstractTimer.State getState(int input) {
        switch (input) {
            case 1:
                return AbstractTimer.State.STARTED;
            case 2:
                return AbstractTimer.State.SUSPENDED;
            case 3:
                return AbstractTimer.State.RESUMED;
            case 4:
                return AbstractTimer.State.STOPPED;
            default:
                break;
        }
        return AbstractTimer.State.STOPPED;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String commands;
        TimeUnit unit = Utils.Timers.TimeUnit.SECONDS;
        AbstractTimer timer;
        String s = "";
        int input = 0;

        commands = "\t" + padToLeft(20, ' ', "[1] Nanoseconds") + "[4] Seconds" + "\n" +
                "\t" + padToLeft(20, ' ', "[2] Microseconds") + "[5] Minutes" + "\n" +
                "\t" + padToLeft(20, ' ', "[3] Milliseconds") + "[6] hours";
        while(input > 6 || input < 1) {
            ConsolePrinting.println("Please enter a number between 1 and 6");
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
                    unit = Utils.Timers.TimeUnit.NANO;
                    break;
                case 2:
                    unit = Utils.Timers.TimeUnit.MICRO;
                    break;
                case 3:
                    unit = Utils.Timers.TimeUnit.MILLI;
                    break;
                case 4:
                    unit = Utils.Timers.TimeUnit.SECONDS;
                    break;
                case 5:
                    unit = Utils.Timers.TimeUnit.MINUTES;
                    break;
                case 6:
                    unit = Utils.Timers.TimeUnit.HOURS;
                    break;
            }
        }
        println("Using time unit:", unit);
        timer = new SYSTimer(unit);

        commands = "start[1] suspend[2] resume[3] stop[4] quit[~]";
        s = "";
        input = 4;
        String lastAction = "timer application";
        ConsolePrinting.Special color = FG_BRIGHT_RED;
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
            } catch (AbstractTimer.IllegalStateTransitionException ex) {
                lastAction = ex.toString();
                color = FG_BRIGHT_MAGENTA;
            }
            ConsolePrinting.println(color, lastAction, ":", timer);
            ConsolePrinting.println(commands);
            ConsolePrinting.println(">> ");
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
