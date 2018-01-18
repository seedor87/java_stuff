package Utils.Sorting;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import Utils.Sorting.SortingAlgorithms.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DualPivotQuickSort.class,
        InsertionSort.class,
        MedianOfThreePartitioningQuickSort.class,
        MergeSort.class,
        QuickSort.class,
        RandomPivotQuickSort.class,
        SelectionSort.class
})
public class SortingTestSuite {}
