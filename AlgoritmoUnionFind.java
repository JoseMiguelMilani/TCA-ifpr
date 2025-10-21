import java.util.HashMap;
import java.util.Map;

public class AlgoritmoUnionFind {

    public static void main(String[] args) {
        int[][] matriz = VariaveisGlobais.getMatrizProcessada();

        if (matriz == null) {
            System.out.println("nao carrego");
        }

        int[][] resultado = aplicarUnionFind(matriz);

        int[] posicaoIniciais = definirPosInicial(resultado);

        VariaveisGlobais.setPosIniciais(posicaoIniciais);
        VariaveisGlobais.setMatrizPronta(resultado);
    }







    static class classUnionFind {
    
        private int[] pai;
        private int[] posAltura;

        public classUnionFind(int n) {//constructor da classe 
            pai = new int[n];
            posAltura = new int[n];

            for (int i = 0; i < n; i++) {//inicializa todos como eles mesmos, pois nao estao ligado a nada
                pai[i] = i;
                posAltura[i] = 0;
            }
        }
        
        public int achar(int posAtual) {
            if (pai[posAtual] != posAtual) {

                pai[posAtual] = achar(pai[posAtual]);
            }
            return pai[posAtual];
        }
        
        public void unir(int x, int y) {
            int raizesX = achar(x);
            int raizesY = achar(y);
            
            if (ehDiferente(raizesX, raizesY)) {
                acharPai(raizesX, raizesY);
            }
        }

        public void acharPai(int X, int Y){
            //estrutura de dados = binary tree

            if (posAltura[X] < posAltura[Y]) {
                pai[X] = Y;
            }else if (posAltura[X] > posAltura[Y]) {
                pai[Y] = X;
            } else{
                pai[Y] = X;
                posAltura[X]++;
            }
        }
        
    }

    public static int[][] aplicarUnionFind(int[][] matriz){

        int linhas = matriz.length;
        int colunas = matriz[0].length;
        int tamanhoVetor = linhas*colunas;

        classUnionFind conectados = new classUnionFind(tamanhoVetor); //pois todos podem ser diferente


       verificarPontosMatriz(matriz, linhas, colunas, conectados);//conectas os pontos

        int[][] matrizElemento = criarMatrizElemento(matriz, colunas, linhas, conectados);//transforma em valores unicos
             

        return  matrizElemento;  

    }

//

    public static int[][] criarMatrizElemento(int[][] matrizUnida, int colunas, int linhas, classUnionFind conectados){
        Map<Integer, Integer> hashElementos = new HashMap<>();
        int elementoAtual = 1;
        int[][] matrizElemento = new int[linhas][colunas];

        for (int i = 0; i < linhas; i++) {
            
            for (int j = 0; j < colunas; j++) {
            
                if (naoehBranco(matrizUnida, i, j)) {

                    int posicao1d = i * colunas + j;

                    int posConectado = conectados.achar(posicao1d);
                    
                    if (!hashElementos.containsKey(posConectado)) {
                        hashElementos.put(posConectado, elementoAtual++);
                    }
                    
                    matrizElemento[i][j] = hashElementos.get(posConectado);
                    
                }

            }
        }

        return matrizElemento;
    }

    public static void verificarPontosMatriz(int[][] matriz, int linha, int coluna, classUnionFind conectados){

        

        for (int i = 0; i < linha; i++) {
            
            for (int j = 0; j < coluna; j++) {

                if (naoehBranco(matriz, i, j)) {

                    int posicaoAtual1d = i * coluna + j;
                    int valorAtual = matriz[i][j];

                    verificarAdjascente(matriz, i, j, linha, coluna, conectados, posicaoAtual1d);
                    
                }
                
            }
        }
        
    }

    public static void verificarAdjascente(int[][] matriz, int i, int j, int linhas, int colunas, classUnionFind conectados, int posicaoAtual1d){

        int[][] direcao = {{0, 1}, {1, 0}};

        for(int[] direcoes : direcao){

            int linhaVizinho = i + direcoes[0]; 
            int colunaVizinho = j + direcoes[1];

            if (estaDentroDoLimiteDaMatriz(linhaVizinho, colunaVizinho, linhas, colunas)) {
                            
            if (naoehBranco(matriz, linhaVizinho,colunaVizinho) && ehMesmoValor(matriz, i, j, linhaVizinho, colunaVizinho)) {
                                
                int posicaoAdjascente1d = linhaVizinho * colunas + colunaVizinho;

                conectados.unir(posicaoAtual1d, posicaoAdjascente1d);

                }

            }

        }
    }

//  

    public static boolean naoehBranco(int[][] matriz, int i, int j){
        if (matriz[i][j] != -1) {
            return true;
        }

        return false;
    }

    public static boolean estaDentroDoLimiteDaMatriz(int posIAnalisada, int posJAnalisada, int linhas, int colunas){
        if ( (posIAnalisada >= 0 && posIAnalisada < linhas) && (posJAnalisada >= 0 && posJAnalisada < colunas)) {
            return true;
        }

        return false;
    }
    
    public static boolean ehMesmoValor(int[][] matriz, int i, int j, int Iadjascente, int Jadjscente){
        if (matriz[i][j] == matriz[Iadjascente][Jadjscente]) {
            return true;
        }

        return false;
    }

    public static int[] definirPosInicial(int[][] matriz){
        int[] posicaoIniciais = new int[2];

        int[] posVerde = VariaveisGlobais.getPosVerde();
        int[] posVermelha = VariaveisGlobais.getPosVermelha();

        posicaoIniciais[0] = matriz[posVerde[0]][posVerde[1]];
        posicaoIniciais[1] = matriz[posVermelha[0]][posVermelha[1]];

        return posicaoIniciais;
        
    }

    public static boolean  ehDiferente(int x,int y){
        if(x != y){
            return true;
        }

        return false;
    }







}
