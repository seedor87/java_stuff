package ATOC;

import static Utils.ConsolePrinting.println;

public class Functions {

    public static Variable alpha(Variable x) {
        if(x.value != 0) {
            return new Variable(0);
        }
        return new Variable(1);
    }

    public static Variable monus(Variable x1, Variable x2) {
        if (lte(x2, x1).value != 0) {
            return new Variable(x1.value - x2.value);
        }
        return new Variable(0);
    }

    public static Variable and(Variable x1, Variable x2) {
        if(x1.value > 0 && x2.value > 0) {
            return new Variable(1);
        }
        return new Variable(0);
    }

    public static Variable or(Variable x1, Variable x2) {
        if(x1.value > 0 || x2.value > 0) {
            return new Variable(1);
        }
        return new Variable(0);
    }

    public static Variable lte(Variable x1, Variable x2) {
        Variable u = new Variable(x2);
        Variable v = new Variable(x1);
        Variable y = new Variable();
        while (true) {
            if(u.value <= 0) {
                break;
            }
            v.decr();
            u.decr();
        }
        if(v.value <= 0) {
            y.incr();
        }

        if (y.value != 0) {
            return new Variable(1);
        }
        return new Variable(0);
    }

    public static Variable gt(Variable x1, Variable x2) {
        return alpha(lte(x1, x2));
    }

    public static Variable lt (Variable x1, Variable x2) {
        return gt(x2, x1);
    }

    public static Variable gte(Variable x1, Variable x2) {
        return lte(x2, x1);
    }

    // left off on page 42
    public static void main(String[] args) {
        Variable x1 = new Variable(6);
        Variable x2 = new Variable(5);
        println(x1, "<=" , x2, "=", lte(x1, x2));
        println(x2, "<=" , x1, "=", lte(x2, x1));
        println(x1, "<=" , x2, "=", alpha(monus(x1, x2)));
        println(x2, "<=" , x1, "=", alpha(monus(x2, x1)));
        println(x1, ">=" , x2, "=", gte(x1, x2));
        println(x2, ">=" , x1, "=", gte(x2, x1));
        println(x1, " >" , x2, "=", gt(x1, x2));
        println(x2, " >" , x1, "=", gt(x2, x1));
        println(x1, " <" , x2, "=", lt(x1, x2));
        println(x2, " <" , x1, "=", lt(x2, x1));
    }
}
