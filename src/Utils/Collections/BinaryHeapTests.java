package Utils.Collections;

import Utils.MyRandom;
import Utils.Timers.AbstractTimer;
import Utils.Timers.SYSTimer;
import static Utils.ConsolePrinting.*;

public class BinaryHeapTests {

    public static void arrayBasedTest(int n, int max) {
        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();
        ArrayBasedBinaryHeap bh = new ArrayBasedBinaryHeap(Integer.class).setSize(n);
        Integer[] ints = MyRandom.randomInts(n, max);
        for(Integer i : ints) {
            bh.push(i);
            println(bh);
        }
        timer.stop();
        println(timer);
    }

    public static void listBasedTest(int n, int max) {
        AbstractTimer timer = new SYSTimer(AbstractTimer.TimeUnit.SECONDS);
        timer.start();
        ListBasedBinaryHeap<Integer> bh = new ListBasedBinaryHeap<Integer>(n);
        Integer[] ints = MyRandom.randomInts(n, max);
        for(Integer i : ints) {
            bh.push(i);
            println(bh);
        }
        timer.stop();
        println(timer);
    }

    public static void main(String args[]) {
//        listBasedTest(1000, 10000);
        arrayBasedTest(1000, 10000);
    }
}
