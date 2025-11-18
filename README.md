# TCA-ifpr
Projeto feito para o instituto federal Campus Cascavel, consiste em receber uma imagem, processar essa imagem e então criar um grafo desta imagem

Como usar:
 No terminal escreva:
  *baixe todos os arquivos do github
  *escolha uma imagem(mudando o nome no VariaveisGlobais.java)
  *rode o Main

como funciona:
 1º- É salvo uma imagem no pasta de imagens<br>
 2º- Esta imagem é transformada em uma matriz tridimensional, [largura][altura][valorRgb]<br>
 3º- Matriz da imagem uniformizar as cores, virando uma matriz bidimensional<br>
 4º- Matriz separa cada conjunto de valores iguais que estão conectados de forma adjscente<br>
 5º- Essa matriz é transformada em um grafo\n
 6º- Grafo passa pelo algoritmo Djikstra, vendo o menor caminho em quantia de pixeis
 7º- Abre uma tela Jpanel
