package ATOC;

import static ATOC.Arithmetic.*;
import static ATOC.Basic.*;
import static Utils.ConsolePrinting.println;

public class Booleans {

    public static boolean prime(Variable x) {
        Variable count = new Variable(2);
        Variable bound = new Variable(sqrt(x));
        while(lte(count, bound)) {
            if(eq(mod(x, count), n)) {
                return false;
            }
            count.incr();
        }
        return true;
    }

    public static boolean smith(Variable x) {
        return eq(sumDigits(x), sumPrimeFactors(x));
    }

    public static boolean perfectPow(Variable x) {
        Variable count = new Variable(2);
        Variable t;
        while (lte(mult(count, count), x)) {
            t = new Variable(x);
            while (eq(mod(t, count), n)) {
                t = div(t, count);
            }
            if (eq(t, new Variable(1))) {
                return true;
            }
            count.incr();
        }
        return false;
    }

    public static boolean mersenne(Variable x) {
        Variable count = new Variable(0);
        if (prime(count)) {
            while (lte(pow(new Variable(2), count), add(x, new Variable(1)))) {
                if (eq(monus(pow(new Variable(2), count), new Variable(1)), n)) {
                    return true;
                }
                count.incr();
            }
        }
        return false;
    }

    public static boolean coprime(Variable x1, Variable x2) {
        if(eq(gcd(x1, x2), new Variable(1))) {
            return true;
        }
        return false;
    }

    public static Variable isPrime(Variable x) {
        return isPrime(x, new Variable(2));
    }

    public static Variable isPrime(Variable x, Variable t) {
        if(neq(t, x)) {
            return mult(alpha(alpha(mod(x, t))), isPrime(x, add(t, new Variable(1))));
        } return new Variable(1);
    }

    public static Variable E(Variable x) {
        return E(x, new Variable(x));
    }

    public static Variable E(Variable x, Variable t) {
        if(eq(t, n)) {
            return x;
        }
        return pow(x, E(x, t.decr()));
    }

    public static boolean and(Variable x1, Variable x2) {
        if(x1.value > 0 && x2.value > 0) {
            return true;
        }
        return false;
    }

    public static boolean or(Variable x1, Variable x2) {
        if(x1.value > 0 || x2.value > 0) {
            return true;
        }
        return false;
    }

    public static boolean lte(Variable x1, Variable x2) {
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
            return true;
        }
        return false;
    }

    public static boolean gt(Variable x1, Variable x2) {
        return !lte(x1, x2);
    }

    public static boolean lt (Variable x1, Variable x2) {
        return gt(x2, x1);
    }

    public static boolean gte(Variable x1, Variable x2) {
        return lte(x2, x1);
    }

    public static boolean eq(Variable x1, Variable x2) {
        return !gt(x1, x2) && !lt(x1, x2);
    }

    public static boolean neq(Variable x1, Variable x2) {
        return !eq(x1, x2);
    }

    public static void main(String[] args) {
        println(alpha(mod(new Variable(3), new Variable(3))));
        println(isPrime(new Variable(2)));
        println(isPrime(new Variable(4)));
        println(isPrime(new Variable(5)));
        println(isPrime(new Variable(6)));
        println(isPrime(new Variable(9)));
    }

}
