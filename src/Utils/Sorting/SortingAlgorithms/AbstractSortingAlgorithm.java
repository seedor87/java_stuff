package Utils.Sorting.SortingAlgorithms;

import java.util.Collection;
import java.util.Comparator;
import org.junit.Rule;
import TestingUtils.JUnitTesting.TimedRule.TimedRule;
import Utils.Timing.SYSStopwatch;
import Utils.Timing.TimeUnit;

public abstract class AbstractSortingAlgorithm<T extends Comparable<? super T>> implements SortingInterface<T> {

    @Rule
    public TimedRule jcr = new TimedRule(SYSStopwatch.class, TimeUnit.MILLISECONDS);

    private Comparator<T> DEFAULT_COMPARATOR = Comparator.naturalOrder();

    public AbstractSortingAlgorithm() {}

    public AbstractSortingAlgorithm(Comparator<T> comp) {
        this.DEFAULT_COMPARATOR = comp;
    }

    public void setDefaultComparator(Comparator<T> comp) {
        this.DEFAULT_COMPARATOR = comp;
    }

    public T[] sort(T[] arr) {
        return sort(DEFAULT_COMPARATOR, arr);
    }

    public T[] sort(Comparator<T> comp, T[] arr) {
        return sort(comp, arr, 0, arr.length - 1);
    }

    public <C extends Collection<T>> T[] sort(C arr) {
        return sort(DEFAULT_COMPARATOR, arr);
    }

    public <C extends Collection<T>> T[] sort(Comparator<T> comp, C arr) {
        return sort(comp, (T[]) arr.toArray());
    }

}
