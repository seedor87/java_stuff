package GradDataWarehousing.HWResources;

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

}
