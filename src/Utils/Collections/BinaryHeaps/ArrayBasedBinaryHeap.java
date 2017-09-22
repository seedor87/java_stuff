package Utils.Collections.BinaryHeaps;

import Utils.Comparison;

import java.lang.reflect.Array;
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
                               Class<E> type,
                               int maxSize,
                               E[] data,
                               int heapSize) {
            this.temp = new ArrayBasedBinaryHeap(type)
                    .setSize(maxSize)
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

    protected E[] elements;
    protected Class type;

    public <E extends Comparable<? super E>> ArrayBasedBinaryHeap(Class<E> type) {
        this.type = type;
    }

    public ArrayBasedBinaryHeap setSize(int maxSize) {
        this.maxSize = maxSize;
        this.elements = (E[]) Array.newInstance(this.type, this.maxSize);
        return this;
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
                this.type,
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
        ArrayBasedBinaryHeap bh;

        bh = new ArrayBasedBinaryHeap(Integer.class).setSize(10000);
        bh.push(4);
        bh.push(2);
        bh.push(1);
        bh.push(5);
        bh.push(3);
        println(bh);
        bh.push(7, 8, 6);
        println(bh);
        bh.pop();
        bh.pop();
        println(bh);

        while(true) {
            try {
                print(bh.pop());
            } catch (HeapException ex) {
                println();
                break;
            }
        }

        bh = new ArrayBasedBinaryHeap(Character.class)
                .setSize(10000)
                .setComp(gt)
                .setArgs('a','b','c','z');
        println(bh);

        bh = new ArrayBasedBinaryHeap(String.class)
                .setSize(10000)
                .setArgs(new HashSet(Arrays.asList(
                        "star",
                        "alex",
                        "bob")));
        println(bh);

        bh.setComp(gt);
        println(bh);

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

        bh = new ArrayBasedBinaryHeap(MyClass.class)
                .setSize(10000).setArgs(
                        new MyClass(9),
                        new MyClass(10),
                        new MyClass(11)
                );
        println(bh);
    }
}
