package ATOC;

import static Utils.ConsolePrinting.println;

public class Functions {

    public static Variable n = new Variable(0);

    public static Variable alpha(Variable x) {
        if(x.value != 0) {
            return n(x);
        }
        return new Variable(1);
    }

    public static Variable n(Variable x) {
        return n;
    }

    public static Variable sum(Variable x1, Variable x2) {
        Variable y = new Variable(x1);
        Variable temp = new Variable(x2);
        while(neq(temp, n).value > 0) {
            y.incr();
            temp.decr();
        }
        return y;
    }

    public static Variable mult(Variable x1, Variable x2) {
        Variable y = new Variable(n);
        Variable temp = new Variable(x2);
        while(neq(temp, n).value > 0) {
            y = sum(y, x1);
            temp.decr();
        }
        return y;
    }

    public static Variable pow(Variable x1, Variable x2) {
        Variable y = new Variable(1);
        Variable temp = new Variable(x2);
        while(neq(temp, n).value > 0) {
            y = mult(y, x1);
            temp.decr();
        }
        return y;
    }

    public static Variable sqrt(Variable x) {
        Variable temp = new Variable(1);
        Variable count = new Variable(1);
        Variable y = new Variable(0);
        while (lte(temp, x).value > 0) {
            y = new Variable(count);
            count.incr();
            temp = mult(count, count);
        }
        return y;
    }

    public static Variable monus(Variable x1, Variable x2) {
        return (x2.value <= x1.value) ? new Variable(x1.value - x2.value) : n(x1);
    }

    public static Variable div(Variable x1, Variable x2) {
        Variable y = new Variable(0);
        Variable temp = new Variable(x1);
        while(gte(temp, x2).value > 0) {
            temp = monus(temp, x2);
            y.incr();
        }
        return y;
    }

    public static Variable mod(Variable x1, Variable x2) {
        Variable temp = new Variable(x1);
        while(gte(temp, x2).value > 0) {
            temp = monus(temp, x2);
        }
        return temp;
    }

    public static Variable prime(Variable x) {
        Variable count = new Variable(2);
        Variable bound = new Variable(sqrt(x));
        while(lte(count, bound).value > 0) {
            if(eq(mod(x, count), n).value > 0) {
                return new Variable(0);
            }
            count.incr();
        }
        return new Variable(1);
    }

    public static Variable perfect(Variable x) {
        Variable y = new Variable();
        Variable sum = new Variable(1);
        Variable count = new Variable(2);
        while(lt(mult(count, count), x).value > 0) {
            if(mod(x, count).value == 0) {
                sum = sum(sum, count);
                sum = sum(sum, div(x, count));
            }
            count.incr();
        }
        y = eq(sum, x);
        return y;
    }

    public static Variable and(Variable x1, Variable x2) {
        if(x1.value > 0 && x2.value > 0) {
            return alpha(n(x1));
        }
        return n(x1);
    }

    public static Variable or(Variable x1, Variable x2) {
        if(x1.value > 0 || x2.value > 0) {
            return alpha(n(x1));
        }
        return n(x1);
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
            return alpha(n(x1));
        }
        return n(x1);
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

    public static Variable eq(Variable x1, Variable x2) {
        return and(alpha(gt(x1, x2)), alpha(lt(x1, x2)));
    }

    public static Variable neq(Variable x1, Variable x2) {
        return alpha(eq(x1, x2));
    }

    public static Variable gcd(Variable x1, Variable x2) {
        if(or(lte(x1, n), lte(x2, n)).value > n.value) {
            return n;
        }
        while(neq(x1, x2).value > 0) {
            while(gt(x1, x2).value > 0) {
                x1 = monus(x1, x2);
            }
            while(gt(x2, x1).value > 0) {
                x2 = monus(x2, x1);
            }
        }
        return x1;
    }

    // left off on page 42
    public static void main(String[] args) {
        Variable x1 = new Variable(4);
        Variable x2 = new Variable(5);
        Variable x3 = new Variable(6);
        Variable x4 = new Variable(8);

        println(x1, "+", x2, "=", sum(x1, x2));
        println(x1, "+", n, "=", sum(x1, n));
        println(x2, "*", x1, "=", mult(x1, x2));
        println(n, "*", x3, "=", mult(n, x3));
        println(x2, "-", x1, "=", monus(x1, x2));
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
                        sum(
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
    }
}
