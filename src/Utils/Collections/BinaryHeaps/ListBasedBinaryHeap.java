package Utils.Collections.BinaryHeaps;

import java.util.*;

public class ListBasedBinaryHeap<E> extends BinaryHeap {

    public class ListBasedHeapIterator<C extends E> implements Iterator {

        ListBasedBinaryHeap<C> temp;
        ListBasedHeapIterator(Comparator<C> comp, int heapSize, List<C> data) {
            this.temp = new ListBasedBinaryHeap(comp, data.subList(0, heapSize));
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

    protected List<E> elements;

    public ListBasedBinaryHeap() {
        super();
        this.elements = new ArrayList<>(this.maxSize);
    }

    public ListBasedBinaryHeap(int maxSize) {
        super(maxSize);
        this.elements = new ArrayList<>(maxSize);
    }

    public ListBasedBinaryHeap(Comparator comp) {
        super(comp);
        this.elements = new ArrayList<>(this.maxSize);
    }

    public ListBasedBinaryHeap(E... elems) {
        this.elements = new ArrayList<>(this.maxSize);
        pushAll(elems);
    }

    public <T extends Iterable<E>> ListBasedBinaryHeap(T elems) {
        this.elements = new ArrayList<>(this.maxSize);
        pushAll(elems);
    }

    public ListBasedBinaryHeap(Comparator comp, int maxSize) {
        super(comp, maxSize);
        this.elements = new ArrayList<>(this.maxSize);
    }

    public ListBasedBinaryHeap(Comparator comp, E... elems) {
        super(comp);
        this.elements = new ArrayList<>(this.maxSize);
        pushAll(elems);
    }

    public <T extends Iterable<E>> ListBasedBinaryHeap(Comparator comp, T elems) {
        super(comp);
        this.elements = new ArrayList<>(this.maxSize);
        pushAll(elems);
    }

    public E peek() {
        if (isEmpty()) {
            throw new HeapException("Heap is empty");
        }
        return elements.get(0);
    }

    public boolean isEmpty() {
        return (this.heapSize == 0);
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
        this.elements = new ArrayList<>();
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
        return new ListBasedHeapIterator(
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
