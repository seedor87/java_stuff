package Utils.Collections.BinaryHeaps;

import Utils.Comparison;

import java.util.*;

import static Utils.Comparison.evaluate;

public class ArrayBasedBinaryHeap<E extends Comparable<? super E>> extends BinaryHeap {

    public class ArrayBasedHeapIterator<E extends Comparable<? super E>> implements Iterator {

        ArrayBasedBinaryHeap<E> temp;
        ArrayBasedHeapIterator(Comparison.BinaryComparator<E> comp,
                               int heapSize,
                               E[] data) {
            this.temp = new ArrayBasedBinaryHeap(comp, Arrays.copyOfRange(data, 0, heapSize));
        }

        @Override
        public boolean hasNext() {
            try {
                this.temp.peek();
            } catch (EmptyHeapException ex) {
                return false;
            }
            return true;
        }

        @Override
        public E next() {
            return this.temp.pop();
        }
    }

    protected Comparable[] elements;

    public ArrayBasedBinaryHeap(int maxSize) {
        super(maxSize);
        this.elements = new Comparable[this.maxSize];
    }

    public ArrayBasedBinaryHeap(Comparison.BinaryComparator comp) {
        super(comp);
        this.elements = new Comparable[this.maxSize];
    }

    public ArrayBasedBinaryHeap(E... elems) {
        this.elements = new Comparable[this.maxSize];
        pushAll(elems);
    }

    public <T extends Iterable<E>> ArrayBasedBinaryHeap(T elems) {
        this.elements = new Comparable[this.maxSize];
        pushAll(elems);
    }

    public ArrayBasedBinaryHeap(Comparison.BinaryComparator comp, int maxSize) {
        super(comp, maxSize);
        this.elements = new Comparable[this.maxSize];
    }

    public ArrayBasedBinaryHeap(Comparison.BinaryComparator comp, E... elems) {
        super(comp);
        this.elements = new Comparable[this.maxSize];
        pushAll(elems);
    }

    public <T extends Iterable<E>> ArrayBasedBinaryHeap(Comparison.BinaryComparator comp, T elems) {
        super(comp);
        this.elements = new Comparable[this.maxSize];
        pushAll(elems);
    }

    public ArrayBasedBinaryHeap() {
        super();
        this.elements = new Comparable[this.maxSize];
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
    public void push(Comparable value) {
        if (this.heapSize == elements.length) {
            throw new FullHeapException("Heap's underlying storage is overflow");
        }
        this.heapSize++;
        this.elements[this.heapSize - 1] = (E) value;
        siftUp(this.heapSize - 1);
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayBasedHeapIterator(
                this.comp,
                this.heapSize,
                this.elements
        );
    }

    protected void siftUp(int nodeIndex) {
        int parentIndex;
        E tmp;
        if (nodeIndex != 0) {
            parentIndex = getParentIndex(nodeIndex);
            if (evaluate(this.comp, this.elements[nodeIndex], this.elements[parentIndex])) {
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
        this.elements = new Comparable[maxSize];
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
            if (evaluate(this.comp, this.elements[leftChildIndex], this.elements[rightChildIndex])
                    || evaluate(Comparison.eq, this.elements[leftChildIndex], this.elements[rightChildIndex])) {
                minIndex = leftChildIndex;
            } else {
                minIndex = rightChildIndex;
            }
        }

        if (evaluate(this.comp, this.elements[minIndex], this.elements[nodeIndex])) {
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
