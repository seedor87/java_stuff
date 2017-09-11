package Utils.Collections;

import Utils.Equivalence;

import java.util.*;

import static Utils.Equivalence.evaluate;
import static Utils.ConsolePrinting.*;
import static Utils.Equivalence.gt;

public class ListBasedBinaryHeap<E extends Comparable<? super E>> extends BinaryHeap{

    public class ListBasedHeapIterator<C extends E> implements Iterator {

        ListBasedBinaryHeap<C> temp;
        ListBasedHeapIterator(Equivalence.Comparator<C> comp, List<C> data) {
            this.temp = new ListBasedBinaryHeap().setComp(comp).setArgs(data);
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

    public <E extends Comparable<? super E>> ListBasedBinaryHeap() {
        this.elements = new ArrayList<>();
    }

    public ListBasedBinaryHeap<E> setSize(int maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public ListBasedBinaryHeap<E> setComp(Equivalence.Comparator comp) {
        this.comp = comp;
        return this;
    }

    public <T extends E> ListBasedBinaryHeap<E> setArgs(Collection<T> args) {
        for (T arg : args) {
            push(arg);
        }
        return this;
    }

    public <T extends E> ListBasedBinaryHeap<E> setArgs(T... args) {
        return this.setArgs(Arrays.asList(args));
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

    public void push(Comparable... values) {
        for(Comparable elem : values) {
            push(elem);
        }
    }

    @Override
    public void push(Iterable values) {
        for(Object elem : values) {
            push((E) elem);
        }
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
        return new ListBasedHeapIterator(
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

        ListBasedBinaryHeap bhi = new ListBasedBinaryHeap();
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
            } catch (EmptyHeapException ex) {
//                ex.printStackTrace();
                println();
                break;
            }
        }

        ListBasedBinaryHeap<Character> bhc = new ListBasedBinaryHeap().setComp(gt).setArgs('a','b','c','z');
        println(bhc);

        ListBasedBinaryHeap<String> bhs = new ListBasedBinaryHeap().setArgs(new HashSet(Arrays.asList("star", "alex", "bob")));
        println(bhs);

        bhs.setComp(gt);
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
