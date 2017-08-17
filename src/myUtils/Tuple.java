package myUtils;

import java.util.*;

import static myUtils.ConsolePrinting.print;
import static myUtils.ConsolePrinting.println;
import static myUtils.ConsolePrinting.printlnDelim;

public class Tuple<T> implements Iterable<T>{

    private T[] composite;

    @SafeVarargs
    public <E extends T> Tuple(E... composite) {
        this.composite = composite;
    }

    public T getZero() { return getN(0); }

    public T getOne() { return getN(1); }

    public T getTwo() { return getN(2); }

    public T getN(int n) { return this.composite[n]; }

    public T[] getComposite() { return this.composite; }

    @Override
    public Iterator<T> iterator() {
        return (new LinkedList(Arrays.asList(this.composite))).iterator(); }

    @Override
    public String toString() {
        String ret = "";
        String delim = "{";
        for(T elem: composite) {
            ret += delim + elem;
            delim = ", ";
        }
        delim = (delim.equals("{")) ? "{}" : "}";
        ret+= delim;
        return ret;
    }

    public static void main(String[] args) {

        println("test 0");
        Tuple tuple0 = new Tuple(1, true, 'a', "test", new int[3]);
        printlnDelim("; ", tuple0);

        println("test 1");
//        Tuple<Integer> tuple = new Tuple<>("test");
        Tuple<Integer> tuple1 = new Tuple("test");
        println(tuple1);

        println("test 2");
        Character[] composite = new Character[]{'a','b','c'};
        // without param generic
        Tuple tuple2 = new Tuple<>(composite);
        println(tuple2.getComposite());
        // with generic
        Tuple<Character> tuple3 = new Tuple<>(composite);
        println(tuple3.getComposite());
    }
}
