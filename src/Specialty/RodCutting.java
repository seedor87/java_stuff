package Specialty;

import Utils.ConsolePrinting;

public class RodCutting {

    public static int cutRod(int[] arr, int n) {
        int[] val = new int[n+1];
        val[0] = 0;

        for (int i = 1; i <= n; i++) {
            int max = Integer.MIN_VALUE;
            for(int j = 0; j < i; j++) {
                max = Math.max(max, arr[j] + val[i-j-1]);
            }
            val[i] = max;
        }
        return val[n];
    }

    public static void main(String args[])
    {
        int arr[] = new int[] {1, 5, 8, 9, 10, 17, 17, 20};
        int size = arr.length;
        ConsolePrinting.println("Maximum Obtainable Value is " + cutRod(arr, size));
    }

}
