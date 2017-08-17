package myUtils;

import java.util.List;

public class Equivalence {

    public static <T extends Comparable<? super T>> boolean equal(T elem1, T elem2) {
        return elem1.equals(elem2) || elem1 == elem2;
    }

    public static <T extends Comparable<? super T>> boolean contains(List<T> arr, T elem1) {
        for (T elem2 : arr) {
            if (equal(elem1, elem2)) {
                return true;
            }
        }
        return false;
    }

    public static <T extends Comparable<? super T>> boolean equal(List<T> arr1, List<T> arr2) {
        for (T elem : arr1) {
            if (!contains(arr2, elem)) {
                return false;
            }
        }
        return true;
    }
}
