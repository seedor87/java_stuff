package Utils.Collections.Graphs;

import Utils.Collections.StaQ;

import java.util.*;

import static Utils.ConsolePrinting.println;

/**
 * source:
 * https://stackoverflow.com/questions/19757371/directed-graph-in-java
 *
 * @param <V>
 */
public class DirectedGraph<V> {

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

    public List<V> dfs(V vertex) {
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
                        staQ.push(v);
                    }
                }
            }
        }
        return ret;
    }

    public List<V> bfs(V vertex) {
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
                        staQ.enqueue(v);
                    }
                }
            }
        }
        return ret;
    }

    public static void main(String[] args) {

        DirectedGraph<Integer> graph = new DirectedGraph<>();
        graph.add(0, 1, 1);
        graph.add(0, new Edge<Integer>(2,2));
        graph.add(2, 3, 2);
        graph.add(3, 0, 2);
        graph.add(1, 3, 1);
        graph.add(0, 3, 3);
        graph.add(2, 1, 5);
        graph.add(5,5, 0);

        println("The nr. of vertices is:", graph.neighbors.keySet().size());
        println("The nr. of edges is:", graph.getNumberOfEdges()); // to be fixed
        println("The current graph:", graph);
        println("In-degrees for 0:", graph.inDegree(0));
        println("Out-degrees for 0:", graph.outDegree(0));
        println("In-degrees for 3:", graph.inDegree(3));
        println("Out-degrees for 3:", graph.outDegree(3));
        println("Outbounds for 1:", graph.outboundNeighbors(1));
        println("Inbounds for 1:", graph.inboundNeighbors(1));
        println("(0,2)?", (graph.isEdge(0, 2) ? "It's an edge" : "It's not an edge"));
        println("(1,3)?", (graph.isEdge(1, 3) ? "It's an edge" : "It's not an edge"));

        println("Cost for (1,3)?", graph.getCost(1, 3));

        println(graph.dfs(3));
        println(graph.bfs(3));
    }
}