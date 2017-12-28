package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Utils.Console.Printing.println;

/**
 * Created by Bob S on 8/17/2017.
 */
public class Exchange {

    public static <T extends Comparable<? super T>> void exchange(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    public static <T extends Comparable<? super T>> void exchange(List<T> arr, int i, int j) {
        T tmp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, tmp);
    }

    public static String exchange(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
        return new String(arr);
    }

    public static String exchange(String arr, int i, int j) {
        return exchange(arr.toCharArray(), i, j);
    }

    public static void main(String[] args) {
        List<Integer> iarr = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
        for(int i = 0; i < iarr.size()/2; i++) {
            exchange(iarr, i, iarr.size()-1-i);
        }
        println(iarr);

        Character[] carr = new Character[]{'a','b','c','d','e','f'};
        for(int i = 0; i < carr.length/2; i++) {
            exchange(carr, i, carr.length-1-i);
        }
        println(carr);

        String test = "supercalifragilisticexpialadotious";
        for(int i = 0; i < test.length()/2; i++) {
            test = exchange(test, i, test.length()-1-i);
        }
        println(test);
    }
}
