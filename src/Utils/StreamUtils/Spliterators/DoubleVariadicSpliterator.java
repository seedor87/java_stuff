package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.VariadicFunctionalInterfaces.*;

import java.util.*;
import java.util.Spliterator.OfDouble;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.DoubleConsumer;

public class DoubleVariadicSpliterator implements OfDouble {

    protected OfDouble source;
    protected Transformation<Double> condition;
    protected final AtomicBoolean found = new AtomicBoolean();
    private List<Double> list = new LinkedList<>();

    public DoubleVariadicSpliterator(OfDouble source, Transformation<Double> predicate) {
        this.source = source;
        this.condition = predicate;
    }

    public boolean actionAccept(DoubleConsumer action) {
        try {
            action.accept(condition.execute(list));
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
        return true;
    }


    public OfDouble getEmtpySpliterator() {
        return Spliterators.emptyDoubleSpliterator();
    }

    @Override
    public boolean tryAdvance(DoubleConsumer action) {
        return (this.getSource().tryAdvance((DoubleConsumer) (e) -> {
            list.add(e);
            if (this.actionAccept(action)) {
                list.remove(0);
            }
        }));
    }

    @Override
    public OfDouble trySplit() {
        OfDouble prefix = source.trySplit();
        if (prefix == null) {
            return null;
        }
        if (found.get()) {
            return this.getEmtpySpliterator();
        }
        DoubleVariadicSpliterator clone;
        try {
            clone = (DoubleVariadicSpliterator) clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
        clone.setSource(prefix);
        return clone;
    }

    public OfDouble getSource() {
        return source;
    }

    public void setSource(OfDouble source) {
        this.source = source;
    }

    @Override
    public long estimateSize() {
        return source.estimateSize();
    }

    @Override
    public int characteristics() {
        return source.characteristics();
    }

    @Override
    public Comparator<? super Double> getComparator() {
        return source.getComparator();
    }

}