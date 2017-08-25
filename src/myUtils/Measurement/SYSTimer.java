package myUtils.Measurement;

import myUtils.Measurement.Timer;

public class SYSTimer extends Timer {

    private final double nativeFactor = 1000000000.0;

    public SYSTimer() {}
    public SYSTimer(units u) {
        super(u);
    }

    @Override
    public double getTime() {
        return System.nanoTime();
    }

    @Override
    public double getElapsedTime(units u) {
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
