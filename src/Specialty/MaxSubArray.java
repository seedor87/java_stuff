package Specialty;

import Utils.ConsolePrinting;

import java.util.Arrays;

public class MaxSubArray {

    public static class myInt implements Arithmetic {
        int val;
        public myInt(int val) {
            this.val = val;
        }

        @Override
        public Arithmetic add(Arithmetic o) {
            return new myInt(this.getVal() + o.getVal());
        }

        @Override
        public Arithmetic sub(Arithmetic o) {
            return new myInt(this.getVal() - o.getVal());
        }

        @Override
        public int compareTo(Arithmetic o) {
            return (this.getVal() > o.getVal()) ? -1 : 1;
        }

        @Override
        public int getVal() {
            return this.val;
        }

        @Override
        public void setval(int val) {
            this.val = val;
        }

        public String toString() {
            return "" + this.getVal();
        }
    }

    public static <T extends Arithmetic> Arithmetic sum(T[] arr) {
        Arithmetic count = arr[0];
        for (T elem : Arrays.copyOfRange(arr, 1, arr.length)) {
            count = elem.add(count);
        }
        return count;
    }

    public static <T extends Arithmetic> Arithmetic[] maxSubArray(T[] arr) {
        Arithmetic best_so_far = arr[0];
        Arithmetic cur_best = arr[0];
        int start_index = 0;
        int final_end = 0;
        int final_start = 0;
        for (int i = 0; i < arr.length; i++) {
            if(cur_best.add(arr[i]).compareTo(new myInt(0)) < 0) {
                cur_best = cur_best.add(arr[i]);
            } else {
                cur_best = new myInt(0);
                final_start = i+1;
            }
            if(cur_best.compareTo(best_so_far) < 0) {
                start_index = final_start;
                final_end = i+1;
                best_so_far = cur_best;
            }
        }
        return Arrays.copyOfRange(arr, start_index, final_end);
    }

    public static Arithmetic[] test(int[] inp) {
        myInt[] arr = new myInt[inp.length];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new MaxSubArray.myInt(inp[i]);
        }
        return maxSubArray(arr);
    }


    public static void main(String[] args) {
        int[] inp;
        Arithmetic[] out;

        inp = new int[]{1, 2, -4, 1, 2, 3};
        out = test(inp);
        ConsolePrinting.print("result: ");
        ConsolePrinting.println(out);
        ConsolePrinting.print("sum: ");
        ConsolePrinting.println(sum(test(inp)));

        inp = new int[]{9, 9, 2, 5, -8, 8, 2, -6, -10, 5, 2, -6, -9, 1, 2, 10, -5, 9, 9, 3, -7, 8, 1, 6, -3, -9, -7, 7, 7, 7, 10, 10, 0, 6, -2, -6, 4, 5, -4, 5, -5, 1, 9, -4, -10, 8, -3, -1, 1, 8, 10, 10, 6, 10, 5, 10, 5, -10, -9, -8, -9, 10, -2, 5, 9, 0, -5, 6, 10, -9, 9, -7, 0, -1, -6, 10, -8, 4, -3, -5, 6, 2, -10, 5, 7, 0, -6, -2, -10, 2, -1, 8, 2, 1, 8, -7, 7, -8, 8, -6};
        out = test(inp);
        ConsolePrinting.print("result: ");
        ConsolePrinting.println(out);
        ConsolePrinting.print("sum: ");
        ConsolePrinting.println(sum(test(inp)));
    }
}
