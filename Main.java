
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Main {
    


    public static void main(String[] args) {

        
        System.out.println("iniciou");

        int quantPontosDeCor = VariaveisGlobais.getQuantiaPontosDeCor();
        String nomeArquivoDeImagem = VariaveisGlobais.getNomeImagem();
        int quantIteracoes = VariaveisGlobais.getQuantIteracoes();
        int tentativa = VariaveisGlobais.getTentativas();

        textoGlobais();
        

        int[][][] img = LendoUmaImagem.lerImagem(nomeArquivoDeImagem);

        imagemLida();

        Kmeans.ResultadoKmeans resultado = Kmeans.aplicarKMeansNaImagemCompleto(quantPontosDeCor, quantIteracoes, tentativa, img);
        KmeansAplicado();


        int[][] matrizProcessada = resultado.matrizClusters;
        VariaveisGlobais.setMatrizProcessada(matrizProcessada);
        double[][] centroides = resultado.centroides;

        imprimirMatriz2D(matrizProcessada);
        gerarImagemComCoresKMeans(matrizProcessada, centroides, "imagem_kmeans_" + quantPontosDeCor + "cores", img);

        
        VariaveisGlobais.setPosVerde(Kmeans.encontrarPixelVerde(img));
        VariaveisGlobais.setPosVermelha(Kmeans.encontrarPixelVermelho(img));


        //chamando outros arquivos

        CriandoGrafo.main(args);
        Grafo.main(args);
        
    }

    public static void contarQuantiaDePixelCadaUmTem(int quantPontosDeCor, int[][] matrizPronta){
        int[] contador = new int[quantPontosDeCor];
        for (int x = 0; x < matrizPronta.length; x++) {
            for (int y = 0; y < matrizPronta[0].length; y++) {
                contador[matrizPronta[x][y]]++;
            }
        }

        for (int i = 0; i < contador.length; i++) {
            System.out.println("Cluster " + i + ": " + contador[i] + " pixels");
        }
    }

    public static void imprimirMatriz2D(int[][] matriz) {
    for (int i = 0; i < matriz.length; i++) {
        for (int j = 0; j < matriz[i].length; j++) {
            if (matriz[i][j] == -1) {  
                System.out.print("  ");
            } else {
                System.out.print(matriz[i][j] + " "); 
            }
        }
        System.out.println();
    }
}

    public static void gerarImagemComCoresKMeans(int[][] matrizClusters, double[][] centroides, String nomeArquivo, int[][][] imagemOriginal) {
    int largura = matrizClusters.length;
    int altura = matrizClusters[0].length;

    BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);


    int[][] coresCentroides = new int[centroides.length][3];
    for (int i = 0; i < centroides.length; i++) {
        coresCentroides[i][0] = (int) (centroides[i][0] * 255);
        coresCentroides[i][1] = (int) (centroides[i][1] * 255);
        coresCentroides[i][2] = (int) (centroides[i][2] * 255);
        

        coresCentroides[i][0] = Math.max(0, Math.min(255, coresCentroides[i][0]));
        coresCentroides[i][1] = Math.max(0, Math.min(255, coresCentroides[i][1]));
        coresCentroides[i][2] = Math.max(0, Math.min(255, coresCentroides[i][2]));
    }

    for (int x = 0; x < largura; x++) {
        for (int y = 0; y < altura; y++) {
            int cluster = matrizClusters[x][y];
            Color cor;
            
            if (cluster == -1) {
                cor = Color.WHITE;
            } else {
                if (cluster >= 0 && cluster < coresCentroides.length) {
                    cor = new Color(
                        coresCentroides[cluster][0],
                        coresCentroides[cluster][1],
                        coresCentroides[cluster][2]
                    );
                } else {
                    cor = Color.BLACK;
                }
            }
            imagem.setRGB(x, y, cor.getRGB());
        }
    }

    try {
        ImageIO.write(imagem, "png", new File("output/" + nomeArquivo + ".png"));
        System.out.println("Imagem com " + centroides.length + " cores salva como " + nomeArquivo + ".png");
    } catch (Exception e) {
        System.out.println("Erro ao salvar imagem: " + e.getMessage());
    }
}

    public static void textoGlobais(){
        System.out.println("------/ Variaveis Globais Carregadas \\-----");
        System.out.println("");
    }

    public static void imagemLida(){
        System.out.println("------| imagem lida |-----");
        System.out.println("");
    }

    public static void KmeansAplicado(){
        System.out.println("------| Kmeans aplicado |-----");
        System.out.println("");
    }



}
