package Utils.Sorting;

import java.util.Comparator;

import static Utils.Console.Printing.println;

public class MergeSort extends AbstractSortingAlgorithm {

    @Override
    public  <T extends Comparable<? super T>> T[] sort(Comparator<T> comp, T[] arr, int lowIndex, int highIndex) {
        int mid;
        if(lowIndex < highIndex) {
            mid = (lowIndex + highIndex) / 2;
            sort(comp, arr, lowIndex, mid);
            sort(comp, arr, mid + 1, highIndex);
            merge(comp, arr, lowIndex, mid, highIndex);
        }
        return arr;
    }

    private static <T extends Comparable<? super T>> void merge(Comparator<T> comp, T[] arr, int i, int mid, int j) {
        Comparable temp[] = new Comparable[arr.length];
        int l = i, r = j, m = mid + 1, k = l;

        while(l <= mid && m <= r) {
            if(comp.compare(arr[l], arr[m]) <= 0) {
                temp[k++] = arr[l++];
            } else {
                temp[k++] = arr[m++];
            }
        }
        while(l <= mid) {
            temp[k++] = arr[l++];
        }
        while(m <= r) {
            temp[k++] = arr[m++];
        }
        for(int _i = i; _i <= j; _i++) {
            arr[_i] = (T) temp[_i];
        }
    }

    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{3,2,5,6,1,7,8,4};
        Character[] carr = new Character[]{'d','f','b','a','g','e','c','h'};

        MergeSort ms = new MergeSort();

        println(ms.sort(iarr));

        println(ms.sort(carr));

        println(ms.sort((o1, o2) -> ((Comparable) o2).compareTo(o1), iarr, 0, iarr.length-1));

        println(ms.sort((o1, o2) -> ((Comparable) o2).compareTo(o1), carr, 0, carr.length-1));
    }
}
