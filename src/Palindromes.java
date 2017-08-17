import myUtils.ConsolePrinting;

import java.util.*;

public class Palindromes {

    public static <T extends CharSequence> boolean  isPalindrome(T arr) {
        return isPalindrome(arr, 0, arr.length()-1);
    }

    public static <T extends CharSequence> boolean isPalindrome(T arr, int left, int right) {
        if (left >= right) {
            return true;
        } else {
            if (arr.charAt(left) != arr.charAt(right)) {
                return false;
            }
        }
        return isPalindrome(arr, left+1, right-1);
    }

    public static <T extends List<E>, E extends Comparable<? super E>> boolean  isPalindrome(T arr) {
        return isPalindrome(arr, 0, arr.size()-1);
    }

    public static <T extends List<E>, E extends Comparable<? super E>> boolean isPalindrome(T arr, int left, int right) {
        if (left >= right) {
            return true;
        } else {
            if (arr.get(left).compareTo(arr.get(right)) != 0) {
                return false;
            }
        }
        return isPalindrome(arr, left+1, right-1);
    }

    public static <E extends Comparable<? super E>> boolean isPalindrome(E[] arr) {
        return isPalindrome(arr, 0, arr.length-1);
    }

    public static  <E extends Comparable<? super E>> boolean isPalindrome(E[] arr, int left, int right) {
        if (left >= right) {
            return true;
        } else {
            if (arr[left].compareTo(arr[right]) != 0) {
                return false;
            }
        }
        return isPalindrome(arr, left+1, right-1);
    }

    public static void main(String[] args) {
        Character[] arr = new Character[]{'e','e','v','e','e'};
        ConsolePrinting.println(isPalindrome(arr));

        List<Integer> aray = new ArrayList<>(Arrays.asList(1,2,3,2,1));
        ConsolePrinting.println(isPalindrome(aray));

        String str = "racecar";
        ConsolePrinting.println(isPalindrome(str));

    }
}
