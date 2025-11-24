import java.util.Scanner;

public class VariaveisGlobais {

    public static Scanner TECLADO = new Scanner(System.in);

    private static String caminhoPasta = "C:\\programas\\projetos\\2025\\TCA-JoseMiguel"+"\\Imagens";

    //variaveis que recebem valor durante o codigo

    public static String getCaminhoPasta() {
        return caminhoPasta;
    }

    public static void setCaminhoPasta(String caminhoPasta) {
        VariaveisGlobais.caminhoPasta = caminhoPasta;
    }

    private static int[] posVerde;

    private static int[] posVermelha;

    private static int[] posIniciais;

    private static int[][][] imagem;

    private static int[][] matrizProcessada;

    private static int[][] matrizPronta;

    private static int quantiaElemento;
    
    private static int[][] corPorElemento;

    private static int quantiaPassos;

    private static int[] listaCaminho;

    private static long tempoQueRodou;

    private static int[][] valorLimites;

    private static int[] distancia;













    //getter e setters


    public static int[] getDistancia() {
        return distancia;
    }

    public static void setDistancia(int[] distancia) {
        VariaveisGlobais.distancia = distancia;
    }

    public static int[][] getValorLimites() {
        return valorLimites;
    }

    public static void setValorLimites(int[][] valorLimites) {
        VariaveisGlobais.valorLimites = valorLimites;
    }

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

    public static int[][] getMatrizPronta() {
        return matrizPronta;
    }

    public static void setMatrizPronta(int[][] matrizPronta) {
        VariaveisGlobais.matrizPronta = matrizPronta;
    }

    public static String getNomeImagem() {
        return caminhoPasta;
    }

    public static void setNomeImagem(String nomeImagem) {
        VariaveisGlobais.caminhoPasta = nomeImagem;
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

    public static int[] getPosIniciais() {
        return posIniciais;
    }

    public static void setPosIniciais(int[] posIniciais) {
        VariaveisGlobais.posIniciais = posIniciais;
    }

    public static int getQuantiaElemento() {
        return quantiaElemento;
    }

    public static void setQuantiaElemento(int quantiaElemento) {
        VariaveisGlobais.quantiaElemento = quantiaElemento;
    }

    public static int[][] getCorPorElemento() {
        return corPorElemento;
    }

    public static void setCorPorElemento(int[][] corPorElemento) {
        VariaveisGlobais.corPorElemento = corPorElemento;
    }

    public static int getQuantiaPassos() {
        return quantiaPassos;
    }

    public static void setQuantiaPassos(int quantiaPassos) {
        VariaveisGlobais.quantiaPassos = quantiaPassos;
    }

    public static int[] getListaCaminho() {
        return listaCaminho;
    }

    public static void setListaCaminho(int[] listaCaminho) {
        VariaveisGlobais.listaCaminho = listaCaminho;
    }

    public static long getTempoQueRodou() {
        return tempoQueRodou;
    }

    public static void setTempoQueRodou(long tempoQueRodou) {
        VariaveisGlobais.tempoQueRodou = tempoQueRodou;
    }



    
    
}
