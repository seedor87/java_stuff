package Utils.Sorting;

import java.util.Collection;
import java.util.Comparator;

public abstract class AbstractSortingAlgorithm {

    public <T extends Comparable<? super T>> T[] sort(T[] arr) {
        return sort(Comparator.naturalOrder(), arr);
    }

    public <T extends Comparable<? super T>> T[] sort(Comparator<T> comp, T[] arr) {
        return sort(comp, arr, 0, arr.length - 1);
    }

//    public <T extends Comparable<? super Comparable>, C extends Collection<T>> C sort(C arr) {
//        return sort(Comparator.naturalOrder(), arr);
//    }
//
//    public <T extends Comparable<? super Comparable>, C extends Collection<T>> C sort(Comparator<T> comp, C arr) {
//        T[] ret = (T[]) new Object[arr.size()];
//        return sort(comp, arr.toArray(ret));
//    }

    abstract <T extends Comparable<? super T>> T[] sort(Comparator<T> comp, T[] arr, int lowIndex, int highIndex);
}
