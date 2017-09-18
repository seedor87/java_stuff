package Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static Utils.ConsolePrinting.*;

public class ListUtils {

    public static Object[] join(Object[]... params) {
        int fullSize = 0;
        for (Object[] param : params) {
            fullSize += param.length;
        }
        Object[] result = new Object[fullSize];
        int i = 0;
        int j = 0;
        int breaker = 0;
        while(true) {
            if(breaker >= params.length) {
                break;
            }
            breaker = 0;
            for(Object[] param : params) {
                try {
                    result[j] = param[i];
                    j++;
                } catch (IndexOutOfBoundsException e) {
                    breaker+= 1;    // go again
                }
            }
            i++;
        }
        return result;
    }

    public static <E extends Object> List<Object> join(List<E>... params) {
        List<Object> result = new ArrayList<>();
        int i = 0;
        int breaker = 0;
        while(true) {
            if(breaker >= params.length) {
                break;
            }
            breaker = 0;
            for(List<E> param : params) {
                try {
                    result.add(param.get(i));
                } catch (IndexOutOfBoundsException e) {
                    breaker+= 1;    // go again
                }
            }
            i++;
        }
        return result;
    }

    public static void main(String[] args) {
        String[] array1 = new String[]{"one", "two", "three"};
        String[] array2 = new String[]{"four", "five", "six"};
        String[] array3 = new String[]{"seven", "eight", "nine", "ten", "eleven"};
        println(fgRed, join(array1, array2, array3));

        List<String> list1 = new ArrayList<>(Arrays.asList("one", "two", "three"));
        List<String> list2 = new ArrayList<>(Arrays.asList("four", "five", "six"));
        List<String> list3 = new ArrayList<>(Arrays.asList("seven", "eight", "nine", "ten", "eleven"));
        println(fgBlue, join(list1, list2, list3));

        println(fgGreen, join(
                new Character[]{'a', 'b'},
                new Character[]{'c', 'd'},
                new Character[]{'e', 'f'}
        ));
    }

}
