package Utils.Timing;

import static Utils.Console.Printing.*;

interface TimeGetter {
    long getTime();
    default double getElapsed(long elapsedTime, TimeUnit u) { return (double) elapsedTime; }
}

interface ElapsedGetter extends TimeGetter {
    @Override
    double getElapsed(long elapsedTime, TimeUnit u);
}

public class CustomStopwatch extends AbstractStopwatch {

    private TimeGetter getter;

    public static final class DefaultGetter implements ElapsedGetter {

        @Override
        public long getTime() {
            return System.nanoTime();
        }

        @Override
        public double getElapsed(long elapsedTime, TimeUnit u) {
            switch(u) {
                case MICROSECONDS:
                    return elapsedTime / (1000000000.0 / 1000000.0);
                case MILLISECONDS:
                    return elapsedTime / (1000000000.0 / 1000.0);
                case SECONDS:
                    return elapsedTime / 1000000000.0;
                case MINUTES:
                    return elapsedTime / (60.0 * 1000000000.0);
                case HOURS:
                    return elapsedTime / (3600.0 * 1000000000.0);
                default:
                    return elapsedTime;
            }
        }
    }

    public CustomStopwatch(TimeGetter getter) {
        this.getter = getter;
    }

    public CustomStopwatch() {
        this.getter = new DefaultGetter();
    }

    @Override
    public long getTime() {
        return this.getter.getTime();
    }

    @Override
    public double getElapsed(TimeUnit u) {
        return this.getter.getElapsed(this.elapsedTime, u);
    }

    public static void main(String[] args) {
        AbstractStopwatch myCustomStopwatch;
        int i;

        i = 0;
        while(true) {
            print(i++);
            if(i % 20 == 0) {
                break;
            }
        }
        println();


        myCustomStopwatch = new CustomStopwatch(System::nanoTime);
        myCustomStopwatch.start();
        i = 0;
        while(true) {
            print(i++);
            if(i % 20 == 0) {
                break;
            }
        }
        myCustomStopwatch.stop();
        println("\n", myCustomStopwatch);


        myCustomStopwatch = new CustomStopwatch(new ElapsedGetter() {
            @Override
            public long getTime() {
                return System.nanoTime();
            }

            @Override
            public double getElapsed(long elapsedTime, TimeUnit u) {
                switch(u) {
                    case MICROSECONDS:
                        return elapsedTime / (1000000000.0 / 1000000.0);
                    case MILLISECONDS:
                        return elapsedTime / (1000000000.0 / 1000.0);
                    case SECONDS:
                        return elapsedTime / 1000000000.0;
                    case MINUTES:
                        return elapsedTime / (60.0 * 1000000000.0);
                    case HOURS:
                        return elapsedTime / (3600.0 * 1000000000.0);
                    default:
                        return elapsedTime;
                }
            }
        });
        myCustomStopwatch.start();
        i = 0;
        while(true) {
            print(i++);
            if(i % 20 == 0) {
                break;
            }
        }
        myCustomStopwatch.stop();
        println("\n", myCustomStopwatch);


        myCustomStopwatch = new CustomStopwatch();
        myCustomStopwatch.start();
        i = 0;
        while(true) {
            print(i++);
            if(i % 20 == 0) {
                break;
            }
        }
        myCustomStopwatch.stop();
        println("\n", myCustomStopwatch);
    }
}
