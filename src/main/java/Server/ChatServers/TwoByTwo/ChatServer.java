package Server.ChatServers.TwoByTwo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {


    //    private static Vector<ClientHandler> allClients = new Vector<>();
    private static int numberOfClients = 0;

    private ServerSocket serverSocket = new ServerSocket(9091);
    private Socket server;

    private static ChatServer chatServerInstance;

    static {
        try {
            chatServerInstance = new ChatServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ChatServer getInstance(){
        return chatServerInstance;
    }

    public ChatServer() throws IOException {

        //Infinite loop for getting clients
        while (true) {

            server = serverSocket.accept();
            System.out.println("New client request received : " + server);

            DataInputStream dis = new DataInputStream(server.getInputStream());
            DataOutputStream dos = new DataOutputStream(server.getOutputStream());

            dos.writeUTF(String.valueOf(numberOfClients));
            dis.readUTF();

            System.out.println("Creating a new handler for this client...");
            ChatClientHandler cHandler = new ChatClientHandler(server, String.valueOf(numberOfClients), dis, dos);
            System.out.println("Adding this client to active client list");
//            allClients.add(cHandler);
//            ClientChat.allClients.add(cHandler);
            System.out.println("done!");

            Thread t = new Thread(cHandler);
            t.start();

            numberOfClients++;
        }
    }
}
