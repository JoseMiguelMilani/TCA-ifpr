import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class telaNova {

    public static int alturaTela = 1000;
    public static int larguraTela = 1000;

    //elemento princiapis
    public static void main(String[] args) {
        telaNova tela = new telaNova();
        tela.janelaPrincipal();



    }

    public void janelaPrincipal() {
        JFrame janela = new JFrame();
        configurarJanelaPrincipal(janela);
        
    }

    public JButton criarBotaoAcessarInformacao() {
        JButton botao = new JButton("-INFORMAÇÕES-");
        configurarBotaoInformacao(botao);

        return botao;
    }

    public JButton criarBotaoAnimacao(){
        JButton botaoAnima = new JButton("-ANIMAÇÃO-");
        configurarBotaoAnimacao(botaoAnima);

        return botaoAnima;
    }

    public static void criarAnimacao(JLabel janelaAnimacao, JLabel posicao) {
    int quantiaElemento = VariaveisGlobais.getQuantiaElemento();
    String nomeArquivo = VariaveisGlobais.getNomeImagem();
    int[][] matriz = VariaveisGlobais.getMatrizPronta();
    int[] posVerde = VariaveisGlobais.getPosVerde();
    int[] posVermelha = VariaveisGlobais.getPosVermelha();
    int quantiaCaminho = VariaveisGlobais.getQuantiaPassos();
    int[] vetorPasso = VariaveisGlobais.getListaCaminho();

    int inicio = matriz[posVerde[0]][posVerde[1]];
    int fim = matriz[posVermelha[0]][posVermelha[1]];
    
    int[][][] imagemMatriz = new int[matriz.length][matriz[0].length][3];
    
    BufferedImage imagem = new BufferedImage(matriz[0].length, matriz.length, BufferedImage.TYPE_INT_RGB);

    imagemMatriz = criarImagemInicial(quantiaElemento, nomeArquivo, imagemMatriz, matriz, imagem);

    BufferedImage imagemAtual = imagemCriar(imagem, imagemMatriz);
    
    atualizarImagem(janelaAnimacao, imagem);

    esperar(500);

    //ponto inicial (verde)
    imagemMatriz = pintarPonto(imagemMatriz, inicio, 1, imagem, matriz, nomeArquivo);
    imagemAtual = imagemCriar(imagem, imagemMatriz);
    atualizarImagem(janelaAnimacao, imagem);
    atualizarOndeEstou(posicao, inicio);

    esperar(500);

    //ponto final (vermelho)
    imagemMatriz = pintarPonto(imagemMatriz, fim, 0, imagem, matriz, nomeArquivo);
    imagemAtual = imagemCriar(imagem, imagemMatriz);
    atualizarImagem(janelaAnimacao, imagem);
    atualizarOndeEstou(posicao, fim);

    esperar(500);

    //caminho (azul)
    for (int i = 0; i < quantiaCaminho - 1; i++) {
        pintarPonto(imagemMatriz, vetorPasso[vetorPasso.length - 2 - i], 2, imagem, matriz, nomeArquivo);
        imagemAtual = imagemCriar(imagem, imagemMatriz);
        atualizarImagem(janelaAnimacao, imagem);
        atualizarOndeEstou(posicao, vetorPasso[vetorPasso.length - 1 - i]);
        esperar(500);
    }
    }

    public static int[][][] criarImagemInicial(int quantiaElemento, String nomeArquivo, int[][][] imagemMatriz, int[][] matriz, BufferedImage imagem) {

    for (int i = 0; i < matriz.length; i++) {
        for (int j = 0; j < matriz[0].length; j++) {
            int valorAtual = matriz[i][j];
            

            for (int k = 0; k < 3; k++) {

                if(valorAtual == 0){
                    imagemMatriz[i][j][k] = 255;
                }else{
                    imagemMatriz[i][j][k] = 0;
                }
                
            }
        }
    }
    return imagemMatriz;
    }

    private static JLabel criarJanelaOndeEstou(){
        JLabel posicao = new JLabel("iniciando Processo");
        posicao.setBounds(40, 20, 30, 40);
        posicao.setFont(new Font("Arial", Font.BOLD, 20));

        return posicao;
    }

    private static void criarCaminhoBfs(JFrame janela){
        int[] vetor = VariaveisGlobais.getListaCaminho();

        String texto = "" +vetor[0];

        for (int i = 0; i < vetor.length; i++) {
            texto += " <-"+vetor[i];
        }

        JLabel caminho = new JLabel(texto);
        caminho.setBounds(300, 600, 300, 30);
        caminho.setFont(new Font("Arial", Font.BOLD, 14));

        janela.add(caminho);

    }
    ////funções botao

    private void mostrarAnimação(ActionEvent evento){

        int tamanhoJanelaPrincipal = 600;

        JFrame janelaAnimacao = new JFrame();
        configurarTelaAnimacao(janelaAnimacao, tamanhoJanelaPrincipal);

        JLabel imagemAnimacao = new JLabel();
        imagemAnimacao.setHorizontalAlignment(JLabel.CENTER);
        janelaAnimacao.add(imagemAnimacao, BorderLayout.CENTER);

        janelaAnimacao.setVisible(true);

        JFrame janelaOndeEstou = new JFrame();
        JLabel ondeEstou = configurarJanelaPosicao(janelaOndeEstou, tamanhoJanelaPrincipal, janelaOndeEstou);


        new Thread(() -> {
            criarAnimacao(imagemAnimacao, ondeEstou);
        }).start();
    }

    private void mostrarGrafo(ActionEvent evento) {
        JFrame janelaImagem = new JFrame();

        int larguraGrafo = larguraTela/2;
        int alturGrafo =  alturaTela/2;

        janelaImagem.setLayout(new BorderLayout());
        
        //configs
        janelaImagem.setSize(larguraGrafo, alturGrafo);
        janelaImagem.setTitle("Imagem Grafo");
        janelaImagem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaImagem.setResizable(false);
        janelaImagem.setLocationRelativeTo(null);

        String nomeArquivo = VariaveisGlobais.getNomeImagem();
        
        String caminhoImagem = "output/"+ nomeArquivo;
            
            

        ImageIcon imagem = new ImageIcon(caminhoImagem);
        Image img = imagem.getImage();
        Image novaImg = img.getScaledInstance(larguraGrafo-20, alturGrafo-20, Image.SCALE_SMOOTH);
        imagem = new ImageIcon(novaImg);

        JLabel labelImagem = new JLabel(imagem);
        labelImagem.setHorizontalAlignment(JLabel.CENTER);
            
        janelaImagem.add(labelImagem, BorderLayout.CENTER);
            
        

        janelaImagem.setVisible(true);
    }

    private void mostrarTabelaElementos(ActionEvent evento) {
        JFrame janelaImagem = new JFrame();
        
        int quantiaElemento = VariaveisGlobais.getQuantiaElemento();
        int largura = ((int) quantiaElemento / 15) * larguraTela/5 + 10;
        int altura = 520;
        configurarTelaInformacoes(janelaImagem, largura, altura);

        int[][] corElemento = VariaveisGlobais.getCorPorElemento();

        int posX = 10;
        int posY = 10;

        for (int i = 0; i < quantiaElemento; i++) {

            if (posY > altura) {
                posY = 10;
                posX += 50;
            }

            JLabel elemento = new JLabel("" + i);
            elemento.setBounds(posX + 25, posY, 100, 20);
            
            janelaImagem.add(criarQuadrado(posX, posY, corElemento, i));
            janelaImagem.add(elemento);
            
            posY += 30;
        }
        

        janelaImagem.setVisible(true);
    }

    //funções

    private static int[] inverterVetor(int[]vetor){

        int momentaneo;
        int tamanhoVetor = vetor.length-1;

        for (int i = 0; i < vetor.length/2; i++) {
            momentaneo = vetor[i];
            vetor[i] = vetor[tamanhoVetor-i];
            vetor[tamanhoVetor-i] = momentaneo;
            
        }


        return vetor;
    }

    private static void esperar(int milissegundos) {
    try {
        Thread.sleep(milissegundos);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    }

    private static void atualizarImagem(JLabel painel, BufferedImage imagem) {

    BufferedImage image = new BufferedImage(imagem.getHeight(), imagem.getWidth(), BufferedImage.TYPE_INT_RGB);

    rotacionarImagem(imagem, 90);
    
    for (int x = 0; x < imagem.getWidth(); x++) {
        for (int y = 0; y < imagem.getHeight(); y++) {
            int rgb = imagem.getRGB(x, y);
            image.setRGB(y, imagem.getWidth() - 1 - x, rgb);
        }
    }

    ImageIcon icon = new ImageIcon(imagem.getScaledInstance(500, 500, Image.SCALE_SMOOTH));
    
    javax.swing.SwingUtilities.invokeLater(() -> {
        painel.setIcon(icon);
        painel.repaint();
    });
    }

    private static void atualizarOndeEstou(JLabel posicao, int i){

        javax.swing.SwingUtilities.invokeLater(() -> {
        posicao.setText(""+i);
        });
        
    }

    private static BufferedImage imagemCriar(BufferedImage imagem, int[][][] imagemMatriz) {
    for (int i = 0; i < imagemMatriz.length; i++) {
        for (int j = 0; j < imagemMatriz[0].length; j++) {
            int r = imagemMatriz[i][j][0];
            int g = imagemMatriz[i][j][1];
            int b = imagemMatriz[i][j][2];

            Color corDoPonto = new Color(r, g, b);
            imagem.setRGB(j, i, corDoPonto.getRGB());
        }
    }
    return imagem;
    }

    public static int[][][] pintarPonto(int[][][] imagemMatriz, int valorProcurado, int cor, BufferedImage imagem, int[][] matriz, String nomeArquivo) {
    for (int i = 0; i < matriz.length; i++) {
        for (int j = 0; j < matriz[0].length; j++) {
            int valorAtual = matriz[i][j];

            if (valorAtual == valorProcurado) {

                for (int k = 0; k < 3; k++) {
                    if (k == cor) {
                        imagemMatriz[i][j][k] = 255;
                    } else {
                        imagemMatriz[i][j][k] = 0;
                    }
                }
            }
        }
    }
    return imagemMatriz;
}

    public static JPanel criarQuadrado(int posX, int posY, int[][] corElemento, int i){
        JPanel quadradoColorido = new JPanel();

        quadradoColorido.setBounds(posX, posY, 20, 20);

        Color cor = new Color(corElemento[i][0], corElemento[i][1], corElemento[i][2]);

        quadradoColorido.setBackground(cor);

        quadradoColorido.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));

        return quadradoColorido;

    }

    public static void criarLogoIfpr(JFrame janela){
        try{
            File arquivoLogoIf = new File("Imagens/logoIf.png");

            BufferedImage imagemLogoIf = ImageIO.read(arquivoLogoIf);
            
            Image imagemtratada = imagemLogoIf.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            ImageIcon imagemIfpr = new ImageIcon(imagemtratada);

            JLabel imagem = new JLabel(imagemIfpr);
            imagem.setBounds(470, 20, 200, 100); 

            janela.add(imagem);

        }catch(IOException erro){
            System.out.println("arquivo não encontrado");
            erro.printStackTrace();
        }

    }

    public static void criarImagemGrafoOringinal(JFrame janela){

        String nomeImagem = VariaveisGlobais.getNomeImagem();
        String enderecoImagem = "Imagens/" + nomeImagem;

        try{
            File arquivoImagem = new File(enderecoImagem);

            BufferedImage imagemPng = ImageIO.read(arquivoImagem);
            
            Image imagemtratada = imagemPng.getScaledInstance(400, 500, Image.SCALE_SMOOTH);
            ImageIcon imagem = new ImageIcon(imagemtratada);

            JLabel Jlimagem = new JLabel(imagem);
            Jlimagem.setBounds(10, 30, 400, 500); 

            janela.add(Jlimagem);

        }catch(IOException erro){
            System.out.println("arquivo não encontrado");
            erro.printStackTrace();
        }

    }

    public static void criarCaixaInformacao(JFrame janela){

        JPanel quadrado = new JPanel();

        int PosQua[] = {450, 150};

        quadrado.setBounds(PosQua[0], PosQua[1], 250, 350);

        quadrado.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));


        adicionarTamanho(janela, PosQua, 1);
        adicionarQuantiaElementos(janela, PosQua, 2);
        adicionarTempoQueRodou(janela, PosQua, 3);
        adicionarArquivoAcessado(janela, PosQua, 4);
        criarCaminhoBfs(janela);


        janela.add (quadrado);
        

    }

    public static void adicionarTamanho(JFrame janela, int PosQua[], int posicao){
        int matriz[][] = VariaveisGlobais.getMatrizPronta();
        int largura = matriz.length;
        int altura = matriz[0].length;

        int poslargura = PosQua[0]+10;
        int posaltura = PosQua[1]+(30*posicao);

        JLabel tamanho = new JLabel("Largura = "+ largura + "  Altura = "+ altura);
        tamanho.setBounds(poslargura, posaltura, 300, 30);
        tamanho.setFont(new Font("Arial", Font.BOLD, 14));
        janela.add(tamanho);
    }

    public static void adicionarQuantiaElementos(JFrame janela, int PosQua[], int posicao){
        int quantiaElemento = VariaveisGlobais.getQuantiaElemento();

        int poslargura = PosQua[0]+10;
        int posaltura = PosQua[1]+(30*posicao);

        JLabel tamanho = new JLabel("Quantia de elementos = "+ quantiaElemento);
        tamanho.setBounds(poslargura, posaltura, 300, 30);
        tamanho.setFont(new Font("Arial", Font.BOLD, 14));
        janela.add(tamanho);
    }

    public static void adicionarTempoQueRodou(JFrame janela, int PosQua[], int posicao){
        double tempo = ((double)VariaveisGlobais.getTempoQueRodou())/1000.00;

        int poslargura = PosQua[0]+10;
        int posaltura = PosQua[1]+(30*posicao);

        JLabel tamanho = new JLabel("Codigo rodou por "+ tempo + " segundos");
        tamanho.setBounds(poslargura, posaltura, 300, 30);
        tamanho.setFont(new Font("Arial", Font.BOLD, 14));
        janela.add(tamanho);
    }

    public static void adicionarArquivoAcessado(JFrame janela, int PosQua[], int posicao){
        String arquivo = VariaveisGlobais.getNomeImagem();
        

        int poslargura = PosQua[0]+10;
        int posaltura = PosQua[1]+(30*posicao);

        JLabel tamanho = new JLabel("Acessou :" + arquivo);
        tamanho.setBounds(poslargura, posaltura, 300, 30);
        tamanho.setFont(new Font("Arial", Font.BOLD, 14));
        janela.add(tamanho);
    }

    public static String[] lerNomesArquivosImagens(String caminhoPasta) {
        File pasta = new File(caminhoPasta);
        
        String[] arquivos = pasta.list((dir, nome) -> nome.toLowerCase().endsWith(".png"));
        
        if (arquivos == null) {
            System.out.println("Pasta não encontrada ou não é um diretório");
            return new String[0];
        }
        
        return arquivos;
    }
    

    //configurações

    private static JLabel configurarJanelaPosicao(JFrame janelaOndeEstou, int tamanhoJanelaPrincipal, JFrame janelaAnimacao){
        janelaOndeEstou.setSize(larguraTela/10, tamanhoJanelaPrincipal/10);
        janelaOndeEstou.setTitle("Onde estou");
        janelaOndeEstou.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaOndeEstou.setLocationRelativeTo(janelaAnimacao);
        janelaOndeEstou.setLayout(new BorderLayout());
        janelaOndeEstou.setLocation(tamanhoJanelaPrincipal+300, tamanhoJanelaPrincipal-(tamanhoJanelaPrincipal/2)*2+(tamanhoJanelaPrincipal/7));
        JLabel ondeEstou = criarJanelaOndeEstou();
        janelaOndeEstou.add(ondeEstou);
        janelaOndeEstou.setVisible(true);

        return ondeEstou;
    }

    private void configurarJanelaPrincipal(JFrame  janela){
        janela.setSize(larguraTela- larguraTela/4, larguraTela- larguraTela/4);
        janela.setTitle("TCA-Jose Miguel Milani");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setResizable(false);
        janela.setLocationRelativeTo(null);
        janela.setLayout(null);
        janela.add(criarBotaoAcessarInformacao());
        janela.add(criarBotaoAnimacao());
        criarLogoIfpr(janela);
        criarImagemGrafoOringinal(janela);
        criarCaixaInformacao(janela);
    
        

        
        janela.setVisible(true);
    }

    private void configurarBotaoInformacao(JButton botao){
        botao.setBounds(100, 550, 200, 40);
        botao.setFont(new Font("Arial", Font.BOLD, 20));

        botao.addActionListener(this::mostrarGrafo);
        botao.addActionListener(this::mostrarTabelaElementos);
    }

    private void configurarBotaoAnimacao(JButton botaoAnima ){
        botaoAnima.setBounds(400, 550, 200, 40);
        botaoAnima.setFont(new Font("Arial", Font.BOLD, 20));

        botaoAnima.addActionListener(this::mostrarAnimação);
    }

    private void configurarTelaAnimacao(JFrame janelaAnimacao, int tamanhoJanelaPrincipal){
        janelaAnimacao.setSize(tamanhoJanelaPrincipal, tamanhoJanelaPrincipal);
        janelaAnimacao.setTitle("Animação do Caminho");
        janelaAnimacao.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaAnimacao.setLocationRelativeTo(null);
        janelaAnimacao.setLayout(new BorderLayout());
    }

    private void configurarTelaInformacoes(JFrame janelaImagem, int largura, int altura){
        janelaImagem.setLayout(null);
        janelaImagem.setSize(largura, altura);
        janelaImagem.setTitle("Imagem Grafo");
        janelaImagem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaImagem.setResizable(false);
        janelaImagem.setLocationRelativeTo(null);
        janelaImagem.setLocation(largura+300, altura-(altura/2)*2+(altura/7));
    }

   private static BufferedImage rotacionarImagem(BufferedImage imagemOriginal, int anguloGraus) {
    int largura = imagemOriginal.getWidth();
    int altura = imagemOriginal.getHeight();
    

    int novaLargura, novaAltura;
    if (anguloGraus == 90 || anguloGraus == 270) {
        novaLargura = altura;
        novaAltura = largura;
    } else {
        novaLargura = largura;
        novaAltura = altura;
    }
    
    BufferedImage imagemRotacionada = new BufferedImage(novaLargura, novaAltura, BufferedImage.TYPE_INT_RGB);
    
    switch (anguloGraus) {
        case 90:

            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {
                    int rgb = imagemOriginal.getRGB(x, y);
                    imagemRotacionada.setRGB(altura - 1 - y, x, rgb);
                }
            }
            break;
            
        case 180:
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {
                    int rgb = imagemOriginal.getRGB(x, y);
                    imagemRotacionada.setRGB(largura - 1 - x, altura - 1 - y, rgb);
                }
            }
            break;
            
        case 270:
            for (int x = 0; x < largura; x++) {
                for (int y = 0; y < altura; y++) {
                    int rgb = imagemOriginal.getRGB(x, y);
                    imagemRotacionada.setRGB(y, largura - 1 - x, rgb);
                }
            }
            break;
            
        default:
            return imagemOriginal;
    }
    
    return imagemRotacionada;
    }

    private static void criarImagemDeMatriz3D(int[][][] matriz3D, int n) {
    int altura = matriz3D.length;
    int largura = matriz3D[0].length;
    
    BufferedImage imagem = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
    
    for (int y = 0; y < altura; y++) {
        for (int x = 0; x < largura; x++) {
            int r = matriz3D[y][x][0];
            int g = matriz3D[y][x][1];
            int b = matriz3D[y][x][2];
            
            Color cor = new Color(r, g, b);
            imagem.setRGB(x, y, cor.getRGB());
        }
    }
    try {
        
        File arquivo = new File("output/" + "porra" + n);
        ImageIO.write(imagem, "png", arquivo);

        System.out.println("Imagem salva como: " + arquivo.getAbsolutePath());

        } catch (Exception e) {
            System.out.println("Erro ao salvar imagem: " + e.getMessage());
        }
    
    
}

}