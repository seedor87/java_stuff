package GradDataWarehousing.HWResources;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Custom comparator used to sort the map of sku counts to grab most frequent items
 */
public class SkuMapComparator implements Comparator {
    Map map;

    public SkuMapComparator(Map map) {
        this.map = map;
    }

    public int compare(Object pairA, Object pairB) {
        AtomicInteger countA = (AtomicInteger) map.get((SkuPrice) pairA);
        AtomicInteger countB = (AtomicInteger) map.get((SkuPrice) pairB);
        return (countB.intValue() < countA.intValue()) ? -1 : 1;
    }
}