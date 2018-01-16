package Utils.Sorting;

import java.util.Comparator;

import static Utils.Console.Printing.println;
import static Utils.Exchange.exchange;

public class QuickSort<T extends Object> extends AbstractSortingAlgorithm {

    @Override
    public <T extends Comparable<? super T>> T[] sort(Comparator<T> comp, T[] arr, int lowIndex, int highIndex) {
        int i = partition(comp, arr, lowIndex, highIndex);
        if (lowIndex < i - 1) {
            sort(comp, arr, lowIndex, i - 1);
        }
        if (i < highIndex) {
            sort(comp, arr, i, highIndex);
        }
        return arr;
    }

    private static  <T extends Comparable<? super T>> int partition(Comparator<T> comp, T[] arr, int left, int right) {
        int i = left, j = right;
        T pivot = arr[(left + right) / 2];

        while (i <= j) {
            while (comp.compare(arr[i], pivot) < 0) {
                i++;
            }
            while (comp.compare(pivot,arr[j]) < 0) {
                j--;
            }
            if (i <= j) {
                exchange(arr, i, j);
                i++;
                j--;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{3,2,5,6,1,7,8,4};
        Character[] carr = new Character[]{'d','f','b','a','g','e','c','h'};

        QuickSort qs = new QuickSort();

        println(qs.sort(iarr));

        println(qs.sort(carr));

        println(qs.sort((o1, o2) -> ((Comparable) o2).compareTo(o1), iarr, 0, iarr.length-1));

        println(qs.sort((o1, o2) -> ((Comparable) o2).compareTo(o1), carr, 0, carr.length-1));
    }
}
