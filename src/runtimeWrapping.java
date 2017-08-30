import myUtils.ConsolePrinting;
import myUtils.Measurement.SYSTimer;
import myUtils.Measurement.Timer;
import myUtils.Tuple;

import static Random.FermatNumbers.fermatNumber;
import static myUtils.ConsolePrinting.println;

public class runtimeWrapping<T extends Object> extends superWrapper {

    @Override
    <T> Tuple<T> exe(VarArgs<T> lam, T... args) {
        Timer timer = new SYSTimer();
        timer.start();
        Tuple ret = lam.varArgs(args);
        timer.stop();
        println("Runtime: " + timer);
        return ret;
    }

    @Override
    <T> Tuple<T> runIt(T... params) {
        for (int i = 0; i < (Integer) params[0]; i++) {
            int fn = fermatNumber(i);
            ConsolePrinting.println(i + ": " + fermatNumber(i));
        }
        return new Tuple(1,2,3);
    };

    public static void main(String[] args) {
        runtimeWrapping<Integer> rtw = new runtimeWrapping<Integer>();
        println(rtw.exe(rtw.test, 10));
    }
}
