package Utils.Sorting;

import java.util.Random;

import static Utils.Console.Printing.println;
import static Utils.Comparison.lt;
import static Utils.Exchange.exchange;

public class RandomPivotQuickSort {

    private static  <T extends Comparable<? super T>> int partition(T[] arr, int left, int right) {
        int i = left, j = right;
        int index = new Random().nextInt(((right - left) + 1) + left);
        T pivot = arr[index];

        while (i <= j) {
            while (lt(arr[i],pivot))
                i++;
            while (lt(pivot,arr[j]))
                j--;
            if (i <= j) {
                exchange(arr, i, j);
                i++;
                j--;
            }
        }
        return i;
    }

    public static  <T extends Comparable<? super T>> void quickSort(T[] arr, int left, int right) {
        int i = partition(arr, left, right);
        if (left < i - 1)
            quickSort(arr, left, i - 1);
        if (i < right)
            quickSort(arr, i, right);
    }

    public static  <T extends Comparable<? super T>> void quickSort(T[] arr) {
        quickSort(arr, 0, arr.length-1);
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
