package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.VariadicFunctionalInterfaces.Transformation;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class IntVariadicSpliterator extends AbstractPrimitiveVariadicSpliterator<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt {

    public IntVariadicSpliterator(OfInt source, Transformation<Integer> transformation, Process process) {
        super(source, transformation, process);
    }

    public IntVariadicSpliterator(OfInt source, Transformation<Integer> transformation) {
        super(source, transformation);
    }

    @Override
    public OfInt getEmtpySpliterator() {
        return Spliterators.emptyIntSpliterator();
    }

    @Override
    public boolean actionAccept(IntConsumer action) {
        if(queue.size() >= transformationSize) {
            action.accept(transformation.execute(queue));
            return true;
        }
        return false;
    }
}