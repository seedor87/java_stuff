package myUtils;

import java.util.Arrays;
import java.util.List;

public class Equivalence {

    interface Comparator<T extends Object> {
        boolean compare(T elem1, T elem2);
    }

    static Comparator<Comparable> lt = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) < 0;
    static Comparator<Comparable> gt = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) > 0;
    static Comparator<Comparable> lte = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) <= 0;
    static Comparator<Comparable> gte = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) >= 0;
    static Comparator<Comparable> eq = (Comparable elem1, Comparable elem2) -> elem1.compareTo(elem2) == 0;

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
        return elem1.compareTo(elem2) < 0;
    }

    public static <T extends Comparable<? super T>> boolean lte(T elem1, T elem2) {
        return lt(elem1, elem2) || equal(elem1, elem2);
    }

    public static <T extends Comparable<? super T>> boolean gt(T elem1, T elem2) {
        return elem1.compareTo(elem2) > 0;
    }

    public static <T extends Comparable<? super T>> boolean gte(T elem1, T elem2) {
        return gt(elem1, elem2) || equal(elem1, elem2);
    }


    public static <E extends Comparable<? super E>> boolean contains(List<E> arr, E elem1) {
        for (E elem2 : arr) {
            if (equal(elem1, elem2)) {
                return true;
            }
        }
        return false;
    }

    public static <E extends Comparable<? super E>> boolean contains(E[] arr, E elem1) {
        for (E elem2 : arr) {
            if (equal(elem1, elem2)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable<? super T>> boolean equal(T elem1, T elem2) {
        return elem1.equals(elem2) || elem1 == elem2;
    }

    public static <E extends Comparable<? super E>> boolean equal(List<E> arr1, List<E> arr2) {
        for (E elem : arr1) {
            if (!contains(arr2, elem)) {
                return false;
            }
        }
        return true;
    }

    public static <E extends Comparable<? super E>> boolean equal(E[] arr1, E[] arr2) {
        for (E elem : arr1) {
            if (!contains(arr2, elem)) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Object> void main(String[] args) {
        Character[] carr1 = {'a','b','c','d'};
        Character[] carr2 = {'d','c','b','a'};
//        ConsolePrinting.println(equal(carr1, carr2));

        String[] sarr1 = {"bob", "alex", "star", "cammi", "joey"};
        String[] sarr2 = {"joey", "cammi", "star", "alex", "bob"};
//        ConsolePrinting.println(equal(sarr1, sarr2));

//        ConsolePrinting.println(equal("bob", "bob"));

        ConsolePrinting.println(evaluate(lt, 'a', 'b', 'c', 'd'));
        ConsolePrinting.println(evaluate(gt, 'd', 'c', 'b', 'a'));
        ConsolePrinting.println(evaluate(lte, 'a','a', 'a', 'a'));
        ConsolePrinting.println(gte('a', 'b'));


    }
}
