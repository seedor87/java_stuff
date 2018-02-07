package Utils.Timing;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CPUStopwatch extends AbstractStopwatch {

    public CPUStopwatch() { super(); }
    public CPUStopwatch(TimeUnit u) {
        super(u);
    }

    @Override
    public long getTime () {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        return bean.isCurrentThreadCpuTimeSupported() ? bean.getCurrentThreadCpuTime(): 0L;
    }

    @Override
    public double getElapsed(TimeUnit u) {
        switch(u) {
            case NANOSECONDS:
                return this.elapsedTime;
            case MICROSECONDS:
                return this.elapsedTime / 1000.0;
            case MILLISECONDS:
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
