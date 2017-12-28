package ATOC;

import static ATOC.Basic.*;
import static ATOC.Booleans.*;
import static Utils.Console.Printing.println;
import static Utils.StringUtils.padJustify;

public class Binary {

    static Variable two = new Variable(2);
    static Variable ten = new Variable(10);

    public static Variable toBinary(Variable x) {
        if (eq(x, n)) {
            return new Variable(0);
        }
        return add(mod(x, two), mult(ten, toBinary(div(x, two))));
        // (x % 2 ) + (10 * f(x/2))
    }

    public static Variable toDecimal(Variable x) {
        return toDecimal(x, new Variable(0));
    }
    public static Variable toDecimal(Variable x, Variable power) {
        if(eq(x, n)) {
            return new Variable(0);
        }
        return add(mult(mod(x, ten), pow(two, power)), toDecimal(div(x, ten), add(power, new Variable(1))));
        // ((x % 10) * 2 ^ p) + f(x/10, p+1)
    }

    public static Variable toBinary2(Variable x) {
        Variable i = new Variable(x);
        Variable y = new Variable(0);
        Variable count = new Variable(0);
        Variable remainder;
        while(gt(i, n)) {
            remainder = mod(i, two);
            i = div(i, two);
            if(gt(remainder, n)) {
                y = add(y, pow(ten, count));
            }
            count.incr();
        }
        return y;
    }

    public static Variable toDecimal2(Variable x) {
        Variable y = new Variable(0);
        Variable power = new Variable(0);
        Variable count = new Variable(x);
        while (neq(count, n)) {
            Variable temp = mod(count, ten);
            y = add(y, mult(temp, pow(two, power)));
            count = div(count, ten);
            power.incr();
        }
        return y;
    }

    public static void main(String[] args) {
        for(int i = 100; i > 0; i--) {
            Variable bin = toBinary(new Variable(i));
            println(padJustify(20, ' ', bin, toDecimal(bin)));
        }
    }
}
