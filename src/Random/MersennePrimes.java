package Random;

import static myUtils.ConsolePrinting.*;
import static myUtils.Primes.isPrime;

public class MersennePrimes {

    public static boolean isMersenne(int n) {
        int i = 0;
        if (isPrime(n)) {
            while(Math.pow(2, i) <= n+1) {
                if (Math.pow(2, i) -1 == n) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        for (int i = 1; i < 100000; i++) {
            if (isMersenne(i)) {
                print("found @ " + i + " ");
            }
        }
    }
}
