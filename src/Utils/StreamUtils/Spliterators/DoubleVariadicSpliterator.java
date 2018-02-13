package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.VariadicFunctionalInterfaces.Transformation;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.DoubleConsumer;

public class DoubleVariadicSpliterator extends AbstractPrimitiveVariadicSpliterator<Double, DoubleConsumer, Spliterator.OfDouble> implements Spliterator.OfDouble {

    public DoubleVariadicSpliterator(OfDouble source, Transformation<Double> transformation, Process process) {
        super(source, transformation, process);
    }

    public DoubleVariadicSpliterator(OfDouble source, Transformation<Double> transformation) {
        super(source, transformation);
    }

    @Override
    public OfDouble getEmtpySpliterator() {
        return Spliterators.emptyDoubleSpliterator();
    }

    @Override
    public boolean actionAccept(DoubleConsumer action) {
        if(queue.size() >= transformationSize) {
            action.accept(transformation.execute(queue));
            return true;
        }
        return false;
    }
}
