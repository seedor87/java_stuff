package Specialty;

import Utils.Console.Printing;

public class RodCutting {

    public static Arithmetic cutRod(Arithmetic[] arr, int n) {
        myInt[] val = new myInt[n+1];
        val[0] = new myInt(0);

        for (int i = 1; i <= n; i++) {
            myInt max = new myInt(Integer.MIN_VALUE);
            for(int j = 0; j < i; j++) {
                max = ((max.compareTo(arr[j].add(val[i-j-1])) < 0) ? max : (myInt) arr[j].add(val[i-j-1]));
            }
            val[i] = max;
        }
        return val[n];
    }

    public static void main(String args[])
    {
        myInt[] arr = new myInt[] {
                new myInt(1),
                new myInt(5),
                new myInt(8),
                new myInt(9),
                new myInt(10),
                new myInt(17),
                new myInt(17),
                new myInt(20)
        };
        int size = arr.length;
        Printing.println("Maximum Obtainable Value is " + cutRod(arr, size));
    }

}
