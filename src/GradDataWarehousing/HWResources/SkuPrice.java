package GradDataWarehousing.HWResources;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SkuPrice {
    private int sku;
    private double price;

    public SkuPrice(int sku, double price) {
        this.sku = sku;
        this.price = price;
    }

    public int getSku() {
        return this.sku;
    }

    public double getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return this.sku + " " + this.price;
    }

    @Override
    public boolean equals(Object obj) {
        return this.sku == ((SkuPrice) obj).getSku();
    }

    @Override
    public int hashCode() {
        return this.sku;
    }

    /**
     * Custom comparator used to sort the map of sku counts to grab most frequent items
     */
    public static class SkuMapComparator implements Comparator {
        Map<SkuPrice, AtomicInteger> map;

        public SkuMapComparator(Map<SkuPrice, AtomicInteger> map) {
            this.map = map;
        }

        public int compare(Object pairA, Object pairB) {
            AtomicInteger countA = map.get(pairA);
            AtomicInteger countB = map.get(pairB);
            return (countB.intValue() < countA.intValue()) ? -1 : 1;
        }
    }
}
