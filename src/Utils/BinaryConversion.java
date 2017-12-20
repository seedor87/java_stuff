package Utils;

import Utils.Timers.SYSTimer;
import Utils.Timers.TimeUnit;

import static Utils.ConsolePrinting.*;
import static Utils.StringUtils.StringUtils.padCenter;
import static Utils.StringUtils.StringUtils.padJustify;
import static Utils.StringUtils.StringUtils.padToRight;

public class BinaryConversion {

    static long MAX_BINARY = 65536;

    public static long toBinary(long n) {
        return (n <= 0) ? 0 : (n % 2 ) + (10 * toBinary(n/2));
    }

    public static long toDecimal(String b) {
        return toDecimal(Integer.parseInt(b), 0);
    }

    public static long toDecimal(long b) {
        return toDecimal(b, 0);
    }

    public static long toDecimal(long b, long p) {
        return (b <= 0) ? 0 : ((b%10) * (long) Math.pow(2,p)) + toDecimal(b/10, p+1);
    }

    public static long addBinaries(long x, long y) {
        return toBinary(toDecimal(x) + toDecimal(y));
    }

//    public static long incrBinary(long x) {
//        return addBinaries(x, 1);
//    }

    public static long incrBinary(long x) {
        return incrBinary(x, 0);
    }

    public static long incrBinary(long x, long p) {
        if (x % 2 == 0) {
            return (long) ((x * Math.pow(10, p)) + Math.pow(10, p));
        }
        return incrBinary(x/10, p+1);
    }

    public static void main(String args[]) {
        SYSTimer timer = new SYSTimer(TimeUnit.MILLI);
        timer.start();
        for (long i = 0; i < MAX_BINARY; i++) {
            long x = toBinary(i);
            long y = toDecimal(x);
            println(padCenter(60, ' ',
                    padJustify(30, ' ',
                            padToRight(10, x), "=", y),
                    padJustify(30, ' ', "+=1 :", incrBinary(x))
                    )
            );
        }
        timer.stop();
        println(timer);
    }
}
