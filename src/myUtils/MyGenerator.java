package myUtils;

import java.util.Random;

public abstract class MyGenerator {

    private static Random generator = new Random();

    public static Integer[] randomInts(int n, int max) {
        Integer[] ret = new Integer[n];
        for (int i = 0; i < n; i++) {
            ret[i] = generator.nextInt(max);
        }
        return ret;
    }

    public static Character[] randomChars(int n) {
        Character[] ret = new Character[n];
        for (int i = 0; i < n; i++) {
            ret[i] = (char)(generator.nextInt(26) + 'a');
        }
        return ret;
    }

    public static void main(String[] args) {
        ConsolePrinting.println(randomChars(100));
        ConsolePrinting.println(randomInts(100, 100));
    }
}
