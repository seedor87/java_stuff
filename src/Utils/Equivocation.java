package Utils;

import java.lang.reflect.Array;
import java.util.*;

import static Utils.ListUtils.contains;

public class Equivocation<T extends Object> {

    public static <T> boolean equal(T first, T second) {
        if(!first.getClass().equals(second.getClass())) {
            return false;
        }
        if (first.getClass().isPrimitive()) {
            return first == second;
        }
        if (first.getClass().isArray()) {
            return (Array.getLength(first) > Array.getLength(second)) ? equalArray(first, second) : equalArray(second, first);
        }
        if(Iterable.class.isInstance(first)) {
            return equalIterable(first, second);
        }
        return first.equals(second);
    }

    private static <T> boolean equalArray(T first, T second) {
        int i = 0;
        while(i < Array.getLength(first)) {
            int j = 0;
            boolean found = false;
            while(j < Array.getLength(second) && !found) {
                if(equal(Array.get(first, i), Array.get(second, j))) {
                    found = true;
                    break;
                }
                j++;
            }
            if (!found) {
                return false;
            }
            i++;
        }
        return true;
    }

    private static <T> boolean equalIterable(T first, T second) {
        boolean found = false;
        for (Object elem1 : (Iterable) first) {
            for (Object elem2 : (Iterable) second) {
                if (equal(elem1, elem2)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
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

        ConsolePrinting.println(equal(5, new Integer(5)));
        ConsolePrinting.println(equal(
                new char[]{'a'},
                new char[]{'a','b'})
        );

        Character[] carr1 = {'a','b','c','d'};
        Character[] carr2 = {'d','c','b','a'};
        ConsolePrinting.println(carr1, " == ", carr2, equal(carr1, carr2));

        String[] sarr1 = {"bob", "alex", "star", "cammi", "joey"};
        String[] sarr2 = {"joey", "cammi", "star", "alex", "bob"};
        ConsolePrinting.println(sarr1, " == ", sarr2, equal(sarr1, sarr2));

        List<Integer> ilist1 = new LinkedList<>(Arrays.asList(1,2,3,4));
        List<Integer> ilist2 = new LinkedList<>(Arrays.asList(4,3,2,1));
        ConsolePrinting.println(ilist1, " == ", ilist2, equal(ilist1, ilist2));

        ConsolePrinting.println("distinct?", distinct(new LinkedList(Arrays.asList('a','b','c','d','e','f'))));
        ConsolePrinting.println("distinct?", distinct(1,2,3,4,5,6,1));

        ConsolePrinting.println("intersection :", intersection(
                new ArrayList<>(Arrays.asList(1,2,3)),
                new ArrayList<>(Arrays.asList(1,3)))
        );
        ConsolePrinting.println("difference :", difference(
                new ArrayList<>(Arrays.asList(1,2,3)),
                new ArrayList<>(Arrays.asList(1,3)))
        );
    }
}
