package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryHomogenousMapping;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.LongConsumer;

public class LongVariadicSpliterator extends PrimitiveVariadicSpliterator<Long, LongConsumer, Spliterator.OfLong> implements Spliterator.OfLong {

    public LongVariadicSpliterator(OfLong source, NaryHomogenousMapping<Long> mapping, Process process) {
        super(source, mapping, process);
    }

    public LongVariadicSpliterator(OfLong source, NaryHomogenousMapping<Long> mapping) {
        super(source, mapping);
    }

    @Override
    public OfLong getEmtpySpliterator() {
        return Spliterators.emptyLongSpliterator();
    }

    @Override
    public boolean actionAccept(LongConsumer action) {
        if(queue.size() >= transformationSize) {
            action.accept(mapping.execute(queue));
            return true;
        }
        return false;
    }
}
