package Server.Model.BankPrime;

import javax.xml.crypto.Data;
import java.awt.dnd.DropTarget;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BankClient {
    public static void main(String[] args) {
        new ClientImplement().run();
    }

    public static class ClientImplement{
//        public final int PORT = 9000;
//        public final int SERVER_PORT = 8080;

        private DataOutputStream dos;
        private DataInputStream dis;
        private DataOutputStream shopDos;
        private DataInputStream shopDis;

        private String bankToken;

        public void connectToBankServer() throws IOException {
            Socket s = new Socket("localHost" ,9000);
            dos = new DataOutputStream(s.getOutputStream());
            dis = new DataInputStream(s.getInputStream());

            throw new IOException("Connection faces a problem !");
        }

        public void startListeningOnInput() {
            new Thread(() -> {
                while (true) {
                    try {
                        shopDos.writeUTF(dis.readUTF());
                    } catch (IOException e) {
                        System.out.println("disconnected");
                        System.exit(0);
                    }
                }
            }).start();
        }

        public void sendMessage (String message) throws IOException {
            dos.writeUTF(message);
            throw new IOException("Sending Message faces a problem!");
        }

        public void run(){
            try{
                connectToBankServer();
                startListeningOnInput();
                while (true){
                    String messageToSend = shopDis.readUTF();
                    sendMessage(messageToSend);
                }
            }catch (IOException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
