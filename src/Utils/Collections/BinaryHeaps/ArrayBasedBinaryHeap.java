package Utils.Collections.BinaryHeaps;

import Utils.Comparison;

import java.util.*;

import static Utils.ConsolePrinting.print;
import static Utils.ConsolePrinting.println;
import static Utils.Comparison.evaluate;
import static Utils.Comparison.gt;
import static Utils.Comparison.lt;

public class ArrayBasedBinaryHeap<E extends Comparable<? super E>> extends BinaryHeap implements Iterable<E> {

    public class ArrayBasedHeapIterator<E extends Comparable<? super E>> implements Iterator {

        ArrayBasedBinaryHeap<E> temp;
        ArrayBasedHeapIterator(Comparison.Comparator<E> comp,
                               int maxSize,
                               E[] data,
                               int heapSize) {
            this.temp = new ArrayBasedBinaryHeap(maxSize)
                    .setComp(comp)
                    .setArgs(Arrays.copyOfRange(data, 0, heapSize));
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

    public <E extends Comparable<? super E>> ArrayBasedBinaryHeap(int maxSize) {
        this.maxSize = maxSize;
        this.elements = new Comparable[this.maxSize];
    }
    public <E extends Comparable<? super E>> ArrayBasedBinaryHeap() {
        this.elements = new Comparable[this.maxSize];
    }

    public ArrayBasedBinaryHeap setComp(Comparison.Comparator comp) {
        this.comp = comp;
        return this;
    }

    public <T extends E> ArrayBasedBinaryHeap setArgs(Collection<T> args) {
        for (T arg : args) {
            push(arg);
        }
        return this;
    }

    public <T extends E> ArrayBasedBinaryHeap setArgs(T... args) {
        return this.setArgs(Arrays.asList(args));
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

    public void push(Comparable... values) {
        for(Comparable elem : values) {
            push(elem);
        }
    }

    public void push(Iterable values) {
        for(Object elem : values) {
            push((Comparable) elem);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayBasedHeapIterator(
                this.comp,
                this.maxSize,
                (E[]) this.elements,
                this.heapSize
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
        bhi.push(7, 2, 4);
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

        ArrayBasedBinaryHeap<Character> bhc = new ArrayBasedBinaryHeap()
                .setComp(gt)
                .setArgs('a','b','c','z');
        println(bhc);

        ArrayBasedBinaryHeap<String> bhs = new ArrayBasedBinaryHeap()
                .setArgs(new HashSet(Arrays.asList(
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

        ArrayBasedBinaryHeap<MyClass<Integer>> bhm = new ArrayBasedBinaryHeap()
                .setArgs(
                        new MyClass(9),
                        new MyClass(10),
                        new MyClass(11)
                );
        println(bhm);

        bhm.setComp(gt);
        println(bhm);
    }
}
