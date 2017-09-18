package Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static Utils.ConsolePrinting.println;

public class ListUtils {

    public static String[] join(String[]... params) {
        int fullSize = 0;
        for (String[] param : params) {
            fullSize += param.length;
        }
        String[] result = new String[fullSize];
        int i = 0;
        int j = 0;
        int breaker = 0;
        while(true) {
            if(breaker >= params.length) {
                break;
            }
            breaker = 0;
            for(String[] param : params) {
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

    public static List<String> join(List<String>... params) {
        List<String> result = new ArrayList<>();
        int i = 0;
        int breaker = 0;
        while(true) {
            if(breaker >= params.length) {
                break;
            }
            breaker = 0;
            for(List<String> param : params) {
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
        println(join(array1, array2, array3));

        List<String> list1 = new ArrayList<>(Arrays.asList("one", "two", "three"));
        List<String> list2 = new ArrayList<>(Arrays.asList("four", "five", "six"));
        List<String> list3 = new ArrayList<>(Arrays.asList("seven", "eight", "nine", "ten", "eleven"));
        println(join(list1, list2, list3));
    }

}
