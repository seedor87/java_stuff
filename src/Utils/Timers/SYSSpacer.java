package Utils.Timers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import static Utils.ConsolePrinting.println;

public class SYSSpacer {

    private long memBefore, memAfter, memConsumed;
    DecimalFormat formatter = new DecimalFormat("###,###.####");
    private Runtime runtime;
    private MemUnit unit = MemUnit.KILO;

    public SYSSpacer() {
        runtime = Runtime.getRuntime();
    }

    public SYSSpacer(MemUnit u) {
        runtime = Runtime.getRuntime();
        setUnit(u);
    }

    public long start() {
        System.gc();
        memBefore = runtime.totalMemory() - runtime.freeMemory();
        return memBefore;
    }

    public long stop() {
        memAfter = runtime.totalMemory() - runtime.freeMemory();
        memConsumed = memAfter - memBefore;
        System.gc();
        return memAfter;
    }

    public double getMemConsumed(MemUnit u) {
        return memConsumed / u.factor;
    }

    public double getMemConsumed() {
        return getMemConsumed(this.getUnit());
    }

    @Override
    public String toString() {
        return toString(this.getUnit());
    }

    public String toString(MemUnit u) {
        setUnit(u);
        return formatter.format(getMemConsumed()) + " " + this.getUnit().name;
    }

    public void setUnit(MemUnit unit) {
        this.unit = unit;
        formatter = new DecimalFormat(getUnit().stringFormat);
    }

    public MemUnit getUnit() {
        return unit;
    }
    public static void main(String args[]) {
        SYSSpacer spacer = new SYSSpacer(MemUnit.GIGA);
        spacer.start();
        int i = 0;
        ArrayList<Integer> arr = new ArrayList<>();
        while(true) {
            arr.add(i);
            i++;
            if (i % 2000000 == 0) {
                break;
            }
        }
        spacer.stop();
        println(spacer.toString(MemUnit.BYTE));
    }
}
