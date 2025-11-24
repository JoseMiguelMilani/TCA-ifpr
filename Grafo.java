import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import javafx.scene.Node;
@SuppressWarnings ("unchecked")


public class Grafo {
    
    //funcionaento
        //recebe a matriz de pontos
        //acha os pontos conectados
        //acha a distancia entre os pontos
        //acha a menor distancia do ponto inicial ao final 

    private int numeroDeVertices;
    private LinkedList<Integer>[] listaAdjacencia;
    
    
    public class Resultado {

    //incializar
    private int[] vetorAnterior;
    private boolean[] visitiados;
    private boolean temCaminho;
    private int[] distancia;

    public Resultado(int[] vetorAnterior, boolean[] visitados, boolean temCaminho, int[] distancia){
        this.vetorAnterior = vetorAnterior;
        this.visitiados  = visitados;
        this.temCaminho = temCaminho;
        this.distancia = distancia;

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

    public int[] distancia() {
            return distancia;
        }

    public void setdistancia(int[] distancia) {
            this.distancia = distancia;
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


        Resultado resposta = grafo.acharMenorCaminho(valorVerde, valorVermelho, matriz, Arrays.asList(grafo.listaAdjacencia));

        boolean temCaminho = resposta.isTemCaminho();

        System.out.printf("\n\n");
        System.out.println("\t\t--/Caminho\\--");
        
        if(temCaminho){
            int[] vetorCaminho = resposta.getVetorAnterior();

            imprimirMenorCaminho(vetorCaminho, valorVerde, valorVermelho);
        }else{
            System.out.println("Não há caminho");
        }
        
        VariaveisGlobais.setDistancia(resposta.distancia());
        


    }

    public Resultado acharMenorCaminho(int inicio, int destino, int[][] matriz, List<LinkedList<Integer>> grafo){

        int[] distancia = new int[numeroDeVertices+1];
        int[] anterior = new int[numeroDeVertices+1];
        boolean[] visitado = new boolean[numeroDeVertices + 1];
        boolean temCaminho = false;

        Arrays.fill(distancia, Integer.MAX_VALUE);
        Arrays.fill(anterior, -1);
        distancia[inicio] = 0;

        PriorityQueue<int[]> fila = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        

        fila.add(new int[]{inicio, 0});

        while (!(fila.isEmpty())) {
            
            int[] posAtual = fila.poll();//retorna valor e tira ele   
            int valorAtual = posAtual[0]; 
            
            if (visitado[valorAtual]){
                continue;
            }

            visitado[valorAtual] = true;

            if (valorAtual == destino) {//condição de vitoria
                temCaminho = true;
                break;
                
            }

           if (valorAtual >= 1 && valorAtual <= numeroDeVertices) {

                for (int vizinho : grafo.get(valorAtual)) {//checa todos os vizinho

                    if (!visitado[vizinho]) {

                        int valorDistancia = calcularDistancia(valorAtual, vizinho, matriz);
                        int novaDistancia = distancia[valorAtual] + valorDistancia;
                        
                        if (novaDistancia < distancia[vizinho]) {//atualiza
                            distancia[vizinho] = novaDistancia;
                            anterior[vizinho] = valorAtual;
                            fila.add(new int[]{vizinho, novaDistancia});
                        }
                    }

                }
            }


            
        }


        Resultado resultado = new Resultado(anterior, visitado, temCaminho, distancia);

        
        return resultado;

    }

    public static int imprimirMenorCaminho(int[]anterior, int inicio, int fim){
        
        int atual = fim;
        int contador = 0;
        int pontos = 0;

        ArrayList<Integer> elementosPassados = new ArrayList<>();

        

        while (atual != inicio) {
            
            System.out.print(atual + " <- ");
            elementosPassados.add(atual);
            atual = anterior[atual];
            contador++;
            
            if (contador > anterior.length * 2) {
                System.out.println("Loop detectado no caminho");
                break;
            }
            
        }
        elementosPassados.add(inicio);

        ArrayList<Integer> caminhoReverso = new ArrayList<>();
        for (int i = elementosPassados.size() - 1; i >= 0; i--) {
            caminhoReverso.add(elementosPassados.get(i));
        }


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

        System.out.println("ponto\t /\tvizinhos");

        for (int i = 1; i <= numeroDeVertices; i++) {
            System.out.print(i + "\t->\t");
            for (int vizinho : listaAdjacencia[i]) {
                System.out.print(vizinho + "   ");
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

    public static int calcularDistancia(int atual, int anterior, int[][] matriz){

        int maxElement = acharQuantiaElemento(matriz);
        int[][] valorLimites = acharLimites(matriz, anterior);
        
        if (atual >= valorLimites.length || anterior >= valorLimites.length) {
        return 1;
    }

        int[] pontoCentralob1 = new int[3];
        int[] pontoCentralob2 = new int[3];

        for (int i = 0; i < 2 ; i++) {//ponto central objeto 1

                pontoCentralob1[i*1] = (int)  (valorLimites[atual][i*2] - valorLimites[atual][(i*2)+1])/2;
        }

        for (int i = 0; i < 2 ; i++) {//ponto central objeto 2
            pontoCentralob2[i*1] = (int) (valorLimites[atual][i*2] - valorLimites[atual][(i*2)+1])/2;
            }

            int distacia = calcularDistanciaPitagoras(pontoCentralob1, pontoCentralob2);

            return distacia;
            
    }

    public static int calcularDistanciaPitagoras(int[] p1, int[] p2){

        int distanciaX = Math.abs(p1[0] - p2[0]);
        double Cateto1 = Math.pow(distanciaX, 2);

        int distanciaY = Math.abs(p1[1] - p2[1]);
        double Cateto2 = Math.pow(distanciaY, 2);

        int distancia = (int) Math.sqrt(Cateto2+Cateto1);

        return distancia;
    }

    public static int[][] acharLimites(int[][] matriz, int quantiaElemento){
        int[][] limites = new int[quantiaElemento+1][4];

        for (int i = 0; i <= quantiaElemento; i++) {//0-direita, 1-esquerda, 2-baixo, 3-cima
            limites[i][0] = Integer.MIN_VALUE;
            limites[i][1] = Integer.MAX_VALUE;
            limites[i][2] = Integer.MIN_VALUE;
            limites[i][3] = Integer.MAX_VALUE;
        }

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                
                int valor = matriz[i][j];


                if (valor >= 0 && valor <= quantiaElemento) { 
                    checarLimites(i, j, valor, limites);
                }
            }
        }

        return limites;
    }

    public static void checarLimites(int i, int j, int valor, int[][] limites) {
        if (valor < 0 || valor >= limites.length){ 
            return;
        }

        if (i > limites[valor][0]){
            limites[valor][0] = i;
        } 
        if (i < limites[valor][1]){
            limites[valor][1] = i; 
        }
        if (j > limites[valor][2]){
            limites[valor][2] = j;
        }
        if (j < limites[valor][3]){
            limites[valor][3] = j;
        }
    }




}
