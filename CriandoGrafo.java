import java.util.*;

public class CriandoGrafo {

    public static void main(String[] args) {

        int[][] matriz = VariaveisGlobais.getMatrizProcessada();

        int[][] resultado = findConnectedComponents(matriz);

        VariaveisGlobais.setMatrizPronta(resultado);

    }

    
    static class UnionFind {
        private int[] parent;
        private int[] rank;
        
        public UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 0;
            }
        }
        
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }
        
        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            
            if (rootX != rootY) {
                if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }
    }
    
    public static int[][] findConnectedComponents(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return new int[0][0];
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int totalCells = rows * cols;
        
        UnionFind uf = new UnionFind(totalCells);
        

        int[][] direcao = {{0, 1}, {1, 0}};
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != -1) {
                    int currentIndex = i * cols + j;
                    int currentValue = matrix[i][j];

                    for (int[] dir : direcao) {
                        int ni = i + dir[0];
                        int nj = j + dir[1];
                        
                        if (ni >= 0 && ni < rows && nj >= 0 && nj < cols && 
                            matrix[ni][nj] != -1 && matrix[ni][nj] == currentValue) {
                            // Só une se for o MESMO valor
                            int neighborIndex = ni * cols + nj;
                            uf.union(currentIndex, neighborIndex);
                        }
                    }
                }
            }
        }
        

        Map<Integer, Integer> componentLabels = new HashMap<>();
        int currentLabel = 1;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            Arrays.fill(result[i], -1);
        }
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] != -1) {
                    int index = i * cols + j;
                    int root = uf.find(index);
                    
                    if (!componentLabels.containsKey(root)) {
                        componentLabels.put(root, currentLabel++);
                    }
                    
                    result[i][j] = componentLabels.get(root);
                }
            }
        }
        
        return result;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int cell : row) {
                if (cell == -1) {
                    System.out.print("  "); 
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println();
        }
    }
    

}