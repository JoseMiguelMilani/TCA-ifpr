import java.util.HashSet;
import java.util.Random;

public class Kmeans {

    public static class objetoskmeans {
        public double[][] centroides;  
        public int[] labels;           
        public int iteracoes;          
    }

    public static class ResultadoKmeans {
        public int[][] matrizClusters;
        public double[][] centroides;
        public int iteracoes;
    }

    public static ResultadoKmeans aplicarKMeansNaImagemCompleto(int quantidadeDeClusters, int iteracoesMaximas, 
        int numeroDeTentativas, int[][][] matrizImagem) {
        int largura = matrizImagem.length;
        int altura = matrizImagem[0].length;

        double[][] pixels = criarVetorPixel(matrizImagem, null);
        objetoskmeans resultado = executarKMeans(pixels, quantidadeDeClusters, iteracoesMaximas, numeroDeTentativas);

        int[][] matrizClusters = new int[largura][altura];
        int indice = 0;
        
        for(int x = 0; x < largura; x++) {
            for(int y = 0; y < altura; y++) {
                if (isPixelBranco(matrizImagem, x, y)) {
                    matrizClusters[x][y] = -1;
                } else {
                    matrizClusters[x][y] = resultado.labels[indice];
                    indice++;
                }
            }
        }

        ResultadoKmeans resultadoCompleto = new ResultadoKmeans();
        resultadoCompleto.matrizClusters = matrizClusters;
        resultadoCompleto.centroides = resultado.centroides;
        resultadoCompleto.iteracoes = resultado.iteracoes;

        return resultadoCompleto;
    }

    public static int[][] aplicarKMeansNaImagem(int quantidadeDeClusters, int iteracoesMaximas, int numeroDeTentativas,int[][][] matrizImagem) {
        int largura = matrizImagem.length;
        int altura = matrizImagem[0].length;

        double[][] pixels = criarVetorPixel(matrizImagem, null);
        objetoskmeans resultado = executarKMeans(pixels, quantidadeDeClusters, iteracoesMaximas, numeroDeTentativas);

        int[][] matrizClusters = new int[largura][altura];
        int indice = 0;
        

        for(int x = 0; x < largura; x++) {
            for(int y = 0; y < altura; y++) {
                if (isPixelBranco(matrizImagem, x, y)) {
                    matrizClusters[x][y] = -1;
                } else {
                    matrizClusters[x][y] = resultado.labels[indice];
                    indice++;
                }
            }
        }

        return matrizClusters;
    }

    private static boolean isPixelBranco(int[][][] matrizImagem, int x, int y) {
        int r = matrizImagem[x][y][0];
        int g = matrizImagem[x][y][1];
        int b = matrizImagem[x][y][2];
        

        int threshold = 200;
        
        return r >= threshold && g >= threshold && b >= threshold;
    }

    public static objetoskmeans executarKMeans(double[][] listaDePixels, int quantidadeDeClusters, int iteracoesMaximas, int numeroDeTentativas) {
        objetoskmeans melhorResultado = null;
        double melhorInertia = Double.POSITIVE_INFINITY;

        for (int tentativa = 0; tentativa < numeroDeTentativas; tentativa++) {

            Random geradorAleatorio = new Random(42 + tentativa);
            objetoskmeans resultadoAtual = executarUmaVez(listaDePixels, quantidadeDeClusters, iteracoesMaximas, geradorAleatorio);
            double inertiaAtual = calcularInertia(listaDePixels, resultadoAtual.labels, resultadoAtual.centroides);

            if (inertiaAtual < melhorInertia) {
                melhorResultado = resultadoAtual;
                melhorInertia = inertiaAtual;
            }
        }

        return melhorResultado;
    }

    public static double[][] criarVetorPixel(int[][][] matrizImagem, double[][] vetorPixeis){
        int largura = matrizImagem.length;
        int altura = matrizImagem[0].length;
        
        int countNaoBrancos = 0;
        for(int x = 0; x < largura; x++) {
            for(int y = 0; y < altura; y++) {
                if (!isPixelBranco(matrizImagem, x, y)) {
                    countNaoBrancos++;
                }
            }
        }

        vetorPixeis = new double[countNaoBrancos][3];
        formatarRgb(vetorPixeis, matrizImagem);

        return vetorPixeis;
    }

    public static void formatarRgb(double[][] vetorPixeis, int[][][] matrizImagem){
        int largura = matrizImagem.length;
        int altura = matrizImagem[0].length;
        int indice = 0;

        for(int x = 0; x < largura; x++) {
            for(int y = 0; y < altura; y++) {
                if (!isPixelBranco(matrizImagem, x, y)) {
                    vetorPixeis[indice][0] = matrizImagem[x][y][0] / 255.0;
                    vetorPixeis[indice][1] = matrizImagem[x][y][1] / 255.0;
                    vetorPixeis[indice][2] = matrizImagem[x][y][2] / 255.0;
                    indice++;
                }
            }
        }
    }
   
    private static double calcularDistanciadoCentroid(double[] ponto1, double[] ponto2){
        double soma = 0;
        for (int i = 0; i < ponto1.length; i++) {
            soma += Math.pow((ponto1[i] - ponto2[i]), 2);
        }
        return soma;
    }

