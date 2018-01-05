package ADAA;

import java.math.BigDecimal;
import java.math.BigInteger;

public class SumAggregator<T extends Number> extends Aggregator {

    @Override
    protected BigDecimal aggregate(BigDecimal first, BigDecimal second) {
        return first.add(second);
    }
    @Override
    protected BigInteger aggregate(BigInteger first, BigInteger second) {
        return first.add(second);
    }
    @Override
    protected Byte aggregate(Byte first, Byte second) {
        return (byte) (first + second);
    }
    @Override
    protected Double aggregate(Double first, Double second) {
        return first + second;
    }
    @Override
    protected Float aggregate(Float first, Float second) {
        return first + second;
    }
    @Override
    protected Integer aggregate(Integer first, Integer second) {
        return first + second;
    }
    @Override
    protected Long aggregate(Long first, Long second) {
        return first + second;
    }
    @Override
    protected Short aggregate(Short first, Short second) {
        return (short) (first + second);
    }
}
