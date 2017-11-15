package Utils.Collections;

import java.util.*;

import static Utils.ConsolePrinting.*;

public class MapMap<T1 extends Object, T2 extends Object, E extends Object> implements Map{

    private Map<T1, Map<T2, E>> internal;
    public MapMap() {
        internal = new HashMap<>();
    }

    public MapMap(MapMap<T1, T2, E> mapMap) {
        this();
        this.putAll(mapMap);
    }

    public MapMap(Set<T1> set) {
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

    public void putAll(MapMap<T1, T2, E> m) {
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
        MapMap<Character, String, Integer> mapMap = new MapMap<>();
        mapMap.put('a', "first", 0);
        mapMap.put('a', "second", 1);
        mapMap.put('b', "third", 2);
        mapMap.put('c', "fourth", 3);

        Map<String, Integer> tempMap = new HashMap<>();
        tempMap.put("fifth", 4);
        tempMap.put("sixth", 5);
        mapMap.put('d', tempMap);

        MapMap<Character, String, Integer> tempMapMap = new MapMap<>();
        tempMapMap.put('e', "seventh", 6);
        tempMapMap.put('e', "eighth", 7);
        mapMap.putAll(tempMapMap);

        printlnDelim("\n", mapMap);

        mapMap.remove('e', "eighth");
        println(mapMap);

        println(mapMap.get('b', "third"));
        println(mapMap.get('b', "NA"));
        mapMap.remove('b', "third");
        println(mapMap);
        println(mapMap.get('b'));
        println(mapMap.get('b', "third"));

        mapMap.clear('e');
        println(mapMap);
    }
}
