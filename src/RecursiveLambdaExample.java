import myUtils.ConsolePrinting;
import myUtils.Measurement.SYSTimer;
import myUtils.Measurement.Timer;
import myUtils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.UnaryOperator;

import static Random.FermatNumbers.fermatNumber;

public class RecursiveLambdaExample<T extends Object> {

    static UnaryOperator<Integer> fac = i -> (int) new RecursiveLambdaExample().f.apply(i);
    UnaryOperator<Integer> f = i -> i == 0 ? 1 : i * this.f.apply(i - 1);

    interface VarArgs<T extends Object> {
        int varArgs(T... params);
    }

    static VarArgs<Object> test = (Object... params) -> {
        if (params.length < 1) {
            return 0;
        }
        return RecursiveLambdaExample.test.varArgs(Arrays.copyOfRange(params, 1, params.length)) + 1;
    };

    VarArgs<T>  test2 = (T...params) -> {
        for (int i = 0; i < (Integer) params[0]; i++) {
            ConsolePrinting.println(i + ": " + fermatNumber(i));
        }
        return 0;
    };

    public void exe(VarArgs<T> lam, T... args) {
        lam.varArgs(args);
    }

    public static void main(String[] args) {

        System.out.println(fac.apply(5));

        RecursiveLambdaExample reclambex = new RecursiveLambdaExample();
        System.out.println(reclambex.f.apply(5));

        System.out.println(test.varArgs(1,2,3,4,5,6,7,8,9,0));
        System.out.println(test.varArgs(new String[4]));
        System.out.println(test.varArgs("one", 2, 'c', false, new Tuple()));
        System.out.println(test.varArgs(new HashSet(), new ArrayList()));


        reclambex.exe(reclambex.test2, 10);
    }
}