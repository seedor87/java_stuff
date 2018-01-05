package ADAA;

import Utils.Console.Printing;
import java.util.Comparator;

public class RodCutting {

    static Comparator<Number> NumComparator = (o1, o2) -> ((Comparable) o1).compareTo(o2);

    public static <T extends Number> T cutRod(T[] arr) {
        return cutRod(arr, arr.length);
    }

    public static <T extends Number> T cutRod(T[] arr, int n) {
        Number[] val = new Number[n+1];
        val[0] = new ZeroAggregator().apply(arr[0]);

        for (int i = 1; i <= n; i++) {
            Number max = new MinValueAggregator().apply(arr[0]);
            for(int j = 0; j < i; j++) {
                Number temp = new SumAggregator().apply(arr[j], val[i-j-1]);
                max = NumComparator.compare(max, temp) > 0 ? max : temp;
            }
            val[i] = max;
        }
        return (T) val[n];
    }

    public static void main(String args[])
    {
        Integer[] ints = new Integer[] {1,5,8,9,10,15,17,20};
        Printing.println("Maximum Obtainable Value is", cutRod(ints));

        Double[] doubles = new Double[] {1.1, 2.2, 3.4, 5.1, 6.4, 7.5};
        Printing.println("Maximum Obtainable Value is", cutRod(doubles));

        Long[] longs = new Long[] {10000L, 10250L, 105000L, 10700L, 10900L, 11000L, 12000L};
        Printing.println("Maximum Obtainable Value is", cutRod(longs));
    }

}
