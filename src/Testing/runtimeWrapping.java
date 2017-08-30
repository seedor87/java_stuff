package Testing;

import myUtils.ConsolePrinting;
import myUtils.Tuple;

import static Random.FermatNumbers.fermatNumber;
import static myUtils.ConsolePrinting.println;

public class runtimeWrapping<T extends Object> extends superWrapper {

    @Override
    public Tuple runIt(Object... params) {
        for (int i = 0; i < (Integer) params[0]; i++) {
            int fn = fermatNumber(i);
            ConsolePrinting.println(i + ": " + fermatNumber(i));
        }
        return new Tuple(1,2,3);
    }

    public static void main(String[] args) {
        runtimeWrapping<Integer> rtw = new runtimeWrapping<Integer>();
        println(rtw.test(10));
    }
}
