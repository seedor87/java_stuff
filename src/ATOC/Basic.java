package ATOC;

import static ATOC.Booleans.*;
import static ATOC.Booleans.gte;
import static Utils.ConsolePrinting.*;

public class Basic {

    public static Variable n = new Variable(0);

    public static Variable alpha(Variable x) {
        if(x.value != 0) {
            return new Variable(0);
        }
        return new Variable(1);
    }

    public static Variable max(Variable x1, Variable x2) {
        if(gt(x1, x2)) {
            return x1;
        }
        return x2;
    }

    public static Variable nil(Variable x) {
        return n;
    }

    public static Variable add(Variable x1, Variable x2) {
        Variable y = new Variable(x1);
        Variable t = new Variable(x2);
        while(neq(t, n)) {
            y.incr();
            t.decr();
        }
        return y;
    }

    public static Variable mult(Variable x1, Variable x2) {
        Variable y = new Variable(n);
        Variable t = new Variable(x2);
        while(neq(t, n)) {
            y = add(y, x1);
            t.decr();
        }
        return y;
    }

    public static Variable pow(Variable x1, Variable x2) {
        Variable y = new Variable(1);
        Variable t = new Variable(x2);
        while(neq(t, n)) {
            y = mult(y, x1);
            t.decr();
        }
        return y;
    }

    public static Variable sqrt(Variable x) {
        Variable t = new Variable(1);
        Variable count = new Variable(1);
        Variable y = new Variable(0);
        while (lte(t, x)) {
            y = new Variable(count);
            count.incr();
            t = mult(count, count);
        }
        return y;
    }

    public static Variable monus(Variable x1, Variable x2) {
        Variable y = new Variable(x1);
        Variable t = new Variable(x2);
        while(neq(t, n)) {
            y.decr();
            t.decr();
        }
        return y;
    }

    public static Variable div(Variable x1, Variable x2) {
        Variable y = new Variable(0);
        Variable t = new Variable(x1);
        if(neq(x2, n)) {
            while (gte(t, x2)) {
                t = monus(t, x2);
                y.incr();
            }
        }
        return y;
    }

    public static Variable mod(Variable x1, Variable x2) {
        return monus(x1, mult(div(x1, x2), x2));
//        Variable y = new Variable(x1);
//        while(gte(y, x2)) {
//            y = monus(y, x2);
//        }
//        return y;
    }
}
