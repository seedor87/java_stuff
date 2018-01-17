package Utils.Sorting;

import java.util.Comparator;

import static Utils.Exchange.exchange;

public class MedianOfThreePartitioningQuickSort<T extends Comparable<? super T>> extends AbstractSortingAlgorithm<T> {

    private static final int CUTOFF = 10;

    @Override
    public T[] sort(Comparator comp, Comparable[] arr, int lowIndex, int highIndex) {
        if(lowIndex + CUTOFF > highIndex)
            insertionSort(arr, lowIndex, highIndex);
        else {
            // Sort low, middle, high
            int middle = (lowIndex + highIndex) / 2;
            if(comp.compare(arr[middle], arr[lowIndex]) < 0)
                exchange(arr, lowIndex, middle);
            if(comp.compare(arr[highIndex], arr[lowIndex]) < 0)
                exchange(arr, lowIndex, highIndex);
            if(comp.compare(arr[highIndex], arr[middle]) < 0)
                exchange(arr, middle, highIndex);

            // Place pivot at position high - 1
            exchange(arr, middle, highIndex - 1);
            Comparable pivot = arr[highIndex - 1];

            // Begin partitioning
            int i, j;
            for (i = lowIndex, j = highIndex - 1; ;) {
                while (comp.compare(arr[++i], pivot) < 0);
                while (comp.compare(pivot, arr[--j]) < 0);
                if(i >= j)
                    break;
                exchange(arr, i, j);
            }

            // Restore pivot
            exchange(arr, i, highIndex - 1);

            sort(comp, arr, lowIndex, i - 1);    // Sort small elements
            sort(comp, arr, i + 1, highIndex);   // Sort large elements
        }
        return (T[]) arr;
    }
    
    private static void insertionSort(Comparable [] a, int low, int high) {
        for(int p = low + 1; p <= high; p++) {
            Comparable tmp = a[p];
            int j;

            for(j = p; j > low && tmp.compareTo(a[j - 1]) < 0; j--)
                a[j] = a[j - 1];
            a[j] = tmp;
        }
    }

}
