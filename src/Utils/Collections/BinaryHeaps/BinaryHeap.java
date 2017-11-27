package Utils.Collections.BinaryHeaps;

import Utils.Comparison;

import java.util.Arrays;

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

    private final int DEFAULT_MAX_SIZE = 1000;
    private final Comparison.Comparator DEFAULT_COMPARATOR = lt;

    protected int maxSize;
    protected int heapSize;
    protected Comparison.Comparator comp;

    protected BinaryHeap() {
        this.maxSize = DEFAULT_MAX_SIZE;
        this.heapSize = 0;
        this.comp = DEFAULT_COMPARATOR;
    }

    protected BinaryHeap(Comparison.Comparator comp) {
        this();
        this.comp = comp;
    }

    protected BinaryHeap(int maxSize) {
        this();
        this.maxSize = maxSize;
    }

    protected BinaryHeap(Comparison.Comparator comp, int maxSize) {
        this();
        this.maxSize = maxSize;
        this.comp = comp;
    }

    protected void setComp(Comparison.Comparator comp) {
        this.comp = comp;
    }

    public void pushAll(E... elems) {
        pushAll(Arrays.asList(elems));
    }

    public <T extends Iterable<E>> void pushAll(T elems) {
        for(E elem : elems) {
            push(elem);
        }
    }

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
