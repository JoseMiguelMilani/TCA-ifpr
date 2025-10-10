import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;

public class LendoUmaImagem{

    public static int[][][] lerImagem(String nomeArquivoDeImagem) {

        int larguraImagem;
        int alturaImagem;
        int[][][] matrizImagem = null;


        try{
            imprimirArquivoAcessado(nomeArquivoDeImagem);

            BufferedImage arquivoImagem = ImageIO.read(new File("Imagens/"+nomeArquivoDeImagem));//acessa a imagem

            larguraImagem = arquivoImagem.getWidth();
            alturaImagem = arquivoImagem.getHeight();
            matrizImagem = new int[larguraImagem][alturaImagem][3];

            criarMatriz(arquivoImagem, larguraImagem, alturaImagem, matrizImagem);

            

        }
        catch(IOException exception){//caso não consiga ler o arquivo
            imprimirErroDeAcesso();
            exception.printStackTrace();

        }

        return matrizImagem;
    }
   
   
    public static Scanner TECLADO = new Scanner(System.in);

    public static void imprimirArquivoAcessado(String nomeArquivo){
            System.out.println("---------------------------------------------------------");
            System.out.println("Arquivo selecionado:");
            System.out.println("Imagens/"+nomeArquivo);
            System.out.println("---------------------------------------------------------");
    }

    public static void imprimirErroDeAcesso(){
        System.out.println("---------------------------------------------------------");
        System.out.println("Arquivo não encontrado");
        System.out.println("---------------------------------------------------------");
    }

    public static void porValorNoVetor(int linha,int coluna, int[][][] matrizImagem, int[] rgb){
        matrizImagem[coluna][linha][0] = rgb[0];
        matrizImagem[coluna][linha][1] = rgb[1];
        matrizImagem[coluna][linha][2] = rgb[2];
    }

    public static void definirValoresRgb(int corImagem, int[] rgb){
        rgb[0] = (corImagem >> 16) & 0xFF; 
        rgb[1] = (corImagem >> 8) & 0xFF; 
        rgb[2] = corImagem & 0xFF; 
    }

    public static int[][][] criarMatriz(BufferedImage imagem, int largura, int altura, int[][][] matrizImagem){
        
        int[] rgb = new int[3];
        
        for (int linha = 0; linha < altura; linha++) {
            
            for (int coluna = 0; coluna < largura; coluna++) {
                int corImagem = imagem.getRGB(coluna, linha);

                definirValoresRgb(corImagem, rgb);

                porValorNoVetor(linha, coluna, matrizImagem, rgb);

            }
        }

       //imprimirMatrizImagem(matrizImagem, altura, largura);

        return matrizImagem;
    }

    public static void imprimirMatrizImagem(int[][][] matrizImagem, int altura, int largura) {

    for (int linha = 0; linha < altura; linha++) {
        for (int coluna = 0; coluna < largura; coluna++) {
            int r = matrizImagem[coluna][linha][0];
            int g = matrizImagem[coluna][linha][1];
            int b = matrizImagem[coluna][linha][2];

            System.out.printf("[%d %d] = [%d, %d, %d]\n",linha,coluna,r,g,b);
        }
    }
}






}
