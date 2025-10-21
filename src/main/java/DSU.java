public class DSU {
    private int[] parent;
    private int[] rank;
    private int operationsCount;

    public DSU(int n) {
        parent = new int[n];
        rank = new int[n];
        operationsCount = 0;

        for (int i = 0; i < n; i++) {
            parent[i] = i;
            operationsCount++;
        }
    }

    public int find(int x) {
        operationsCount++;
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
            operationsCount++;
        }
        return parent[x];
    }

    public boolean union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        operationsCount += 2;

        if (rootX == rootY) {
            return false;
        }

        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
            operationsCount++;
        }
        operationsCount += 3;
        return true;
    }

    public int getOperationsCount() {
        return operationsCount;
    }

    public void resetOperationsCount() {
        operationsCount = 0;
    }
}
