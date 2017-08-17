package Specialty;

import myUtils.ConsolePrinting;
import java.util.ArrayList;
import java.util.List;

public class LongestIncreasingSubsequence {

    public static <T extends Comparable<? super T>> List<T> longestIncreasingSubsequence(T[] arr) {
        List<T> ret = new ArrayList<>();
        int n = arr.length;
        if (n < 2) {
            if (n < 1) {
                return ret;
            }
            ret.add(arr[0]);
            return ret;
        }
        int[] D = new int[n];
        D[n-1] = 1;
        int index_max = n-1;
        int max_so_far = 0;
        for (int i=n-2; i > -1; i--) {
            D[i] = 1;
            for(int j=n-1; j > i; j--) {
                if (arr[i].compareTo(arr[j]) < 0 && D[j] + 1 > D[i]) {
                    D[i] = D[j] + 1;
                    if (D[i] > max_so_far) {
                        index_max = i;
                        max_so_far = D[i];
                    }
                }
            }
        }
        ret.add(arr[index_max]);
        if (max_so_far != 0) {
            while (D[index_max] > 1 && D[index_max] > D[index_max+1]) {
                ret.add(arr[index_max+1]);
                index_max += 1;
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        Integer[] iarr = new Integer[]{2,5,-2,-3,3,4,5,3,7};
        ConsolePrinting.println(longestIncreasingSubsequence(iarr));

        Character[] carr = new Character[]{'d','f','g','a','b','c','e','h'};
        ConsolePrinting.println(longestIncreasingSubsequence(carr));
    }
}
