package NewTesting;

import Sorting.DualPivotQuickSort;
import Sorting.QuickSort;
import Utils.ConsolePrinting;
import Utils.Collections.Tuple;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static Sorting.RandomPivotQuickSort.quickSort;
import static Utils.ConsolePrinting.println;
import static Utils.ConsolePrinting.printlnDelim;

public class NewTestBench extends NewSYSTimeTest {

    public <T extends Comparable> T[] regQS(T[] carr) {
        QuickSort.quickSort(carr);
        return carr;
    }

    public <T extends Comparable> T[] dualQS(T[] carr) {
        DualPivotQuickSort.quickSort(carr);
        return carr;
    }

    public <T extends Comparable> T[] randQS(T[] carr) {
        quickSort(carr);
        return carr;
    }

    public static void wrapTest(VarArgs lam, Object... args) {
        new NewTestBench().test(lam, args);
    }

    @Override
    public Tuple runThis(Object... params) {
        Character[] carr = (Character[]) params;
        regQS(carr);
        return new Tuple(carr);
    }

    public static void main(String[] args) {

//        Character[] arr1 = MyRandom.randomChars(50);
//        println(arr1);
//
//        Character[] arr2 = MyRandom.randomChars(500);
//        println(arr2);
//
//        Character[] arr3 = MyRandom.randomChars(5000);
//        println(arr3);

        Character[] carr1 = {'q', 'r', 'h', 'm', 'n', 's', 'v', 'n', 'a', 't', 'a', 's', 'k', 'g', 'b', 'w', 'd', 'g', 'q', 'l', 'a', 'q', 'q', 'p', 'c', 'x', 'p', 'o', 'x', 'e', 'm', 'z', 'o', 'm', 'i', 'q', 'l', 't', 'd', 't', 'a', 'p', 'j', 's', 's', 'o', 'p', 'd', 'm', 'c'};
        ConsolePrinting.println(new NewTestBench().test(carr1));

        Character[] carr2 = {'o', 'b', 'j', 'e', 'b', 'u', 'r', 'a', 'r', 't', 'l', 'f', 'd', 'k', 'x', 'z', 'y', 'h', 'h', 'f', 'l', 'z', 'j', 'y', 'e', 'o', 'k', 'c', 'n', 'v', 'p', 't', 'h', 't', 'y', 'b', 'm', 'x', 'u', 'm', 'x', 't', 'c', 's', 'p', 't', 'e', 'r', 'h', 'j', 'j', 'e', 'd', 'h', 'u', 'k', 'l', 'r', 'c', 'b', 'o', 'c', 'h', 'i', 'w', 'b', 'b', 'f', 'm', 'b', 'a', 'u', 'n', 'w', 'h', 'a', 'g', 'l', 'm', 'd', 'w', 'p', 'c', 'a', 't', 'f', 't', 'j', 'x', 'k', 'o', 'k', 'm', 'n', 'k', 'u', 'b', 't', 'k', 'x', 'u', 'p', 'y', 'j', 'x', 'i', 'e', 'd', 'g', 'c', 'c', 'q', 'b', 'e', 'w', 'e', 'g', 'h', 'd', 'i', 'i', 'r', 'f', 'p', 'y', 'f', 'b', 'd', 'l', 'e', 'u', 'z', 'l', 'b', 'w', 's', 'n', 'j', 'z', 'w', 'p', 'z', 'o', 'x', 'a', 'o', 'r', 'f', 'm', 'v', 'd', 'r', 'g', 'k', 'l', 't', 't', 'c', 'b', 'r', 'r', 's', 'w', 'p', 'p', 'd', 'r', 'w', 'd', 'q', 'o', 'n', 't', 'n', 'x', 'w', 'b', 'f', 'x', 't', 'm', 'q', 'b', 'o', 'u', 'n', 'i', 'c', 'u', 'f', 'a', 'l', 'k', 'u', 'u', 'v', 'h', 'y', 'w', 'c', 'r', 'r', 'v', 'j', 'v', 's', 's', 'o', 'i', 'n', 'x', 'd', 'j', 'm', 's', 'q', 'k', 'p', 'n', 'k', 'i', 'r', 'v', 'm', 'b', 'k', 'g', 'i', 'e', 't', 'f', 'b', 'k', 'm', 'i', 'y', 'l', 'd', 'l', 'z', 'a', 'd', 'd', 's', 'b', 'g', 'z', 't', 'v', 'd', 'w', 'l', 'q', 'h', 'f', 'w', 'w', 'o', 'o', 'q', 'p', 'j', 'v', 'g', 'm', 'v', 'e', 'w', 'v', 'o', 'h', 'l', 's', 'a', 'p', 'k', 'l', 'v', 'm', 'k', 'x', 'v', 'n', 'l', 'g', 'u', 'q', 'p', 'c', 'r', 'm', 'i', 'j', 'e', 'v', 'i', 'n', 'b', 'y', 'x', 'd', 'n', 'b', 'd', 'm', 's', 's', 's', 'k', 'h', 'm', 'q', 'q', 'r', 'p', 'p', 'z', 'e', 'l', 'm', 'l', 'r', 'p', 'v', 'j', 'y', 'p', 'c', 'd', 'n', 'e', 'f', 'c', 'p', 'o', 'z', 'h', 'y', 'c', 'm', 'n', 'n', 'q', 'c', 'o', 's', 's', 'n', 'k', 'd', 't', 'm', 'p', 'x', 'r', 'd', 'a', 'c', 'a', 'h', 'w', 'r', 'c', 'a', 'q', 'v', 'e', 'j', 'q', 'y', 'r', 'u', 'd', 'm', 'v', 'k', 'g', 'p', 'e', 'v', 'e', 'x', 'm', 'c', 'i', 'm', 'z', 'v', 'g', 'd', 'v', 'e', 'f', 'a', 'y', 'k', 'y', 'a', 'r', 'u', 'f', 'g', 'y', 'e', 'p', 'i', 'g', 'm', 'i', 'a', 'a', 'n', 'q', 'j', 'h', 's', 'z', 'r', 'n', 's', 'o', 'z', 'j', 'a', 's', 'i', 'h', 'j', 'x', 'e', 'i', 'h', 'p', 'n', 'n', 'w', 's', 'v', 'n', 'd', 'x', 'h', 'k', 's', 'o', 'x', 'z', 'p', 'g', 'n', 'd', 'q', 'h', 'w', 'f', 'b', 'h', 'z', 'd', 'h', 'y', 't', 'h', 'x', 'v', 'h', 'k', 'i', 'e', 'i', 'o', 'c', 'w', 'k', 'k', 'e', 'x', 'g', 'm', 't', 'c', 'r', 'w', 'f', 'n', 'f', 'z', 'm', 'o', 'o', 'g', 'm', 'e', 'o', 'o', 'h', 'z', 'z', 'c', 'g'};
        ConsolePrinting.println(new NewTestBench().test(carr2));

        Character[] carr3 = {'m', 'q', 'c', 'i', 'k', 'o', 'o', 'z', 's', 'q', 'v', 'v', 'k', 'n', 'k', 'l', 'r', 'l', 'r', 'm', 'p', 'b', 'b', 'k', 'f', 'w', 'v', 'o', 'p', 'c', 'r', 'e', 'r', 'j', 'y', 'q', 'x', 'e', 'q', 'x', 'k', 'k', 'p', 'f', 'b', 'l', 'b', 'w', 'v', 'n', 'o', 'm', 'p', 'r', 'k', 'i', 'u', 'm', 'p', 'v', 'z', 'l', 'h', 'i', 'h', 'l', 'd', 'y', 's', 'w', 'p', 'k', 'w', 't', 'b', 'a', 'o', 'v', 'j', 'm', 't', 'b', 'z', 'k', 'y', 'r', 'l', 'g', 'j', 'q', 'z', 'e', 'z', 'j', 'n', 'm', 'b', 'f', 'p', 'i', 'f', 'k', 'e', 'z', 's', 't', 'o', 'p', 'd', 'j', 'y', 'f', 'i', 'j', 'n', 'l', 'q', 'e', 'k', 'l', 'm', 'y', 'm', 'p', 'w', 'h', 'x', 'e', 'z', 's', 'p', 'p', 'w', 'r', 'z', 'a', 'x', 'd', 's', 'j', 'j', 'h', 'x', 'y', 'n', 'o', 'z', 'z', 'l', 'h', 's', 'r', 'n', 'p', 'g', 'f', 'q', 'e', 'j', 'e', 's', 'c', 'g', 'q', 'b', 'i', 'k', 'i', 'g', 'l', 'v', 'y', 'o', 'r', 'j', 'i', 'e', 'z', 'x', 'x', 'l', 'p', 'x', 'x', 'p', 'u', 'c', 'u', 'l', 'j', 'o', 'l', 'y', 'g', 'm', 's', 'o', 'k', 'x', 'e', 'g', 'v', 'y', 'm', 'm', 'q', 'g', 'n', 'b', 'i', 'h', 'i', 'v', 'i', 'v', 'l', 'o', 'x', 'v', 'q', 'p', 'd', 'k', 'h', 'r', 'h', 'k', 'x', 'm', 'a', 'g', 'o', 'w', 'e', 'y', 'q', 'c', 'q', 'a', 'u', 'u', 'l', 's', 'd', 'l', 'a', 'n', 'q', 'i', 'g', 'r', 'o', 'h', 'c', 'z', 'd', 's', 'd', 'l', 's', 'r', 'v', 'g', 'j', 'n', 'n', 'i', 'n', 'c', 'd', 'p', 'l', 'm', 'o', 'u', 'r', 'b', 'h', 'v', 'p', 'p', 'y', 'x', 'a', 'l', 'h', 'z', 'v', 'v', 'o', 'c', 'q', 'x', 'b', 's', 'a', 'b', 't', 'b', 'h', 'p', 'k', 'h', 'e', 'e', 'z', 'b', 'o', 'h', 'r', 'c', 'u', 'j', 'k', 'g', 'v', 'p', 'l', 's', 'w', 'g', 'z', 'b', 'k', 'w', 'l', 'a', 'f', 'l', 'a', 'b', 's', 'g', 't', 'b', 'e', 'a', 'y', 'r', 't', 't', 'u', 'w', 'z', 'z', 'k', 'x', 'q', 'v', 'h', 'f', 'i', 'y', 'b', 'x', 'c', 'o', 'x', 'o', 's', 'q', 'e', 'p', 'v', 'm', 'a', 'o', 'p', 'a', 'o', 'j', 'u', 'r', 'g', 'f', 'g', 'f', 'p', 'm', 'i', 'p', 'k', 'x', 'i', 'a', 'k', 'b', 'i', 'y', 'n', 's', 'j', 'j', 'b', 'z', 'l', 'o', 't', 'h', 'w', 'e', 'w', 'm', 'o', 'n', 'k', 'z', 'f', 'a', 'a', 'l', 'e', 'm', 'p', 'e', 'p', 'a', 'f', 'x', 'h', 'a', 'b', 'g', 'q', 'r', 'l', 'i', 'b', 'k', 'q', 'o', 'v', 'h', 'q', 'b', 'm', 'i', 'i', 'e', 'm', 'p', 'h', 'w', 'v', 'f', 'l', 'c', 'f', 'm', 'p', 'x', 'i', 'g', 'k', 'n', 'v', 'u', 'g', 'v', 'x', 'h', 'r', 'e', 'y', 'o', 'c', 'c', 'b', 'w', 'i', 'z', 'd', 'l', 'k', 'q', 'y', 'o', 't', 'r', 'u', 'd', 'c', 'q', 'q', 'y', 'r', 'r', 'w', 'v', 'd', 'a', 'i', 'v', 'b', 'q', 'b', 'p', 'o', 'a', 'd', 'r', 'r', 'r', 'f', 'l', 'a', 'h', 'o', 'y', 'c', 'v', 's', 'd', 'a', 'm', 'c', 'w', 'q', 'w', 'a', 'z', 'u', 'w', 'u', 'a', 'u', 'i', 'q', 'u', 'g', 'm', 'l', 'w', 'l', 'q', 'z', 'e', 'k', 'z', 'o', 'b', 'u', 'x', 'r', 'z', 'u', 'p', 'p', 'i', 'o', 'h', 'a', 'p', 'y', 'e', 'p', 'o', 'w', 'u', 'v', 'u', 'o', 'a', 'g', 'y', 'g', 'f', 'r', 's', 'm', 'z', 'd', 'c', 'o', 'c', 'e', 'e', 'b', 'j', 'a', 'o', 'r', 'i', 'c', 'k', 'o', 't', 's', 'j', 'p', 'j', 'i', 'b', 'z', 't', 'p', 'x', 'q', 'z', 'c', 'k', 'x', 'e', 's', 'n', 's', 'h', 'u', 'i', 'p', 'e', 'z', 'l', 'l', 't', 'p', 'b', 's', 'x', 'h', 'k', 's', 'x', 'f', 'k', 's', 'e', 'k', 's', 'j', 't', 's', 'p', 'o', 'f', 'o', 'd', 'w', 'w', 'l', 'c', 'z', 'y', 'o', 'h', 'b', 'r', 'l', 'o', 'g', 'r', 'a', 'n', 'o', 'k', 'n', 'a', 'b', 't', 'r', 'n', 'j', 'x', 'w', 'l', 'f', 'g', 'm', 'l', 'v', 'b', 'b', 'n', 's', 'i', 'c', 'u', 'r', 'o', 'p', 'w', 'x', 'g', 'g', 'k', 'h', 'o', 'c', 't', 'k', 'r', 'h', 'f', 'a', 'h', 'm', 'c', 'i', 'y', 'g', 'b', 'y', 'u', 'i', 'y', 'u', 'f', 'j', 'd', 'm', 'e', 't', 'y', 'k', 'o', 'c', 'd', 'g', 'l', 'r', 'g', 'w', 'h', 'r', 'o', 'k', 'f', 'b', 't', 'g', 'a', 't', 'r', 't', 'm', 'v', 's', 'j', 'x', 'c', 'i', 's', 'y', 's', 'i', 'e', 'm', 'b', 'g', 'h', 'y', 'w', 'e', 's', 'd', 'e', 'n', 'b', 'y', 'h', 'u', 's', 'e', 'x', 'o', 'k', 'q', 'c', 'f', 'g', 'h', 'h', 'r', 'n', 'g', 'c', 'l', 'l', 'r', 'e', 'w', 'w', 'q', 'q', 'w', 'f', 'm', 'n', 'h', 'v', 't', 'a', 'y', 'i', 'p', 'i', 'c', 'w', 'r', 'i', 'a', 'w', 'x', 'd', 'e', 'i', 'a', 'r', 'v', 'j', 'z', 'a', 'h', 'u', 'a', 'l', 'n', 'p', 't', 'i', 'e', 'c', 'd', 'm', 's', 'j', 's', 'v', 'h', 't', 'p', 'z', 'f', 't', 'u', 'y', 'n', 's', 'd', 'h', 'd', 'r', 'v', 'm', 'd', 'h', 'u', 'a', 'w', 'g', 'l', 'j', 'y', 'r', 'm', 'r', 'e', 'z', 's', 'o', 'j', 'd', 's', 'c', 'u', 'z', 'n', 'f', 'm', 'x', 'p', 'i', 'r', 'w', 'e', 'q', 'i', 'm', 's', 'e', 'r', 'r', 'w', 'd', 'c', 'g', 'j', 'v', 'r', 'f', 'd', 'r', 'w', 'd', 'o', 'n', 'l', 'x', 'w', 'h', 'v', 'x', 'g', 'i', 'j', 's', 'x', 'v', 'a', 'e', 'e', 'h', 'f', 'p', 'f', 'r', 'y', 'y', 't', 'w', 'g', 'w', 'f', 'f', 'k', 'u', 'w', 'k', 's', 'e', 'q', 'n', 'z', 'd', 'n', 't', 'w', 'x', 's', 'p', 't', 'x', 'x', 'q', 'u', 'w', 'v', 's', 'u', 'f', 'u', 'g', 'w', 'm', 'l', 'p', 'o', 'w', 'o', 'h', 'g', 'i', 'a', 'q', 'k', 'l', 'g', 'r', 's', 'v', 'r', 'j', 'i', 'l', 'a', 'k', 's', 'a', 's', 'q', 'i', 'x', 'm', 'n', 'k', 'l', 'a', 'k', 'o', 'z', 'w', 'a', 'p', 'w', 'j', 'z', 'y', 'a', 'p', 'g', 'g', 'g', 'y', 'z', 'j', 'y', 'a', 'v', 'd', 'q', 'n', 'a', 'k', 'f', 'z', 'f', 'j', 'k', 'z', 'x', 'r', 'j', 'v', 't', 'o', 'j', 'm', 'p', 'l', 'e', 'q', 'u', 'q', 'e', 'y', 's', 'm', 'v', 'j', 'd', 's', 'b', 'c', 'k', 'm', 'x', 'r', 'w', 'c', 'q', 'p', 'b', 'g', 'r', 'b', 'q', 'm', 'r', 'f', 'x', 'f', 'h', 'b', 'i', 'e', 'c', 't', 'w', 's', 'q', 'h', 'n', 'b', 'g', 'm', 'u', 's', 'c', 'x', 'l', 'd', 'p', 'w', 'b', 'y', 'w', 'i', 'v', 's', 'd', 'c', 'w', 'x', 'i', 'v', 'i', 'j', 'h', 'b', 'q', 'k', 't', 'a', 'c', 'c', 'v', 'q', 'b', 'x', 'o', 'b', 'k', 'v', 'k', 'x', 'b', 'l', 'y', 'i', 'u', 'h', 't', 'e', 'j', 'd', 'e', 'z', 'l', 'v', 'h', 'h', 's', 'i', 'a', 'j', 'w', 'f', 'i', 'b', 'r', 'n', 'n', 'o', 'k', 't', 'j', 'l', 'e', 'n', 'g', 'o', 'v', 'f', 'd', 's', 'p', 'l', 'v', 'z', 'r', 'n', 'w', 'i', 'j', 'l', 'j', 'g', 't', 'u', 'o', 'k', 'g', 'g', 'q', 'a', 'r', 'h', 'd', 'k', 'u', 'w', 'o', 's', 'v', 'v', 'l', 'd', 'c', 't', 'b', 'r', 't', 'n', 'c', 'l', 'q', 'd', 's', 'r', 'l', 'u', 'a', 'b', 'j', 'i', 'a', 'l', 'u', 'a', 'b', 'b', 's', 'e', 'k', 'y', 'w', 'm', 'f', 'c', 'h', 'v', 't', 'l', 'b', 'e', 'd', 'p', 's', 'q', 'k', 'h', 'd', 'd', 'u', 'n', 'v', 'b', 'l', 'g', 'f', 'c', 't', 'p', 'l', 'h', 'h', 'z', 'h', 'x', 'l', 'l', 'e', 'y', 'p', 'b', 's', 't', 'd', 'c', 'g', 'l', 'h', 'g', 'n', 's', 'o', 'i', 'z', 'w', 't', 'j', 'j', 'v', 'e', 'i', 't', 'p', 't', 'n', 'w', 'r', 'q', 'h', 'g', 'a', 'x', 'e', 'l', 'm', 'v', 'a', 'd', 'c', 'j', 's', 'q', 'y', 'k', 'f', 'c', 'd', 'j', 'g', 'p', 'i', 'w', 't', 'r', 'y', 'h', 't', 'q', 'q', 't', 'g', 'f', 'v', 'g', 'n', 'a', 'i', 'h', 'c', 'o', 'a', 't', 'o', 'u', 'w', 'e', 'z', 'j', 'u', 't', 'j', 'x', 'y', 'h', 'v', 'v', 'b', 'x', 'd', 'b', 'l', 'f', 'r', 's', 'f', 'b', 'u', 'x', 's', 'w', 'v', 'l', 's', 'j', 'k', 'w', 'p', 'e', 'a', 'n', 'h', 's', 'h', 'n', 'd', 'b', 'q', 'j', 'e', 'b', 'f', 'a', 'f', 'i', 'f', 's', 'c', 'w', 't', 'o', 'n', 'z', 'u', 'f', 'e', 'f', 's', 'a', 'f', 'j', 'j', 'm', 'l', 'z', 'u', 'v', 'g', 'o', 'l', 'a', 'f', 'o', 's', 'w', 'r', 'u', 'a', 'u', 'd', 'f', 's', 'r', 'k', 'g', 'c', 's', 'a', 't', 'h', 'g', 'b', 'k', 'z', 'x', 'e', 'n', 'o', 'b', 'q', 'e', 'r', 'v', 't', 'y', 'b', 'f', 'u', 'r', 'g', 't', 'e', 'h', 'x', 'e', 'w', 'i', 'z', 'v', 'p', 'j', 'j', 'c', 'k', 'r', 't', 'w', 'x', 'v', 'q', 'h', 'o', 'a', 'j', 'd', 'o', 'g', 'n', 'f', 'i', 's', 'x', 'a', 'f', 'q', 'z', 'd', 'n', 'a', 'q', 'd', 'p', 'b', 'y', 'a', 'x', 'c', 'h', 'g', 't', 'i', 'm', 'r', 'z', 'f', 'r', 'j', 'u', 'x', 'x', 'e', 'o', 'a', 'd', 'o', 'm', 'y', 'c', 'y', 'y', 'i', 'm', 'v', 'e', 'w', 'w', 'w', 'j', 'b', 'a', 'p', 'h', 'z', 'w', 'k', 'y', 'z', 's', 'e', 'h', 'h', 'o', 'i', 'b', 'i', 'f', 'y', 'v', 'd', 'k', 'd', 'h', 's', 'f', 'p', 'g', 'y', 'z', 's', 'v', 'a', 'n', 'w', 'q', 'd', 'm', 'z', 'q', 'e', 'z', 'p', 'p', 'm', 'd', 'n', 'c', 'u', 'k', 'h', 'g', 'l', 'g', 'l', 'm', 'd', 'p', 'm', 'z', 'f', 'q', 'u', 'z', 'z', 'w', 'l', 's', 'n', 'j', 'y', 'z', 'a', 'm', 'f', 'k', 's', 'y', 'r', 'm', 'a', 'r', 'g', 'x', 'z', 'i', 'c', 'd', 'q', 'l', 'b', 'd', 'w', 'm', 'm', 'u', 'b', 'c', 'g', 's', 'v', 'q', 'w', 'e', 'j', 'w', 'n', 'c', 'y', 'h', 'i', 'j', 'k', 'q', 'w', 'r', 'i', 'w', 'v', 'n', 'b', 'p', 'v', 'j', 'p', 'l', 'k', 'x', 'j', 'm', 'e', 'q', 's', 's', 'f', 'a', 'v', 'f', 'h', 'g', 'h', 'v', 'u', 'u', 'a', 'u', 'l', 'e', 'r', 'd', 's', 'n', 'e', 'z', 'n', 'x', 'i', 'x', 'a', 'k', 'h', 'm', 'z', 'g', 'q', 'f', 'c', 'b', 'p', 'i', 'u', 'e', 'r', 'm', 'm', 'c', 'm', 'q', 'l', 'q', 't', 'h', 'w', 'm', 'g', 'j', 'c', 'z', 'm', 'k', 'o', 'r', 'z', 'v', 'b', 'i', 'j', 'x', 't', 'p', 'n', 'q', 'w', 'a', 'w', 'l', 't', 'k', 'v', 'p', 'x', 'w', 'w', 'k', 'b', 'v', 'g', 'v', 'b', 'z', 'e', 'l', 'j', 'v', 'u', 'w', 'd', 'h', 'y', 'q', 't', 'x', 'i', 'u', 'u', 'm', 'z', 'u', 'n', 'j', 'p', 'r', 'z', 'o', 'm', 'b', 'd', 'l', 'w', 'n', 'x', 'j', 's', 'k', 'z', 'g', 'x', 'q', 'y', 'f', 'q', 'd', 'c', 'v', 'd', 'z', 'y', 'q', 'q', 'd', 'h', 'm', 'z', 'm', 'e', 'w', 'g', 'w', 'd', 'p', 'j', 'x', 'x', 'a', 'o', 'v', 'l', 'z', 'n', 'z', 'm', 't', 'l', 'l', 'o', 'm', 'k', 'h', 'n', 'n', 'h', 'j', 't', 's', 'y', 's', 'x', 'g', 'q', 'x', 't', 'l', 'u', 'b', 'q', 'w', 'r', 'l', 'i', 'j', 's', 'h', 'b', 'a', 'r', 'n', 'x', 'h', 'k', 'v', 'j', 'z', 'e', 'k', 'x', 'b', 'o', 'a', 'y', 'g', 'y', 'n', 'c', 'i', 'h', 'w', 'l', 'h', 'i', 'o', 'q', 'w', 'o', 'k', 'm', 'j', 'f', 'v', 'p', 's', 'c', 'f', 'f', 'y', 't', 's', 'w', 'a', 'm', 'e', 'e', 'g', 't', 'k', 'k', 'h', 'c', 'y', 'z', 'h', 'z', 'i', 'k', 'n', 'y', 'x', 't', 'l', 't', 'd', 'g', 'g', 'q', 'g', 'j', 'g', 'j', 'f', 'i', 'i', 'l', 's', 'b', 'm', 'h', 'o', 'b', 'b', 'u', 'j', 'x', 'e', 'q', 'j', 'f', 'k', 'x', 'u', 'a', 's', 'r', 'j', 'e', 'k', 'w', 'l', 'a', 'u', 'j', 'z', 'w', 'd', 'r', 'b', 'y', 'p', 'k', 'g', 'v', 'j', 'w', 'm', 'h', 'e', 'c', 'a', 'j', 'n', 'j', 'd', 'q', 'j', 's', 'c', 'f', 'z', 'f', 'y', 'f', 'i', 'c', 'h', 'i', 'a', 'u', 'a', 'k', 'l', 'a', 'w', 'l', 'n', 'w', 'a', 'e', 's', 'f', 'l', 'm', 'c', 'g', 'l', 'b', 'i', 't', 'q', 'z', 'i', 'a', 'o', 'p', 'n', 'z', 'q', 'w', 'j', 'l', 't', 'g', 'g', 'j', 'w', 'p', 'w', 'x', 'p', 'g', 'w', 'l', 'c', 'm', 'z', 'p', 'r', 'a', 'x', 'k', 'j', 'k', 'u', 't', 'o', 'h', 'g', 'w', 'l', 's', 'q', 'f', 't', 'z', 's', 'h', 'm', 's', 'n', 'y', 'q', 'i', 'q', 'r', 'u', 'j', 'v', 'v', 'i', 'h', 'f', 'r', 'u', 'u', 'o', 'g', 'b', 'o', 'z', 'd', 'j', 'a', 'l', 'm', 'z', 'w', 'h', 'z', 'w', 'w', 'h', 'n', 's', 'u', 'k', 'o', 'j', 'z', 'q', 'z', 'i', 'l', 'q', 'l', 'h', 'p', 's', 'k', 'f', 'h', 'u', 'n', 'b', 'k', 'w', 'u', 'o', 'i', 'm', 'h', 'n', 'l', 'l', 'i', 'g', 'j', 'o', 'y', 'z', 's', 'h', 'v', 'e', 'e', 't', 's', 'i', 'y', 'f', 's', 'n', 's', 'o', 'k', 'i', 'y', 'w', 'q', 't', 'p', 'j', 'b', 'i', 'n', 'z', 's', 'o', 'm', 'v', 'x', 'j', 'k', 'h', 'l', 'j', 'l', 'p', 'e', 'v', 'l', 'e', 'd', 'o', 'w', 'z', 'q', 'v', 'x', 'd', 'r', 'q', 'm', 'k', 's', 'b', 'd', 'u', 'g', 'z', 's', 'p', 'd', 's', 'v', 'j', 'e', 't', 'l', 'e', 'y', 'h', 'x', 'd', 't', 'o', 'p', 'r', 'k', 'q', 'r', 's', 'l', 'k', 'p', 'x', 'a', 'o', 'z', 'f', 'u', 'w', 'u', 'y', 'f', 'h', 'q', 'k', 'd', 'x', 'v', 'e', 'h', 'm', 'l', 'z', 'o', 'y', 'g', 'y', 'e', 'x', 'b', 'i', 'l', 'f', 'f', 's', 'j', 't', 'q', 'v', 'z', 'x', 'n', 'q', 'q', 'c', 'z', 'g', 'w', 'q', 'm', 'k', 'b', 'y', 'l', 'm', 'r', 'l', 't', 'u', 'i', 't', 't', 'c', 'o', 'm', 'o', 'i', 's', 'w', 'h', 'd', 'a', 'b', 'u', 'z', 'w', 'w', 'x', 's', 'x', 'v', 'v', 'y', 'g', 'i', 'm', 'm', 'f', 'f', 'j', 'p', 'y', 'a', 'd', 'f', 'c', 'g', 'b', 'j', 'o', 'a', 'y', 'i', 'r', 'v', 'w', 'q', 'l', 'b', 'i', 'n', 'o', 'w', 'd', 'c', 'l', 'y', 'x', 'g', 'b', 'j', 'g', 'a', 'u', 'u', 'c', 'l', 'f', 'j', 'q', 'p', 'n', 'm', 'f', 'r', 'i', 'q', 'e', 'r', 'a', 'u', 'p', 'e', 'f', 'n', 'b', 'o', 'w', 'b', 'e', 'f', 'l', 'a', 'b', 'v', 'm', 'k', 'y', 't', 'q', 's', 'a', 'm', 'j', 'k', 'u', 'g', 's', 'y', 'l', 'u', 'o', 'b', 'q', 'y', 'm', 'n', 'a', 'u', 'i', 'p', 'x', 'v', 'v', 'a', 'w', 'w', 'f', 'g', 'q', 'w', 'm', 'a', 'f', 'c', 'b', 's', 'z', 'm', 'w', 'r', 'o', 'r', 'z', 'k', 'd', 'j', 'e', 'e', 'a', 'm', 'f', 'w', 'u', 'm', 'z', 'q', 'q', 't', 'n', 'e', 'f', 'b', 'r', 'k', 'q', 'e', 'y', 'e', 'c', 'u', 'm', 'k', 'f', 'u', 'h', 'k', 'w', 'g', 'a', 'i', 'o', 'c', 'n', 's', 'j', 'c', 'k', 'z', 's', 'i', 'k', 'z', 'p', 'n', 'm', 's', 'u', 'k', 'q', 'c', 'p', 'q', 'b', 's', 's', 'a', 'i', 'b', 'z', 'w', 'u', 'r', 'i', 'z', 'd', 'v', 'i', 'o', 'b', 'g', 'l', 's', 'x', 'n', 'k', 'g', 'a', 'j', 'u', 'w', 'p', 'v', 'c', 'h', 'r', 'y', 'q', 't', 'p', 'd', 'l', 'c', 'u', 'g', 'p', 'b', 'e', 'i', 'p', 'b', 'd', 't', 'g', 'r', 'q', 'e', 'u', 'j', 'h', 'k', 'p', 'w', 'e', 'd', 'l', 'i', 'q', 'd', 'j', 'b', 'p', 'y', 'm', 'v', 'y', 's', 'b', 's', 'u', 'i', 'r', 'p', 'v', 'k', 'e', 'v', 'v', 'f', 'r', 'b', 'r', 'v', 'y', 'q', 'z', 'u', 'b', 'm', 'o', 'b', 'a', 'a', 's', 'p', 'o', 'p', 'o', 'm', 'h', 'n', 'p', 'b', 'x', 'a', 'n', 'v', 'o', 'u', 'o', 'c', 'b', 'z', 'j', 'b', 'o', 'e', 'o', 'p', 'x', 'm', 'n', 'f', 'e', 'k', 'u', 'u', 't', 'z', 'o', 'i', 'l', 'g', 'u', 'y', 'h', 's', 'b', 'b', 'x', 'a', 'd', 'v', 'c', 'y', 'w', 'r', 'g', 'v', 'u', 'a', 'b', 'd', 'z', 'u', 'z', 'j', 'c', 'z', 'a', 'w', 'l', 'f', 'i', 'r', 'x', 'e', 'w', 'c', 'p', 'i', 'c', 'e', 'k', 'n', 'h', 'r', 'e', 'n', 'b', 'r', 'g', 'r', 'm', 'i', 's', 'w', 'w', 'j', 's', 'n', 'r', 'z', 'm', 'a', 'm', 'w', 'h', 'e', 'd', 'x', 'o', 'k', 'v', 'y', 'j', 'q', 'b', 'e', 'q', 'y', 't', 'c', 'g', 'h', 'l', 'p', 'i', 'k', 'h', 'j', 'u', 'c', 'e', 't', 'l', 'm', 'w', 'n', 'm', 'o', 'w', 'h', 'x', 'o', 'h', 'm', 'l', 'o', 'g', 'u', 'l', 'd', 'n', 'e', 'k', 'e', 'w', 'j', 'f', 't', 'x', 'h', 'u', 'e', 'j', 'v', 'i', 'x', 'r', 'o', 'e', 'c', 'o', 'w', 'v', 'b', 's', 'c', 'x', 'x', 'd', 'm', 'h', 'a', 'v', 'l', 'i', 'k', 'o', 'l', 'k', 'h', 'b', 'j', 'e', 'm', 'y', 'y', 's', 'n', 'i', 'n', 'm', 'g', 'r', 'p', 'o', 'o', 'j', 'i', 'u', 'g', 'l', 'v', 'c', 'i', 'q', 'm', 'l', 'e', 'n', 't', 'b', 'd', 's', 'w', 'i', 'i', 'i', 'm', 'e', 'a', 'g', 's', 'l', 'l', 'l', 'q', 'y', 'u', 'e', 't', 'x', 'y', 'w', 'a', 'n', 'o', 'o', 't', 'o', 'u', 'd', 'e', 'n', 'a', 't', 'e', 'm', 'y', 'r', 'k', 'v', 'o', 'k', 'o', 'h', 'c', 'o', 'a', 'l', 'v', 'q', 'e', 'z', 'm', 'f', 'p', 'h', 'q', 'o', 'k', 'z', 'a', 'q', 'i', 'f', 'e', 'g', 'r', 'k', 'n', 'e', 'x', 'p', 'x', 'z', 'i', 'u', 'a', 'c', 'g', 'h', 'l', 'v', 'p', 'm', 'm', 'a', 'z', 'i', 'r', 'h', 'f', 'g', 'h', 'o', 'a', 'p', 's', 'h', 'j', 'w', 'v', 'i', 'v', 's', 'd', 'l', 'd', 'p', 'p', 'b', 'q', 'q', 'n', 'q', 'v', 'r', 'b', 'r', 'b', 'q', 'u', 'o', 'i', 'c', 'l', 'r', 'u', 't', 'd', 'p', 'w', 'm', 'u', 'f', 'h', 'l', 'd', 'i', 'd', 'y', 'y', 'b', 't', 'p', 'h', 'v', 'h', 'c', 'y', 'f', 'g', 'w', 'e', 'z', 'r', 'm', 'i', 'f', 'u', 'm', 't', 'k', 'q', 'g', 'o', 'k', 'u', 'h', 'n', 'r', 'd', 'f', 'w', 'w', 'k', 'o', 'r', 'b', 'p', 'x', 'l', 'w', 'l', 'v', 'd', 'q', 'n', 'i', 'l', 'e', 'z', 'd', 'r', 'u', 'l', 'n', 'y', 'q', 'h', 'k', 'l', 's', 'p', 'b', 'n', 'w', 'y', 'u', 'j', 'p', 'y', 'v', 'e', 'j', 'r', 'j', 'a', 'g', 'i', 'q', 'g', 'w', 'x', 'm', 'c', 'u', 'o', 'f', 'r', 'h', 'h', 'k', 'a', 'd', 'b', 'd', 'n', 'j', 'y', 'u', 'a', 'k', 'p', 's', 'v', 'g', 'u', 'l', 'q', 'q', 'c', 'u', 'z', 'p', 'v', 'c', 'b', 'q', 'f', 'm', 'o', 'd', 'h', 's', 'b', 'v', 'h', 'd', 'g', 'l', 'n', 'c', 'z', 'v', 'k', 'l', 'q', 'z', 'z', 'y', 'j', 't', 'n', 'f', 's', 'l', 'n', 'e', 'k', 't', 's', 'v', 'h', 'u', 'q', 'z', 'f', 's', 'n', 'v', 'o', 'm', 'i', 'f', 'd', 'd', 'b', 'c', 'w', 'a', 'v', 's', 'k', 's', 'r', 'k', 'l', 's', 'f', 'o', 'c', 'd', 'd', 'b', 't', 'u', 'o', 's', 'z', 'n', 'p', 'r', 'g', 'a', 'v', 'j', 'e', 'z', 'o', 'p', 'n', 'z', 's', 'w', 'm', 'j', 'z', 'r', 'x', 'p', 'k', 'g', 'e', 'k', 'm', 'o', 'x', 'i', 'o', 't', 'v', 'b', 'e', 'e', 'e', 'w', 'h', 'z', 'r', 'j', 'd', 'l', 'a', 'f', 'p', 'e', 'b', 'q', 'd', 'q', 'd', 'e', 'u', 'r', 'y', 'd', 'e', 'w', 'l', 'j', 'j', 'a', 'z', 'd', 'c', 'r', 'n', 'o', 'l', 'm', 'j', 'i', 'x', 'r', 'r', 'q', 'q', 'y', 'y', 'u', 'h', 'y', 'b', 'w', 'o', 'o', 'h', 'q', 't', 'l', 't', 'j', 'm', 's', 'm', 'g', 'b', 'a', 'z', 'j', 'j', 'z', 'o', 'w', 'i', 'r', 's', 'l', 'd', 'a', 'e', 'q', 'b', 'o', 'r', 'c', 'a', 'n', 'r', 't', 'b', 'i', 'z', 'v', 'q', 'e', 'l', 'f', 'k', 'q', 'y', 'u', 'c', 't', 'z', 'a', 'w', 'd', 'o', 's', 'b', 'y', 'n', 't', 'o', 'm', 'h', 'k', 'f', 'p', 'j', 'h', 's', 'k', 'r', 'q', 'f', 'e', 'p', 'r', 'u', 'r', 'h', 'z', 'c', 'w', 'x', 'w', 'v', 'i', 'l', 'p', 'v', 'w', 'a', 't', 'e', 'm', 'b', 'x', 'i', 'p', 'z', 'r', 'g', 'v', 'q', 'k', 'c', 'w', 'p', 'f', 'b', 'f', 'y', 'r', 'j', 'u', 'o', 'j', 'c', 'q', 'r', 'z', 'v', 'z', 'o', 'x', 'g', 's', 'y', 'c', 'd', 'j', 'x', 'f', 'o', 'z', 'r', 'k', 'w', 'c', 'b', 'w', 'a', 't', 'v', 'd', 'c', 'r', 'n', 'd', 'd', 'e', 'y', 'g', 'r', 'u', 'i', 'k', 'v', 'r', 'h', 'b', 'l', 'r', 'z', 't', 'u', 't', 'a', 'g', 'g', 'w', 'e', 'v', 'b', 't', 'i', 'd', 'k', 'y', 'x', 'l', 'a', 'i', 'z', 'b', 'd', 'i', 'q', 'n', 'f', 'c', 'n', 'w', 'l', 'q', 'u', 'e', 'x', 'c', 'b', 'p', 'l', 'i', 'w', 't', 'c', 'q', 'j', 'r', 'v', 'z', 'u', 'l', 'x', 'o', 'k', 'r', 'w', 'v', 'r', 'u', 'c', 'v', 'b', 'h', 'c', 'l', 'h', 'q', 'u', 'c', 'q', 'z', 'j', 'h', 'y', 'd', 'a', 'e', 'f', 'l', 'l', 'b', 'b', 'c', 'w', 'n', 'x', 'g', 'b', 'r', 'g', 'v', 'l', 'c', 'o', 'm', 'f', 'w', 'v', 'n', 'w', 'h', 'z', 'j', 'v', 'o', 't', 'u', 'h', 'd', 'r', 'p', 'm', 'x', 'v', 'h', 'o', 's', 'z', 'n', 'e', 'c', 'v', 'd', 'e', 'h', 'b', 'b', 'b', 'h', 'o', 'm', 'x', 'j', 'd', 'p', 'e', 'v', 'b', 'i', 'b', 'g', 'h', 't', 'h', 'l', 'i', 'p', 'w', 'v', 'y', 't', 'v', 'h', 's', 'd', 'r', 'l', 'e', 'g', 'h', 'j', 'o', 'b', 'g', 'h', 'a', 'h', 'b', 'i', 'm', 's', 'o', 'e', 'n', 'd', 'f', 'f', 'd', 'h', 'e', 'a', 'w', 'u', 'g', 'b', 'm', 'a', 'o', 'h', 'u', 'b', 'd', 't', 'a', 'n', 'f', 'm', 'i', 'p', 'l', 'l', 'w', 'k', 'g', 'z', 'h', 'a', 's', 'q', 'b', 'o', 'f', 'c', 'u', 'c', 'q', 'p', 'z', 'b', 'v', 'v', 'o', 'e', 'c', 'i', 'g', 'u', 'h', 'e', 'u', 'f', 'a', 'y', 'h', 'b', 'n', 'v', 'x', 'r', 'c', 'p', 't', 'b', 'd', 'b', 'g', 's', 'y', 'b', 'z', 'f', 'q', 'm', 'q', 'g', 'x', 's', 'a', 'h', 'd', 'z', 'x', 'l', 'x', 't', 'p', 'r', 'f', 'j', 'u', 'o', 'b', 's', 'k', 'j', 'f', 'w', 'q', 'z', 'u', 'h', 'q', 't', 'y', 's', 'u', 'g', 'w', 'r', 'm', 'g', 'n', 'u', 'k', 'n', 'x', 'h', 'i', 'v', 't', 'r', 't', 'w', 'i', 's', 'u', 'd', 'v', 'r', 'j', 'f', 'e', 'f', 'w', 'b', 'd', 'n', 'i', 'e', 'a', 'c', 'i', 'm', 'c', 'n', 'v', 'e', 'n', 'b', 'k', 'q', 't', 'e', 'n', 'i', 'w', 'x', 'f', 'l', 'a', 'c', 'l', 'e', 'b', 'z', 'b', 't', 'v', 'e', 'd', 'p', 'p', 'u', 'o', 'g', 'v', 'o', 'p', 'y', 'e', 'd', 'r', 'v', 'h', 'g', 'k', 'w', 'n', 'c', 'w', 's', 'z', 'y', 'w', 'q', 'l', 'd', 'b', 'e', 'a', 'r', 'b', 'z', 'l', 'c', 'a', 'p', 'q', 'z', 't', 'e', 'y', 't', 'v', 't', 'n', 'j', 'y', 'l', 'x', 'c', 'q', 'e', 'm', 'd', 'o', 'u', 'x', 'u', 'k', 'u', 'i', 'p', 's', 'i', 'w', 'q', 'i', 'e', 'r', 'c', 'j', 's', 'k', 'w', 'i', 'f', 'g', 'y', 'l', 'j', 'h', 'b', 'g', 'i', 'j', 'y', 'u', 'f', 'k', 't', 'p', 'q', 'q', 'i', 'p', 'h', 'k', 'y', 'w', 'q', 'p', 'f', 'v', 'w', 'k', 'l', 'm', 'm', 'm', 'b', 'q', 's', 'd', 'g', 'z', 'e', 'v', 'o', 'i', 'j', 'i', 'a', 'y', 'w', 'y', 'c', 'c', 'u', 'm', 'q', 'm', 'y', 'h', 's', 'z', 'r', 'a', 'r', 'u', 'x', 'r', 'f', 's', 'g', 'i', 'j', 'z', 'r', 'o', 'x', 'y', 'n', 'y', 'h', 'r', 'h', 'i', 'y', 'l', 'k', 'y', 'v', 'm', 'z', 'f', 'a', 'n', 'd', 'i', 's', 'i', 'd', 'p', 'r', 'e', 'h', 'x', 'a', 'o', 'v', 'i', 'f', 'j', 'l', 'i', 'n', 'l', 'z', 'y', 'b', 'g', 'z', 'v', 'h', 'q', 'e', 's', 'g', 'w', 'v', 'g', 'w', 'm', 'j', 'g', 'j', 'k', 'b', 'a', 'i', 'd', 'y', 'z', 'g', 'x', 'r', 'c', 'j', 'p', 'z', 'g', 'd', 'm', 'z', 'u', 'e', 'a', 'o', 'a', 'l', 'i', 'g', 't', 'a', 'n', 'k', 'r', 't', 'y', 'i', 'y', 'a', 'l', 'p', 'z', 'f', 'o', 'p', 'k', 'i', 'l', 'o', 'a', 'o', 'a', 'c', 'o', 'o', 'e', 'm', 'i', 'k', 'e', 'k', 'x', 'j', 'u', 'k', 'h', 'v', 'o', 'c', 'q', 'v', 'n', 'm', 'm', 'z', 'j', 'v', 'q', 'a', 'q', 'v', 'l', 'z', 'k', 'q', 's', 'u', 'f', 'm', 's', 'u', 'g', 'j', 'v', 'j', 'j', 'a', 'g', 'a', 'u', 'b', 'v', 'b', 'w', 'j', 'r', 'e', 'a', 'd', 'x', 'r', 'l', 'j', 't', 'w', 'j', 'h', 'y', 'g', 'x', 't', 'p', 'd', 'e', 'l', 'k', 'k', 'z', 'm', 'p', 'f', 't', 'l', 'a', 'q', 'u', 'b', 'u', 's', 'x', 'd', 'n', 'u', 'f', 'x', 'c', 's', 'w', 'u', 'j', 'b', 'm', 'n', 'g', 'b', 'x', 'y', 'l', 'r', 'r', 'd', 'e', 's', 'a', 'e', 'w', 'f', 'c', 'l', 'u', 'j', 'n', 'l', 'd', 'q', 'j', 's', 'n', 'f', 'c', 'b', 'a', 'l', 'h', 'l', 'v', 'z', 'j', 'g', 'z', 'b', 'w', 'n', 'm', 'z', 'r', 'o', 'g', 'z', 'h', 'd', 'a', 'g', 'c', 'b', 'a', 'p', 'f', 't', 'x', 'p', 'm', 'j', 'k', 'f', 'v', 'h', 'k', 'c', 'e', 'h', 'c', 'p', 'x', 'f', 'd', 's', 's', 'j', 'y', 'h', 'n', 'z', 'p', 'k', 'u', 'z', 'r', 't', 'f', 'b', 'y', 'p', 'h', 'm', 'c', 's', 's', 'g', 'o', 'm', 'k', 'd', 'p', 'r', 'e', 'c', 'g', 'k', 'x', 's', 'f', 'v', 'r', 'g', 'j', 'b', 'c', 'z', 't', 'd', 'y', 'p', 'h', 'y', 's', 's', 'a', 'i', 'h', 'f', 'f', 'b', 'n', 'u', 'h', 'x', 'p', 't', 'o', 'u', 'g', 'f', 'c', 'b', 'm', 'k', 'a', 'd', 'm', 'r', 's', 'w', 'h', 'i', 'u', 'e', 'z', 'o', 'r', 'e', 'b', 'r', 'b', 's', 'm', 'z', 'k', 'k', 'n', 's', 'y', 'e', 'e', 'j', 'f', 'i', 'm', 'z', 'i', 'l', 'u', 'v', 'b', 'i', 't', 'u', 'v', 'n', 'a', 'y', 'f', 'a', 'h', 'b', 'w', 'l', 'q', 'f', 'b', 'l', 'r', 'b', 'r', 'm', 'v', 'x', 'n', 'h', 's', 'c', 'h', 'g', 'g', 'r', 'n', 'i', 'z', 'c', 'f', 'u', 'v', 'p', 'm', 'k', 'k', 'o', 'q', 'i', 'd', 'e', 'i', 'o', 'q', 'b', 'd', 'f', 't', 'x', 'q', 'w', 'd', 'h', 'n', 'w', 'w', 'w', 'q', 'd', 'r', 'y', 'e', 'd', 'e', 'm', 'q', 'r', 'q', 'r', 'f', 'q', 'x', 'n', 'k', 'z', 'x', 'f', 't', 'a', 'b', 'y', 's', 'l', 't', 't', 'w', 'p', 'c', 'm', 'b', 'f', 'r', 't', 'h', 'p', 'i', 'd', 'p', 'c', 'v', 'v', 'f', 'o', 'q', 'c', 'v', 'g', 'o', 'l', 'v', 't', 'z', 'f', 'q', 'h', 'x', 'y', 'w', 'z', 'i', 's', 'q', 'm', 'g', 'd', 'd', 'u', 'i', 'k', 'i', 'a', 's', 'z', 't', 's', 'q', 'q', 'h', 'd', 'w', 'd', 'v', 'g', 'q', 'q', 'f', 'j', 'w', 'z', 'e', 'p', 'l', 'w', 'e', 'n', 'm', 'r', 'e', 'a', 'f', 'f', 'h', 'z', 'p', 'm', 't', 's', 'u', 'n', 't', 'm', 'e', 'm', 'j', 'u', 'm', 'n', 'j', 'b', 'o', 'y', 'i', 'q', 'a', 'x', 'n', 'x', 't', 's', 'w', 'e', 'n', 'd', 'h', 'n', 'a', 'd', 'j', 'n', 'a', 'u', 'f', 'e', 'w', 'x', 'v', 'j', 'd', 'o', 'c', 'e', 'w', 'q', 'o', 'a', 's', 't', 'x', 'i', 't', 'h', 'w', 'i', 'o', 'v', 'p', 'q', 'x', 'm', 'i', 'v', 'o', 'r', 'i', 'k', 'r', 'b', 'u', 'f', 't', 'd', 'i', 'z', 'x', 'g', 'h', 'z', 'n', 'e', 'q', 'r', 't', 'r', 'k', 'p', 'i', 'a', 'd', 'u', 't', 'w', 'p', 'w', 'b', 'q', 'e', 'q', 't', 'k', 'w', 'l', 'q', 'g', 'a', 'q', 't', 'z', 'j', 'k', 'r', 'o', 'z', 't', 'o', 'p', 'k', 'm', 'g', 'j', 'o', 'f', 'v', 'j', 'u', 'l', 'h', 't', 'b', 'q', 'x', 'r', 'j', 'b', 'b', 'r', 'b', 'z', 'n', 'c', 'k', 'e', 'o', 'p', 'w', 's', 'r', 'e', 'k', 'o', 'z', 'i', 'v', 'i', 'c', 'i', 'm', 'z', 'v', 'c', 'u', 'w', 'm', 'c', 'r', 'k', 'a', 'u', 'p', 't', 'q', 'u', 'k', 'b', 'g', 'd', 'i', 'o', 'u', 'n', 'g', 'q', 's', 'b', 'l', 'f', 'y', 'b', 'd', 'w', 'h', 'n', 'b', 'e', 'i', 's', 's', 'f', 'u', 'r', 'y', 'r', 'f', 'c', 'f', 'l', 'g', 'i', 'm', 'u', 'w', 'j', 'k', 'd', 'f', 'o', 'n', 'f', 'y', 'x', 'o', 'i', 'r', 'g', 'b', 'o', 'd', 'd', 'd', 'a', 'x', 'x', 'w', 'h', 'i', 'z', 'l', 'u', 't', 'k', 'n', 'n', 'u', 'n', 'z', 'l', 'g', 'e', 'g', 'v', 'p', 'a', 'h', 'o', 'u', 'x', 's', 'h', 'f', 's', 'c', 'b', 'n', 'a', 'c', 'y', 'k', 'o', 'g', 'h', 'u', 'l', 'h', 'p', 's', 'k', 'x', 'q', 'f', 'x', 't', 'v', 'b', 'q', 'n', 'z', 'l', 'm', 'n', 'm', 'm', 'y', 'd', 'u', 'l', 'r', 'v', 'b', 'o', 'p', 'f', 'p', 'p', 'k', 'r', 't', 'v', 'v', 'c', 'u', 'r', 'm', 'm', 'q', 'h', 'o', 'n', 'r', 'p', 'p', 'j', 'p', 'y', 'm', 's', 's', 'b', 'y', 'f', 'm', 'm', 'f', 'k', 'u', 'k', 'a', 'c', 'k', 'u', 't', 'y', 'r', 'w', 'z', 'y', 'm', 'w', 'l', 'j', 'e', 'd', 'v', 'w', 'c', 'w', 'l', 't', 'y', 'v', 'g', 's', 't', 'z', 'y', 'p', 'c', 'x', 'd', 'l', 'o', 'w', 's', 'l', 'z', 'g', 'j', 'k', 'z', 's', 'm', 'r', 'c', 'm', 'e', 'r', 's', 'u', 'c', 'q', 'y', 'v', 'j', 'x', 'v', 'y', 'v', 's', 'm', 'n', 'k', 'f', 't', 'h', 't', 'y', 'a', 'a', 'y', 'z', 'd', 'i', 'w', 'p', 'm', 'd', 't', 'p', 'y', 'q', 'g', 'i', 'c', 'o', 'p', 'a', 'z', 'f', 'm', 'r', 'k', 'd', 'u', 'f', 'y', 'f', 'n', 'h', 'r', 'k', 'a', 'g', 'y', 'v', 'q', 'r', 'p', 'k', 'k', 'v', 'b', 'l', 'y', 'n', 'h', 'o', 'g', 'm', 'f', 'r', 'w', 'n', 'i', 'c', 't', 'l', 's', 'a', 'l', 'u', 'y', 'k', 'n', 'd', 'e', 'r', 'd', 'u', 'd', 'v', 'w', 'a', 'r', 'o', 'p', 'p', 't', 'j', 'y', 'a', 'q', 'r', 'b', 'u', 'd', 'y', 'k', 'm', 'f', 'i', 'd', 'l', 't', 'q', 'j', 'p', 'i', 'b', 'n', 'g', 'w', 'd', 'p', 'z', 'm', 'r', 's', 'c', 'k', 'g', 'z', 'c', 'k', 'b', 'j', 'u', 's', 'p', 'd', 'm', 't', 'm', 'i', 'y', 'd', 'l', 'q', 'j', 't', 'y', 'i', 'p', 'm', 'n', 'e', 'h', 'x', 'n', 'u', 'y', 'i', 'h', 'h', 't', 'a', 'w', 'p', 'd', 'v', 'b', 'w', 'd', 'b', 't', 'o', 'k', 'r', 'm', 'k'};
        ConsolePrinting.println(new NewTestBench().test(carr3));


        VarArgs myExe2 = (Object[] oarr) -> {
            quickSort((Comparable[]) oarr);
            return new Tuple<>(oarr);
        };
        println(new NewTestBench().test(myExe2, new Character[]{'1','0','6','3','4','2','5','7'}));


        VarArgs myExe3 = (Object... params) -> {
            for(int i = 0; i <( Integer) params[0]; i++) {
                ConsolePrinting.print(params[1] + " ");
            }
            ConsolePrinting.println();
            return new Tuple<>(params);
        };
        println(new NewTestBench().test(myExe3, 3, 'a'));

        VarArgs myExe4 = (Object... params) -> {
            printlnDelim((String) params[0], (List) params[1]);
            return new Tuple<>(params);
        };
        wrapTest(myExe4, "~", new LinkedList<>(Arrays.asList(1,2,3,4)));
    }
}
