import java.util.*;

public class MSTAlgorithm {

    public static MSTResult primMST(Graph graph) {
        long startTime = System.nanoTime();
        int operationsCount = 0;

        if (graph.getVertices().isEmpty()) {
            long executionTime = (System.nanoTime() - startTime) / 1_000_000;
            return new MSTResult(new ArrayList<>(), 0, operationsCount, executionTime, true);
        }

        List<Edge> mstEdges = new ArrayList<>();
        int totalWeight = 0;
        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>();

        String startVertex = graph.getVertices().get(0);
        visited.add(startVertex);
        operationsCount++;

        minHeap.addAll(graph.getAdjacencyList().get(startVertex));
        operationsCount += graph.getAdjacencyList().get(startVertex).size();

        while (!minHeap.isEmpty() && visited.size() < graph.getVertexCount()) {
            Edge currentEdge = minHeap.poll();
            operationsCount++;

            String currentVertex = currentEdge.getTo();

            if (visited.contains(currentVertex)) {
                continue;
            }

            visited.add(currentVertex);
            mstEdges.add(currentEdge);
            totalWeight += currentEdge.getWeight();
            operationsCount += 3;

            for (Edge edge : graph.getAdjacencyList().get(currentVertex)) {
                if (!visited.contains(edge.getTo())) {
                    minHeap.add(edge);
                    operationsCount++;
                }
            }
        }

        long executionTime = (System.nanoTime() - startTime) / 1_000_000;
        boolean connected = visited.size() == graph.getVertexCount();
        return new MSTResult(mstEdges, totalWeight, operationsCount, executionTime, connected);
    }

    public static MSTResult kruskalMST(Graph graph) {
        long startTime = System.nanoTime();
        int operationsCount = 0;

        List<Edge> mstEdges = new ArrayList<>();
        int totalWeight = 0;

        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        Collections.sort(sortedEdges);
        operationsCount += sortedEdges.size() * (int)(Math.log(sortedEdges.size()) / Math.log(2));

        Map<String, Integer> vertexToIndex = new HashMap<>();
        for (int i = 0; i < graph.getVertices().size(); i++) {
            vertexToIndex.put(graph.getVertices().get(i), i);
            operationsCount++;
        }

        DSU dsu = new DSU(graph.getVertexCount());

        for (Edge edge : sortedEdges) {
            int u = vertexToIndex.get(edge.getFrom());
            int v = vertexToIndex.get(edge.getTo());

            if (dsu.union(u, v)) {
                mstEdges.add(edge);
                totalWeight += edge.getWeight();
                operationsCount += 2;
            }
            operationsCount++;

            if (mstEdges.size() == graph.getVertexCount() - 1) {
                break;
            }
        }

        operationsCount += dsu.getOperationsCount();
        long executionTime = (System.nanoTime() - startTime) / 1_000_000;
        boolean connected = graph.getVertexCount() == 0 || mstEdges.size() == graph.getVertexCount() - 1;
        return new MSTResult(mstEdges, totalWeight, operationsCount, executionTime, connected);
    }

    public static void compareAlgorithms(Graph graph) {
        System.out.println("=== Graph " + graph.getId() + " ===");
        System.out.println("Vertices: " + graph.getVertexCount() + ", Edges: " + graph.getEdgeCount());

        MSTResult primResult = primMST(graph);
        MSTResult kruskalResult = kruskalMST(graph);

        System.out.println("Prim - Cost: " + primResult.getTotalWeight() +
                         ", Time: " + primResult.getExecutionTimeMs() + "ms" +
                         ", Operations: " + primResult.getOperations());

        System.out.println("Kruskal - Cost: " + kruskalResult.getTotalWeight() +
                         ", Time: " + kruskalResult.getExecutionTimeMs() + "ms" +
                         ", Operations: " + kruskalResult.getOperations());

        if (primResult.getTotalWeight() == kruskalResult.getTotalWeight()) {
            System.out.println("✓ MST costs are identical");
        } else {
            System.out.println("✗ ERROR: MST costs differ!");
        }

        System.out.println();
    }
}
