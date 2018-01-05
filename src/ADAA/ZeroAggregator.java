package ADAA;

import java.math.BigDecimal;
import java.math.BigInteger;

public class ZeroAggregator<T extends Number> extends Aggregator {

    @Override
    protected Object aggregate(BigDecimal first, BigDecimal second) {
        return new BigDecimal(0);
    }
    @Override
    protected Object aggregate(BigInteger first, BigInteger second) {
        return new BigInteger("0");
    }
    @Override
    protected Object aggregate(Byte first, Byte second) {
        return new Byte("0");
    }
    @Override
    protected Object aggregate(Double first, Double second) {
        return 0D;
    }
    @Override
    protected Object aggregate(Float first, Float second) {
        return 0F;
    }
    @Override
    protected Object aggregate(Integer first, Integer second) {
        return 0;
    }
    @Override
    protected Object aggregate(Long first, Long second) {
        return 0L;
    }
    @Override
    protected Object aggregate(Short first, Short second) {
        return 0;
    }
}
