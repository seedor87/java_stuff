package Utils.Sorting;

import java.util.Collection;
import java.util.Comparator;

public abstract class AbstractSortingAlgorithm<T extends Comparable<? super T>> {

    public T[] sort(T[] arr) {
        return sort(Comparator.naturalOrder(), arr);
    }

    public T[] sort(Comparator<T> comp, T[] arr) {
        return sort(comp, arr, 0, arr.length - 1);
    }

    public <C extends Collection<T>> T[] sort(C arr) {
        return sort(Comparator.naturalOrder(), arr);
    }

    public <C extends Collection<T>> T[] sort(Comparator<T> comp, C arr) {
        return sort(comp, (T[]) arr.toArray());
    }

    abstract T[] sort(Comparator<T> comp, T[] arr, int lowIndex, int highIndex);
}
