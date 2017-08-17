package Random;

import myUtils.ConsolePrinting;

public class FermatNumbers {

    public static void main(String[] args) {
        for(int i = 0; i < 1000; i++) {
            ConsolePrinting.println((int) Math.pow(2, Math.pow(2, i)) + 1);
        }
    }
}
