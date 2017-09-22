package Utils;

import Utils.Collections.Tuple;

import java.util.*;

import static Utils.ConsolePrinting.*;
import static Utils.Comparison.*;

public class ListUtils {

    public static <E extends Comparable<? super E>> boolean contains(Iterable<E> arr, E elem1) {
        for (E elem2 : arr) {
            if (eq(elem1, elem2)) {
                return true;
            }
        }
        return false;
    }

    public static <E extends Comparable<? super E>> boolean contains(E[] arr, E elem1) {
        for (E elem2 : arr) {
            if (eq(elem1, elem2)) {
                return true;
            }
        }
        return false;
    }

    public static Object[] inLineZip(Object[]... params) {
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

    public static <E extends Object> List<Object> inLineZip(List<E>... params) {
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

    public static <E extends Object> Tuple<E>[] zip(E[]... params) {
        int len = params[0].length;
        for (E[] objs : params) {
            if(objs.length != len) {
                throw new RuntimeException("Invalid args: must all be same length");
            }
        }

        Tuple<E>[] result = new Tuple[len];
        for(int i = 0; i < len; i++) {
            Object[] subArr = new Object[len];
            int index = 0;
            for(int j = 0; j < params.length; j++) {
                subArr[index++] = params[j][i];
            }
            result[i] = new Tuple(subArr);
        }
        return result;
    }

    public static <E extends Object> List<Tuple<E>> zip(List<E>... params) {
        int len = params[0].size();
        for (List<E> objs : params) {
            if(objs.size() != len) {
                throw new RuntimeException("Invalid args: must all be same length");
            }
        }

        List<Tuple<E>> result = new ArrayList<>();
        for(int i = 0; i < len; i++) {
            List<E> subList = new ArrayList<>();
            for(int j = 0; j < params.length; j++) {
                subList.add(params[j].get(i));
            }
            result.add(new Tuple(subList.toArray()));
        }
        return result;
    }

    private static <E extends Comparable<? super E>> E most(Comparison.Comparator comp, E... params) {
        E hold = params[0];
        for (E elem : params) {
            if(evaluate(comp, elem, hold)) {
                hold = elem;
            }
        }
        return hold;
    }

    public static<E extends Comparable<? super E>> E max(E... params) {
        return most(gt, params);
    }
    public static <E extends Comparable<? super E>> E min(E... params) {
        return most(lt, params);
    }

    public static void main(String[] args) {
        String[] array1 = new String[]{"one", "two", "three"};
        String[] array2 = new String[]{"four", "five", "six"};
        String[] array3 = new String[]{"seven", "eight", "nine", "ten", "eleven"};
        println(fgRed, inLineZip(array1, array2, array3));

        List<String> list1 = new ArrayList<>(Arrays.asList("one", "two", "three"));
        List<String> list2 = new ArrayList<>(Arrays.asList("four", "five", "six"));
        List<String> list3 = new ArrayList<>(Arrays.asList("seven", "eight", "nine", "ten", "eleven"));
        println(fgBlue, inLineZip(list1, list2, list3));

        println(fgGreen, inLineZip(
                new Character[]{'a', 'b'},
                new Character[]{'c', 'd'},
                new Character[]{'e', 'f'}
        ));

        ConsolePrinting.println(max(1,2,3,4,5));
        ConsolePrinting.println(min(1,2,3,4,5,0));

        ConsolePrinting.println(zip(
                new Object[]{1,2,3},
                new Object[]{'a','b','c'},
                new Object[]{"do", "re", "mi"}
                ));

        ConsolePrinting.println(zip(
                new LinkedList<>(Arrays.asList(1,2,3)),
                new LinkedList<>(Arrays.asList('a','b','c')),
                new LinkedList<>(Arrays.asList("do", "re", "mi"))
        ));
    }

}
