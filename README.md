# MST Algorithms: City Transportation Network Optimization

## 📊 Project Overview
This project implements Prim's and Kruskal's algorithms to find Minimum Spanning Trees (MST) for optimizing city transportation networks. It calculates the most cost‑effective road connections between city districts and compares algorithm performance.

 ## ✅Assignment Requirements Completed

- Prim's Algorithm
- Kruskal's Algorithm (with DSU)
- JSON I/O: read input graphs and write detailed results
- CSV summary export of performance metrics
- Performance metrics recorded: execution time (ms), operation counts, connectivity

## 🚀Quick Start

### Prerequisites
- Java 11+
- Maven 3.6+

### Build and Run
```bash
mvn -q -DskipTests compile
mvn -q exec:java -Dexec.mainClass="Main"
```

### Run Tests
```bash
mvn test
```

## 📁Project Structure
```text
java-mst-algorithms/
├── input.json                 # All datasets merged into one file (11 graphs)
├── output.json                # Detailed JSON results per graph (Prim, Kruskal)
├── results_summary.csv        # CSV summary (cost, time, ops, connected)
├── pom.xml                    # Maven config (Java 11, org.json)
├── src/
│   └── main/
│       ├── java/
│       │   ├── CSVExporter.java        # CSV writer
│       │   ├── DSU.java                # Disjoint Set Union
│       │   ├── Graph.java              # Edge, Graph, MSTResult
│       │   ├── JSONProcessor.java      # JSON reader/writer
│       │   ├── MSTAlgorithm.java       # Prim & Kruskal implementations
│       │   └── Main.java               # Entry point
│       └── resources/
│           ├── META-INF/maven/archetype.xml
│           └── archetype-resources/...
└── assign_3_input_small.json           # Original sample datasets (merged into input.json)
   assign_3_input_medium.json
   assign_3_input_large.json
```

## Datasets
- input.json contains 11 graphs:
  - Small (4–6 vertices): ids 1, 2, 101, 103
  - Medium (10–15 vertices): ids 201, 202, 203
  - Large (20–30 vertices): ids 301, 302, 303, 102 (6 vertices)
- Format matches JSONProcessor schema:
  - graphs: [{ id, nodes: [String], edges: [{from,to,weight}] }]

## Outputs
- output.json
  - For each graph: input_stats, prim, kruskal
  - For each algorithm: mst_edges, total_cost, operations_count, execution_time_ms, connected
- results_summary.csv
  - Columns:
    - graph_id, vertices, edges
    - prim_cost, prim_time_ms, prim_ops, prim_connected
    - kruskal_cost, kruskal_time_ms, kruskal_ops, kruskal_connected

## 📈MST Algorithms Performance Analysis
#### Based on 11 Test Graphs (4-30 vertices)
##### Prim Algorithm
- Total Operations: 696
- Average Operations/Graph: 63.3
- Efficiency Ratio: 3.67x better
##### Kruskal Algorithm
- Total Operations: 2,556
- Average Operations/Graph: 232.4
- Sorting Overhead: Significant
<img width="1569" height="464" alt="Снимок экрана 2025-10-24 231605" src="https://github.com/user-attachments/assets/f092b688-0ca3-4330-85fb-b208d5b8b537" />
<img width="1552" height="451" alt="Снимок экрана 2025-10-24 231616" src="https://github.com/user-attachments/assets/fd91d29a-d100-48ae-86c7-4cefefc72e54" />
<img width="1567" height="467" alt="Снимок экрана 2025-10-24 231625" src="https://github.com/user-attachments/assets/0807c3a0-3806-4d35-baea-db900fee925f" />

#### Performance Summary
<img width="1184" height="195" alt="Снимок экрана 2025-10-24 233218" src="https://github.com/user-attachments/assets/b328cd47-6267-469a-a26d-493d0594ff17" />


#### 🎯Key Performance Findings
-  Both algorithms produced identical MST costs - Correctness verified across all 11 graphs
-  Prim is 3.67x more efficient on average - 696 total operations vs 2,556 for Kruskal
-  Kruskal shows consistent overhead - Operations ratio remains stable (2.9x-4.1x) across graph sizes
-  Prim scales better - More efficient growth pattern with increasing graph size
-  Recommendation - Use Prim for better performance, Kruskal when edge sorting is available or for sparse graphs

- Use results_summary.csv for exact totals:
  - Total Prim Operations = sum of prim_ops
  - Total Kruskal Operations = sum of kruskal_ops
  - Compare median/mean execution times and operations across size ranges.

## 🔧How to Reproduce Results
- Edit input.json to adjust datasets if needed.
- Run the app to regenerate output.json and append to results_summary.csv.
- Optionally aggregate or visualize results (e.g., spreadsheets or plotting tools).

#### 📝 made for Design and Analysis of Algorithms course.
#### 👥 Astana IT University 2025
