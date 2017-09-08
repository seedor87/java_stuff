package Sorting;

import myUtils.MyGenerator;

import static myUtils.ConsolePrinting.println;
import static myUtils.Equivalence.lt;
import static myUtils.Exchange.exchange;

public class DualPivotQuickSort {

    public static <T extends Comparable<? super T>> void quickSort(T[] arr) {
        quickSort(arr, 0, arr.length-1);
    }

    private static <T extends Comparable<? super T>> void quickSort(T[] arr, int lowIndex, int highIndex) {
        if(highIndex <= lowIndex) {
            return;
        }

        T lowPivot = arr[lowIndex];
        T highPivot = arr[highIndex];

        if(lt(highPivot, lowPivot)) {
            exchange(arr, lowIndex, highIndex);
        }
        int lt = lowIndex + 1;
        int gt = highIndex - 1;
        int i = lowIndex + 1;

        while (i <= gt) {
            if (lt(arr[i], lowPivot)) {
                exchange(arr, i++, lt++);
            } else if (lt(highPivot, arr[i])) {
                exchange(arr, i, gt--);
            } else {
                i++;
            }
        }
        exchange(arr, lowIndex, --lt);
        exchange(arr, highIndex, ++gt);

        quickSort(arr, lowIndex, lt-1);
        if (lt(arr[lt], arr[gt])) {
            quickSort (arr, lt+1, gt-1);
        }
        quickSort(arr, gt+1, highIndex);
    }

    public static void test(int len, int max) {
        Integer[] itest1 = MyGenerator.randomInts(len, max);
        quickSort(itest1);
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
