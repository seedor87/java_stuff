package Utils.Collections.BinaryHeaps;

import java.util.*;

import static Utils.ConsolePrinting.print;
import static Utils.ConsolePrinting.println;

/**
 * Created by robertseedorf on 9/9/17.
 */
public abstract class BinaryHeap<E> implements Iterable<E> {

    public static Comparator lt = (Object o1, Object o2) -> ((Comparable) o1).compareTo((Comparable) o2);
    public static Comparator gt = (Object o1, Object o2) -> ((Comparable) o2).compareTo((Comparable) o1);

    private final int DEFAULT_MAX_SIZE = 1000;  // Default value assigned to the size of internal collection
    private final Comparator<E> DEFAULT_COMPARATOR = gt;  // default comparator for heapification maintenance

    protected int maxSize;  // limit of max size to heap
    protected int heapSize; // tracks of current heap size
    protected Comparator<E> comp; // current comparator

    public class HeapIterator<E> implements Iterator {

        BinaryHeap<E> temp;
        HeapIterator(Comparator<E> comp,
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

        @Override
        public void remove() { this.temp.pop(); }
    }

    @Override
    public Iterator<E> iterator() {
        return new HeapIterator(
                this.comp,
                this.heapSize,
                this.getElements()
        );
    }

    /**
     * The Custom Exception for further heap exception extension
     */
    public static class HeapException extends RuntimeException {
        public HeapException(String message) { super(message); }
    }

    /**
     * The custom exception for Empty heap case
     */
    public static class EmptyHeapException extends HeapException {
        public EmptyHeapException(String message) {
            super(message);
        }
    }

    /**
     * The custom exception for Full heap
     */
    public static class FullHeapException extends HeapException {
        public FullHeapException(String message) {
            super(message);
        }
    }

    /**
     * Default constructor to build heap with default params
     */
    protected BinaryHeap() {
        this.maxSize = DEFAULT_MAX_SIZE;
        this.heapSize = 0;
        this.comp = DEFAULT_COMPARATOR;
    }

    /**
     * Constructor to build heap with specified comparator
     */
    protected BinaryHeap(Comparator comp) {
        this();
        this.comp = comp;
    }

    /**
     * constructor to build binary heap with specified max size
     */
    protected BinaryHeap(int maxSize) {
        this();
        this.maxSize = maxSize;
    }

    /**
     * constructor to build binary heap with specified comparator and max size
     */
    protected BinaryHeap(Comparator comp, int maxSize) {
        this();
        this.maxSize = maxSize;
        this.comp = comp;
    }

    /**
     * heapify up method
     */
    protected abstract void siftUp(int nodeIndex);

    /**
     * heapify down method
     */
    protected abstract void siftDown(int nodeIndex);

    /**
     * returns true heap has no elements, else false
     */
    public abstract boolean isEmpty();

    /**
     * inserts element in sorted order onto the heap
     */
    public abstract void push(E value);

    /**
     * inspects heap for top item
     */
    public abstract E peek();

    /**
     * removes item from top of heap
     */
    public abstract E pop();

    /**
     * clears all the elements on the heap by call to popAll()
     */
    public abstract void clear();

    /**
     * returns true if heap contains element
     */
    public abstract boolean contains(Object elem);

    /**
     * returns last index of specified element, -1 if heap does not contain elem
     */
    public abstract int lastIndexOf(Object elem);

    /**
     * returns the Array of the elements foe iteration
     */
    protected abstract E[] getElements();

    /**
     * return the current size of the heap
     */
    public int size() {
        return this.heapSize;
    }

    /**
     * returns index of left child of element at given index
     */
    protected int getLeftChildIndex(int nodeIndex) {
        return 2 * nodeIndex + 1;
    }

    /**
     * returns index of right child of element at given index
     */
    protected int getRightChildIndex(int nodeIndex) {
        return 2 * nodeIndex + 2;
    }

    /**
     * returns index of parent node from given index
     */
    protected int getParentIndex(int nodeIndex) {
        return (nodeIndex - 1) / 2;
    }

    /**
     * sets the comparator to that specified, DOES NOT SORT when comparator is updated
     */
    protected void setComp(Comparator comp) {
        this.comp = comp;
    }

    /**
     * sorts the list based on the installed comparator
     */
    protected void sort() {
        if (this.heapSize > 0) {
            siftDown(0);
        }
    }

    /**
     * First sets the installed comparator to that specified and then sorts the list based on the specified comparator
     */
    protected void sort(Comparator comp) {
        this.setComp(comp);
        sort();
    }

    /**
     * inserts all the specified elements into the heap
     */
    public void pushAll(E... elems) {
        pushAll(Arrays.asList(elems));
    }

    /**
     * inserts all the specified elements into the heap
     */
    public <T extends Iterable<E>> void pushAll(T elems) {
        for(E elem : elems) {
            push(elem);
        }
    }

    /**
     * pops the number of elements equal to the number specified by parameter
     */
    public E[] pop(int count) {
        Object[] ret = new Object[count];
        int i = 0;
        while(true) {
            try {
                if (i >= count) {
                    break;
                }
                ret[i] = this.pop();
                i++;
            } catch (HeapException ex) {
                break;
            }
        }
        return (E[]) ret;
    }

    /**
     * pops all elements from the heap
     */
    public E[] popAll() {
        Object[] ret = new Object[this.heapSize];
        int i = 0;
        while(true) {
            try {
                ret[i] = this.pop();
                i++;
            } catch (HeapException ex) {
                break;
            }
        }
        return (E[]) ret;
    }

