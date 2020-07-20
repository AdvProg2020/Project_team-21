package Client.GUIControllers.Chat;

import Server.Model.Chat.Group_Message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Group_Client {

    final static int ServerPort = 9091;
    public String chatOtherSide = "NOBODY";

     private DataInputStream dis;
     private DataOutputStream dos;
     private Group_ClientChatController groupClientChatController;
     public static ArrayList<String> allClientsArr = new ArrayList<>();

     private static ArrayList<Group_Client> allGroupClients = new ArrayList<>();

     private String id;

    public Group_Client(Group_ClientChatController groupClientChatController) throws IOException {
         this.groupClientChatController = groupClientChatController;

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
        allGroupClients.add(this);
//        allClientsArr.add(this.id);

        System.out.println(allGroupClients.size());
        for (Group_Client allGroupClient : allGroupClients) {
            allGroupClient.groupClientChatController.showContacts();
            System.out.println("tamam");
        }


//        System.out.println(allClientsArr.size());

    }

    public void handleConnection() throws IOException {
        final Scanner scn = new Scanner(System.in);

        // establish the connection
        Socket s = new Socket("localhost", ServerPort);

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



                        String msg = dis.readUTF();
                        System.out.println(msg);
                            String sender = msg.substring(0, msg.indexOf(" : "));

                            new Group_Message(sender, id, msg, groupClientChatController.getGroupChatId());
                            System.out.println(msg);
                            groupClientChatController.printMessages();


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
            new Group_Message(this.id, chatOtherSide, message, Group_ClientChatController.getGroupChatId());
//            if(!chatOtherSide.equals("NOBODY")){
                dos.writeUTF(msg + "#" + Group_ClientChatController.getGroupChatId());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendFinalMessage() throws IOException {
        dos.writeUTF("finish");
    }

    public String getId() {
        return id;
    }

    public void getContacts(String contactsStr) throws IOException {

//        dos.writeUTF("getContacts");
//        String cont = dis.readUTF();
//        getContacts(cont);
        String[] contacts = contactsStr.split("#");
        allClientsArr.clear();
        allClientsArr.addAll(Arrays.asList(contacts));
        groupClientChatController.showContacts();
    }

//    public static void main(String[] args) throws IOException {
//        new TwoByTwoChat.Client();

//
//        dos.writeUTF("getContacts");
//        String cont = dis.readUTF();
//        getContacts(cont);
//    }

}
