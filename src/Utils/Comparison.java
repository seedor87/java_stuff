package Utils;

import java.util.*;
public class Comparison {

    public interface BinaryComparator<T extends Object> extends Comparator {
        int compare(Object elem1, Object elem2);
    }

    static public BinaryComparator<Comparable> lt = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) < 0;
    static public BinaryComparator<Comparable> gt = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) > 0;
    static public BinaryComparator<Comparable> lte = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) <= 0;
    static public BinaryComparator<Comparable> gte = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) >= 0;
    static public BinaryComparator<Comparable> eq = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) == 0;

    public static <T extends Comparable<? super T>> boolean evaluate(BinaryComparator comp, T first, T... rest) {
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

    public static <T extends Comparable<? super T>> int lt(T elem1, T elem2) {
        return lt.compare(elem1, elem2);
    }

    public static <T extends Comparable<? super T>> int lte(T elem1, T elem2) {
        return lte.compare(elem1, elem2);
    }

    public static <T extends Comparable<? super T>> int gt(T elem1, T elem2) {
        return gt.compare(elem1, elem2);
    }

    public static <T extends Comparable<? super T>> int gte(T elem1, T elem2) {
        return gte.compare(elem1, elem2);
    }

    public static <T extends Comparable<? super T>> int eq(T elem1, T elem2) {
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
