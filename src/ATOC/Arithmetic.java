package ATOC;

import static ATOC.Basic.*;
import static ATOC.Booleans.*;
import static Utils.Console.Printing.println;

public class Arithmetic {

    public static Variable fermat(Variable x) {
        return add(pow(new Variable(2), pow(new Variable(2), x)), new Variable(1));
    }

    public static Variable sumPrimeFactors(Variable x) {
        Variable count = new Variable(2);
        Variable y = new Variable(0);
        Variable t = new Variable(x);
        while(lte(count, t)) {
            while(eq(mod(t, count), n)) {
                y = add(y, count);
                t = div(t, count);
            }
            count.incr();
        }
        return y;
    }

    public static Variable numPrimeFactors(Variable x) {
        Variable count = new Variable(2);
        Variable y = new Variable(0);
        Variable t = new Variable(x);
        while(lte(count, t)) {
            while(eq(mod(t, count), n)) {
                y = add(y, count);
                t = div(t, count);
            }
            count.incr();
        }
        return count;
    }

    public static Variable sumDigits(Variable x) {
        Variable y = new Variable(0);
        Variable t = new Variable(x);
        while (gt(t, n)) {
            y = add(y, mod(t, new Variable(10)));
            t = div(t, new Variable(10));
        }
        return y;
    }

    public static Variable perfect(Variable x) {
        Variable sum = new Variable(1);
        Variable count = new Variable(2);
        while(lt(mult(count, count), x)) {
            if(eq(mod(x, count), n)) {
                sum = add(sum, count);
                sum = add(sum, div(x, count));
            }
            count.incr();
        }
        return new Variable(eq(sum, x));
    }

    public static Variable fac(Variable x) {
        if(neq(x, n)) {
            return mult(x, fac(monus(x, new Variable(1))));
        }
        return new Variable(1);
    }

    public static Variable sum(Variable x) {
        if(neq(x, n)) {
            return add(x, sum(monus(x, new Variable(1))));
        }
        return new Variable(0);
    }

    public static Variable gcd(Variable x1, Variable x2) {
        if(lte(x1, n)) {
            return n;
        }
        if(lte(x2, n)) {
            return n;
        }
        while(neq(x1, x2)) {
            while(gt(x1, x2)) {
                x1 = monus(x1, x2);
            }
            while(gt(x2, x1)) {
                x2 = monus(x2, x1);
            }
        }
        return x1;
    }

    public static Variable lcm(Variable x1, Variable x2) {
        return div(mult(x1, x2), gcd(x1, x2));
    }

    public static boolean perfectSq(Variable x) {
        Variable T = sqrt(x);
        return eq(mult(T, T), x);
    }

    public static Variable SMSQ(Variable x) {
        Variable sum;
        Variable first = new Variable(n);
        Variable second;
        while(neq(first, x)) {
            second = new Variable(n);
            while(neq(second, x)) {
                sum = add(first, second);
                if(perfectSq(first)) {
                    if (perfectSq(second)) {
                        if (eq(sum, x)) {
                            return new Variable(1);
                        }
                    }
                }
                second.incr();
            }
            first.incr();
        }
        return new Variable(0);
    }

