import java.util.Scanner;

public class VariaveisGlobais {

    public static Scanner TECLADO = new Scanner(System.in);

    private static int[][] matrizProcessada;

    private static int[][] matrizPronta;

    private static int[][][] imagem;

    private static int quantiaPontosDeCor = 6;
    
    private static int quantIteracoes = 20;

    private static int tentativas = 20;

    private static String nomeImagem = "imagem5.png";

    private static int[] posVerde;

    private static int[] posVermelha;














    //getter e setters

    public static int[][] getMatrizProcessada() {
        return matrizProcessada;
    }

    public static void setMatrizProcessada(int[][] matrizProcessada) {
        VariaveisGlobais.matrizProcessada = matrizProcessada;
    }

    public static int[][][] getImagem() {
        return imagem;
    }

    public static void setImagem(int[][][] imagem) {
        VariaveisGlobais.imagem = imagem;
    }

    public static int getQuantiaPontosDeCor() {
        return quantiaPontosDeCor;
    }

    public static void setQuantiaPontosDeCor(int quantiaPontosDeCor) {
        VariaveisGlobais.quantiaPontosDeCor = quantiaPontosDeCor;
    }

    public static int[][] getMatrizPronta() {
        return matrizPronta;
    }

    public static void setMatrizPronta(int[][] matrizPronta) {
        VariaveisGlobais.matrizPronta = matrizPronta;
    }

    public static String getNomeImagem() {
        return nomeImagem;
    }

    public static void setNomeImagem(String nomeImagem) {
        VariaveisGlobais.nomeImagem = nomeImagem;
    }

    public static int getQuantIteracoes() {
        return quantIteracoes;
    }

    public static void setQuantIteracoes(int quantIteracoes) {
        VariaveisGlobais.quantIteracoes = quantIteracoes;
    }

    public static int getTentativas() {
        return tentativas;
    }

    public static void setTentativas(int tentativas) {
        VariaveisGlobais.tentativas = tentativas;
    }

    public static int[] getPosVerde() {
        return posVerde;
    }

    public static void setPosVerde(int[] posVerde) {
        VariaveisGlobais.posVerde = posVerde;
    }

    public static int[] getPosVermelha() {
        return posVermelha;
    }

    public static void setPosVermelha(int[] posVermelha) {
        VariaveisGlobais.posVermelha = posVermelha;
    }



    
    
}
