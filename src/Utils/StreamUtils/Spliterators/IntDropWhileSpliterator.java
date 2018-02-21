package Utils.StreamUtils.Spliterators;

import Utils.StreamUtils.Interfaces.NaryPredicate;
import Utils.StreamUtils.Interfaces.UnaryPredicate;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static Utils.Console.Printing.println;

public class IntDropWhileSpliterator extends PrimitiveDropWhileSpliterator<Integer, IntConsumer, Spliterator.OfInt> implements Spliterator.OfInt {

    public IntDropWhileSpliterator(Spliterator.OfInt source, NaryPredicate<Integer> predicate) {
        super(source, predicate);
    }

    @Override
    public void actionAccept(IntConsumer action, Integer e) {
        if (!found.get()) {
            queue.add(e);
            if (!queueFilled.get()) {
                if (queue.size() < conditionSize) {
                    return;
                }
                queueFilled.set(true);
                if (!condition.execute(queue)) {
                    for (int i = 0; i < conditionSize - 1; i++) {
                        action.accept(queue.get(i));
                    }
                }
            }
            if (!condition.execute(queue)) {
                found.set(true);
                action.accept(e);
            }
            queue.remove(0);
            return;
        }
        action.accept(e);
    }

    @Override
    public OfInt getEmtpySpliterator() {
        return Spliterators.emptyIntSpliterator();
    }

    public static void main(String[] args) {
        println(StreamSupport.intStream(new IntDropWhileSpliterator(IntStream.range(0,10).spliterator(), (UnaryPredicate<Integer>) i -> i < 0), false).map(i -> i));
    }
}

