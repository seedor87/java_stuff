package Utils.Collections.BinaryHeaps;

import java.util.*;

public class ArrayBasedBinaryHeap<E> extends BinaryHeap {

    protected Object[] elements;

    public ArrayBasedBinaryHeap(int maxSize) {
        super(maxSize);
        this.elements = new Object[this.maxSize];
    }

    public ArrayBasedBinaryHeap(Comparator<E> comp) {
        super(comp);
        this.elements = new Object[this.maxSize];
    }

    public ArrayBasedBinaryHeap(E... elems) {
        this.elements = new Object[this.maxSize];
        pushAll(elems);
    }

    public <T extends Iterable<E>> ArrayBasedBinaryHeap(T elems) {
        this.elements = new Object[this.maxSize];
        pushAll(elems);
    }

    public ArrayBasedBinaryHeap(Comparator<E> comp, int maxSize) {
        super(comp, maxSize);
        this.elements = new Object[this.maxSize];
    }

    public ArrayBasedBinaryHeap(Comparator<E> comp, E... elems) {
        super(comp);
        this.elements = new Object[this.maxSize];
        pushAll(elems);
    }

    public <T extends Iterable<E>> ArrayBasedBinaryHeap(Comparator<E> comp, T elems) {
        super(comp);
        this.elements = new Object[this.maxSize];
        pushAll(elems);
    }

    public ArrayBasedBinaryHeap() {
        super();
        this.elements = new Object[this.maxSize];
    }


    public E peek() {
        if (isEmpty()) {
            throw new EmptyHeapException("Heap is empty");
        }
        return (E) elements[0];
    }

    public boolean isEmpty() {
        return (this.heapSize == 0);
    }

    @Override
    public void push(Object value) {
        if (this.heapSize == elements.length) {
            throw new FullHeapException("Heap's underlying storage is overflow");
        }
        this.heapSize++;
        this.elements[this.heapSize - 1] = value;
        siftUp(this.heapSize - 1);
    }

    protected void siftUp(int nodeIndex) {
        int parentIndex;
        E tmp;
        if (nodeIndex != 0) {
            parentIndex = getParentIndex(nodeIndex);
            if (this.comp.compare(this.elements[nodeIndex], this.elements[parentIndex]) > 0) {
                tmp = (E) this.elements[parentIndex];
                this.elements[parentIndex] = this.elements[nodeIndex];
                this.elements[nodeIndex] = tmp;
                siftUp(parentIndex);
            }
        }
    }

    public E pop() {
        if (isEmpty()) {
            throw new EmptyHeapException("Heap is empty");
        }
        E ret = (E) elements[0];
        this.elements[0] = this.elements[this.heapSize - 1];
        this.heapSize--;
        if (this.heapSize > 0) {
            siftDown(0);
        }
        return ret;
    }

    @Override
    public void clear() {
        this.elements = new Object[maxSize];
        this.heapSize = 0;
    }

    @Override
    public boolean contains(Object elem) {
        return lastIndexOf(elem) > -1;
    }

    @Override
    public int lastIndexOf(Object elem) {
        int i = -1;
        int index = 0;
        try {
            for(Object e : this) {
                if(elem.equals((E) e)) {
                    i = index;
                }
                index++;
            }
        } catch (EmptyHeapException ex) {}
        return i;
    }

    @Override
    protected Object[] getElements() {
        return this.elements;
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
            if ( this.comp.compare(this.elements[leftChildIndex], this.elements[rightChildIndex]) >= 0 ) {
                minIndex = leftChildIndex;
            } else {
                minIndex = rightChildIndex;
            }
        }

        if ( this.comp.compare(this.elements[minIndex], this.elements[nodeIndex]) > 0 ) {
            tmp = (E) this.elements[minIndex];
            this.elements[minIndex] = this.elements[nodeIndex];
            this.elements[nodeIndex] = tmp;
            siftDown(minIndex);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
