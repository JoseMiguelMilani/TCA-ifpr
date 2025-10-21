# TCA-ifpr
Projeto feito para o instituto federal Campus Cascavel, consiste em receber uma imagem, processar essa imagem e então criar um grafo desta imagem

Como usar:
 No terminal escreva:
 "javac VariaveisGlobais.java AlgoritmoUnionFind.java Main.java Grafo.java LendoUmaImagem.java ProcessandoImagem.java telaNova.java"<br>
 "java Main"<br>

como funciona:
 1º- É salvo uma imagem no pasta de imagens<br>
 2º- Esta imagem é transformada em uma matriz tridimensional, [largura][altura][valorRgb]<br>
 4º- Matriz da imagem passa por um algoritmo de Kmeans, que serve para uniformizar as cores<br>
 5º- Matriz separa cada conjunto de valores iguais que estão conectados de forma adjscente<br>
 6º- Essa matriz é transformada em um grafo\n
