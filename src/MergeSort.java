
public class MergeSort {

    public static <T extends Comparable<T>> void mergeSort(T[] arr) {
        mergeSort(arr, 0, arr.length-1);
    }

    public static <T extends Comparable<T>> void mergeSort(T[] arr, int i, int j) {
        int mid = 0;
        if(i < j) {
            mid = (i + j) / 2;
            mergeSort(arr, i, mid);
            mergeSort(arr, mid + 1, j);
            merge(arr, i, mid, j);
        }
    }

    public static <T extends Comparable<T>> void merge(T[] arr, int i, int mid, int j) {
        Comparable temp[] = new Comparable[arr.length];
        int l = i;
        int r = j;
        int m = mid + 1;
        int k = l;

        while(l <= mid && m <= r) {
            if(arr[l].compareTo(arr[m]) <= 0) {
                temp[k++] = arr[l++];
            }
            else {
                temp[k++] = arr[m++];
            }
        }
        while(l <= mid)
            temp[k++] = arr[l++];

        while(m <= r) {
            temp[k++] = arr[m++];
        }

        for(int i1 = i; i1 <= j; i1++) {
            arr[i1] = (T) temp[i1];
        }
    }

    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{3,2,5,6,1,7,8,4};
        mergeSort(iarr);
        ConsolePrinting.println(iarr);

        Character[] carr = new Character[]{'d','f','b','a','g','e','c','h'};
        mergeSort(carr);
        ConsolePrinting.println(carr);
    }
}
