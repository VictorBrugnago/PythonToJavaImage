import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;


import javax.imageio.ImageIO;

class Server{

    public static void main(String[] args) {
        String exception = null;
        String ipServer = "localhost";
        int portaServer = 50007;
        int tempoTimeout = 5000;
        Socket clientSocket = new Socket();
        int fileName;

        try {
            clientSocket.connect(new InetSocketAddress(ipServer, portaServer), tempoTimeout);

            OutputStream sout = clientSocket.getOutputStream();
            InputStream sin = clientSocket.getInputStream();

            System.out.println("GOT CONNECTION FROM: " + clientSocket.getInetAddress().toString());
            byte[] OK = new byte[] {0x4F, 0x4B};
            sout.write(OK);

            do {
                // Recebe o tamanho da imagem (em bytes)
                byte[] size_buff = new byte[4];
                sin.read(size_buff);
                int size = ByteBuffer.wrap(size_buff).asIntBuffer().get();
                System.out.format("\n\nExpecting %d bytes\n", size);

                // Send it back (?)
                sout.write(size_buff);

                // Recebe o nome do arquivo
                byte[] fileName_buff = new byte[4];
                sin.read(fileName_buff);
                fileName = ByteBuffer.wrap(fileName_buff).asIntBuffer().get();
                System.out.format("File name: img%d.png\n", fileName);

                // Send it back
                sout.write(fileName_buff);

                // Create Buffers
                byte[] msg_buff = new byte[1024];
                byte[] img_buff = new byte[size];
                int img_offset = 0;
                while(true) {
                    int bytes_read = sin.read(msg_buff, 0, msg_buff.length);
                    if(bytes_read == -1) { break; }

                    // Copy bytes into img_buff
                    System.arraycopy(msg_buff, 0, img_buff, img_offset, bytes_read);
                    img_offset += bytes_read;
                    System.out.format("Read %d / %d bytes...\n", img_offset, size);

                    if(img_offset >= size) { break; }
                }
                sout.write(OK);
                BufferedImage image = ImageIO.read(new ByteArrayInputStream(img_buff));
                ImageIO.write(image, "png", new File("image\\" + fileName + ".png"));

                if (fileName == 1) {
                    //byte[] Finish = new byte[] {0x46, 0x69, 0x6e, 0x69, 0x73, 0x68};
                    //sout.write(Finish);
                    clientSocket.close();
                }

                // Send "OK"
//            byte[] Ok = new byte[] {0x4F, 0x4B};
//            sout.write(Ok);
            } while (fileName < 1);
//            // Recebe o tamanho da imagem (em bytes)
//            byte[] size_buff = new byte[4];
//            sin.read(size_buff);
//            int size = ByteBuffer.wrap(size_buff).asIntBuffer().get();
//            System.out.format("Expecting %d bytes\n", size);
//
//            // Send it back (?)
//            sout.write(size_buff);
//
//            // Recebe o nome do arquivo
//            byte[] fileName_buff = new byte[4];
//            sin.read(fileName_buff);
//            int fileName = ByteBuffer.wrap(fileName_buff).asIntBuffer().get();
//            System.out.format("File name: %d\n", fileName);
//
//            // Send it back
//            sout.write(fileName_buff);
//
//            // Create Buffers
//            byte[] msg_buff = new byte[1024];
//            byte[] img_buff = new byte[size];
//            int img_offset = 0;
//            while(true) {
//                int bytes_read = sin.read(msg_buff, 0, msg_buff.length);
//                if(bytes_read == -1) { break; }
//
//                // Copy bytes into img_buff
//                System.arraycopy(msg_buff, 0, img_buff, img_offset, bytes_read);
//                img_offset += bytes_read;
//                System.out.format("Read %d / %d bytes...\n", img_offset, size);
//
//                if(img_offset >= size) { break; }
//            }
//            BufferedImage image = ImageIO.read(new ByteArrayInputStream(img_buff));
//            ImageIO.write(image, "png", new File("image\\test-output.png"));
//
//            if (fileName == 2) {
//
//            }
//            clientSocket.close();
//
//            // Send "OK"
////            byte[] Ok = new byte[] {0x4F, 0x4B};
////            sout.write(Ok);
        } catch (SocketTimeoutException timeout) {
            timeout.printStackTrace();
            exception = "Tempo limite de conexão atingido! Tente novamente...";
        } catch (IOException e) {
            e.printStackTrace();
            exception = "Erro de conexão! Tente novamente...";
        }


    }
//        int PORT_NUMBER = 8888;
//
//        try (
//                ServerSocket server = new ServerSocket(PORT_NUMBER);
//                Socket client = server.accept();
//                OutputStream sout = client.getOutputStream();
//                InputStream sin = client.getInputStream();
//        ){
//            System.out.println("GOT CONNECTION FROM: " + client.getInetAddress().toString());
//
//            // Recebe o tamanho da imagem (em bytes)
//            byte[] size_buff = new byte[4];
//            sin.read(size_buff);
//            int size = ByteBuffer.wrap(size_buff).asIntBuffer().get();
//            System.out.format("Expecting %d bytes\n", size);
//
//            // Send it back (?)
//            sout.write(size_buff);
//
//            // Create Buffers
//            byte[] msg_buff = new byte[1024];
//            byte[] img_buff = new byte[size];
//            int img_offset = 0;
//            while(true) {
//                int bytes_read = sin.read(msg_buff, 0, msg_buff.length);
//                if(bytes_read == -1) { break; }
//
//                // Copy bytes into img_buff
//                System.arraycopy(msg_buff, 0, img_buff, img_offset, bytes_read);
//                img_offset += bytes_read;
//                System.out.format("Read %d / %d bytes...\n", img_offset, size);
//
//                if(img_offset >= size) { break; }
//            }
//            BufferedImage image = ImageIO.read(new ByteArrayInputStream(img_buff));
//            ImageIO.write(image, "png", new File("image\\test-output.png"));
//
//            // Send "OK"
//            byte[] OK = new byte[] {0x4F, 0x4B};
//            sout.write(OK);
//        }
//        catch (IOException ioe) { ioe.printStackTrace(); }
//    }
}