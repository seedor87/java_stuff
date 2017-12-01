package Utils.Collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static Utils.ConsolePrinting.*;

public class StaQ<E extends Object> implements Iterable{

    public class StackIterator implements Iterator{
        StaQ<E> temp = new StaQ<>();
        StackIterator(List<E> elems) {
            for(E elem : elems) {
                temp.push(elem);
            }
        }

        @Override
        public boolean hasNext() {
            return !temp.empty();
        }

        @Override
        public E next() {
            return temp.dequeue();
        }
    }

    List<E> elements;

    public StaQ() {
        elements = new LinkedList<>();
    }

    public void push(E elem) {
        elements.add(elem);
    }

    public E pop() {
        return elements.remove(elements.size()-1);
    }

    public E peek() {
        return elements.get(elements.size()-1);
    }

    public void enqueue(E elem) {
        elements.add(0, elem);
    }

    public E dequeue() {
        return elements.remove(0);
    }

    public E poll() {
        return elements.get(0);
    }

    public boolean empty() {
        return elements.size() == 0;
    }

    @Override
    public Iterator iterator() {
        return new StackIterator(this.elements);
    }

    public static void main(String args[]) {

        Arrays.stream(new int[] {1, 2, 3})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(Utils.ConsolePrinting::println);  // 5.0

        StaQ<Integer> s = new StaQ<>();
        s.push(5);
        s.push(10);
        s.push(15);
        s.enqueue(0);
        println(s);
        s.dequeue();
        s.dequeue();
        println(s.poll() == 10);
        s.pop();
        println(s);
    }
}
