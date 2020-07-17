package Server;

import Server.Controller.Control;
import Server.Controller.ServerRequest;
import Server.Model.*;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;
import Server.Model.Account.Manager;
import Server.Model.Account.Seller;
import Server.Model.Log.BuyLog;
import Server.Model.Log.Log;
import Server.Model.Log.SellLog;
import Server.Model.Request.OffRequest;
import Server.Model.Request.ProductRequest;
import Server.Model.Request.SellerRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Server {
    static void putToAbstract(){
        for (Seller seller : Seller.getAllSeller()) {
            Account.addAccount(seller);
        }
        for (Manager manager : Manager.getAllManagers()) {
            Account.addAccount(manager);
        }
        for (Customer customer : Customer.getaAllCustomers()) {
            Account.addAccount(customer);
        }
    }
    static void readFilesFromDatabase(){
        new SaveData();
        SaveData.createAllFiles();
        BuyLog.getObjectFromDatabase();
        Log.getObjectFromDatabase();
        SellLog.getObjectFromDatabase();
        Category.getObjectFromDatabase();
        DiscountCode.getObjectFromDatabase();
        Off.getObjectFromDatabase();
        Product.getObjectFromDatabase();
        Customer.getObjectFromDatabase();
        Manager.getObjectFromDatabase();
        Seller.getObjectFromDatabase();
        OffRequest.getObjectFromDatabase();
        ProductRequest.getObjectFromDatabase();
        SellerRequest.getObjectFromDatabase();
        Company.getObjectFromDatabase();
        Score.getObjectFromDatabase();
        Review.getObjectFromDatabase();
        putToAbstract();
    }

    private static class HandleClient extends Thread{
        Socket clientSocket;
        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;
        ServerSocket serverSocket;
        OutputStream outputStream;
        InputStream inputStream;
        PrintWriter printWriter;
        InputStreamReader inputStreamReader;
        BufferedReader bufferedReader;

        private void sendMessageToClient(String message) throws IOException {
//            dataOutputStream.writeUTF(message);
//            dataOutputStream.flush();
//            PrintWriter printWriter = new PrintWriter(dataOutputStream);
            printWriter.println(message);
            printWriter.flush();
//            printWriter.close();
        }
        private String readMessageFromClient() throws IOException {
            String result;
            result = bufferedReader.readLine();
//            bufferedReader.close();
//            bufferedReader.close();
            return result;
        }
        private String getFileExt(String name){
            String extReversed = "";
            String ext = "";
            for(int i = name.length()-1;name.charAt(i) != '.';i--){
                extReversed += name.charAt(i);
            }
            for(int i = extReversed.length()-1;i>=0;i--){
                ext += extReversed.charAt(i);
            }
            return ext;
        }
        private void sendImage(String path) throws IOException {
            sendMessageToClient(getFileExt(path));
//            dataOutputStream.writeUTF(getFileExt(path));
//            dataOutputStream.flush();
            FileInputStream fileInputStream = new FileInputStream(path);
            byte[] b = new byte[2000000];
            fileInputStream.read(b,0,b.length);
            dataOutputStream.write(b,0,b.length);
            dataOutputStream.flush();
        }

        HandleClient(Socket clientSocket, DataOutputStream dataOutputStream, DataInputStream dataInputStream,ServerSocket serverSocket,InputStream inputStream,OutputStream outputStream){
            this.clientSocket = clientSocket;
            this.dataInputStream = dataInputStream;
            this.dataOutputStream = dataOutputStream;
            this.serverSocket = serverSocket;
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            printWriter = new PrintWriter(outputStream);
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
        }

        @Override
        public void run() {
            String URL = "";
            while(true){
                try {

                    URL = readMessageFromClient();
//                    URL = dataInputStream.readUTF();
                    System.out.println(URL);
                    String[] parsedURL = URL.trim().split(" - ");
                    String token = parsedURL[0];
                    String request = parsedURL[1];
                    String data = parsedURL[2];

                    if(request.equalsIgnoreCase(ServerRequest.LOGIN.toString())){
                        String[] dataParsed = data.split("&");
                        String username = dataParsed[0];
                        String password = dataParsed[1];
                        try {
                            Control.getInstance().login(username,password);
                            String generatedToken = Control.getInstance().randomString(10);
                            ServerCenter.getInstance().addToken(generatedToken,Account.getAllAccounts().get(username));
                            sendMessageToClient(ServerRequest.DONE.toString() + "&" + generatedToken + "&" + Account.getAllAccounts().get(username).getFirstName());
                        } catch (Control.notFoundUserOrPass notFoundUserOrPass) {
                            sendMessageToClient(notFoundUserOrPass.getMessage());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.HASMANAGER.toString())){
                        if(Manager.getAllManagers().isEmpty()){
                            sendMessageToClient(ServerRequest.TRUE.toString());
                        }
                        else{
                            sendMessageToClient(ServerRequest.FALSE.toString());
                        }
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETACCOUNTTYPE.toString())){
                        Account account = ServerCenter.getInstance().getAccountFromToken(token);
                        System.out.println(account.getType());
                        if(account instanceof Customer)
                            sendMessageToClient("Customer");
                        if(account instanceof Manager)
                            sendMessageToClient("Manager");
                        if(account instanceof Seller)
                            sendMessageToClient("Seller");
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.SIGNOUT.toString())){
//                        Control.getInstance().logout();
                        ServerCenter.getInstance().expireToken(token);
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETPROFILEPHOTO.toString())){
                        Account account = ServerCenter.getInstance().getAccountFromToken(token);
                        sendImage(account.getImagePath());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETNAME.toString())){
                        Account account = ServerCenter.getInstance().getAccountFromToken(token);
                        sendMessageToClient(account.getFirstName());
                    }
                    else if(request.equalsIgnoreCase(ServerRequest.GETCUSTOMERINFOS.toString())){
                        Customer account =(Customer) ServerCenter.getInstance().getAccountFromToken(token);
                        sendMessageToClient(account.getUsername() + " - "+account.getFirstName()+" - "+account.getLastName()+" - "+account.getAddress()+" - "
                        +account.getPhoneNumber()+" - "+account.getEmail()+" - "+account.getPassword()+" - "+account.getBalance());
                    }
                } catch (IOException e) {
//                    System.out.println("error in reading req in server");
                }
            }
        }
    }

    public static void main(String[] args) {
        readFilesFromDatabase();
        for (String s : Account.getAllAccounts().keySet()) {
            System.out.println(s + "  " + Account.getAllAccounts().get(s).getPassword() + "    " + Account.getAllAccounts().get(s).getType());
        }
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            Socket clientSocket;
            while (true){
                clientSocket = serverSocket.accept();
                DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));
                DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(clientSocket.getInputStream()));
                new HandleClient(clientSocket,dataOutputStream,dataInputStream,serverSocket,clientSocket.getInputStream(),clientSocket.getOutputStream()).start();
            }
        } catch (IOException e) {
            System.out.println("Error in server socket");
        }

    }
}
