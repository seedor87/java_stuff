package myUtils.Measurement;

import java.text.DecimalFormat;

public class Spacer {

    public enum MemUnit {
        BYTE, KILO, MEGA, GIGA, TERA
    }

    private long memBefore, memAfter, memConsumed;
    DecimalFormat formatter = new DecimalFormat("###,###.####");
    private Runtime runtime;
    private MemUnit unit = MemUnit.KILO;

    public Spacer() {
        runtime = Runtime.getRuntime();
    }

    public Spacer(MemUnit u) {
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
        switch(u) {
            case BYTE:
                return memConsumed;
            case KILO:
                return memConsumed / Math.pow(1024, 1);
            case MEGA:
                return memConsumed / Math.pow(1024, 2);
            case GIGA:
                return memConsumed / Math.pow(1024, 3);
            case TERA:
                return memConsumed / Math.pow(1024, 4);
            default:
                break;
        }
        return memConsumed;
    }

    public double getMemConsumed() {
        return getMemConsumed(getUnit());
    }

    public String getMemConsumedString() {
        String ret = formatter.format(getMemConsumed());
        switch(unit) {
            case BYTE:
                ret += " Bytes";
                break;
            case KILO:
                ret += " KB";
                break;
            case MEGA:
                ret += " MB";
                break;
            case GIGA:
                ret += " GB";
                break;
            case TERA:
                ret += " TB";
                break;
            default:
                break;
        }
        return ret;
    }

    public void setUnit(Spacer.MemUnit u) {
        switch(unit) {
            case BYTE:
                formatter = new DecimalFormat("##,###,###,###");
                break;
            case KILO:
                formatter = new DecimalFormat("##,###,###.####");
                break;
            case MEGA:
                formatter = new DecimalFormat("##,###.########");
                break;
            case GIGA:
                formatter = new DecimalFormat("###.############");
                break;
            case TERA:
                formatter = new DecimalFormat("##.################");
                break;
            default:
                break;
        }
    }

    public MemUnit getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return getMemConsumedString();
    }
}
