package Utils.Sorting;

import java.util.Comparator;
import static Utils.Console.Printing.println;
import static Utils.Exchange.exchange;

public class SelectionSort<T extends Comparable<? super T>> extends AbstractSortingAlgorithm<T> {

    @Override
    public T[] sort(Comparator comp, Comparable[] arr, int lowIndex, int highIndex) {
        int i, iMin;

        if (lowIndex <= highIndex) {
            iMin = lowIndex;
            for (i = lowIndex; i <= highIndex; i++) {
                if (comp.compare(arr[i], arr[iMin]) < 0) {
                    iMin = i;
                }
            }
            if(iMin != lowIndex) {
                exchange(arr, lowIndex, iMin);
            }
            return sort(comp, arr, lowIndex + 1, highIndex);
        }
        return (T[]) arr;


//        for (j = lowIndex; j <= highIndex; j++) {
//            iMin = j;
//            for (i = j; i <= highIndex; i++) {
//                if (comp.compare(arr[i], arr[iMin]) < 0) {
//                    iMin = i;
//                }
//            }
//            if(iMin != j) {
//                exchange(arr, j, iMin);
//            }
//        }
//        return arr;
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
