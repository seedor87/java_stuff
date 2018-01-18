package Utils.Sorting.SortingAlgorithms;

import java.util.Comparator;

public interface SortingInterface<T extends Comparable<? super T>> {
    T[] sort(Comparator<T> comp, T[] arr, int lowIndex, int highIndex);
}
