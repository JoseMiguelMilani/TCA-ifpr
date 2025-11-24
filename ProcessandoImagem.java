
public class ProcessandoImagem{

    public static void processarImagem(int[][][]imagem){

        int tamanhoLinhaImagem = imagem.length;
        int tamanhoColunaImagem = imagem[0].length;

        int[][] matrizProcessada = new int[tamanhoLinhaImagem][tamanhoColunaImagem]; 

        trocarValores(imagem, matrizProcessada, tamanhoLinhaImagem, tamanhoColunaImagem);
        VariaveisGlobais.setMatrizProcessada(matrizProcessada);
        
    }

    public static void trocarValores(int[][][] imagem, int[][] matriz, int tamanhoLinhaImagem, int tamanhoColunaImagem){

        for (int i = 0; i < tamanhoLinhaImagem; i++) {//percorre o vetor inteiro, definindo o valor rgb das posições
            
            for (int j = 0; j < tamanhoColunaImagem; j++) {

                matriz[i][j] = somatoriaDosRgb(imagem, i, j);
                
                if (matriz[i][j] == 255*3) {//se for branco
                    matriz[i][j] = -1;
                }
            }
        }

        
    }

    public static int somatoriaDosRgb(int[][][] imagem, int i, int j){

        int somatoria = 0;

        for (int valorRgb = 0; valorRgb < 3; valorRgb++) {
            somatoria += imagem[i][j][valorRgb];
        }

        return somatoria;
    }
}