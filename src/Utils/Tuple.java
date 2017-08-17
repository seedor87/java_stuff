package Utils;

import java.util.*;

public class Tuple<T extends Object> {

    private List<T> components;

    public Tuple(T... composite) {
        components = new ArrayList<>(Arrays.asList(composite));
    }

    public Tuple(Collection<T> composite) {
        components = new ArrayList<>(composite);
    }

    public T getZero() {
        return this.components.get(0);
    }

    public T getOne() {
        return this.components.get(1);
    }

    public T getN(int n) {
        return this.components.get(n);
    }

    public List<T> getComponents() {
        return this.components;
    }

    @Override
    public String toString() {
        return this.components.toString();
    }

    public static void main(String[] args) {
        Tuple tuple0 = new Tuple(1, true, 'a', "test");
        ConsolePrinting.println(tuple0.toString());

        Tuple<Integer> tuple1 = new Tuple("test");
        ConsolePrinting.println(tuple1.toString());

        Tuple tuple2 = new Tuple(new ArrayList<>(Arrays.asList('a','b','c')));
        ConsolePrinting.println(tuple2.toString());
    }
}
