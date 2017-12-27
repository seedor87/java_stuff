package Utils.Timers;

import Utils.ConsolePrinting;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import static Utils.ConsolePrinting.*;
import static Utils.StringUtils.StringUtils.padToLeft;

public class StopwatchApp {

    private static boolean validate(int input) {
        return 0 < input && input < 6;
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        String commands;
        TimeUnit unit = Utils.Timers.TimeUnit.SECONDS;
        AbstractStopwatch stopwatch;
        Map<AbstractStopwatch.State, Special> stateColorMap = new HashMap<AbstractStopwatch.State, Special>()
        {{
            put(AbstractStopwatch.State.STARTED,    FG_BRIGHT_GREEN);
            put(AbstractStopwatch.State.SUSPENDED,  FG_BRIGHT_YELLOW);
            put(AbstractStopwatch.State.RESUMED,    FG_BRIGHT_CYAN);
            put(AbstractStopwatch.State.STOPPED,    FG_BRIGHT_RED);
        }};

        String s = "";
        int input = 0;
        commands =  "\t" + padToLeft(20, ' ', "[1] Nanoseconds") + "[4] Seconds" + "\n" +
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
            unit = Utils.Timers.TimeUnit.values()[input-1]; // -1 for array index offset
        }
        println("Using time unit:", unit);
        stopwatch = new SYSStopwatch(unit);

        commands = "start[1] suspend[2] resume[3] stop[4] print[5] quit[~]";
        s = "";
        input = 4;
        String lastAction = "stopwatch application";
        ConsolePrinting.Special color = FG_BRIGHT_RED;
        AbstractStopwatch.State next;
        do {
            try {
                next = AbstractStopwatch.State.values()[input-1];   // -1 for offset of commands to arr index
                next.enact(stopwatch);
                lastAction = next.name;
                color = stateColorMap.get(next);
            } catch (AbstractStopwatch.IllegalStateTransitionException ex) {
                lastAction = ex.toString();
                color = FG_BRIGHT_MAGENTA;
            } catch (Exception ex) {
                // pass on to printing for stopwatch
            }
            ConsolePrinting.println(color, lastAction, ":", stopwatch);
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
