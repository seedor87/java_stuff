package ADAA;

import java.math.BigDecimal;
import java.math.BigInteger;
import static Utils.Console.Printing.*;

interface Aggregatable<T extends Number> {
    T apply(Aggregator<Number> aggregator, T first, T second);
}

enum SupportedType implements Aggregatable<Number> {

    BIG_DECIMAL(    BigDecimal.class,   (aggregator, first, second) -> (BigDecimal) aggregator.aggregate((BigDecimal) first, (BigDecimal) second)),
    BIG_INTEGER(    BigInteger.class,   (aggregator, first, second) -> (BigInteger) aggregator.aggregate((BigInteger) first, (BigInteger) second)),
    BYTE(           Byte.class,         (aggregator, first, second) -> (Byte) aggregator.aggregate((Byte) first, (Byte) second)),
    DOUBLE(         Double.class,       (aggregator, first, second) -> (Double) aggregator.aggregate((Double) first, (Double) second)),
    FLOAT(          Float.class,        (aggregator, first, second) -> (Float) aggregator.aggregate((Float) first, (Float) second)),
    INTEGER(        Integer.class,      (aggregator, first, second) -> (Integer) aggregator.aggregate((Integer) first, (Integer) second)),
    LONG(           Long.class,         (aggregator, first, second) -> (Long) aggregator.aggregate((Long) first, (Long) second)),
    SHORT(          Short.class,        (aggregator, first, second) -> (Short) aggregator.aggregate((Short) first, (Short) second));

    private Class<? extends Number> classAttr;
    private Aggregatable<Number> application;
    SupportedType(Class<? extends Number> classAttr, Aggregatable<Number> application) {
        this.classAttr = classAttr;
        this.application = application;
    }
    public Number apply(Aggregator aggregator, Number first, Number second) {
        return this.application.apply(aggregator, first, second);
    }
    public Class<? extends Number> getClassAttr() {
        return this.classAttr;
    }
}

public abstract class Aggregator<T extends Number> {

    public T apply(Iterable<T> iterable) {
        T result = null;
        for (T item : iterable) {
            result = aggregate(result, item);
        }
        return result;
    }
    public T apply(T... arr) {
        T result = null;
        for (T item : arr) {
            result = aggregate(result, item);
        }
        return result;
    }

    private T aggregate(T first, T second) {
        if (first == null) {
            return second;
        } else if (second == null) {
            return first;
        }
        for (SupportedType type : SupportedType.values()) {
            if (type.getClassAttr().isInstance(first)) {
                return (T) type.apply(this, first, second);
            }
        }
        throw new UnsupportedOperationException("Aggregator only supports official subclasses of Number");
    }

    protected abstract Object aggregate(BigDecimal first, BigDecimal second);
    protected abstract Object aggregate(BigInteger first, BigInteger second);
    protected abstract Object aggregate(Byte first, Byte second);
    protected abstract Object aggregate(Double first, Double second);
    protected abstract Object aggregate(Float first, Float second);
    protected abstract Object aggregate(Integer first, Integer second);
    protected abstract Object aggregate(Long first, Long second);
    protected abstract Object aggregate(Short first, Short second);

    public static void main(String args[]) {
        Number res;

        res = new SumAggregator().apply(1, 2, 3, 4);
        println(res.getClass(), res);

        res = new SumAggregator().apply(1D, 2D, 3D, 4D);
        println(res.getClass(), res);

        res = new SumAggregator().apply(1D, 2D, new Double(3), new Double(4));
        println(res.getClass(), res);

        res = SupportedType.FLOAT.apply(new SumAggregator(), 1F, 2F);
        println(res.getClass(), res);
    }
}
