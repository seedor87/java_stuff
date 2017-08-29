import myUtils.ConsolePrinting;
import myUtils.Measurement.SYSTimer;
import myUtils.Measurement.Timer;
import myUtils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.UnaryOperator;

import static Random.FermatNumbers.fermatNumber;
import static myUtils.ConsolePrinting.println;

public class RecursiveLambdaExample {

    static UnaryOperator<Integer> fac = i -> new RecursiveLambdaExample().f.apply(i);
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

    interface timeIt<T extends Object> {
        Tuple<T> exe(T... params);
    }

    static timeIt<Object> timer = (Object... params) -> {
        Timer timer = new SYSTimer();
        timer.start();
        List ret = new ArrayList<Integer>();
        for(int i = 0; i < 1000; i++) {
            ret.add(fermatNumber(i));
        }
        Tuple retur = new Tuple(ret);
        timer.stop();
        println("Runtime: " + timer);
        return retur;
    };


    public static void main(String[] args) {

        System.out.println(fac.apply(5));

        RecursiveLambdaExample reclambex = new RecursiveLambdaExample();
        System.out.println(reclambex.f.apply(5));

        System.out.println(test.varArgs(1,2,3,4,5,6,7,8,9,0));
        System.out.println(test.varArgs(new String[4]));
        System.out.println(test.varArgs("one", 2, 'c', false, new Tuple()));
        System.out.println(test.varArgs(new HashSet(), new ArrayList()));

        println(timer.exe());
    }
}