package Utils;

import java.lang.reflect.Array;
import java.util.*;

import static Utils.ListUtils.contains;
import static Utils.ListUtils.distinct;

public class Equivalence {

    public interface Comparator<T extends Object> {
        boolean compare(T elem1, T elem2);
    }

    static public Comparator<Comparable> lt = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) < 0;
    static public Comparator<Comparable> gt = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) > 0;
    static public Comparator<Comparable> lte = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) <= 0;
    static public Comparator<Comparable> gte = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) >= 0;
    static public Comparator<Comparable> eq = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) == 0;

    public static <T extends Comparable<? super T>> boolean evaluate(Comparator comp, T first, T... rest) {
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

        /** alt way without recursion **/
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

    public static boolean eq(Object a, Object b) {
        if(!a.getClass().equals(b.getClass())) {
            return false;
        }
        if (a.getClass().isPrimitive()) {
            return a == b;
        }
        if (a.getClass().isArray()) {
            if(Array.getLength(a) != Array.getLength(b)) {
                return false;
            }
            for(int i = 0; i < Array.getLength(a); i++) {
                if(!eq(Array.get(a, i), Array.get(b, i))) {
                    return false;
                }
            }
            return true;
        }
        if(a.getClass().isInstance(Collection.class)) {
            return eq((Collection) a, (Collection) b);
        }
        return a.equals(b);
    }

    public static <E extends Comparable<? super E>> boolean eq(Collection<E> arr1, Collection<E> arr2) {
        for (E elem : arr1) {
            if (!contains(arr2, elem)) {
                return false;
            }
        }
        return true;
    }

    public static <E extends Comparable<? super E>> boolean eq(E[] arr1, E[] arr2) {
        for (E elem : arr1) {
            if (!contains(arr2, elem)) {
                return false;
            }
        }
        return true;
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

    public static <T extends Collection<E>, E extends Comparable> Collection<E> intersection(T first, T second) {
        List<E> ret = new ArrayList<>();
        for (E elem : first) {
            if(contains(second, elem)) {
                ret.add(elem);
            }
        }
        return ret;
    }

    public static <T extends Collection<E>, E extends Comparable> Collection<E> difference(T first, T second) {
        List<E> ret = new ArrayList<>();
        for (E elem : first) {
            if(!contains(second, elem)) {
                ret.add(elem);
            }
        }
        return ret;
    }


    public static <T extends Object> void main(String[] args) {
        Character[] carr1 = {'a','b','c','d'};
        Character[] carr2 = {'d','c','b','a'};
        ConsolePrinting.println(eq(carr1, carr2));

        String[] sarr1 = {"bob", "alex", "star", "cammi", "joey"};
        String[] sarr2 = {"joey", "cammi", "star", "alex", "bob"};
        ConsolePrinting.println(eq(sarr1, sarr2));

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
