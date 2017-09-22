package Utils;

import java.lang.reflect.Array;
import java.util.*;

import static Utils.ListUtils.contains;

public class Equivocation<T extends Object> {

    public static <T> boolean equal(T a, T b) {
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
                if(!equal(Array.get(a, i), Array.get(b, i))) {
                    return false;
                }
            }
            return true;
        }
        if(a.getClass().isInstance(Iterable.class)) {
            return equal((Collection) a, (Collection) b);
        }
        return a.equals(b);
    }

    public static <E extends Comparable<? super E>> boolean equal(Collection<E> arr1, Collection<E> arr2) {
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

    public static <E extends Comparable<? super E>> boolean distinct(Collection<E> arr) {
        HashSet<E> set = new HashSet<>();
        for(E elem : arr) {
            if(!set.add(elem)) {
                return false;
            }
        }
        return true;
    }

    public static <E extends Comparable<? super E>> boolean distinct(E... args) {
        HashSet<E> set = new HashSet<>();
        for(E elem : args) {
            if(!set.add(elem)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Character[] carr1 = {'a','b','c','d'};
        Character[] carr2 = {'d','c','b','a'};
        ConsolePrinting.println(carr1, " == ", carr2, equal(carr1, carr2));

        String[] sarr1 = {"bob", "alex", "star", "cammi", "joey"};
        String[] sarr2 = {"joey", "cammi", "star", "alex", "bob"};
        ConsolePrinting.println(sarr1, " == ", sarr2, equal(sarr1, sarr2));

        List<Integer> ilist1 = new LinkedList<>(Arrays.asList(1,2,3,4));
        List<Integer> ilist2 = new LinkedList<>(Arrays.asList(4,3,2,1));
        ConsolePrinting.println(ilist1, " == ", ilist2, equal(ilist1, ilist2));

        ConsolePrinting.println("distinct?", distinct(new Character[]{'a','b','c','d','e','f'}));
        ConsolePrinting.println("distinct?", distinct(1,2,3,4,5,6,1));
    }
}
