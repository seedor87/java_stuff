package Utils.Collections;

import Utils.Equivalence;

import java.lang.reflect.Array;
import java.util.*;

import static Utils.Equivalence.evaluate;
import static Utils.ConsolePrinting.*;
import static Utils.Equivalence.lt;

public class ListBasedBinaryHeap<E extends Comparable<? super E>> implements Iterable{

    private final int MAX_SIZE = 10000;
    private List<E> elements;
    private int heapSize;
    private Equivalence.Comparator comp;

    public ListBasedBinaryHeap(int size) {
        this(lt, size);
    }

    public ListBasedBinaryHeap(Equivalence.Comparator comp, int size) {
        this.elements = new ArrayList<>(size);
        this.heapSize = 0;
        this.comp = comp;
    }

    public <T extends E> ListBasedBinaryHeap(T... data) {
        this(lt, Arrays.asList(data));
    }

    public <T extends E> ListBasedBinaryHeap(Equivalence.Comparator comp, T... data) {
        this(comp, Arrays.asList(data));
    }

    public <T extends E> ListBasedBinaryHeap(Collection<T> data) {
        this(lt, data);
    }

    public <T extends E> ListBasedBinaryHeap(Equivalence.Comparator comp, Collection<T> data) {
        this.elements = new ArrayList<>();
        this.heapSize = 0;
        this.comp = comp;
        for (T e : data ) {
            push(e);
        }
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
        return new HeapIterator<E>(this.comp, this.elements);
    }

    public class HeapIterator<C extends E> implements Iterator {

        ListBasedBinaryHeap<C> temp;
        HeapIterator(Equivalence.Comparator<C> comp, List<C> data) {
            this.temp = new ListBasedBinaryHeap(comp, data);
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
        if (this.heapSize == MAX_SIZE) {
            throw new HeapException("Heap's underlying storage is overflow");
        }
        this.heapSize++;
        this.elements.add(value);
        siftUp(this.heapSize - 1);
    }

    public void push(E... values) {
        for(E elem : values) {
            push(elem);
        }
    }

    public <C extends Iterable<? extends E>> void push(C values) {
        for(E elem : values) {
            push(elem);
        }
    }

    private void siftUp(int nodeIndex) {
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
            throw new HeapException("Heap is empty");
        }
        this.elements.set(0, this.elements.get(this.heapSize - 1));
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
            if (evaluate(this.comp, this.elements.get(leftChildIndex), this.elements.get(rightChildIndex))
                    || evaluate(Equivalence.eq, this.elements.get(leftChildIndex), this.elements.get(rightChildIndex))) {
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

        ListBasedBinaryHeap bhi = new ListBasedBinaryHeap(lt);
        bhi.push(4);
        bhi.push(2);
        bhi.push(1);
        bhi.push(5);
        bhi.push(3);
        println(bhi);
        bhi.push(7, 8, 6);
        println(bhi);

        while(true) {
            try {
                print(bhi.pop() + " ");
            } catch (HeapException ex) {
                ex.printStackTrace();
                println();
                break;
            }
        }

        ListBasedBinaryHeap<Character> bhc = new ListBasedBinaryHeap<Character>(Equivalence.gt, 'a','b','c','z');
        println(bhc);

        ListBasedBinaryHeap<String> bhs = new ListBasedBinaryHeap(new HashSet(Arrays.asList("star", "alex", "bob")));
        println(bhs);


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

        ListBasedBinaryHeap bht = new ListBasedBinaryHeap();
        bht.push(new test(9));
        bht.push(new test(10));
        bht.push(new test(11));
        println(bht);
    }
}
