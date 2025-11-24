import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Main {

    //funcionamento

    //Main-chamada de função
        //conta o tempo
        //recebe e passa o nome do arquivo
        //chama lerImagem
        //chama processar imagem
        //chama unionFind
        //chama criar grafo
        //cria imagem
        //termina de conta tempo
        //chama JPanel


    public static void main(String[] args) {
        System.out.println("iniciou");

        escolherImagem();

        long inicio = System.currentTimeMillis();
        String nomeArquivoDeImagem = VariaveisGlobais.getNomeImagem();

        textoGlobais();

        int[][][] img = LendoUmaImagem.lerImagem(nomeArquivoDeImagem);
        imagemLida();

        ProcessandoImagem.processarImagem(img);

        acharPosicaoInicial(img);

        AlgoritmoUnionFind.main(args);
        int[] posicaoInicias = VariaveisGlobais.getPosIniciais();
        textoValoresInciais(posicaoInicias[0], posicaoInicias[1]);

        texteiniciandoGrafo();
        Grafo.main(args);

        int quantiaElemento = VariaveisGlobais.getQuantiaElemento();
        System.out.printf("\n\n\t   --/quantia Elementos\\--\n\thá %s elementos no grafo\n\n----------------------------------------------------\n", quantiaElemento);
        criarImagemComCor(quantiaElemento, nomeArquivoDeImagem);

        //acaba o codigo
        long fim = System.currentTimeMillis();
        calcularTempoQueRodou(inicio, fim);

        telaNova.main(args);    
    }

    public static Scanner TECLADO = new Scanner(System.in);

    public static void contarQuantiaDePixelCadaUmTem(int quantPontosDeCor, int[][] matrizPronta){
        int[] contador = new int[quantPontosDeCor];
        for (int x = 0; x < matrizPronta.length; x++) {
            for (int y = 0; y < matrizPronta[0].length; y++) {
                contador[matrizPronta[x][y]]++;
            }
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


    public static void textoGlobais(){
        System.out.println("");
        System.out.println("\t--//Variaveis Globais Carregadas\\\\--");
        System.out.println("");
    }

    public static void texteiniciandoGrafo(){
        System.out.println("---------------/ Grafo.java rodando \\---------------");
        System.out.println("");
    }

    public static void textoValoresInciais(int pontoInicial, int pontoFinal){
        System.out.println("\t----/ Valores iniciais definidos \\----");
        System.out.printf("\t      começo = [%d], fim =[%d]\n", pontoInicial, pontoFinal);
        System.out.println("");
    }

    public static void imagemLida(){
        System.out.printf("\n\n");
        System.out.println("------------------/ imagem lida \\-------------------");
        System.out.println("");
    }

    public static boolean acharVerde(int[][][] img, int x, int y){
        int[] pos = new int[2];

        int r = img[x][y][0];
        int g = img[x][y][1];
        int b = img[x][y][2];

        if (g > r+50 && g > b+50) {
            pos[0] = x;
            pos[1] = y;



            VariaveisGlobais.setPosVerde(pos);
            return true;
        }
        return false;
    }

    public static boolean  acharVermelho(int[][][] img, int x, int y){
        int[] pos = new 
        int[2];

        int r = img[x][y][0];
        int g = img[x][y][1];
        int b = img[x][y][2];

        if (r > g+50 && r > b+50) {
            pos[0] = x;
            pos[1] = y;


            VariaveisGlobais.setPosVermelha(pos);
            return true;
        }
        return false;
    }

    public static void acharPosicaoInicial(int[][][] img){
        boolean verde = false;
        boolean red = false;
       
        
        for (int i = 0; i < img.length; i++) {
            
            for (int j = 0; j < img[0].length; j++) {
                
                if(!verde){
                    
                    
                    verde = acharVerde(img, i, j);

                }
                if (!red) {
                    
                    red = acharVermelho(img, i, j);
                }

            }
        }
    }

    public static void criarImagemComCor(int quantiaElemento, String nomeArquivo){
        int[][] matriz = VariaveisGlobais.getMatrizPronta();
        int[][] corPorElemento = new int[quantiaElemento+1][3];

        Random gerarAleatorio = new Random();
        BufferedImage imagem = new BufferedImage( matriz.length,  matriz[0].length, BufferedImage.TYPE_INT_RGB);

        for (int elemento = 0; elemento < quantiaElemento+1; elemento++) {
            
            for (int rgb = 0; rgb < 3; rgb++) {
                corPorElemento[elemento][rgb] = gerarAleatorio.nextInt(255);
                
            }

        }
    
        VariaveisGlobais.setCorPorElemento(corPorElemento);


        for (int i = 0; i < matriz.length; i++) {

            for (int j = 0; j < matriz[0].length; j++) {

                int valorAtual = matriz[i][j];
                Color corDoPonto;
                int[] rgb = new int[3];

                if (valorAtual == 0) {
                    corDoPonto = Color.WHITE;

                } else{
                    
                    for (int k = 0; k < rgb.length; k++) {
                        rgb[k] = corPorElemento[valorAtual][k];
                    }

                    corDoPonto = new Color(rgb[0], rgb[1], rgb[2]);
                    
                }

                imagem.setRGB(i, j, corDoPonto.getRGB());
            }
        }

        try {
                
            File arquivo = new File("output/" + nomeArquivo);
            ImageIO.write(imagem, "png", arquivo);

            System.out.println("Imagem salva como: " + arquivo.getAbsolutePath());

        }catch (Exception e) {
             System.out.println("Erro ao salvar imagem: " + e.getMessage());
        }
            
    }

    public static void calcularTempoQueRodou(long inicio, long fim){
        long tempo = fim - inicio;

        VariaveisGlobais.setTempoQueRodou(tempo);
    }

    public static void escolherImagem(){

        System.out.println("----------------/ escolha a imagem \\----------------");
        System.out.println(" ");

        File[] arquivos = acessandoPasta();

        System.out.println(" ");
        System.out.printf("numero escolhido: ");
        int escolhido = TECLADO.nextInt();

        System.out.println(" ");
        System.out.println("----------------------------------------------------");


        VariaveisGlobais.setNomeImagem((String)arquivos[escolhido-1].getName());

    }

    public static File[] acessandoPasta(){
        String caminho = VariaveisGlobais.getCaminhoPasta();
        File pasta = new File(caminho);
        File[] arquivos = pasta.listFiles();
        if (arquivos != null) {
            int i = 1;
            for (File arquivo : arquivos) {
                String nomeArquivo = arquivo.getName();

                System.out.printf("\t\t %d - %s \n", i, nomeArquivo);
                i++;
            }
            System.out.println();
        }

        return arquivos;
    }


}