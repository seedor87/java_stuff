package Utils;

import java.util.*;
public class Comparison {

    public interface BinaryComparator {
        boolean compare(Object elem1, Object elem2);
    }

    static public BinaryComparator lt = (Object elem1, Object elem2) -> ((Comparable) elem1).compareTo((Comparable) elem2) < 0;
    static public BinaryComparator gt = (Object elem1, Object elem2) -> ((Comparable) elem1).compareTo((Comparable) elem2) > 0;
    static public BinaryComparator lte = (Object elem1, Object elem2) -> ((Comparable) elem1).compareTo((Comparable) elem2) <= 0;
    static public BinaryComparator gte = (Object elem1, Object elem2) -> ((Comparable) elem1).compareTo((Comparable) elem2) >= 0;
    static public BinaryComparator eq = (Object elem1, Object elem2) -> ((Comparable) elem1).compareTo((Comparable) elem2) == 0;

    public static <T extends Comparable<? super T>> boolean evaluate(Comparator comp, T first, T... rest) {
        if (rest.length < 1) {
            return true;
        } else if (rest.length < 2) {
            return comp.compare(first, rest[0]) > 0;
        } else {
            if (comp.compare(first, rest[0]) > 0) {
                return evaluate(comp, rest[0], Arrays.copyOfRange(rest, 1, rest.length));
            }
        }
        return false;
    }

    public static <T extends Comparable<? super T>> boolean evaluate(BinaryComparator comp, T first, T... rest) {
        if (rest.length < 1) {
            return true;
        } else if (rest.length < 2) {
            return comp.compare(first, rest[0]);
        } else {
            if (comp.compare(first, rest[0])) {
                return evaluate(comp, rest[0], Arrays.copyOfRange(rest, 1, rest.length));
            }
        }
        return false;

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
        if (rest.length < 1) {
            return first;
        }
        if(first) {
            return cnfAnd(rest[0], Arrays.copyOfRange(rest, 1, rest.length));
        }
        return false;
    }

    public static boolean cnfOr(boolean first, boolean... rest) {
        if(rest.length < 1) {
            return first;
        }
        if(first) {
            return true;
        }
        return cnfOr(rest[0], Arrays.copyOfRange(rest, 1, rest.length));
    }

    public static <T extends Object> void main(String[] args) {

//        ConsolePrinting.println(equal("bob", "bob"));

        ConsolePrinting.println(evaluate(lt, 'a', 'b', 'c', 'd'));
        ConsolePrinting.println(evaluate(gt, 'd', 'c', 'b', 'a'));
        ConsolePrinting.println(evaluate(lte, 'a','a', 'a', 'a'));
        ConsolePrinting.println(gte('a','b'));
        ConsolePrinting.println(neq('a','b'));

        ConsolePrinting.println(cnfAnd(true, true, false));
        ConsolePrinting.println(cnfOr(false, false, true));

    }
}
