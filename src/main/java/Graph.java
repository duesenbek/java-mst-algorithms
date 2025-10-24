import java.util.*;

class Edge implements Comparable<Edge> {
    String from, to;
    int weight;
    
    public Edge(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
    
    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
    
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public int getWeight() { return weight; }
}

class Graph {
    int id;
    List<String> vertices;
    List<Edge> edges;
    Map<String, List<Edge>> adjacencyList;
    
    public Graph(int id, List<String> vertices, List<Edge> edges) {
        this.id = id;
        this.vertices = vertices;
        this.edges = edges;
        buildAdjacencyList();
    }
    
    private void buildAdjacencyList() {
        adjacencyList = new HashMap<>();
        for (String vertex : vertices) {
            adjacencyList.put(vertex, new ArrayList<>());
        }
        for (Edge edge : edges) {
            adjacencyList.get(edge.from).add(edge);
            adjacencyList.get(edge.to).add(new Edge(edge.to, edge.from, edge.weight));
        }
    }
    
    public int getId() { return id; }
    public List<String> getVertices() { return vertices; }
    public List<Edge> getEdges() { return edges; }
    public Map<String, List<Edge>> getAdjacencyList() { return adjacencyList; }
    public int getVertexCount() { return vertices.size(); }
    public int getEdgeCount() { return edges.size(); }
}

class MSTResult {
    List<Edge> mstEdges;
    int totalWeight;
    int operations;
    long executionTimeMs;
    boolean connected;
    
    public MSTResult(List<Edge> mstEdges, int totalWeight, int operations, long executionTimeMs, boolean connected) {
        this.mstEdges = mstEdges;
        this.totalWeight = totalWeight;
        this.operations = operations;
        this.executionTimeMs = executionTimeMs;
        this.connected = connected;
    }
    
    public List<Edge> getMstEdges() { return mstEdges; }
    public int getTotalWeight() { return totalWeight; }
    public int getOperations() { return operations; }
    public long getExecutionTimeMs() { return executionTimeMs; }
    public boolean isConnected() { return connected; }
}
