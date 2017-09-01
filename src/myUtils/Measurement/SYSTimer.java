package myUtils.Measurement;

public class SYSTimer extends AbstractTimer {

    private final double nativeFactor = 1000000000.0;

    public SYSTimer() {}
    public SYSTimer(TimeUnit u) {
        super(u);
    }

    @Override
    public double getTime() {
        return System.nanoTime();
    }

    @Override
    public double getElapsedTime(TimeUnit u) {
        switch(u) {
            case NANO:
                break;
            case MICRO:
                return elapsedTime / (nativeFactor / 1000000.0);
            case MILLI:
                return elapsedTime / (nativeFactor / 1000.0);
            case SECONDS:
                return elapsedTime / nativeFactor;
            case MINUTES:
                return elapsedTime / (60.0 * nativeFactor);
            case HOURS:
                return elapsedTime / (3600.0 * nativeFactor);
            default:
                break;
        }
        return elapsedTime;
    }
}