    private static double[][] gerarCentroide(double[][] listaDePixels, int quantidadeDeClusters, Random geradorAleatorio){
        double[][] centroide = new double[quantidadeDeClusters][listaDePixels[0].length];
        HashSet<Integer> escolhidos = new HashSet<>();

        for (int i = 0; i < quantidadeDeClusters; i++) {
            int indice;
            do {
                indice = geradorAleatorio.nextInt(listaDePixels.length);
            } while (escolhidos.contains(indice));
            escolhidos.add(indice);
            System.arraycopy(listaDePixels[indice], 0, centroide[i], 0, listaDePixels[0].length);
        }
        return centroide;
    }

    private static objetoskmeans executarUmaVez(double[][] listaDePixels, int quantidadeDeClusters, int iteracoesMaximas, Random geradorAleatorio) {
        double[][] centroides = gerarCentroide(listaDePixels, quantidadeDeClusters, geradorAleatorio);
        int[] posCentroid = new int[listaDePixels.length];
        objetoskmeans resultado = rodarLoop(listaDePixels, posCentroid, centroides, quantidadeDeClusters, iteracoesMaximas, geradorAleatorio);
        return resultado;
    }

    private static objetoskmeans rodarLoop(double[][] listaDePixels, int[] posCentroid, double[][] centroides, int quantidadeDeClusters, int iteracoesMaximas, Random geradorAleatorio) {
        boolean mudou = true;
        int iteracao = 0;

        while (mudou && iteracao < iteracoesMaximas) {
            iteracao++;
            mudou = false;
            mudou = atualizarLabels(listaDePixels, posCentroid, centroides, quantidadeDeClusters) || mudou;
            centroides = recalcularCentroides(listaDePixels, posCentroid, quantidadeDeClusters, listaDePixels[0].length, geradorAleatorio);
        }

        objetoskmeans resultado = new objetoskmeans();
        resultado.centroides = centroides;
        resultado.labels = posCentroid;
        resultado.iteracoes = iteracao;
        return resultado;
    }

    private static boolean atualizarLabels(double[][] listaDePixels, int[] posCentroid, double[][] centroides, int quantidadeDeClusters) {
        boolean algumMudou = false;
        for (int i = 0; i < listaDePixels.length; i++) {
            int melhorIndice = encontrarCentroideMaisProximo(listaDePixels[i], centroides);
            if (posCentroid[i] != melhorIndice) {
                posCentroid[i] = melhorIndice;
                algumMudou = true;
            }
        }
        return algumMudou;
    }

    private static int encontrarCentroideMaisProximo(double[] ponto, double[][] centroides) {
        int melhorIndice = -1;
        double melhorDistancia = Double.POSITIVE_INFINITY;
        for (int c = 0; c < centroides.length; c++) {
            double distancia = calcularDistanciadoCentroid(ponto, centroides[c]);
            if (distancia < melhorDistancia) {
                melhorDistancia = distancia;
                melhorIndice = c;
            }
        }
        return melhorIndice;
    }

    private static double[][] recalcularCentroides(double[][] listaDePixels, int[] posCentroid,
        int quantidadeDeClusters, int dimensao, Random geradorAleatorio) {
        double[][] novosCentroides = new double[quantidadeDeClusters][dimensao];
        int[] contagem = new int[quantidadeDeClusters];

        for (int i = 0; i < listaDePixels.length; i++) {
            int label = posCentroid[i];
            contagem[label]++;
            for (int d = 0; d < dimensao; d++) {
                novosCentroides[label][d] += listaDePixels[i][d];
            }
        }

        for (int c = 0; c < quantidadeDeClusters; c++) {
            if (contagem[c] == 0) {
                int indiceAleatorio = geradorAleatorio.nextInt(listaDePixels.length);
                System.arraycopy(listaDePixels[indiceAleatorio], 0, novosCentroides[c], 0, dimensao);
            } else {
                for (int d = 0; d < dimensao; d++) {
                    novosCentroides[c][d] /= contagem[c];
                }
            }
        }
        return novosCentroides;
    }

    public static double calcularInertia(double[][] listaDePixels, int[] labels, double[][] centroides) {
        double soma = 0;
        for (int i = 0; i < listaDePixels.length; i++) {
            soma += calcularDistanciadoCentroid(listaDePixels[i], centroides[labels[i]]);
        }
        return soma;
    }

    public static int[] encontrarPixelVerde(int[][][] matrizImagem) {
    int largura = matrizImagem.length;
    int altura = matrizImagem[0].length;

    for (int x = 0; x < largura; x++) {
        for (int y = 0; y < altura; y++) {
            int r = matrizImagem[x][y][0];
            int g = matrizImagem[x][y][1];
            int b = matrizImagem[x][y][2];

            if (g > 100 && g > r + 40 && g > b + 40) {
                return new int[]{x, y}; 
            }
        }
    }
    return null;
}

    public static int[] encontrarPixelVermelho(int[][][] matrizImagem) {
    int largura = matrizImagem.length;
    int altura = matrizImagem[0].length;

    for (int x = 0; x < largura; x++) {
        for (int y = 0; y < altura; y++) {
            int r = matrizImagem[x][y][0];
            int g = matrizImagem[x][y][1];
            int b = matrizImagem[x][y][2];

            
            if (r > 100 && r > g + 40 && r > b + 40) {
                return new int[]{x, y};
            }
        }
    }
    return null;
}




}