    /**
     * pops all elements from the heap until an element is reached that matches the specified parameter
     * (pops all elements up to, but not including, the given element is reached)
     */
    public E[] popTill(E to) {
        List<E> ret = new ArrayList<E>();
        while(true) {
            try {
                if (this.peek().equals(to)) {
                    break;
                }
                ret.add(this.pop());
            } catch (HeapException ex) {
                break;
            }
        }
        return (E[]) ret.toArray(new Comparable[ret.size()]);
    }

    /**
     * returns the elements of the heap as an Object[]
     */
    public Object[] toArray() {
        Comparable[] ret = new Comparable[this.heapSize];
        return toArray(ret);
    }

    /**
     * inserts all the elements of the heap into the specified Array.
     * If the Array is not a fit size for the heap's elements a new one is made that is at least the right size required and that is used to store heap
     */
    public <T extends Comparable<E>> T[] toArray(T[] arr) {
        List<E> ret = new ArrayList<>();
        for(E e : this) {
            ret.add(e);
        }
        arr = ret.toArray(arr);
        return arr;
    }

    public static void main(String[] args) {

        BinaryHeap<Integer> bhi;
        bhi = new ArrayBasedBinaryHeap(5);
//        bhi = new ListBasedBinaryHeap<>(5);
//        bhi = new LinkedListBasedBinaryHeap<>(5);

        BinaryHeap<Character> bhc = new ListBasedBinaryHeap();
        bhc.push('a');
        println(bhc);

        bhi.push(8);
        bhi.push(6);
        bhi.push(1);
        bhi.push(5);
        bhi.push(3);
        println(bhi);

        println();
        for(Integer i : new Integer[]{0,1,2,3,4,5,6,7,8,9}) {
            println("[" + i + "] :", bhi.lastIndexOf(i), bhi.contains(i));
        }

        while(true) {
            try {
                bhi.pushAll(7, 2, 4, 0);
                break;
            } catch (FullHeapException ex) {
                println("\nheap too small, retrying...");
                BinaryHeap<Integer> temp;
                temp = new ArrayBasedBinaryHeap<>((int) ((bhi.size() + 1) * 1.5));
                temp.pushAll(bhi);
                bhi = temp;
            }
        }
        println(bhi);

        println("\nbhi.pop(); bhi.pop()");
        bhi.pop(); bhi.pop();
        println(bhi);

        print("\nbhi: ");
        for (int i = 0; i < bhi.size(); i++) {
            print("~" + bhi.toArray()[i] + "~ ");
        }
        println();

        Comparable[] ints;

        ints = new Integer[9];
        ints = bhi.toArray(ints);
        println("\nbhi.toArray(ints) :", ints, "bhi.len:", ints.length);

        ints = new Integer[4];
        ints = bhi.toArray(ints);
        println("\nbhi.toArray(ints) :", ints, "bhi.len:", ints.length);

        println("\nbhi.popTill(4) :", bhi.popTill(4));
        ints = bhi.toArray(new Integer[0]);
        println("bhi:", ints, "bhi.len:", ints.length);

        bhi.pushAll(1,3,9);
        print("\nbhi.pushAll(1,3,9) -> ");
        println("bhi:", bhi.toArray());

        println("\nbhi.pop(4) :", bhi.pop(4));
        println("bhi :", bhi.toArray());

        println("\nbhi.popAll() : ", bhi.popAll());
        println("bhi :", bhi.toArray());

        println("\n");

        Comparator<MyClass<Integer, String>> keyComp = Comparator.comparingInt(MyClass::getKey);
        keyComp = keyComp.reversed();

        Comparator<MyClass<Integer, String>> valComp = Comparator.comparing(MyClass::getVal);
        valComp = valComp.reversed();

        Comparator<MyClass<Integer, String>> lenComp = (MyClass<Integer, String> m1, MyClass<Integer, String> m2) -> (Integer.compare(m1.getVal().length(), m2.getVal().length()));

        Comparator<MyClass<Integer, String>> modComp = new Comparator<MyClass<Integer, String>>() {
            @Override
            public int compare(MyClass<Integer, String> o1, MyClass<Integer, String> o2) {
                if(o1.getKey() % 2 == 0 && o2.getKey() % 2 == 0) {
                    if(o1.getKey() > o2.getKey()) {
                        return 1;
                    } else {
                        return -1;
                    }
                } else if(o1.getKey() % 2 == 0) {
                    return 1;
                } else if(o2.getKey() % 2 == 0) {
                    return -1;
                }
                if(o1.getKey() > o2.getKey()) {
                    return 1;
                } else if (o1.getKey() < o2.getKey()){
                    return -1;
                }
                return 0;
            }
        };

        BinaryHeap<MyClass<Integer, String>> bhm;
//        bhm = new ArrayBasedBinaryHeap(keyComp);
//        bhm = new ListBasedBinaryHeap(keyComp);
        bhm = new LinkedListBasedBinaryHeap(keyComp);

        bhm.pushAll(
                new MyClass<>(0, "Zero"),
                new MyClass<>(1, "One"),
                new MyClass<>(2, "Two"),
                new MyClass<>(3, "Three"),
                new MyClass<>(4, "Four"),
                new MyClass<>(5, "Five"),
                new MyClass<>(6, "Six"),
                new MyClass<>(7, "Seven"),
                new MyClass<>(8, "Eight"),
                new MyClass<>(9, "Nine"),
                new MyClass<>(10, "Ten"),
                new MyClass<>(11, "Eleven")
        );
        println(bhm);

        bhm.sort(valComp);
        println(bhm);

        bhm.sort(lenComp);
        println(bhm);

        bhm.sort(modComp);
        println(bhm);
    }

    private static class MyClass<K extends Comparable, V extends Object> {
        private K key;
        private V val;
        MyClass(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return this.key;
        }

        public V getVal() {
            return this.val;
        }

        @Override
        public String toString() {
            return "<" + this.getKey() + " : " + this.getVal() + ">";
        }
    }
}
