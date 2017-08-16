package Utils;
import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;
import java.awt.Toolkit;
import java.util.List;

public class Primes {

    public static void main(String[] args) {

        test(100000);
        test(1000000);
        test(10000000);
        test(100000000);

        Toolkit tk = Toolkit.getDefaultToolkit();
        tk.beep();
    }

    public static void test(int lim) {

        ArrayList<Integer> primes = new ArrayList<>();
        long startTime = System.nanoTime();
        for (int j = 0; j < lim; j++) {
            if (isPrime(j)) {
                primes.add(j);
            }
        }
        long endTime = System.nanoTime();

        ConsolePrinting.print("All Primes: ");
        ConsolePrinting.println(primes);
        double duration = (endTime - startTime) / 1000000000.0;
        ConsolePrinting.println("Runtime: " + duration + "s");
    }

    public static List<Integer> getAllPrimes(int lim) {
        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= lim; i++) {
            if (isPrime(i)) {
                primes.add(i);
            }
        }
        return primes;
    }

    public static boolean isPrime(int n) {
        for (int i = 2; i <= (int) Math.sqrt(n); i++) {
            if( n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
