package Client.GUIControllers.Chat;

import Client.Model.Chat.Message;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

import Client.ClientCenter;
import Client.ServerRequest;

public class Client {

    final static int ServerPort = 8091;
    public String chatOtherSide = "NOBODY";

     private DataInputStream dis;
     private DataOutputStream dos;
     private ClientChatController clientChatController;
     public static ArrayList<String> allClientsArr = new ArrayList<>();

     private static ArrayList<Client> allClients = new ArrayList<>();
     private String username;
     private static int guestCounter = 0;

     private String id;

    public static HashMap<String, UserStatusEnum> activityMap = new HashMap<>();
    static {
        activityMap.put("NOBODY", UserStatusEnum.NO_STATUS);
    }

    public Client(ClientChatController clientChatController) throws IOException {
         this.clientChatController = clientChatController;
        activityMap.put(getId(), UserStatusEnum.ONLINE);

//        clientChatController.activityLabel.setText(activityMap.get(chatOtherSide).toString());

//         new Thread(new Runnable() {
//
//             Socket ssss = new Socket("localhost", 8090);
//             DataInputStream dis2 = new DataInputStream(ssss.getInputStream());
//             DataOutputStream dos2 = new DataOutputStream(ssss.getOutputStream());
//
//             @Override
//             public void run() {
//
//                 while (true) {
//                     try {
//
//                        synchronized (new Object()){
//                            String cont = dis2.readUTF();
////                         getContacts(cont);
//                            String[] contacts = cont.split("#");
//                            allClientsArr.clear();
//                            allClientsArr.addAll(Arrays.asList(contacts));
//                            clientChat.showContacts();
//
//                            dos2.writeUTF("getContacts");
//                        }
//
//                     } catch (IOException e) {
//                         e.printStackTrace();
//                     }
//
//                 }
//             }
//         }).start();
        handleConnection();
        allClients.add(this);
//        allClientsArr.add(this.id);

        System.out.println(allClients.size());
//        for (Client allClient : allClients) {
//            allClient.clientChatController.showContacts();
//            System.out.println("tamam");
//        }


//        System.out.println(allClientsArr.size());

    }

    public void handleConnection() throws IOException {
        final Scanner scn = new Scanner(System.in);

        // establish the connection
        Socket s = new Socket("localhost", ServerPort);

        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETACCOUNT);
        String response = ClientCenter.getInstance().readMessageFromServer();

        if(response.equalsIgnoreCase("guest")){
            Random rand = new Random();

// Obtain a number between [0 - 49].
            int n = rand.nextInt(1000);
            username = response + "-"  + n;
        } else {
            username = response;
        }

        System.out.println(response);

        // obtaining input and out streams
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());

        id = dis.readUTF();
        dos.writeUTF("done!");




        String allClientsStr = dis.readUTF();
        if(allClientsStr.startsWith("allClients")){
            String[] strings = allClientsStr.split("#");
            int a=0;
            for (String string : strings) {
               if(a==1){
                   allClientsArr.add(string);
               }
               a=1;
            }
        }
        dos.writeUTF("allClientsArr");


        dos.writeUTF(("name" + username));
        String n = dis.readUTF();

        // sendMessage thread
        Thread sendMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {
                while (true) {

                    // read the message to deliver.
                    String msg = scn.nextLine();

                    try {
                        // write on the output stream
                        dos.writeUTF(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // readMessage thread
        Thread readMessage = new Thread(new Runnable()
        {
            @Override
            public void run() {

                while (true) {
                    try {
                        // read the message sent to this client




                        dos.writeUTF("getContacts");
                        String cont = dis.readUTF();
                        getContacts(cont);

//                        sendActivityStatus();

//                        String message = "activity&" + chatOtherSide;
//
//                        if(!chatOtherSide.equals("NOBODY")){
//                            dos.writeUTF(message);
//                            String read = dis.readUTF();
//                            Platform.runLater(()->clientChatController.activityLabel.setText(read));
//                        } else {
//                            String read = "NO_STATUS";
//                            Platform.runLater(()->clientChatController.activityLabel.setText(read));
//                        }






                        String msg = dis.readUTF();
                        System.out.println(msg);
                            String sender = msg.substring(0, msg.indexOf(" : "));

                            new Message(sender, username, msg);
                            System.out.println(msg);
                            clientChatController.printMessages();


                    } catch (IOException e) {

                        e.printStackTrace();
                    }
                }
            }
        });



        sendMessage.start();
        readMessage.start();
    }

    public void readMessage(String msg){
        try {
            // write on the output stream
            String[] strings = msg.split("#");
            String message = "You" + ": ";
            message += msg;//strings[0];
            new Message(this.username, chatOtherSide, message);
            if(!chatOtherSide.equals("NOBODY")){
                dos.writeUTF(msg + "#" + chatOtherSide);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFinalMessage() throws IOException {
        dos.writeUTF("finish");
        activityMap.replace(this.getId(), UserStatusEnum.OFFLINE);
    }

    public String getId() {
        return username;
    }

    public void getContacts(String contactsStr) throws IOException {

//        dos.writeUTF("getContacts");
//        String cont = dis.readUTF();
//        getContacts(cont);
        String[] contacts = contactsStr.split("#");
        allClientsArr.clear();
        allClientsArr.addAll(Arrays.asList(contacts));
        clientChatController.showContacts();
    }

    public void sendActivityStatus() {
        try {
            // write on the output stream
            String message = "activity&" + chatOtherSide;
            if(!chatOtherSide.equals("NOBODY")){
                dos.writeUTF(message);
            }
            String read = dis.readUTF();
            Platform.runLater(()->clientChatController.activityLabel.setText(read));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) throws IOException {
//        new TwoByTwoChat.Client();

//
//        dos.writeUTF("getContacts");
//        String cont = dis.readUTF();
//        getContacts(cont);
//    }

}
