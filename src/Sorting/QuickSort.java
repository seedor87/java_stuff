package Sorting;

import NewTesting.NewSYSStopwatchTest;
import Utils.MyRandom;
import Utils.Collections.Tuple;

import static Utils.Console.Printing.println;
import static Utils.Console.Printing.printlnDelim;
import static Utils.Comparison.lt;
import static Utils.Exchange.exchange;

public class QuickSort<T extends Object> extends NewSYSStopwatchTest<T> {

    private static  <T extends Comparable<? super T>> int partition(T[] arr, int left, int right) {
        int i = left, j = right;
        T pivot = arr[(left + right) / 2];

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

    public static void test(int len, int max) {
        Integer[] itest1 = MyRandom.randomInts(len, max);
        quickSort(itest1);
    }

    @Override
    public Tuple runThis(Object... params) {
        quickSort((Comparable[]) params);
        return new Tuple<>(params);
    }

    public static void main(String[] args) {

        println(new QuickSort().test(new Integer[]{3,2,5,6,1,7,8,4}));

        VarArgs<Character> myExe1 = (Character[] carr) -> {
            quickSort(carr);
            return new Tuple<>(carr);
        };
        println(new QuickSort().test(myExe1, new Character[]{'d','f','b','a','g','e','c','h'}));
    }

}
