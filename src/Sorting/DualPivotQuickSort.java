package Sorting;

import static myUtils.ConsolePrinting.println;
import static myUtils.Exchange.exchange;

public class DualPivotQuickSort {

    public static <T extends Comparable<? super T>> void quickSort(T[] arr) {
        quickSort(arr, 0, arr.length-1);
    }

    private static <T extends Comparable<? super T>> void quickSort(T[] arr, int lowIndex, int highIndex) {
        if (lowIndex < highIndex) {
            T lowPivot = arr[lowIndex];
            T highPivot = arr[highIndex];

            if(lowPivot.compareTo(highPivot) > 0) {
                exchange(arr, lowIndex, highIndex);
                lowPivot = arr[lowIndex];
                highPivot = arr[highIndex];
            } else if (lowPivot.equals(highPivot)) {
                while (lowPivot.equals(highPivot) && lowIndex < highIndex) {
                    lowIndex++;
                    lowPivot = arr[lowIndex];
                }
            }

            int i = lowIndex+1;
            int lt = lowIndex+1;
            int gt = highIndex-1;
            while (i <= gt) {
                if (arr[i].compareTo(lowPivot) < 0) {
                    exchange(arr, i++, lt++);
                } else if (arr[i].compareTo(highPivot) > 0) {
                    exchange(arr, i, gt--);
                } else {
                    i++;
                }
            }

            exchange(arr, lowIndex, --lt);
            exchange(arr, highIndex, ++gt);
            quickSort(arr, lowIndex, lt-1);
            quickSort (arr, lt+1, gt-1);
            quickSort(arr, gt+1, highIndex);
        }
    }

    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{3,2,5,6,1,7,8,4};
        quickSort(iarr);
        println(iarr);

        Character[] carr = new Character[]{'d','f','b','a','g','e','c','h'};
        quickSort(carr);
        println(carr);
    }
}
