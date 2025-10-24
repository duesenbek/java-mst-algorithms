import java.io.*;
import java.util.*;

public class CSVExporter {
    public static void writeSummary(List<Graph> graphs, MSTResult[] prim, MSTResult[] kruskal, String filename, boolean append) throws IOException {
        boolean writeHeader = !new File(filename).exists() || !append;
        try (FileWriter fw = new FileWriter(filename, append); BufferedWriter bw = new BufferedWriter(fw)) {
            if (writeHeader) {
                bw.write("graph_id,vertices,edges,prim_cost,prim_time_ms,prim_ops,prim_connected,kruskal_cost,kruskal_time_ms,kruskal_ops,kruskal_connected\n");
            }
            for (int i = 0; i < graphs.size(); i++) {
                Graph g = graphs.get(i);
                MSTResult p = prim[i];
                MSTResult k = kruskal[i];
                bw.write(g.getId()+","+g.getVertexCount()+","+g.getEdgeCount()+","+
                        p.getTotalWeight()+","+p.getExecutionTimeMs()+","+p.getOperations()+","+p.isConnected()+","+
                        k.getTotalWeight()+","+k.getExecutionTimeMs()+","+k.getOperations()+","+k.isConnected()+"\n");
            }
        }
    }
}
