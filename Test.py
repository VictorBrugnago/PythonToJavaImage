for i in range(2):
    fileName = 'img' + str(i) + '.png'
    print('File Name: ', fileName)
    with open(fileName, 'rb') as imagem:
        conteudoImagem = imagem.read()

    tamanho = len(conteudoImagem)
    print('Imagem bytes: ', tamanho)
    print('\n')

    if i == 1:
        print('Complete.')


'''with open("img1.png", 'rb') as imagem:
    conteudoImagem = imagem.read()

tamanho = len(conteudoImagem)
print('Imagem bytes: ', tamanho)
'''