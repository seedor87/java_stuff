package Sorting;

import Testing.superWrapper;
import com.sun.xml.internal.bind.v2.model.annotation.Quick;
import myUtils.ConsolePrinting;
import myUtils.MyGenerator;
import myUtils.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static myUtils.ConsolePrinting.println;
import static myUtils.ConsolePrinting.printlnDelim;
import static myUtils.Exchange.exchange;

public class QuickSort<T extends Object> extends superWrapper {

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

    public static void test(int len, int max) {
        Integer[] itest1 = MyGenerator.randomInts(len, max);
        quickSort(itest1);
    }

    @Override
    public Tuple runIt(Object... params) {
        quickSort((Comparable[]) params);
        return new Tuple<>(params);
    }

    public static void main(String[] args) {

        println(new QuickSort().test(new Integer[]{3,2,5,6,1,7,8,4}));

        VarArgs<Character> myExe = (Character[] carr) -> {
            quickSort(carr);
            return new Tuple<>(carr);
        };
        println(QuickSort.test(myExe, new Character[]{'d','f','b','a','g','e','c','h'}));

        VarArgs myExe2 = (Object[] oarr) -> {
            quickSort((Comparable[]) oarr);
            return new Tuple<>(oarr);
        };
        println(QuickSort.test(myExe2, new Character[]{'1','0','6','3','4','2','5','7'}));


        VarArgs myExe3 = (Object... params) -> {
            for(int i = 0; i <( Integer) params[0]; i++) {
                ConsolePrinting.print(params[1] + " ");
            }
            ConsolePrinting.println();
            return new Tuple<>(params);
        };
        println(QuickSort.test(myExe3, 3, 'a'));

        VarArgs myExe4 = (Object... params) -> {
            printlnDelim((String) params[0], (List) params[1]);
            return new Tuple<>(params);
        };
        println(QuickSort.test(myExe4, "~", new LinkedList<>(Arrays.asList(1,2,3,4))));

    }

}
