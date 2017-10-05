package RandomStuff;

import Utils.ConsolePrinting;

public class FermatNumbers {

    public static long fermatNumber(int p) {
        return (long) Math.pow(2, Math.pow(2, p)) + 1;
    }
    public static void main(String[] args) {
        for(int i = 0; i < 1000; i++) {
            ConsolePrinting.println(fermatNumber(i));
        }
    }
}
