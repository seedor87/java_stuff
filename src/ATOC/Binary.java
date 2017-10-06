package ATOC;

import static ATOC.Basic.*;
import static ATOC.Booleans.*;
import static Utils.ConsolePrinting.println;
import static Utils.StringUtils.StringUtils.padJustify;

public class Binary {

//    public static Variable toBinary(Variable x) {
//        return toBinary(x, new Variable(0));
//    }
//    public static Variable toBinary(Variable x, Variable count) {
//        Variable y = new Variable(x);
//        Variable two = new Variable(2);
//        Variable ten = new Variable(10);
//        Variable remainder;
//        if (gt(y, n)) {
//            remainder = mod(y, two);
//            y = div(y, two);
//            if (gt(remainder, n)) {
//                y = add(y, pow(ten, count));
//            }
//            count.incr();
//            y = add(pow(ten, count), toBinary(y, count));
//        }
//        return y;
//    }

    public static Variable toDecimal(Variable x) {
        return toDecimal(x, new Variable(0));
    }
    public static Variable toDecimal(Variable x, Variable power) {
        Variable y = new Variable(0);
        Variable two = new Variable(2);
        Variable ten = new Variable(10);
        Variable count = new Variable(x);
        if(neq(count, n)) {
            Variable temp = mod(count, ten);
            y = add(y, mult(temp, pow(two, power)));
            count = div(count, ten);
            power.incr();
            y = add(y, toDecimal(count, power));
        }
        return y;
    }

    public static Variable toBinary(Variable x) {
        Variable i = new Variable(x);
        Variable y = new Variable(0);
        Variable two = new Variable(2);
        Variable ten = new Variable(10);
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

//    public static Variable toDecimal(Variable x) {
//        Variable y = new Variable(0);
//        Variable power = new Variable(0);
//        Variable two = new Variable(2);
//        Variable ten = new Variable(10);
//        Variable count = new Variable(x);
//        while (neq(count, n)) {
//            Variable temp = mod(count, ten);
//            y = add(y, mult(temp, pow(two, power)));
//            count = div(count, ten);
//            power.incr();
//        }
//        return y;
//    }

    public static void main(String[] args) {
        for(int i = 1; i < 100; i++) {
            Variable bin = toBinary(new Variable(i));
            println(padJustify(20, ' ', bin, toDecimal(bin)));
        }
    }
}
