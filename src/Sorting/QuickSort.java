package Sorting;

import Utils.ConsolePrinting;

public class QuickSort {

    private static  <T extends Comparable<? super T>> void exchange(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    private static  <T extends Comparable<? super T>> int partition(T[] arr, int left, int right) {
        int i = left, j = right;
        T pivot = arr[(left + right) / 2];

        while (i <= j) {
            while (arr[i].compareTo(pivot) < 0)
                i++;
            while (arr[j].compareTo(pivot) > 0)
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
        int index = partition(arr, left, right);
        if (left < index - 1)
            quickSort(arr, left, index - 1);
        if (index < right)
            quickSort(arr, index, right);
    }

    public static  <T extends Comparable<? super T>> void quickSort(T[] arr) {
        quickSort(arr, 0, arr.length-1);
    }

    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{3,2,5,6,1,7,8,4};
        quickSort(iarr);
        ConsolePrinting.println(iarr);

        Character[] carr = new Character[]{'d','f','b','a','g','e','c','h'};
        quickSort(carr);
        ConsolePrinting.println(carr);
    }
}
