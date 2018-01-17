package Utils.Sorting;

import java.util.Comparator;
import static Utils.Console.Printing.println;

public class InsertionSort<T extends Comparable<? super T>> extends AbstractSortingAlgorithm<T> {

//    @Override
//    public T[] sort(Comparator comp, Comparable[] arr, int lowIndex, int highIndex) {
//        Comparable x;
//        int j;
//        if(highIndex > lowIndex) {
//            sort(comp, arr, lowIndex, highIndex-1);
//            x = arr[highIndex];
//            j = highIndex-1;
//            while(j >= 0 && comp.compare(x, arr[j]) < 0) {
//                arr[j+1] = arr[j];
//                j = j-1;
//            }
//            arr[j+1] = x;
//        }
//        return (T[]) arr;
//    }

    @Override
    public T[] sort(Comparator comp, Comparable[] arr, int lowIndex, int highIndex) {
        for(int i = lowIndex + 1; i <= highIndex; i++ ) {
            Comparable x = arr[i];
            int j;
            for(j = i; j > lowIndex && comp.compare(x, arr[j - 1] ) < 0; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = x;
        }
        return (T[]) arr;
    }


    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{3,2,5,6,1,7,8,4};
        Character[] carr = new Character[]{'d','f','b','a','g','e','c','h'};

        InsertionSort is = new InsertionSort();

        println(is.sort(iarr));

        println(is.sort(carr));

        println(is.sort((o1, o2) -> ((Comparable) o2).compareTo(o1), iarr, 0, iarr.length-1));

        println(is.sort((o1, o2) -> ((Comparable) o2).compareTo(o1), carr, 0, carr.length-1));
    }
}
