package myUtils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;

import static myUtils.ConsolePrinting.println;
import static myUtils.Equivalence.Comparator;
import static myUtils.Equivalence.evaluate;
import static myUtils.Equivalence.eq;
import static myUtils.Equivalence.lt;
import static myUtils.Equivalence.gt;

public class BinaryHeap<E extends Comparable<? super E>> implements Iterable<E> {

    private final int MAX_SIZE = 1000;
    private E[] data;
    private int heapSize;
    private Comparator comp;
    private Class type;

    public <T extends E> BinaryHeap(Comparator comp, Class<T> type) {
        this.type = type;
        this.data = (T[]) Array.newInstance(this.type, MAX_SIZE);
        this.heapSize = 0;
        this.comp = comp;
    }

    public <T extends E> BinaryHeap(Comparator comp, Class<T> type, T... args) {
        this(comp, type);
        push(args);
    }

    public  <C extends Collection<E>> BinaryHeap(Comparator comp, Class<E> type, C args) {
        this(comp, type);
        push(args);
    }

    public <T extends E> BinaryHeap(Class<T> type) {
       this(lt, type);
    }

    public <T extends E> BinaryHeap(Class<T> type, T... args) {
       this(lt, type);
        push(args);
    }

    public  <C extends Collection<E>> BinaryHeap(Class<E> type, C args) {
       this(lt, type);
       push(args);
    }

    public E peek() {
        if (empty()) {
            throw new HeapException("Heap is empty");
        }
        return data[0];
    }

    public boolean empty() {
        return (this.heapSize == 0);
    }

    private int getLeftChildIndex(int nodeIndex) {
        return 2 * nodeIndex + 1;
    }

    private int getRightChildIndex(int nodeIndex) {
        return 2 * nodeIndex + 2;
    }

    private int getParentIndex(int nodeIndex) {
        return (nodeIndex - 1) / 2;
    }

    @Override
    public Iterator<E> iterator() {
        return new HeapIterator<E>(this.comp, this.type, this.data, this.heapSize);
    }

    public class HeapIterator<C extends E> implements Iterator {

        BinaryHeap<E> temp;
        HeapIterator(Comparator<E> comp, Class<E> type, E[] data, int heapSize) {
            this.temp = new BinaryHeap<>(comp, type ,Arrays.copyOfRange(data, 0, heapSize));
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

    public static class HeapException extends RuntimeException {
        public HeapException(String message) {
            super(message);
        }
    }

    public void push(E value) {
        if (this.heapSize == data.length) {
            throw new HeapException("Heap's underlying storage is overflow");
        }
        this.heapSize++;
        this.data[this.heapSize - 1] = value;
        siftUp(this.heapSize - 1);
    }

    public void push(E... values) {
        for(E elem : values) {
            push(elem);
        }
    }

    public <C extends Collection<E>> void push(C values) {
        for(E elem : values) {
            push(elem);
        }
    }

    private void siftUp(int nodeIndex) {
        int parentIndex;
        E tmp;
        if (nodeIndex != 0) {
            parentIndex = getParentIndex(nodeIndex);
            if (evaluate(this.comp, this.data[nodeIndex], this.data[parentIndex])) {
                tmp = this.data[parentIndex];
                this.data[parentIndex] = this.data[nodeIndex];
                this.data[nodeIndex] = tmp;
                siftUp(parentIndex);
            }
        }
    }

    public E pop() {
        E ret = data[0];
        if (empty()) {
            throw new HeapException("Heap is empty");
        }
        this.data[0] = this.data[this.heapSize - 1];
        this.heapSize--;
        if (this.heapSize > 0) {
            siftDown(0);
        }
        return ret;
    }

    private void siftDown(int nodeIndex) {
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
            if (evaluate(this.comp, this.data[leftChildIndex], this.data[rightChildIndex])
                    || evaluate(eq, this.data[leftChildIndex], this.data[rightChildIndex])) {
                minIndex = leftChildIndex;
            } else {
                minIndex = rightChildIndex;
            }
        }

        if (evaluate(this.comp, this.data[minIndex], this.data[nodeIndex])) {
            tmp = this.data[minIndex];
            this.data[minIndex] = this.data[nodeIndex];
            this.data[nodeIndex] = tmp;
            siftDown(minIndex);
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public static void main(String[] args) {
        BinaryHeap bh = new BinaryHeap(lt, Integer.class);
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

        bh = new BinaryHeap(gt, Character.class, 'a','b','c','z');
        println(bh);

        bh = new BinaryHeap<>(String.class, new HashSet(Arrays.asList("star", "alex", "bob")));
        println(bh);


        class test implements Comparable {
            private Integer val;
            test(int val) {
                this.val = val;
            }
            public int getVal() {
                return this.val;
            }

            @Override
            public int compareTo(Object o) {
                return -1 * this.val.compareTo(((test) o).getVal());
            }

            @Override
            public String toString() {
                return this.val +  "";
            }
        }

        bh = new BinaryHeap(test.class);
        bh.push(new test(9));
        bh.push(new test(10));
        bh.push(new test(11));
        println(bh);

    }
}
