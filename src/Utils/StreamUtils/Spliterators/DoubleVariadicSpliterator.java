package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.MappingInterfaces.NaryHomogeneousMapping;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class DoubleVariadicSpliterator extends PrimitiveVariadicSpliterator<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble {

    public DoubleVariadicSpliterator(OfDouble source, NaryHomogeneousMapping<Double> mapping, Process process) {
        super(source, mapping, process);
    }

    public DoubleVariadicSpliterator(OfDouble source, NaryHomogeneousMapping<Double> mapping) {
        super(source, mapping);
    }

    @Override
    public OfDouble getEmtpySpliterator() {
        return Spliterators.emptyDoubleSpliterator();
    }

    @Override
    public boolean actionAccept(DoubleConsumer action) {
        if(queue.size() >= transformationSize) {
            action.accept(mapping.execute(queue));
            return true;
        }
        return false;
    }
}
