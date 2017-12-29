package RandomStuff;

import Utils.Console.Printing;

import java.util.stream.IntStream;

public class FermatNumbers {

    public static long fermatNumber(int p) {
        return (long) Math.pow(2, Math.pow(2, p)) + 1;
    }
    public static void main(String[] args) {
        IntStream.range(0, 7)
            .forEach(i -> Printing.println(fermatNumber(i)));
    }
}
