import java.util.*;

public class MSTAlgorithm {

    public static MSTResult primMST(Graph graph) {
        long startTime = System.nanoTime();
        int operationsCount = 0;

        if (graph.getVertices().isEmpty()) {
            long executionTime = (System.nanoTime() - startTime) / 1_000_000;
            return new MSTResult(new ArrayList<>(), 0, operationsCount, executionTime);
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
        return new MSTResult(mstEdges, totalWeight, operationsCount, executionTime);
    }
}
