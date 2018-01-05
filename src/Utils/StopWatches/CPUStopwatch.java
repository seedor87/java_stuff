package Utils.StopWatches;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CPUStopwatch extends AbstractStopwatch {

    public CPUStopwatch() {}
    public CPUStopwatch(TimeUnit u) {
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
                return this.elapsedTime;
            case MICRO:
                return this.elapsedTime / 1000.0;
            case MILLI:
                return this.elapsedTime / 1000000.0;
            case SECONDS:
                return this.elapsedTime / 1000000000.0;
            case MINUTES:
                return this.elapsedTime / (60.0 * 1000000000.0);
            case HOURS:
                return this.elapsedTime / (3600.0 * 1000000000.0);
            default:
                break;
        }
        return this.elapsedTime;
    }
}
