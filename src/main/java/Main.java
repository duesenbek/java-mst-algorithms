import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== Java MST Algorithms - City Road Optimization ===");
            System.out.println("Loading graphs and calculating Minimum Spanning Trees...\n");

            File inputFile = new File("input.json");
            if (!inputFile.exists()) {
                System.out.println("Creating sample input file...");
                JSONProcessor.createSampleInputFile("input.json");
            }

            List<Graph> graphs = JSONProcessor.readGraphsFromFile("input.json");
            System.out.println("✓ Loaded " + graphs.size() + " graphs from input.json");

            MSTResult[] primResults = new MSTResult[graphs.size()];
            MSTResult[] kruskalResults = new MSTResult[graphs.size()];

            System.out.println("\n=== Processing Graphs ===");
            for (int i = 0; i < graphs.size(); i++) {
                Graph graph = graphs.get(i);
                System.out.println("\n--- Graph " + graph.getId() + " ---");
                System.out.println("Vertices: " + graph.getVertexCount() + ", Edges: " + graph.getEdgeCount());

                long startTime = System.nanoTime();
                primResults[i] = MSTAlgorithm.primMST(graph);
                long primTime = System.nanoTime() - startTime;

                startTime = System.nanoTime();
                kruskalResults[i] = MSTAlgorithm.kruskalMST(graph);
                long kruskalTime = System.nanoTime() - startTime;

                printAlgorithmResult("Prim", primResults[i]);
                printAlgorithmResult("Kruskal", kruskalResults[i]);

                if (primResults[i].getTotalWeight() == kruskalResults[i].getTotalWeight()) {
                    System.out.println("✓ CORRECT: Both algorithms found MST with cost " + primResults[i].getTotalWeight());
                } else {
                    System.out.println("✗ ERROR: MST costs differ! Prim: " + primResults[i].getTotalWeight() +
                                     ", Kruskal: " + kruskalResults[i].getTotalWeight());
                }

                System.out.println("Performance comparison:");
                System.out.println("  Prim was " + String.format("%.2f", (double)kruskalTime/primTime) + "x faster than Kruskal");
            }

            JSONProcessor.writeResultsToFile(graphs, primResults, kruskalResults, "output.json");
            System.out.println("\n✓ Results saved to output.json");

            printFinalSummary(graphs, primResults, kruskalResults);

            if (args.length > 0 && args[0].equals("--test")) {
                runTests();
            }

        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printAlgorithmResult(String algorithmName, MSTResult result) {
        System.out.println(algorithmName + ":");
        System.out.println("  MST Cost: " + result.getTotalWeight());
        System.out.println("  Execution Time: " + result.getExecutionTimeMs() + " ms");
        System.out.println("  Operations: " + result.getOperations());
        System.out.println("  MST Edges: " + result.getMstEdges().size());

        if (result.getMstEdges().size() <= 10) {
            for (Edge edge : result.getMstEdges()) {
                System.out.println("    " + edge.getFrom() + " - " + edge.getTo() + " (" + edge.getWeight() + ")");
            }
        }
    }

    private static void printFinalSummary(List<Graph> graphs, MSTResult[] primResults, MSTResult[] kruskalResults) {
        System.out.println("\n=== FINAL SUMMARY ===");
        System.out.println("Graph ID | Vertices | Edges | Prim Time | Kruskal Time | Prim Ops | Kruskal Ops | MST Cost");
        System.out.println("--------|----------|-------|-----------|--------------|----------|-------------|----------");

        for (int i = 0; i < graphs.size(); i++) {
            Graph graph = graphs.get(i);
            MSTResult prim = primResults[i];
            MSTResult kruskal = kruskalResults[i];

            System.out.printf("%7d | %8d | %5d | %9d | %12d | %8d | %11d | %8d%n",
                graph.getId(),
                graph.getVertexCount(),
                graph.getEdgeCount(),
                prim.getExecutionTimeMs(),
                kruskal.getExecutionTimeMs(),
                prim.getOperations(),
                kruskal.getOperations(),
                prim.getTotalWeight()
            );
        }

        analyzePerformance(primResults, kruskalResults);
    }

    private static void analyzePerformance(MSTResult[] primResults, MSTResult[] kruskalResults) {
        long totalPrimTime = 0;
        long totalKruskalTime = 0;
        int totalPrimOps = 0;
        int totalKruskalOps = 0;

        for (int i = 0; i < primResults.length; i++) {
            totalPrimTime += primResults[i].getExecutionTimeMs();
            totalKruskalTime += kruskalResults[i].getExecutionTimeMs();
            totalPrimOps += primResults[i].getOperations();
            totalKruskalOps += kruskalResults[i].getOperations();
        }

        System.out.println("\n=== PERFORMANCE ANALYSIS ===");
        System.out.println("Total Prim Time: " + totalPrimTime + " ms");
        System.out.println("Total Kruskal Time: " + totalKruskalTime + " ms");
        System.out.println("Total Prim Operations: " + totalPrimOps);
        System.out.println("Total Kruskal Operations: " + totalKruskalOps);

        if (totalPrimTime < totalKruskalTime) {
            System.out.println("✓ Prim was faster overall");
        } else if (totalPrimTime > totalKruskalTime) {
            System.out.println("✓ Kruskal was faster overall");
        } else {
            System.out.println("✓ Both algorithms performed equally");
        }
    }

    private static void runTests() {
        System.out.println("\n=== RUNNING TESTS ===");
        System.out.println("To run tests: mvn test");
    }
}
