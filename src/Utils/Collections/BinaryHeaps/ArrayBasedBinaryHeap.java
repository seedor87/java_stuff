package Utils.Collections.BinaryHeaps;

import Utils.Comparison;

import java.util.*;

import static Utils.ConsolePrinting.print;
import static Utils.ConsolePrinting.println;
import static Utils.Comparison.evaluate;
import static Utils.Comparison.gt;

public class ArrayBasedBinaryHeap<E extends Comparable<? super E>> extends BinaryHeap implements Iterable<E> {

    public class ArrayBasedHeapIterator<E extends Comparable<? super E>> implements Iterator {

        ArrayBasedBinaryHeap<E> temp;
        ArrayBasedHeapIterator(Comparison.Comparator<E> comp,
                               int heapSize,
                               E[] data) {
            this.temp = new ArrayBasedBinaryHeap(comp, heapSize, Arrays.copyOfRange(data, 0, heapSize));
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

    public ArrayBasedBinaryHeap(Comparison.Comparator comp) {
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

    public ArrayBasedBinaryHeap(Comparison.Comparator comp, int maxSize) {
        super(comp, maxSize);
        this.elements = new Comparable[this.maxSize];
    }

    public ArrayBasedBinaryHeap(Comparison.Comparator comp, int maxSize, E... elems) {
        super(comp, maxSize);
        this.elements = new Comparable[this.maxSize];
        pushAll(elems);
    }

    public <T extends Iterable<E>> ArrayBasedBinaryHeap(Comparison.Comparator comp, int maxSize, T elems) {
        super(comp, maxSize);
        this.elements = new Comparable[this.maxSize];
        pushAll(elems);
    }

    public ArrayBasedBinaryHeap() {
        super();
        this.elements = new Comparable[this.maxSize];
    }


    public E peek() {
        if (empty()) {
            throw new EmptyHeapException("Heap is empty");
        }
        return (E) elements[0];
    }

    public boolean empty() {
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
        E ret = (E) elements[0];
        if (empty()) {
            throw new EmptyHeapException("Heap is empty");
        }
        this.elements[0] = this.elements[this.heapSize - 1];
        this.heapSize--;
        if (this.heapSize > 0) {
            siftDown(0);
        }
        return ret;
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

    public static void main(String[] args) {

        ArrayBasedBinaryHeap<Integer> bhi = new ArrayBasedBinaryHeap();
        bhi.push(8);
        bhi.push(6);
        bhi.push(1);
        bhi.push(5);
        bhi.push(3);
        println(bhi);
        bhi.pushAll(7, 2, 4);
        println(bhi);
        bhi.pop();
        bhi.pop();
        println(bhi);

        while(true) {
            try {
                print(bhi.pop(), " ");
            } catch (HeapException ex) {
                println();
                break;
            }
        }

        ArrayBasedBinaryHeap<Character> bhc = new ArrayBasedBinaryHeap('a','b','c','z');
        println(bhc);

        ArrayBasedBinaryHeap<String> bhs = new ArrayBasedBinaryHeap(new HashSet(Arrays.asList(
                        "star",
                        "alex",
                        "bob")));
        println(bhs);

        bhs.setComp(gt);
        println(bhs);

        class MyClass<E extends Object> implements Comparable<MyClass> {
            private E val;
            MyClass(E val) {
                this.val = val;
            }
            public E getVal() {
                return this.val;
            }

            @Override
            public String toString() {
                return this.val +  "";
            }

            @Override
            public int compareTo(MyClass o) {
                return -1 * ((Comparable) this.val).compareTo(o.val);
            }
        }

        ArrayBasedBinaryHeap<MyClass<Integer>> bhm = new ArrayBasedBinaryHeap(
                        new MyClass(9),
                        new MyClass(10),
                        new MyClass(11)
                );
        println(bhm);

        bhm.setComp(gt);
        println(bhm);
    }
}
