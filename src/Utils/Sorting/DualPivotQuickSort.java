package Utils.Sorting;

import java.util.Comparator;
import static Utils.Console.Printing.println;
import static Utils.Exchange.exchange;

public class DualPivotQuickSort<T extends Comparable<? super T>> extends AbstractSortingAlgorithm<T> {

    @Override
    public T[] sort(Comparator comp, Comparable[] arr, int lowIndex, int highIndex) {
        if(highIndex > lowIndex) {
            Comparable lowPivot = arr[lowIndex], highPivot = arr[highIndex];

            if (comp.compare(highPivot, lowPivot) < 0) {
                exchange(arr, lowIndex, highIndex);
                lowPivot = arr[lowIndex];
                highPivot = arr[highIndex];
            }
            while(comp.compare(lowPivot, highPivot) > 0 && lowIndex < highIndex) {
                lowIndex++;
                lowPivot = arr[lowIndex];
            }

            int lt = lowIndex + 1, gt = highIndex - 1, i = lowIndex + 1;

            while (i <= gt) {
                if (comp.compare(arr[i], lowPivot) < 0) {
                    exchange(arr, i++, lt++);
                } else if (comp.compare(highPivot, arr[i]) < 0) {
                    exchange(arr, i, gt--);
                } else {
                    i++;
                }
            }
            exchange(arr, lowIndex, --lt);
            exchange(arr, highIndex, ++gt);

            sort(comp, arr, lowIndex, lt - 1);
//        if (comp.compare(arr[lt], arr[gt]) < 0) {
            sort (comp, arr, lt + 1, gt - 1);
//        }
            sort(comp, arr, gt + 1, highIndex);
        }
        return (T[]) arr;
    }

    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{3,2,5,6,1,7,8,4};
        Character[] carr = new Character[]{'d','f','b','a','g','e','c','h'};

        DualPivotQuickSort dpqs = new DualPivotQuickSort();

        println(dpqs.sort(iarr));

        println(dpqs.sort(carr));

        println(dpqs.sort((o1, o2) -> ((Comparable) o2).compareTo(o1), iarr, 0, iarr.length-1));

        println(dpqs.sort((o1, o2) -> ((Comparable) o2).compareTo(o1), carr, 0, carr.length-1));
    }
}
