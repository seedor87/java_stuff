package Utils.Collections;

import java.util.*;

import static Utils.Console.Printing.*;

public class Table<T1 extends Object, T2 extends Object, E extends Object> implements Map{

    public Map<T1, Map<T2, E>> internal;
    public Table() {
        internal = new HashMap<>();
    }

    public Table(Table<T1, T2, E> table) {
        this();
        this.putAll(table);
    }

    public Table(Set<T1> set) {
        this();
        for(T1 elem : set) {
            this.put(elem);
        }
    }

    @Override
    public int size() {
        return internal.size();
    }

    public int size(T1 key) {
        return internal.get(key).size();
    }

    @Override
    public boolean isEmpty() {
        return internal.isEmpty();
    }

    public boolean isEmpty(T1 key) {
        return internal.get(key).isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        for(Entry<T1, Map<T2, E>> pair : internal.entrySet()) {
            if(pair.getValue().containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(T1 key1, T2 key2) {
        return internal.get(key1).containsKey(key2);
    }

    @Override
    public boolean containsValue(Object value) {
        for(Entry<T1, Map<T2, E>> pair : internal.entrySet()) {
            if(pair.getValue().containsValue(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(T1 key, E value) {
        return internal.get(key).containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return internal.get(key);
    }

    public E get(T1 key1, T2 key2) {
        return internal.get(key1).get(key2);
    }

    @Override
    public Object put(Object key, Object value) {
        return internal.put((T1) key, (Map<T2, E>) value);
    }

    public Object put(T1 key1, T2 key2, E value) {
        internal.putIfAbsent(key1, new HashMap<>());
        return internal.get(key1).putIfAbsent(key2, value);
    }

    public Object put(T1 key) {
        return internal.putIfAbsent(key, new HashMap<T2, E>());
    }

    @Override
    public Object remove(Object key) {
        return internal.remove(key);
    }

    @Override
    public boolean remove(Object key1, Object key2) {
        internal.get(key1).remove(key2);
        return true;
    }

    @Override
    public void putAll(Map m) {
        internal.putAll(m);
    }

    public void putAll(Table<T1, T2, E> m) {
        for (Entry<T1, Map<T2, E>> pair : m.entrySet()) {
            this.put(pair.getKey(), pair.getValue());
        }
    }

    @Override
    public void clear() {
        internal = new HashMap<>();
    }

    public void clear(T1 key) {
        this.put(key, new HashMap<T2, E>());
    }

    @Override
    public Set keySet() {
        return internal.keySet();
    }

    public Set keySet(T1 key) { return internal.get(key).keySet(); }

    @Override
    public Collection values() {
        return internal.values();
    }

    public Collection values(T1 key) { return internal.get(key).values(); }

    @Override
    public Set<Entry<T1, Map<T2, E>>> entrySet() {
        return internal.entrySet();
    }

    public Set<Entry<T2, E>> entrySet(T1 key) {
        return internal.get(key).entrySet();
    }

    public static void main(String[] args) {
        Table<Character, String, Integer> table = new Table<>();
        table.put('a', "first", 0);
        table.put('a', "second", 1);
        table.put('b', "third", 2);
        table.put('c', "fourth", 3);

        Map<String, Integer> tempMap = new HashMap<>();
        tempMap.put("fifth", 4);
        tempMap.put("sixth", 5);
        table.put('d', tempMap);

        Table<Character, String, Integer> tempTable = new Table<>();
        tempTable.put('e', "seventh", 6);
        tempTable.put('e', "eighth", 7);
        table.putAll(tempTable);

        printlnDelim("\n", table);

        table.remove('e', "eighth");
        println(table);

        println(table.get('b', "third"));
        println(table.get('b', "NA"));
        table.remove('b', "third");
        println(table);
        println(table.get('b'));
        println(table.get('b', "third"));

        table.clear('e');
        println(table);
    }
}
