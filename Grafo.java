import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
@SuppressWarnings ("unchecked")


public class Grafo {

    private int numeroDeVertices;
    private LinkedList<Integer>[] listaAdjacencia;
    
    
    public class Resultado {

    //incializar
    private int[] vetorAnterior;
    private boolean[] visitiados;
    private boolean temCaminho;

    public Resultado(int[] vetorAnterior, boolean[] visitados, boolean temCaminho){
        this.vetorAnterior = vetorAnterior;
        this.visitiados  = visitados;
        this.temCaminho = temCaminho;

    }

    public int[] getVetorAnterior() {
            return vetorAnterior;
        }

    public void setVetorAnterior(int[] vetorAnterior) {
            this.vetorAnterior = vetorAnterior;
        }

    public boolean[] getVisitiados() {
            return visitiados;
        }

    public void setVisitiados(boolean[] visitiados) {
            this.visitiados = visitiados;
        }

    public boolean isTemCaminho() {
            return temCaminho;
        }

    public void setTemCaminho(boolean temCaminho) {
            this.temCaminho = temCaminho;
        }


    }

    public Grafo(int numeroDeVertices) {
        this.numeroDeVertices = numeroDeVertices;

        listaAdjacencia = new LinkedList[numeroDeVertices + 1];

        for (int i = 1; i <= numeroDeVertices; i++) {
            listaAdjacencia[i] = new LinkedList<>();
        }

    }
    

    public static void main(String[] args) {

        int[] posVerde = VariaveisGlobais.getPosVerde();
        int[] posVermelho = VariaveisGlobais.getPosVermelha();
        int[][] matriz = VariaveisGlobais.getMatrizPronta();

        int valorVerde = matriz[posVerde[0]][posVerde[1]];
        int valorVermelho = matriz[posVermelho[0]][posVermelho[1]];

        Grafo grafo = criarGrafo(matriz);
        grafo.imprimirGrafo();


        Resultado resposta = grafo.acharMenorCaminho(valorVerde, valorVermelho);

        boolean temCaminho = resposta.isTemCaminho();

        System.out.println(temCaminho);
        
        if(temCaminho){

            int[] vetorCaminho = resposta.getVetorAnterior();

            for (int i = 0; i < vetorCaminho.length; i++) {
                System.out.printf("[%d] = %d \n", i, vetorCaminho[i]);
            }

            int pontos = imprimirMenorCaminho(vetorCaminho, valorVerde, valorVermelho);
            
            System.out.println(pontos+1);
        }
        
        
        


    }

    public Resultado acharMenorCaminho(int inicio, int destino){

        int[] anterior = new int[numeroDeVertices+1];
        Queue<Integer> fila = new LinkedList<>();
        boolean[] visitado = new boolean[numeroDeVertices + 1];
        boolean temCaminho = false;

        fila.add(inicio);
        visitado[inicio] = true;

        

        while (!(fila.isEmpty())) {

            
            
            int atual = fila.poll();//retorna valor e tira ele

            if (atual == destino) {//condição de vitoria
                temCaminho = true;
                break;
            }

            for (int vizinhoDoPonto : listaAdjacencia[atual]) {

                if (!(visitado[vizinhoDoPonto])) {
                    
                    visitado[vizinhoDoPonto] = true;
                    anterior[vizinhoDoPonto] = atual;
                    fila.add(vizinhoDoPonto);
                    
                }

                
            }


            
        }


        Resultado resultado = new Resultado(anterior, visitado, temCaminho);
        
        return resultado;

    }

    public static int imprimirMenorCaminho(int[]anterior, int inicio, int fim){
        
        int atual = fim;
        int contador = 0;
        int pontos = 0;

        ArrayList<Integer> elementosPassados = new ArrayList();

        while (atual != inicio) {
            
            System.out.printf("%d <- ",atual);

            atual = anterior[atual];

            contador ++;
            elementosPassados.add(atual);
            
        }
        elementosPassados.reversed();

        int[] caminho = transformarEmVetor(elementosPassados);  
        VariaveisGlobais.setListaCaminho(caminho);
        VariaveisGlobais.setQuantiaPassos(contador);

        
        System.out.println(inicio);
       
        return pontos;
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

        VariaveisGlobais.setQuantiaElemento(maior);

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
    
    public static int[] transformarEmVetor(ArrayList<Integer> lista){

        int[] vetor = new int[lista.size()];

        for (int i = 0; i < lista.size(); i++) {
            vetor[i] = lista.get(i);
        }

        return vetor; 

    }





}
