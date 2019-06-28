# import socket
from socket import *
import sys

class Server:
    # Define o Host
    meuIP = ''

    # Utiliza este número de porta
    minhaPort = 50007
    # scanner = Scanner()

    '''
    Cria um objeto Socket. As duas constantes referem-se a:

    Familia do endereço (Padrão é socket.AF_INET)
    Se é stream (socket.SOCK_STREAM, o padrão) ou datagram (socket.SOCK_DGRAM)
    E o protocolo (padrão é 0)

    Da maneira como configuramos:

    AF_INIT == Protocolo de endereço de IP
    SOCK_STREAM == Protocolo de transferência TCP

    Combinação = Server TCP/IP
    '''

    # Criação do Socket
    socket_objeto = socket(AF_INET, SOCK_STREAM)
    print('\n\nSocket criado!!')

    # Tenta vincular o servidor ao número da porta, caso contrário, informa o tipo de erro
    try:
        socket_objeto.bind((meuIP, minhaPort))
        socket_objeto.setsockopt(SOL_SOCKET, SO_REUSEADDR, 1)
    except Exception as error_connect:
        print('\nERRO DE VINCULAÇÃO | Erro: ' + str(error_connect))
        print('SAINDO...\n')
        sys.exit()

    # Vinculação Concluída
    print('Vinculação Socket Concluída!!')

    # O Socket começa a esperar por clientes limitando a 5 conexões por vez
    socket_objeto.listen(5)
    print('Socket aguardando conexões')

    while True:
        '''
        Aceita uma conexão quando encontrada e devolve a
        um novo socket conexão e o endereço do cliente
        conectado
        '''
        conexao, endereco = socket_objeto.accept()
        print("Cliente conectado no Server por: ", endereco[0] + ':' + str(endereco[1]))

        while True:
            # Recebe dado enviado pelo cliente
            mensagem = conexao.recv(1024).decode('ascii')
            # time.sleep(3)
            print('\nServidor recebeu do Cliente: ' + mensagem)
            #print(conexao.getsockname())

            if (mensagem == 'OK'):
                for i in range(2):
                    fileName = 'img' + str(i) + '.png'
                    print('\nFile Name to be read: ', fileName)

                    with open(fileName, 'rb') as imagem:
                        conteudoImagem = imagem.read()

                    tamanho = len(conteudoImagem)
                    print('Imagem bytes: ', tamanho)
                    conexao.sendall(tamanho.to_bytes(4, byteorder='big'))
                    
                    # Recebe a resposta do Receiver (sout.write(size_buff))
                    buff = conexao.recv(4)
                    resp = int.from_bytes(buff, byteorder='big')
                    print("Response:", resp)

                    # Envia o nome (número) da imagem .png
                    fileName = i
                    conexao.sendall(fileName.to_bytes(4, byteorder='big'))

                    # Recebe a resposta do Receiver
                    buff = conexao.recv(4)
                    respFileName = int.from_bytes(buff, byteorder='big')
                    print("File Name readed: ", respFileName)

                    # Se o tamanho lido em Python for igual a resposta recebida em Java
                    if tamanho == resp:
                        conexao.sendall(conteudoImagem)

                    # Receiver envia uma resposta quando terminar de processar uma imagem
                    buff = conexao.recv(1024)
                    print(buff)
                    # Recebe OK do Receiver (sout.write(OK))
                    # buff = conexao.recv(2)
                    # print(buff)

                    #print("Complete.")

            '''
            if (mensagem == 'OK'):
                with open("img1.png", 'rb') as imagem:
                    conteudoImagem = imagem.read()

                tamanho = len(conteudoImagem)
                print('Imagem bytes: ', tamanho)
                conexao.sendall(tamanho.to_bytes(4, byteorder='big'))
                # Recebe a resposta do Receiver (sout.write(size_buff))
                buff = conexao.recv(4)
                resp = int.from_bytes(buff, byteorder='big')
                print("Response:", resp)

                fileName = 1
                conexao.sendall(fileName.to_bytes(4, byteorder='big'))
                buff = conexao.recv(4)
                respFileName = int.from_bytes(buff, byteorder='big')
                print("File Name: ", respFileName)

                # Se o tamanho lido em Python for igual a resposta recebida em Java
                if tamanho == resp:
                    conexao.sendall(conteudoImagem)

                # Recebe OK do Receiver (sout.write(OK))
                # buff = conexao.recv(2)
                # print(buff)

                print("Complete.")
            '''

            # Se não receber nada paramos o loop
            if not mensagem:
                break

        # Fecha a conexão criada depois de responder o Cliente
        conexao.close()











# with open("img1.png", 'rb') as imagem:
#     conteudoImagem = imagem.read()
#
# tamanho = len(conteudoImagem)
# print('Imagem bytes: ', tamanho)



# with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
#     s.connect(("127.0.0.1", 8888))
#
#     with open("img1.png", 'rb') as f:
#         content = f.read()
#
#     size = len(content)
#     print("File bytes:", size)
#     s.sendall(size.to_bytes(4, byteorder='big'))
#
#     # Recebe a resposta do Receiver (sout.write(size_buff))
#     buff = s.recv(4)
#     resp = int.from_bytes(buff, byteorder='big')
#     print("Response:", resp)
#
#     # Se o tamanho lido em Python for igual a resposta recebida em Java
#     if size == resp:
#         s.sendall(content)
#
#     # Recebe OK do Receiver (sout.write(OK))
#     buff = s.recv(2)
#     print(buff)
#
# print("Complete.")
