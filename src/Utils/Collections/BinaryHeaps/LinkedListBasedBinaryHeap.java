package Utils.Collections.BinaryHeaps;

import Utils.Comparison;

import java.util.*;

import static Utils.Comparison.evaluate;
import static Utils.ConsolePrinting.*;
import static Utils.Comparison.gt;

public class LinkedListBasedBinaryHeap<E extends Comparable<? super E>> extends BinaryHeap implements Iterable<E>{

    public class LinkedListBasedHeapIterator<C extends E> implements Iterator {

        LinkedListBasedBinaryHeap<C> temp;
        LinkedListBasedHeapIterator(Comparison.Comparator<C> comp, LinkedList<C> data) {
            this.temp = new LinkedListBasedBinaryHeap(comp, data.size(), data);
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

    public LinkedListBasedBinaryHeap(Comparison.Comparator comp) {
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

    public LinkedListBasedBinaryHeap(Comparison.Comparator comp, int maxSize) {
        super(comp, maxSize);
        this.elements = new LinkedList<>();
    }

    public LinkedListBasedBinaryHeap(Comparison.Comparator comp, int maxSize, E... elems) {
        super(comp, maxSize);
        this.elements = new LinkedList<>();
        pushAll(elems);
    }

    public <T extends Iterable<E>> LinkedListBasedBinaryHeap(Comparison.Comparator comp, int maxSize, T elems) {
        super(comp, maxSize);
        this.elements = new LinkedList<>();
        pushAll(elems);
    }

    public E peek() {
        if (empty()) {
            throw new HeapException("Heap is empty");
        }
        return elements.get(0);
    }

    public boolean empty() {
        return (this.heapSize == 0);
    }

    public static class HeapException extends RuntimeException {
        public HeapException(String message) {
            super(message);
        }
    }

    public void push(Comparable value) {
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
        E ret = elements.get(0);
        if (empty()) {
            throw new EmptyHeapException("Heap is empty");
        }
        this.elements.set(0, this.elements.get(this.heapSize - 1));
        this.heapSize--;
        if (this.heapSize > 0) {
            siftDown(0);
        }
        return ret;
    }

    @Override
    public Iterator iterator() {
        return new LinkedListBasedHeapIterator(
                this.comp,
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

    public static void main(String[] args) {

        LinkedListBasedBinaryHeap bhi = new LinkedListBasedBinaryHeap();
        bhi.push(4);
        bhi.push(2);
        bhi.push(1);
        bhi.push(5);
        bhi.push(3);
        println(bhi);
        bhi.pushAll(7, 8, 6);
        println(bhi);

        while(true) {
            try {
                print(bhi.pop() + " ");
            } catch (EmptyHeapException ex) {
                println();
                break;
            }
        }

        LinkedListBasedBinaryHeap<Character> bhc = new LinkedListBasedBinaryHeap<>(gt, 1000, 'a','b','c','z');
        println(bhc);

        LinkedListBasedBinaryHeap<String> bhs = new LinkedListBasedBinaryHeap<>(Arrays.asList("star", "alex", "bob"));
        println(bhs);

        bhs.setComp(gt);
        println(bhs);


        class MyClass implements Comparable {
            private Integer val;
            MyClass(int val) {
                this.val = val;
            }
            public int getVal() {
                return this.val;
            }

            @Override
            public int compareTo(Object o) {
                return -1 * this.val.compareTo(((MyClass) o).getVal());
            }

            @Override
            public String toString() {
                return this.val +  "";
            }
        }

        LinkedListBasedBinaryHeap bht = new LinkedListBasedBinaryHeap();
        bht.push(new MyClass(9));
        bht.push(new MyClass(10));
        bht.push(new MyClass(11));
        println(bht);
    }
}
