package Utils;

import Utils.Console.Printing;

import java.util.*;

public class Comparison {

    static public Comparator strComp = Comparator.comparing(s -> ((String) s));
    static public Comparator strLen = (Object s1, Object s2) -> (Integer.compare(((String) s1).length(), ((String) s2).length()));
    static public Comparator ascInt = (Object i1, Object i2) -> (Integer.compare((Integer) i1, (Integer) i2));
    static public Comparator descInt = (Object i1, Object i2) -> (Integer.compare((Integer) i2, (Integer) i1));
    static public Comparator LessThan = (Object o1, Object o2) -> ((Comparable) o1).compareTo((Comparable) o2) < 0 ? 1 : 0;
    static public Comparator GreaterThan = (Object o1, Object o2) -> ((Comparable) o1).compareTo((Comparable) o2) > 0 ? 1 : 0;
    static public Comparator LessThanEqualTo = (Object o1, Object o2) -> ((Comparable) o1).compareTo((Comparable) o2) <= 0 ? 1 : 0;
    static public Comparator GreaterThanEqualTo = (Object o1, Object o2) -> ((Comparable) o1).compareTo((Comparable) o2) >= 0 ? 1 : 0;
    static public Comparator EqualTo = (Object o1, Object o2) -> ((Comparable) o1).compareTo((Comparable) o2) == 0 ? 1 : 0;

    public interface BooleanComparable<T> {
        boolean compare(T elem1, T elem2);
    }
    
    static public BooleanComparable<Comparable> lt = (Comparable elem1, Comparable elem2) -> LessThan.compare(elem1, elem2) > 0;
    static public BooleanComparable<Comparable> gt = (Comparable elem1, Comparable elem2) -> GreaterThan.compare(elem1, elem2) > 0;
    static public BooleanComparable<Comparable> lte = (Comparable elem1, Comparable elem2) -> LessThanEqualTo.compare(elem1, elem2) > 0;
    static public BooleanComparable<Comparable> gte = (Comparable elem1, Comparable elem2) -> GreaterThanEqualTo.compare(elem1, elem2) > 0;
    static public BooleanComparable<Comparable> eq = (Comparable elem1, Comparable elem2) -> EqualTo.compare(elem1, elem2) > 0;

    public static <T extends Comparable<? super T>> boolean evaluate(BooleanComparable comp, T first, T... rest) {
        return (rest.length < 1) ? true :
                (rest.length < 2) ? comp.compare(first, rest[0]) :
                        comp.compare(first, rest[0]) ? evaluate(comp, rest[0], Arrays.copyOfRange(rest, 1, rest.length)) :
                                false;
//        if (rest.length < 1) {
//            return true;
//        } else if (rest.length < 2) {
//            return comp.compare(first, rest[0]);
//        } else {
//            if (comp.compare(first, rest[0])) {
//                return evaluate(comp, rest[0], Arrays.copyOfRange(rest, 1, rest.length));
//            }
//        }
//        return false;

        /******** alt way, without recursion ********/
//        T temp = rest[0];
//        for (T elem : Arrays.copyOfRange(rest, 1, rest.length)) {
//            if(!lt(temp, elem)) {
//                return false;
//            }
//            temp = elem;
//        }
//        return true;
    }

    public static <T extends Comparable<? super T>> boolean lt(T elem1, T elem2) {
        return lt.compare(elem1, elem2);
    }
    public static <T extends Comparable<? super T>> boolean lte(T elem1, T elem2) {
        return lte.compare(elem1, elem2);
    }
    public static <T extends Comparable<? super T>> boolean gt(T elem1, T elem2) {
        return gt.compare(elem1, elem2);
    }
    public static <T extends Comparable<? super T>> boolean gte(T elem1, T elem2) {
        return gte.compare(elem1, elem2);
    }
    public static <T extends Comparable<? super T>> boolean eq(T elem1, T elem2) {
        return eq.compare(elem1, elem2);
    }
    public static <T extends Comparable<? super T>> boolean neq(T elem1, T elem2) {
        return elem1.compareTo(elem2) != 0;
    }

    public static boolean cnfAnd(boolean first, boolean... rest) {
        return (rest.length < 1) ? first :
                (first) ? cnfAnd(rest[0], Arrays.copyOfRange(rest, 1, rest.length)) :
                        false;
//        if (rest.length < 1) {
//            return first;
//        }
//        if(first) {
//            return cnfAnd(rest[0], Arrays.copyOfRange(rest, 1, rest.length));
//        }
//        return false;
    }

    public static boolean cnfOr(boolean first, boolean... rest) {
        return (rest.length < 1) ? first :
                (first) ? true :
                        cnfOr(rest[0], Arrays.copyOfRange(rest, 1, rest.length));
//        if(rest.length < 1) {
//            return first;
//        }
//        if(first) {
//            return true;
//        }
//        return cnfOr(rest[0], Arrays.copyOfRange(rest, 1, rest.length));
    }

    public static <T extends Object> void main(String[] args) {

//        Printing.println(equal("bob", "bob"));

        Printing.println("a < b < c < d :", evaluate(lt, 'a', 'b', 'c', 'd'));
        Printing.println("a > b > c > d :", evaluate(gt, 'd', 'c', 'b', 'a'));
        Printing.println("a <= b <= c <= d :", evaluate(lte, 'a','a', 'a', 'a'));

        Printing.println();
        Printing.println("a >= b :", gte('a','b'));
        Printing.println("a != b :", neq('a','b'));

        Printing.println();
        Printing.println("T & T & F :", cnfAnd(true, true, false));
        Printing.println("F | F | T :", cnfOr(false, false, true));

        Printing.println();
        Printing.println("a < b :", lt.compare('a','b'));
        Printing.println("a < a :", lt.compare('a','a'));
        Printing.println("b < a :", lt.compare('b', 'a'));

        Printing.println();
        Printing.println("a <= b :", lte.compare('a','b'));
        Printing.println("a <= a :", lte.compare('a','a'));
        Printing.println("b <= a :", lte.compare('b', 'a'));

        Printing.println();
        Printing.println("a > b :", gt.compare('a','b'));
        Printing.println("a > a :", gt.compare('a','a'));
        Printing.println("b > a :", gt.compare('b', 'a'));

        Printing.println();
        Printing.println("a >= b :", gte.compare('a','b'));
        Printing.println("a >= a :", gte.compare('a','a'));
        Printing.println("b >= a :", gte.compare('b', 'a'));

        Printing.println();
        Printing.println("a == b :", eq.compare('a','b'));
        Printing.println("a == a :", eq.compare('a','a'));
        Printing.println("b == a :", eq.compare('b', 'a'));
    }
}
