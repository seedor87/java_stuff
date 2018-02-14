package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryHomogenousMapping;
import Utils.StreamUtils.Interfaces.NaryMapping;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;

public class IntVariadicSpliterator extends AbstractPrimitiveVariadicSpliterator<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt {

    public IntVariadicSpliterator(OfInt source, NaryHomogenousMapping<Integer> mapping, Process process) {
        super(source, mapping, process);
    }

    public IntVariadicSpliterator(OfInt source, NaryHomogenousMapping<Integer> mapping) {
        super(source, mapping);
    }

    @Override
    public OfInt getEmtpySpliterator() {
        return Spliterators.emptyIntSpliterator();
    }

    @Override
    public boolean actionAccept(IntConsumer action) {
        if(queue.size() >= transformationSize) {
            action.accept(mapping.execute(queue));
            return true;
        }
        return false;
    }
}