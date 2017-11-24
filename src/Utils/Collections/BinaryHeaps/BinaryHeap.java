package Utils.Collections.BinaryHeaps;

import ATOC.Binary;
import Utils.Comparison;

import java.util.Arrays;
import java.util.Collection;

import static Utils.Comparison.lt;

/**
 * Created by robertseedorf on 9/9/17.
 */
public abstract class BinaryHeap<E extends Comparable<? super E>> {

    public static class HeapException extends RuntimeException {
        public HeapException(String message) { super(message); }
    }

    public static class EmptyHeapException extends HeapException {
        public EmptyHeapException(String message) {
            super(message);
        }
    }

    public static class FullHeapException extends HeapException {
        public FullHeapException(String message) {
            super(message);
        }
    }

    protected int maxSize = 1000;
    protected int heapSize = 0;
    protected Comparison.Comparator comp = lt;

    protected int getLeftChildIndex(int nodeIndex) {
        return 2 * nodeIndex + 1;
    }
    protected int getRightChildIndex(int nodeIndex) {
        return 2 * nodeIndex + 2;
    }
    protected int getParentIndex(int nodeIndex) {
        return (nodeIndex - 1) / 2;
    }

    protected abstract void siftUp(int nodeIndex);
    protected abstract void siftDown(int nodeIndex);
    public abstract boolean empty();
    public abstract void push(E value);
    public abstract E peek();
    public abstract E pop();

}
