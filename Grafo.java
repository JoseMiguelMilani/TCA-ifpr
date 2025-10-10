import java.util.LinkedList;
@SuppressWarnings ("unchecked")

public class Grafo {

    private int numeroDeVertices;
    private LinkedList<Integer>[] listaAdjacencia;

    public static void main(String[] args) {

        int[] posVerde = VariaveisGlobais.getPosVerde();
        int[] posVermelho = VariaveisGlobais.getPosVerde();
        int[][] matriz = VariaveisGlobais.getMatrizPronta();

        Grafo grafo = criarGrafo(matriz);

        grafo.imprimirGrafo();

        int elementoVerde = matriz[posVerde[0]][posVerde[1]];
        int elementoVermelho = matriz[posVermelho[0]][posVermelho[1]];

        System.out.println("elemento verde: "+ elementoVerde + " Elemento Vermelho: "+elementoVermelho);

    }


    public static int acharQuantiaElemento(int[][] matriz){
        int maior =0;
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                if (matriz[i][j] > maior) {
                    maior = matriz[i][j];
                }

            }
        }

        return maior;
    }

    public static Grafo criarGrafo(int[][] matriz){

        int maior = acharQuantiaElemento(matriz);

        System.out.println("maior = "+maior);

        Grafo grafo = new Grafo(maior);

        int[][] direcao = {{-1, 0}, {1 , 0}, {0 , -1}, {0 , 1}} ;

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                
                for (int[] pos : direcao) {
                    
                    if (dentroDosLimites(matriz, i+pos[0], j+pos[1])) {
                        
                        if ((matriz[i][j] != 0 && matriz[i+pos[0]][j+pos[1]] != 0) && (matriz[i][j] != matriz[i+pos[0]][j+pos[1]]))  {
                            grafo.adicionarAresta(matriz[i][j], matriz[i + pos[0]][j + pos[1]]);
                        }
                    }
                }
            }
        }


        return grafo;
    }

    public static boolean dentroDosLimites(int[][] matriz, int linha, int coluna) {
        int linhas = matriz.length;
        if (linhas == 0) return false;
        
        int colunas = matriz[0].length;
        
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
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

    public Grafo(int numeroDeVertices) {
        this.numeroDeVertices = numeroDeVertices;
        listaAdjacencia = new LinkedList[numeroDeVertices + 1];
        for (int i = 1; i <= numeroDeVertices; i++) {
            listaAdjacencia[i] = new LinkedList<>();
        }
    }

    public void adicionarAresta(int origem, int destino) {

        if (origem < 1 || origem > numeroDeVertices || destino < 1 || destino > numeroDeVertices) {
            return;
        }

        if (!listaAdjacencia[origem].contains(destino)) {
            listaAdjacencia[origem].add(destino);
        }

        if (!listaAdjacencia[destino].contains(origem)) {
            listaAdjacencia[destino].add(origem);
        }
    }
    
    public void imprimirGrafo() {
        for (int i = 1; i <= numeroDeVertices; i++) {
            System.out.print(i + " -> ");
            for (int vizinho : listaAdjacencia[i]) {
                System.out.print(vizinho + " ");
            }
            System.out.println();
        }
    }
    
    
}
