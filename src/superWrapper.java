import myUtils.ConsolePrinting;
import myUtils.Measurement.SYSTimer;
import myUtils.Measurement.Timer;
import myUtils.Tuple;

import static Random.FermatNumbers.fermatNumber;
import static myUtils.ConsolePrinting.println;

abstract class superWrapper {

    interface VarArgs<T> {
        Tuple<T> varArgs(T... params);
    }

    abstract  <T> Tuple<T> exe(VarArgs<T> lam, T... args) ;

    abstract  <T> Tuple<T> runIt(T... params);

    VarArgs<Object> test = (Object...params) -> runIt(params);
}
