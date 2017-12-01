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
        if (Iterable.class.isInstance(first)) {
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


    public static <T extends Collection<E>, E extends Comparable> Collection<E> union(T first, T second) {
        Collection<E> ret = new ArrayList<>();
        ret.addAll(first);
        ret.addAll(second);
        return ret;
    }

    public static <E extends Comparable> E[] union(E[] first, E[] second) {
        int size = Array.getLength(first) + Array.getLength(second);
        E[] ret = (E[]) Array.newInstance(first.getClass().getComponentType(), size);
        int i = 0;
        for(E elem : first) {
            ret[i] = elem;
            i++;
        }
        for(E elem : second) {
            ret[i] = elem;
            i++;
        }
        return ret;
    }

    public static <T extends Collection<E>, E extends Comparable> Set<E> intersection(T first, T second) {
        List<E> ret = new ArrayList<>();
        for (E elem : first) {
            if(contains(second, elem)) {
                ret.add(elem);
            }
        }
        return new HashSet<>(ret);
    }

    public static <E extends Comparable> Set<E> intersection(E[] first, E[] second) {
        List<E> ret = new ArrayList<>();
        for (E elem : first) {
            if(contains(second, elem)) {
                ret.add(elem);
            }
        }
        return new HashSet<>(ret);
    }

    public static <T extends Collection<E>, E extends Comparable> Set<E> bilateralDifference(T first, T second) {
        List<E> ret = new ArrayList<>();
        ret.addAll(difference(first, second));
        ret.addAll(difference(second, first));
        return new HashSet<>(ret);
    }

    public static <E extends Comparable> Set<E> bilateralDifference(E[] first, E[] second) {
        List<E> ret = new ArrayList<>();
        ret.addAll(difference(first, second));
        ret.addAll(difference(second, first));
        return new HashSet<>(ret);
    }

    public static <T extends Collection<E>, E extends Comparable> Set<E> difference(T first, T second) {
        List<E> ret = new ArrayList<>();
        for (E elem : first) {
            if(!contains(second, elem)) {
                ret.add(elem);
            }
        }
        return new HashSet<>(ret);
    }

    public static <E extends Comparable> Set<E> difference(E[] first, E[] second) {
        List<E> ret = new ArrayList<>();
        for (E elem : first) {
            if(!contains(second, elem)) {
                ret.add(elem);
            }
        }
        return new HashSet<>(ret);
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

        ConsolePrinting.println(equal(
                (int) 5.0,
                new Integer(5))
        );
        ConsolePrinting.println(equal(
                new Boolean(false),
                new Boolean("false"))
        );
        ConsolePrinting.println(equal(
                new char[]{'a', 'c'},
                new char[]{'a', 'b'})
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


        // first \/ second
        ConsolePrinting.println("union :" , union(
           new Integer[]{1,2,3},
           new Integer[]{4,5,6})
        );
        // first /\ second
        ConsolePrinting.println("intersection :", intersection(
                new ArrayList<>(Arrays.asList(1,2,3)),
                new ArrayList<>(Arrays.asList(1,3)))
        );
        // first - second
        ConsolePrinting.println("difference :", difference(
                new ArrayList<>(Arrays.asList(1,2,3)),
                new ArrayList<>(Arrays.asList(1,3)))
        );
        // first - second
        ConsolePrinting.println("difference :", difference(
                new Character[]{'a','b','d','e'},
                new Character[]{'a','b','c','d'})
        );
        // first - second
        ConsolePrinting.println("difference :", difference(
                new Long[]{Long.MAX_VALUE, Long.MAX_VALUE+1},
                new Long[]{Long.MIN_VALUE, Long.MIN_VALUE-1})
        );
        // (first - second) \/ (second - first)
       ConsolePrinting.println("bilateralDifference :",  bilateralDifference(
                new Character[]{'a','b','d','e'},
                new Character[]{'a','b','c','d'})
       );
        // (first - second) \/ (second - first)
        ConsolePrinting.println("bilateralDifference :", bilateralDifference(
                new LinkedList<>(Arrays.asList(1,2,3)),
                new LinkedList<>(Arrays.asList(4,5,6)))
        );
    }
}
