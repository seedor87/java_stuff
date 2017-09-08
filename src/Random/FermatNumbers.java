package Random;

import myUtils.ConsolePrinting;

public class FermatNumbers {

    public static int fermatNumber(int p) {
        return (int) Math.pow(2, Math.pow(2, p)) + 1;
    }
    public static void main(String[] args) {
        for(int i = 0; i < 1000; i++) {
            ConsolePrinting.println(fermatNumber(i));
        }
    }
}
