package Utils.StopWatches;

public class SYSStopwatch extends AbstractStopwatch {

    private final double nativeFactor = 1000000000.0;

    public SYSStopwatch() {super();}
    public SYSStopwatch(TimeUnit u) {
        super(u);
    }

    @Override
    public double getTime() {
        return System.nanoTime();
    }

    @Override
    public double getElapsed(TimeUnit u) {
        switch(u) {
            case NANO:
                break;
            case MICRO:
                return this.elapsedTime / (nativeFactor / 1000000.0);
            case MILLI:
                return this.elapsedTime / (nativeFactor / 1000.0);
            case SECONDS:
                return this.elapsedTime / nativeFactor;
            case MINUTES:
                return this.elapsedTime / (60.0 * nativeFactor);
            case HOURS:
                return this.elapsedTime / (3600.0 * nativeFactor);
            default:
                break;
        }
        return this.elapsedTime;
    }
}
