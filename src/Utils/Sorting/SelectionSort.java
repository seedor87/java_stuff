package Utils.Sorting;

import java.util.Comparator;

import static Utils.Console.Printing.println;
import static Utils.Exchange.exchange;

public class SelectionSort extends AbstractSortingAlgorithm {

    @Override
    public <T extends Comparable<? super T>> T[] sort(Comparator<T> comp, T[] arr, int lowIndex, int highIndex) {
        int i, j, iMin, n = highIndex;

        for (j = lowIndex; j < n-1; j++) {
            iMin = j;
            for (i = j; i < n; i++) {
                if (comp.compare(arr[i], arr[iMin]) < 0) {
                    iMin = i;
                }
            }
            if(iMin != j) {
                exchange(arr, j, iMin);
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{3,2,5,6,1,7,8,4};
        Character[] carr = new Character[]{'d','f','b','a','g','e','c','h'};

        SelectionSort ss = new SelectionSort();

        println(ss.sort(iarr));

        println(ss.sort(carr));

        println(ss.sort((o1, o2) -> ((Comparable) o2).compareTo(o1), iarr));

        println(ss.sort((o1, o2) -> ((Comparable) o2).compareTo(o1), carr));
    }
}
