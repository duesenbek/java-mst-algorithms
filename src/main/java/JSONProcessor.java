import java.io.*;
import java.nio.file.*;
import java.util.*;
import org.json.*;

public class JSONProcessor {

    public static List<Graph> readGraphsFromFile(String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return parseGraphs(content);
    }

    public static List<Graph> parseGraphs(String jsonContent) {
        List<Graph> graphs = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonContent);
            JSONArray graphsArray = jsonObject.getJSONArray("graphs");

            for (int i = 0; i < graphsArray.length(); i++) {
                JSONObject graphObj = graphsArray.getJSONObject(i);

                int id = graphObj.getInt("id");

                List<String> nodes = new ArrayList<>();
                JSONArray nodesArray = graphObj.getJSONArray("nodes");
                for (int j = 0; j < nodesArray.length(); j++) {
                    nodes.add(nodesArray.getString(j));
                }

                List<Edge> edges = new ArrayList<>();
                JSONArray edgesArray = graphObj.getJSONArray("edges");
                for (int j = 0; j < edgesArray.length(); j++) {
                    JSONObject edgeObj = edgesArray.getJSONObject(j);
                    String from = edgeObj.getString("from");
                    String to = edgeObj.getString("to");
                    int weight = edgeObj.getInt("weight");
                    edges.add(new Edge(from, to, weight));
                }

                graphs.add(new Graph(id, nodes, edges));
            }
        } catch (JSONException e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage());
        }

        return graphs;
    }

    public static void writeResultsToFile(List<Graph> graphs, MSTResult[] primResults,
                                        MSTResult[] kruskalResults, String filename) throws IOException {
        JSONObject outputJson = new JSONObject();
        JSONArray resultsArray = new JSONArray();

        for (int i = 0; i < graphs.size(); i++) {
            Graph graph = graphs.get(i);
            MSTResult prim = primResults[i];
            MSTResult kruskal = kruskalResults[i];

            JSONObject resultObj = new JSONObject();
            resultObj.put("graph_id", graph.getId());

            JSONObject inputStats = new JSONObject();
            inputStats.put("vertices", graph.getVertexCount());
            inputStats.put("edges", graph.getEdgeCount());
            resultObj.put("input_stats", inputStats);

            resultObj.put("prim", createAlgorithmResult(prim));

            resultObj.put("kruskal", createAlgorithmResult(kruskal));

            resultsArray.put(resultObj);
        }

        outputJson.put("results", resultsArray);

        Files.write(Paths.get(filename), outputJson.toString(2).getBytes());
    }

    private static JSONObject createAlgorithmResult(MSTResult result) {
        JSONObject algoResult = new JSONObject();

        JSONArray edgesArray = new JSONArray();
        for (Edge edge : result.getMstEdges()) {
            JSONObject edgeObj = new JSONObject();
            edgeObj.put("from", edge.getFrom());
            edgeObj.put("to", edge.getTo());
            edgeObj.put("weight", edge.getWeight());
            edgesArray.put(edgeObj);
        }

        algoResult.put("mst_edges", edgesArray);
        algoResult.put("total_cost", result.getTotalWeight());
        algoResult.put("operations_count", result.getOperations());
        algoResult.put("execution_time_ms", result.getExecutionTimeMs());
        algoResult.put("connected", result.isConnected());
        
        return algoResult;
    }

    public static void createSampleInputFile(String filename) throws IOException {
        JSONObject sampleJson = new JSONObject();
        JSONArray graphsArray = new JSONArray();

        JSONObject graph1 = new JSONObject();
        graph1.put("id", 1);
        graph1.put("nodes", new JSONArray(Arrays.asList("A", "B", "C", "D", "E")));

        JSONArray edges1 = new JSONArray();
        edges1.put(createEdge("A", "B", 4));
        edges1.put(createEdge("A", "C", 3));
        edges1.put(createEdge("B", "C", 2));
        edges1.put(createEdge("B", "D", 5));
        edges1.put(createEdge("C", "D", 7));
        edges1.put(createEdge("C", "E", 8));
        edges1.put(createEdge("D", "E", 6));
        graph1.put("edges", edges1);

        JSONObject graph2 = new JSONObject();
        graph2.put("id", 2);
        graph2.put("nodes", new JSONArray(Arrays.asList("A", "B", "C", "D")));

        JSONArray edges2 = new JSONArray();
        edges2.put(createEdge("A", "B", 1));
        edges2.put(createEdge("A", "C", 4));
        edges2.put(createEdge("B", "C", 2));
        edges2.put(createEdge("C", "D", 3));
        edges2.put(createEdge("B", "D", 5));
        graph2.put("edges", edges2);

        graphsArray.put(graph1);
        graphsArray.put(graph2);
        sampleJson.put("graphs", graphsArray);

        Files.write(Paths.get(filename), sampleJson.toString(2).getBytes());
        System.out.println("Created sample input file: " + filename);
    }

    private static JSONObject createEdge(String from, String to, int weight) {
        JSONObject edge = new JSONObject();
        edge.put("from", from);
        edge.put("to", to);
        edge.put("weight", weight);
        return edge;
    }
}
