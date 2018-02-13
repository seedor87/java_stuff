package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.VariadicFunctionalInterfaces.Transformation;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class LongVariadicSpliterator extends AbstractPrimitiveVariadicSpliterator<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {

    public LongVariadicSpliterator(OfLong source, Transformation<Long> transformation) {
        super(source, transformation);
    }

    @Override
    public OfLong getEmtpySpliterator() {
        return Spliterators.emptyLongSpliterator();
    }

    @Override
    public boolean actionAccept(LongConsumer action) {
        if(queue.size() >= transformationSize) {
            action.accept(transformation.execute(queue));
            return true;
        }
        return false;
    }
}
