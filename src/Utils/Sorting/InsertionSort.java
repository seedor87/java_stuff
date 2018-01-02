package Utils.Sorting;

import static Utils.Console.Printing.println;

public class InsertionSort {

    public static <T extends Comparable<? super T>> void insertionSort(T[] arr) {
        insertionSort(arr, arr.length-1);
    }

    public static <T extends Comparable<? super T>> void insertionSort(T[] arr, int n) {
        T x;
        int j;
        if(n > 0) {
            insertionSort(arr, n-1);
            x = arr[n];
            j = n-1;
            while(j >= 0 && arr[j].compareTo(x) > 0) {
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = x;
        }
    }

    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{3,2,5,6,1,7,8,4};
        insertionSort(iarr);
        println(iarr);

        Character[] carr = new Character[]{'d','f','b','a','g','e','c','h'};
        insertionSort(carr);
        println(carr);
    }
}
