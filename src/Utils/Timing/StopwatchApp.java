package Utils.Timing;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import Utils.Console.Printing;
import Utils.Console.Special;
import static Utils.Console.Printing.*;
import static Utils.Console.Special.*;

public class StopwatchApp {

    private static String choice = "";
    private static int input = 0;
    private static String commands = "";
    private static String lastAction = "stopwatch application";
    private static Special color = FG_BRIGHT_RED;
    private static AbstractStopwatch stopwatch;
    private static AbstractStopwatch.StopwatchState next;
    private static TimeUnit unit = Utils.Timing.TimeUnit.SECONDS;
    private static final Scanner READER = new Scanner(System.in);
    private static final Map<AbstractStopwatch.StopwatchState, Special> STATE_COLOR_MAP = new HashMap<AbstractStopwatch.StopwatchState, Special>()
    {{
        put(AbstractStopwatch.StopwatchState.STARTED,    FG_BRIGHT_GREEN);
        put(AbstractStopwatch.StopwatchState.SUSPENDED,  FG_BRIGHT_YELLOW);
        put(AbstractStopwatch.StopwatchState.RESUMED,    FG_BRIGHT_CYAN);
        put(AbstractStopwatch.StopwatchState.STOPPED,    FG_BRIGHT_RED);
    }};

    private static boolean validate(int input) {
        return 0 < input && input < 6;
    }

    public static void main(String[] args) {
        int index = 0;
        for (TimeUnit u : TimeUnit.values()) {
            ++index;
            commands += "\t[" + index + "] " + u.name + "\n";
        }
        while(input > 6 || input < 1) {
            Printing.println("Please enter a number between 1 and", index);
            Printing.print(commands);
            Printing.print(">> ");
            try {
                choice = READER.next();
                input = Integer.parseInt(choice);
                unit = Utils.Timing.TimeUnit.values()[input-1]; // -1 for array index offset
            } catch(NumberFormatException ex) {
                println(FG_BRIGHT_BLUE, "Cannot Parse input: " + choice);
                continue;
            } catch (ArrayIndexOutOfBoundsException ex) {
                continue;
            }
        }
        println("Using time unit:", unit);

        stopwatch = new SYSStopwatch(unit);
        choice = "";
        input = 4;
        commands = "start[1] suspend[2] resume[3] stop[4] print[5] quit[~]";
        do {
            try {
                next = AbstractStopwatch.StopwatchState.values()[input-1];   // -1 for offset of commands to arr index
                next.transition(stopwatch);
                lastAction = next.name;
                color = STATE_COLOR_MAP.get(next);
            } catch (AbstractStopwatch.IllegalStateTransitionException ex) {
                lastAction = ex.toString();
                color = FG_BRIGHT_MAGENTA;
            } catch (Exception ex) {
                // pass on to printing for stopwatch
            }
            Printing.println(color, lastAction, ":", stopwatch);
            Printing.println(commands);
            Printing.print(">> ");
            try {
                choice = READER.next();
                input = Integer.parseInt(choice);
            } catch(NumberFormatException ex) {
                println(FG_BRIGHT_BLUE, "Cannot Parse input: " + choice);
            }
        } while(validate(input));
        Printing.print("quiting...");
        READER.close();
        System.exit(0);
    }
}
