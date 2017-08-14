import Sorting.DualPivotQuickSort;
import Sorting.QuickSort;
import Utils.ConsolePrinting;
import Utils.MyGenerator;

public class TestBench {

    public static void main(String[] args) {

        Integer[] itest1 = MyGenerator.randomInts(100000, 100000);
        Character[] ctest1 = MyGenerator.randomChars(100000);
        long startTime, endTime;
        double duration;

        startTime = System.nanoTime();
        QuickSort.quickSort(itest1);
        ConsolePrinting.println(itest1);
        QuickSort.quickSort(ctest1);
        ConsolePrinting.println(ctest1);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000000.0;
        ConsolePrinting.println("Runtime: " + duration + "s");


        startTime = System.nanoTime();
        DualPivotQuickSort.quickSort(itest1);
        ConsolePrinting.println(itest1);
        DualPivotQuickSort.quickSort(ctest1);
        ConsolePrinting.println(ctest1);
        endTime = System.nanoTime();
        duration = (endTime - startTime) / 1000000000.0;
        ConsolePrinting.println("Runtime: " + duration + "s");
    }
}
