package Random;

import Utils.ConsolePrinting;

import static Utils.Primes.isPrime;

public class MersennePrimes {

    public static boolean isMersenne(int n) {
        int i = 0;
        while(Math.pow(2, i) <= n+1) {
            if (Math.pow(2, i) -1 == n) {
                return isPrime(n);
            }
            i++;
        }
        return false;
    }

    public static void main(String[] args) {
        int lim = 1000000000;
        for (int i = 1; i < lim; i++) {
            if (isMersenne(i)) {
                ConsolePrinting.println("found @ " + i);
            }
        }
    }
}
