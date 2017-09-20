package Utils.Collections;

import java.lang.reflect.Array;
import java.util.*;

import static Utils.ConsolePrinting.println;
import static Utils.Equivalence.eq;
import static Utils.Equivalence.neq;

public class Tuple<T> implements Iterable<T>{

    private T[] composite;
    public int length;

    @SafeVarargs
    public <E extends T> Tuple(E... composite) {
        this.composite = composite;
        this.length = composite.length;
    }

    public T getZero() { return get(0); }

    public T getOne() { return get(1); }

    public T getTwo() { return get(2); }

    public T get(int n) { return this.composite[n]; }

    public T[] toArray() { return this.composite; }

    @Override
    public Iterator<T> iterator() {
        return (new LinkedList(Arrays.asList(this.composite))).iterator();
    }

    @Override
    public boolean equals(Object obj) {
        Tuple temp = (Tuple) obj;
        if(this.length != temp.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if(!eq(this.get(i), temp.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {

        println("MyClass 0");
        Tuple tuple0 = new Tuple(2112, true, 'a', "MyClass", new int[3]);
        println(tuple0);

        println("MyClass 1");
//        Tuple<Integer> tuple = new Tuple<>("MyClass");
        Tuple<Integer> tuple1 = new Tuple("MyClass", "yep");
        println(tuple1);

        println("MyClass 2");
        Character[] composite = new Character[]{'a','b','c'};
        println(composite);
        // without param generic
        Tuple tuple2 = new Tuple<>(composite);
        println(tuple2);
        // with generic
        Tuple<Character> tuple3 = new Tuple<>(composite);
        println(tuple3);

        println("MyClass 3");
        Tuple<Object> tt = new Tuple(1, 'a', "abc", true, new long[5], new Tuple(99, 'a', 3.14));
        int zero = (int) tt.getZero();
        char one = (char) tt.getOne();
        String two = (String) tt.getTwo();
        boolean three = (boolean) tt.get(3);
        long[] four = (long[]) tt.get(4);
        Tuple five = (Tuple) tt.get(5);
        println(zero, one, two, three, four, five);
        println(tt);

        println(tuple0.equals(tuple1));
        println(tuple0.equals(tuple0));
        println(tuple0.equals(new Tuple(2112, true, 'a', "MyClass", new int[3])));
        println(tt.equals(new Tuple(1, 'a', "abc", true, new long[5], new Tuple(99, 'a', 3.14))));

        Map<Tuple, Integer> myMap = new HashMap<>();
        myMap.put(tt, 2);
        println(myMap);
        myMap.put(tt, 1);
        myMap.put(tt, 1);
        println(myMap);
        myMap.put(tt, 2);
        println(myMap);

        for(int i = 2; i > 0; i--) {
            myMap.put(tt, myMap.get(tt)-1);
            println(myMap);
        }
    }
}
