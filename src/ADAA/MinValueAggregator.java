package ADAA;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MinValueAggregator<T extends Number> extends Aggregator{

    @Override
    protected Object aggregate(BigDecimal first, BigDecimal second) {
        return new BigDecimal(-1000000000);
    }
    protected Object aggregate(BigDecimal num) {
        return this.aggregate(num, null);
    }
    @Override
    protected Object aggregate(BigInteger first, BigInteger second) {
        return new BigInteger("-1000000000");
    }
    protected Object aggregate(BigInteger num) {
        return this.aggregate(num, null);
    }
    @Override
    protected Object aggregate(Byte first, Byte second) {
        return Byte.MIN_VALUE;
    }
    protected Object aggregate(Byte num) {
        return this.aggregate(num, null);
    }
    @Override
    protected Object aggregate(Double first, Double second) {
        return Double.MIN_VALUE;
    }
    protected Object aggregate(Double num) {
        return this.aggregate(num, null);
    }
    @Override
    protected Object aggregate(Float first, Float second) {
        return Float.MIN_VALUE;
    }
    protected Object aggregate(Float num) {
        return this.aggregate(num, null);
    }
    @Override
    protected Object aggregate(Integer first, Integer second) {
        return Integer.MIN_VALUE;
    }
    protected Object aggregate(Integer num) {
        return this.aggregate(num, null);
    }
    @Override
    protected Object aggregate(Long first, Long second) {
        return Long.MIN_VALUE;
    }
    protected Object aggregate(Long num) {
        return this.aggregate(num, null);
    }
    @Override
    protected Object aggregate(Short first, Short second) {
        return Short.MIN_VALUE;
    }
    protected Object aggregate(Short num) {
        return this.aggregate(num, null);
    }
}
