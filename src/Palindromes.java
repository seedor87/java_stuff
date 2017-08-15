import Utils.ConsolePrinting;

public class Palindromes {

    public static  <T extends Comparable<? super T>> boolean isPalindromeForward(T[] arr) {
        return isPalindromeForward(arr, 0, arr.length-1);
    }

    public static  <T extends Comparable<? super T>> boolean isPalindromeForward(T[] arr, int left, int right) {
        if (left >= right) {
            return true;
        } else {
            if (arr[left].compareTo(arr[right]) != 0) {
                return false;
            }
        }
        return isPalindromeForward(arr, left+1, right-1);
    }

    public static void main(String[] args) {
        Character[] arr = new Character[]{'e','e','v','e','e'};
        ConsolePrinting.println(isPalindromeForward(arr));
    }
}
