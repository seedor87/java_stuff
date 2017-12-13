package Utils.Collections.BinaryHeaps;

import java.util.*;

public class LinkedListBasedBinaryHeap<E> extends BinaryHeap {

    protected LinkedList<E> elements;

    public LinkedListBasedBinaryHeap(int maxSize) {
        super(maxSize);
        this.elements = new LinkedList<>();
    }

    public LinkedListBasedBinaryHeap(Comparator<E> comp) {
        super(comp);
        this.elements = new LinkedList<>();
    }

    public LinkedListBasedBinaryHeap(E... elems) {
        this.elements = new LinkedList<>();
        pushAll(elems);
    }

    public <T extends Iterable<E>> LinkedListBasedBinaryHeap(T elems) {
        this.elements = new LinkedList<>();
        pushAll(elems);
    }

    public LinkedListBasedBinaryHeap(Comparator<E> comp, int maxSize) {
        super(comp, maxSize);
        this.elements = new LinkedList<>();
    }

    public LinkedListBasedBinaryHeap(Comparator<E> comp, E... elems) {
        super(comp);
        this.elements = new LinkedList<>();
        pushAll(elems);
    }

    public <T extends Iterable<E>> LinkedListBasedBinaryHeap(Comparator<E> comp, T elems) {
        super(comp);
        this.elements = new LinkedList<>();
        pushAll(elems);
    }

    public E peek() {
        if (isEmpty()) {
            throw new HeapException("Heap is empty");
        }
        return elements.get(0);
    }

    public boolean isEmpty() {
        return this.heapSize == 0;
    }

    public static class HeapException extends RuntimeException {
        public HeapException(String message) {
            super(message);
        }
    }

    public void push(Object value) {
        if (this.heapSize == this.maxSize) {
            throw new FullHeapException("Heap's underlying storage is overflow");
        }
        this.heapSize++;
        this.elements.add((E) value);
        siftUp(this.heapSize - 1);
    }

    protected void siftUp(int nodeIndex) {
        int parentIndex;
        E tmp;
        if (nodeIndex != 0) {
            parentIndex = getParentIndex(nodeIndex);
            if (this.comp.compare(this.elements.get(nodeIndex), this.elements.get(parentIndex)) > 0) {
                tmp = this.elements.get(parentIndex);
                this.elements.set(parentIndex, this.elements.get(nodeIndex));
                this.elements.set(nodeIndex, tmp);
                siftUp(parentIndex);
            }
        }
    }

    public E pop() {
        if (isEmpty()) {
            throw new EmptyHeapException("Heap is empty");
        }
        E ret = elements.get(0);
        this.elements.set(0, this.elements.get(this.heapSize - 1));
        this.heapSize--;
        if (this.heapSize > 0) {
            siftDown(0);
        }
        return ret;
    }

    @Override
    public void clear() {
        this.elements = new LinkedList<>();
        this.heapSize = 0;
    }

    @Override
    public boolean contains(Object elem) {
        int i = 0;
        while(i < heapSize) {
            if (elem.equals(elements.get(i))) {
                return true;
            }
            i++;
        }
        return false;
    }

    @Override
    public int lastIndexOf(Object elem) {
        int ret = -1;
        int i = 0;
        while(i < heapSize) {
            if (elem.equals(elements.get(i))) {
                ret = i;
            }
            i++;
        }
        return ret;
    }

    @Override
    protected Object[] getElements() {
        return this.elements.toArray();
    }

    @Override
    public Iterator iterator() {
        return new HeapIterator(
                this.comp,
                this.heapSize,
                this.elements.toArray());
    }

    protected void siftDown(int nodeIndex) {
        int leftChildIndex, rightChildIndex, minIndex;
        E tmp;
        leftChildIndex = getLeftChildIndex(nodeIndex);
        rightChildIndex = getRightChildIndex(nodeIndex);
        if (rightChildIndex >= this.heapSize) {
            if (leftChildIndex >= this.heapSize) {
                return;
            } else {
                minIndex = leftChildIndex;
            }
        } else {
            if ( this.comp.compare(this.elements.get(leftChildIndex), this.elements.get(rightChildIndex)) >= 0 ) {
                minIndex = leftChildIndex;
            } else {
                minIndex = rightChildIndex;
            }
        }

        if ( this.comp.compare(this.elements.get(minIndex), this.elements.get(nodeIndex)) > 0 ) {
            tmp = this.elements.get(minIndex);
            this.elements.set(minIndex, this.elements.get(nodeIndex));
            this.elements.set(nodeIndex, tmp);
            siftDown(minIndex);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
