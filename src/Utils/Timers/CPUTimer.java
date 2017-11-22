package Utils.Timers;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CPUTimer extends AbstractTimer {

    public CPUTimer() {}
    public CPUTimer(TimeUnit u) {
        super(u);
    }

    @Override
    public double getTime () {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? (double) bean.getCurrentThreadCpuTime(): 0D;
    }

    @Override
    public double getElapsed(TimeUnit u) {
        switch(u) {
            case NANO:
                return elapsedTime;
            case MICRO:
                return elapsedTime / 1000.0;
            case MILLI:
                return elapsedTime / 1000000.0;
            case SECONDS:
                return elapsedTime / 1000000000.0;
            case MINUTES:
                return elapsedTime / (60.0 * 1000000000.0);
            case HOURS:
                return elapsedTime / (3600.0 * 1000000000.0);
            default:
                break;
        }
        return elapsedTime;
    }
}
