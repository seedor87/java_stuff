package Utils.Collections.Graphs;

import Utils.Collections.StaQ;
import javafx.util.Pair;

import java.util.*;

import static Utils.ConsolePrinting.println;
import static Utils.Equivocation.difference;

/**
 * source:
 * https://stackoverflow.com/questions/19757371/directed-graph-in-java
 *
 * @param <V>
 */
public class DirectedGraph<V extends Comparable> {

    /**
     * A Map is used to map each vertex to its list of adjacent vertices.
     */
    private Map<V, List<Edge<V>>> neighbors = new HashMap<>();
    private int nr_edges;

    /**
     * String representation of graph.
     */
    public String toString() {
        StringBuffer s = new StringBuffer();
        for (V v : neighbors.keySet())
            s.append("\n    " + v + " -> " + neighbors.get(v));
        return s.toString();
    }

    /**
     * Add a vertex to the graph. Nothing happens if vertex is already in graph.
     */
    public void add(V vertex) {
        if (neighbors.containsKey(vertex))
            return;
        neighbors.put(vertex, new ArrayList<Edge<V>>());
    }

    public int getNumberOfEdges(){
        int sum = 0;
        for(List<Edge<V>> outBounds : neighbors.values()){
            sum += outBounds.size();
        }
        return sum;
    }

    /**
     * True iff graph contains vertex.
     */
    public boolean contains(V vertex) {
        return neighbors.containsKey(vertex);
    }

    /**
     * Add an edge to the graph; if either vertex does not exist, it's added.
     * This implementation allows the creation of multi-edges and self-loops.
     */
    public void add(V from, V to, int cost) {
        this.add(from);
        this.add(to);
        neighbors.get(from).add(new Edge<V>(to, cost));
    }

    public void add(V from , Edge<V> to) {
        add(from, to.getVertex(), to.getCost());
    }

    public int outDegree(V vertex) {
        return neighbors.get(vertex).size();
    }

    public int inDegree(V vertex) {
        return inboundNeighbors(vertex).size();
    }

    public List<V> outboundNeighbors(V vertex) {
        List<V> list = new ArrayList<>();
        for(Edge<V> e: neighbors.get(vertex))
            list.add(e.getVertex());
        return list;
    }

    public List<V> inboundNeighbors(V inboundVertex) {
        List<V> inList = new ArrayList<V>();
        for (V to : neighbors.keySet()) {
            for (Edge e : neighbors.get(to))
                if (e.getVertex().equals(inboundVertex))
                    inList.add(to);
        }
        return inList;
    }

    public boolean isEdge(V from, V to) {
        for(Edge<V> e :  neighbors.get(from)){
            if(e.getVertex().equals(to))
                return true;
        }
        return false;
    }

    public int getCost(V from, V to) {
        for(Edge<V> e :  neighbors.get(from)){
            if(e.getVertex().equals(to))
                return e.getCost();
        }
        return -1;
    }

    interface slamb<V> {
        void search(StaQ<V> s, V v);
    }

    private static slamb dfs = (StaQ s, Object v) -> s.push(v);
    private static slamb bfs = (StaQ s, Object v) -> s.enqueue(v);

    public List<V> search(slamb lam, V vertex) {
        List<V> ret = new ArrayList<>();
        Set<V> visited = new HashSet<>();
        StaQ<V> staQ = new StaQ<>();
        staQ.push(vertex);
        while (!staQ.empty()) {
            vertex = staQ.pop();
            if(!visited.contains(vertex)) {
                visited.add(vertex);
                ret.add(vertex);
                for(V v : outboundNeighbors(vertex)) {
                    if(!visited.contains(v)) {
                        lam.search(staQ, v);
                    }
                }
            }
        }
        return ret;
    }

    public List<V> shortestPaths(V start, V goal) {
        List<V> ret = new ArrayList<>();
        StaQ<Pair<V, List<V>>> staQ = new StaQ<>();
        staQ.push(new Pair<>(start, new ArrayList<>(Arrays.asList(start))));
        while (!staQ.empty()) {
            Pair<V, List<V>> p = staQ.pop();
            V vertex = p.getKey();
            List<V> path = p.getValue();
            for (V ver : difference(outboundNeighbors(vertex), path)) {
                if (ver.equals(goal)) {
                    ret.addAll(path);
                    ret.add(ver);
                    return ret;
                } else {
                    List<V> pat = new ArrayList<>();
                    pat.addAll(path);
                    pat.add(ver);
                    staQ.push(new Pair<>(ver, pat));
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {

        DirectedGraph<Integer> graphi = new DirectedGraph<>();
        graphi.add(0, 1, 1);
        graphi.add(0, new Edge<Integer>(2,2));
        graphi.add(2, 3, 2);
        graphi.add(3, 0, 2);
        graphi.add(1, 3, 1);
        graphi.add(3, 2, 3);
        graphi.add(2, 1, 5);
        graphi.add(5,5, 0);

        println("The nr. of vertices is:", graphi.neighbors.keySet().size());
        println("The nr. of edges is:", graphi.getNumberOfEdges()); // to be fixed
        println("The current graph:", graphi);
        println("In-degrees for 0:", graphi.inDegree(0));
        println("Out-degrees for 0:", graphi.outDegree(0));
        println("In-degrees for 3:", graphi.inDegree(3));
        println("Out-degrees for 3:", graphi.outDegree(3));
        println("Outbounds for 1:", graphi.outboundNeighbors(1));
        println("Inbounds for 1:", graphi.inboundNeighbors(1));
        println("(0,2)?", (graphi.isEdge(0, 2) ? "It's an edge" : "It's not an edge"));
        println("(1,3)?", (graphi.isEdge(1, 3) ? "It's an edge" : "It's not an edge"));

        println("Cost for (1,3)?", graphi.getCost(1, 3));


        DirectedGraph<Character> graphc = new DirectedGraph<>();
        graphc.add('A', 'B', 0);
        graphc.add('A', 'C', 0);
        graphc.add('B', 'A', 0);
        graphc.add('B', 'D', 0);
        graphc.add('B', 'E', 0);
        graphc.add('C', 'A', 0);
        graphc.add('C', 'F', 0);
        graphc.add('D', 'B', 0);
        graphc.add('E', 'B', 0);
        graphc.add('E', 'F', 0);
        graphc.add('F', 'C', 0);
        graphc.add('F', 'C', 0);
        graphc.add('F', 'E', 0);

        println(graphc.search(dfs, 'A'));
        println(graphc.search(bfs, 'A'));

        println(graphc.shortestPaths('A','F'));
    }
}