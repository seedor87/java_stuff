package Random;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static myUtils.ConsolePrinting.*;

public class PerfectNumbers {

    public static void isPerfectNumberTest(int lim) {
        for (int i = 2; i < lim; i++) {
            if (isPerfectNumber(i)) {
                println("\tDude Perfect :" + i);
            }
        }
    }

    public static long binaryToInteger(String binary) {
        char[] numbers = binary.toCharArray();
        long result = 0;
        for(int i=numbers.length - 1; i>=0; i--)
            if(numbers[i]=='1')
                result += Math.pow(2, (numbers.length-i - 1));
        return result;
    }

    public static int sum(List<Integer> arr) {
        int total = 0;
        for (Integer elem : arr) {
            total += elem;
        }
        return total;
    }



    public static boolean isPerfectNumber(int n) {
        return sum(allFactors(n)) == n;
    }

    public static List<Integer> allFactors(int n) {
        List<Integer> ret = new ArrayList<>();
        ret.add(1);
        for (int i = 2; i*i < n; i++) {
            if(n % i == 0) {
                ret.add(i);
                ret.add(n/i);
            }
        }
        println(sum(ret));
        return ret;
    }

    public static void main(String[] args) throws IOException {
        int[] primes = new int[]{3,5,7,13,17,19,31};
        for (int prime : primes) {
            StringBuffer binForm = new StringBuffer();
            binForm.append("1");
            for (int i = 0; i < prime-1; i++) {
                binForm.insert(0,"1");
                binForm.append("0");
            }
            println(binForm.toString());
            println(binaryToInteger(binForm.toString()));
        }
    }
}
