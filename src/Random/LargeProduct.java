package Random;

public class LargeProduct {

    public boolean test1() {
        double res1, res2;
        int[] arr = new int[]{6,5,4};
        res1 = myWay(arr);
        res2 = averageCeil(arr);
        return res1 == res2;
    }

    public boolean test2() {
        double res1, res2;
        int[] arr = new int[]{6,5,4,3};
        res1 = myWay(arr);
        res2 = averageCeil(arr);
        return res1 == res2;
    }

    public static double averageCeil(int[] arr) {
        int total = 0;
        for (int i = 0; i < arr.length; i++) {
            total += arr[i];
        }
        return Math.ceil(total * 1.0 / arr.length);
    }

    public static double myWay(int[] arr) {
        int prod = 1;
        for (int i = 0; i < arr.length; i++) {
            prod *= arr[i];
        }
        double min = Math.pow(prod, (1.0 / arr.length));
       return (double) Math.round(min);
    }

    public static void main(String[] args) {}
}
