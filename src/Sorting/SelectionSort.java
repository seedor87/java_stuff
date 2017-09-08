package Sorting;

import static myUtils.ConsolePrinting.println;
import static myUtils.Exchange.exchange;

public class SelectionSort {

    public static  <T extends Comparable<? super T>> void selectionSort(T[] arr) {
        int i,j;
        int n = arr.length;

        for (j = 0; j < n-1; j++) {
            int iMin = j;
            for (i = j; i < n; i++) {
                if (arr[i].compareTo(arr[iMin]) < 0) {
                    iMin = i;
                }
            }
            if(iMin != j) {
                exchange(arr, j, iMin);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{3,2,5,6,1,7,8,4};
        selectionSort(iarr);
        println(iarr);

        Character[] carr = new Character[]{'d','f','b','a','g','e','c','h'};
        selectionSort(carr);
        println(carr);
    }

}
