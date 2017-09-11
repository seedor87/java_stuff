package Utils.Collections.Graphs;

public class Edge<V extends Object>{

    public static final int INF = Integer.MAX_VALUE;
    private V vertex;
    private int cost;

    public Edge(V v, int c){
        vertex = v; cost = c;
    }

    public Edge(V v){
        this(v, Edge.INF);
    }

    public void setVertex(V vertex) {
        this.vertex = vertex;
    }

    public V getVertex() {
        return vertex;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Edge [vertex=" + vertex + ", cost=" + cost + "]";
    }

}