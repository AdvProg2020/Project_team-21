package Server.ChatServers.Group;

import Server.ChatServers.UserStatusEnum;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Group_ClientHandler implements Runnable {

    public static HashMap<Group_ClientHandler, UserStatusEnum> allClients = new HashMap<>();
    private String name;
    private final DataInputStream dis;
    private final DataOutputStream dos;
    private Socket s;
    boolean isloggedin = true;

    // constructor
    public Group_ClientHandler(Socket s, String name,
                               DataInputStream dis, DataOutputStream dos) throws IOException {
        this.dis = dis;
        this.dos = dos;
        this.name = name;
        this.s = s;


        for (Group_ClientHandler groupClientHandler : allClients.keySet()) {
            groupClientHandler.dos.writeUTF(this.name + " : joined the chat!");
        }
        allClients.put(this, UserStatusEnum.ONLINE);
    }

    @Override
    public void run() {
        try {
            String allClientsArr = "allClients#";
            for (Group_ClientHandler client : allClients.keySet()) {
                allClientsArr += client.getName() + "#";
            }
            dos.writeUTF(allClientsArr);
            dis.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String nm = dis.readUTF();
            name = nm;
            dos.writeUTF("hi");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String received;
        while (true)
        {
            try
            {
                // receive the string
                received = dis.readUTF();
                System.out.println(received);

                if(received.equals("getContacts")){
                    String toSend = "";
                    for (Group_ClientHandler groupClientHandler : allClients.keySet()) {
                        toSend += groupClientHandler.name + "#";
                    }
                    dos.writeUTF(toSend);
                    continue;
                }

                if(received.equals("finish")){
                    allClients.replace(this, UserStatusEnum.OFFLINE);
                    System.out.println(this.name + " id is " + allClients.get(this));
                    break;
                }

                if(received.equals("logout")){
                    this.isloggedin=false;
                    this.s.close();
                    break;
                }

                // break the string into message and recipient part
                StringTokenizer st = new StringTokenizer(received, "#");
                String MsgToSend = st.nextToken();
                String recipient = st.nextToken();

                // search for the recipient in the connected devices list.
                // ar is the vector storing client of active users
                for (Group_ClientHandler mc : allClients.keySet())
                {
                    // if the recipient is found, write on its
                    // output stream
                    if ((!mc.name.equals(name)) && mc.isloggedin==true)
                    {
                        mc.dos.writeUTF(this.name+" : "+MsgToSend);
                        break;
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            }

        }
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
            e.printStackTrace();
        }

    }


    public String getName() {
        return name;
    }
}

