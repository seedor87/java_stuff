package Utils.Collections.BinaryHeaps;

import Utils.Comparison;

import java.util.*;

import static Utils.Comparison.evaluate;

public class LinkedListBasedBinaryHeap<E extends Comparable<? super E>> extends BinaryHeap {

    public class LinkedListBasedHeapIterator<C extends E> implements Iterator {

        LinkedListBasedBinaryHeap<C> temp;
        LinkedListBasedHeapIterator(Comparison.BinaryComparator<C> comp, int heapSize, LinkedList<C> data) {
            this.temp = new LinkedListBasedBinaryHeap(comp, data.subList(0, heapSize));
        }

        @Override
        public boolean hasNext() {
            try {
                this.temp.peek();
            } catch (HeapException ex) {
                return false;
            }
            return true;
        }

        @Override
        public E next() {
            return this.temp.pop();
        }
    }

    protected LinkedList<E> elements;

    public LinkedListBasedBinaryHeap(int maxSize) {
        super(maxSize);
        this.elements = new LinkedList<>();
    }

    public LinkedListBasedBinaryHeap(Comparison.BinaryComparator comp) {
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

    public LinkedListBasedBinaryHeap(Comparison.BinaryComparator comp, int maxSize) {
        super(comp, maxSize);
        this.elements = new LinkedList<>();
    }

    public LinkedListBasedBinaryHeap(Comparison.BinaryComparator comp, E... elems) {
        super(comp);
        this.elements = new LinkedList<>();
        pushAll(elems);
    }

    public <T extends Iterable<E>> LinkedListBasedBinaryHeap(Comparison.BinaryComparator comp, T elems) {
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

    public void push(Comparable value) {
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
            if (evaluate(this.comp, this.elements.get(nodeIndex), this.elements.get(parentIndex))) {
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
    public Iterator iterator() {
        return new LinkedListBasedHeapIterator(
                this.comp,
                this.heapSize,
                this.elements);
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
            if (evaluate(this.comp, this.elements.get(leftChildIndex), this.elements.get(rightChildIndex))
                    || evaluate(Comparison.eq, this.elements.get(leftChildIndex), this.elements.get(rightChildIndex))) {
                minIndex = leftChildIndex;
            } else {
                minIndex = rightChildIndex;
            }
        }

        if (evaluate(this.comp, this.elements.get(minIndex), this.elements.get(nodeIndex))) {
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
