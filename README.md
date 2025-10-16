# TCA-ifpr
Projeto feito para o instituto federal Campus Cascavel, consiste em receber uma imagem, processar essa imagem e então criar um grafo desta imagem

Como usar:
 No terminal escreva:
 "javac Main.java VariaveisGlobais.java LendoUmaImagem.java Kmeans.java Grafo.java CriandoGrafo.java"\n
 "java Main"\n

como funciona:
 1º- É salvo uma imagem no pasta de imagens\n
 2º- Esta imagem é transformada em uma matriz tridimensional, [largura][altura][valorRgb]\n
 4º- Matriz da imagem passa por um algoritmo de Kmeans, que serve para uniformizar as cores\n
 5º- Matriz separa cada conjunto de valores iguais que estão conectados de forma adjscente\n
 6º- Essa matriz é transformada em um grafo\n
