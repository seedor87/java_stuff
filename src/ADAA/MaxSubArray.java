package ADAA;

import Utils.Console.Printing;

import java.util.Arrays;
import java.util.Comparator;

public class MaxSubArray {

    static Comparator<Number> NumComparator = (o1, o2) -> ((Comparable) o1).compareTo(o2);

    public static <T extends Number> T[] maxSubArray(T[] arr) {
        T best_so_far = arr[0], cur_best = arr[0];
        int start_index = 0, final_end = 0, final_start = 0;
        for (int i = 0; i < arr.length; i++) {
            if(NumComparator.compare(new SumAggregator().apply(cur_best, arr[i]), new ZeroAggregator().apply(arr[i])) > 0) {
                cur_best = (T) new SumAggregator().apply(cur_best, arr[i]);
            } else {
                cur_best = (T) new MinValueAggregator().apply(cur_best);
                final_start = i+1;
            }
            if(NumComparator.compare(cur_best, best_so_far) > 0) {
                start_index = final_start;
                final_end = i+1;
                best_so_far = cur_best;
            }
        }
        return Arrays.copyOfRange(arr, start_index, final_end);
    }

    public static void main(String[] args) {
        Number[] inp;

        inp = new Integer[]{1, 2, -4, 1, 2, 3};
        Printing.print("result: ");
        Printing.printlnDelim(", ", maxSubArray(inp));
        Printing.println("sum max:", new SumAggregator().apply(maxSubArray(inp)));

        inp = new Integer[]{9, 9, 2, 5, -8, 8, 2, -6, -10, 5, 2, -6, -9, 1, 2, 10, -5, 9, 9, 3, -7, 8, 1, 6, -3, -9, -7, 7, 7, 7, 10, 10, 0, 6, -2, -6, 4, 5, -4, 5, -5, 1, 9, -4, -10, 8, -3, -1, 1, 8, 10, 10, 6, 10, 5, 10, 5, -10, -9, -8, -9, 10, -2, 5, 9, 0, -5, 6, 10, -9, 9, -7, 0, -1, -6, 10, -8, 4, -3, -5, 6, 2, -10, 5, 7, 0, -6, -2, -10, 2, -1, 8, 2, 1, 8, -7, 7, -8, 8, -6};
        Printing.print("result: ");
        Printing.printlnDelim(", ", maxSubArray(inp));
        Printing.println("sum max:", new SumAggregator().apply(maxSubArray(inp)));

        inp = new Double[]{1.1, -1.0, 1.4, 0.65, 1.5, -0.1};
        Printing.print("result: ");
        Printing.printlnDelim(", ", maxSubArray(inp));
        Printing.println("sum max:", new SumAggregator().apply(maxSubArray(inp)));
    }
}
