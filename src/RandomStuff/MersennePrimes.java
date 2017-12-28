package RandomStuff;

import static Utils.Console.Printing.*;
import static Utils.Primes.isPrime;

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

    public static void mersennePrimeTest(int lim) {
        for (int i = 1; i < lim; i++) {
            if (isMersenne(i)) {
                print("found @ " + i + " ");
            }
        }
        println();
    }

    public static void main(String[] args) {
        mersennePrimeTest(1000000);
    }
}