    public static void main(String[] args) {
        Variable x1 = new Variable(4);
        Variable x2 = new Variable(5);
        Variable x3 = new Variable(6);
        Variable x4 = new Variable(8);

        println(x1, "+", x2, "=", add(x1, x2));
        println(x1, "+", n, "=", add(x1, n));
        println(x2, "*", x1, "=", mult(x1, x2));
        println(n, "*", x3, "=", mult(n, x3));
        println(x2, "-", x1, "=", monus(x2, x1));
        println(n, "-", x3, "=", monus(x1, x3));
        println(x3, "/", x1, "=", div(x3, x1));
        println(x3, "%", x1, "=", mod(x3, x1));
        println(x1, "/", x3, "=", div(x1, x3));
        println(x1, "%", x3, "=", mod(x1, x3));
        println(x4, "/", x1, "=", div(x4, x1));
        println(x4, "%", x1, "=", mod(x4, x1));
        println(x1, "^", x2, "=", pow(x1, x2));
        println(x2, "^", x1, "=", pow(x2, x1));
        println(n, "^", n, "=", pow(n, n));

        println(x1, "^1/2 =", sqrt(x1));
        println(x4, "^1/2 =", sqrt(x4));

        println(x1, "! =", fac(x1));
        println(x4, "! =", fac(x4));

        println(x1, "<=", x2, "=", lte(x1, x2));
        println(x2, "<=", x1, "=", lte(x2, x1));
        println(x1, "<=", x2, "=", alpha(monus(x1, x2)));
        println(x2, "<=", x1, "=", alpha(monus(x2, x1)));
        println(x1, ">=", x2, "=", gte(x1, x2));
        println(x2, ">=", x1, "=", gte(x2, x1));
        println(x1, ">", x2, "=", gt(x1, x2));
        println(x2, ">", x1, "=", gt(x2, x1));
        println(x1, "<", x2, "=", lt(x1, x2));
        println(x2, "<", x1, "=", lt(x2, x1));

        println(x1, "==", x2, "=", eq(x1, x2));
        println(x1, "==", x1, "=", eq(x1, x1));
        println(x1, "!=", x2, "=", neq(x1, x2));

        println("gcd(", x1, ",", x3, ") =", gcd(x1, x3));
        println("gcd(", x1, ",", x2, ") =", gcd(x1, x2));
        println("prime(", x1, ") =", prime(x1));
        println("prime(", x2, ") =", prime(x2));
        println("prime(", x3, ") =", prime(x3));
        println("prime((1*2*3*5) + 1) =",
                prime(
                        add(
                                mult(
                                        mult(
                                                mult(
                                                        new Variable(1),
                                                        new Variable(2)
                                                ),
                                                new Variable(3)
                                        ),
                                        new Variable(5)
                                ),
                                new Variable(1)
                        )
                )
        );
        println("perfect(", x1, ") =", perfect(x1));
        println("perfect(", x3, ") =", perfect(x3));
        println("perfect(", new Variable(28), ") =", perfect(new Variable(28)));
        println("perfect(", new Variable(496), ") =", perfect(new Variable(496)));

        println("sum(", x3, ") =", sum(x3));
        println("sum(", x4, ") =", sum(x4));

        println("sumPrimeFactors(", x4, ") =", sumPrimeFactors(x4));
        println("sumPrimeFactors(", x1, ") =", sumPrimeFactors(x1));
        println("sumDigits(", new Variable(1234), ") =", sumDigits(new Variable(1234)));
        println("sumDigits(", x1, ") =", sumDigits(x1));

        println("smith(", x1, ") =", smith(x1));
        println("smith(", x2, ") =", smith(x2));
        println("smith(", x3, ") =", smith(x3));

        println("perfectPow(", x1, ") =", perfectPow(x1));
        println("perfectPow(", x2, ") =", perfectPow(x2));
        println("perfectPow(", x3, ") =", perfectPow(x3));

        println("numPrimeFactors(", x4, ") =", numPrimeFactors(x4));
        println("numPrimeFactors(", x3, ") =", numPrimeFactors(x3));

        println("fermat(", x1, ") =", fermat(x1));

        println("SMSQ(", x2, ") =", SMSQ(x2));
        println("SMSQ(", x3, ") =", SMSQ(x3));

        println("coprime(", x1, ",", x2, ") =", coprime(x1, x2));
        println("coprime(", x1, ",", x3, ") =", coprime(x1, x3));

        println("lcm(", x1, ",", x2, ") =", lcm(x1, x2));
        println("lcm(", x1, ",", x4, ") =", lcm(x1, x4));
    }
}
