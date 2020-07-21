package BankDoublePrime.View;

import BankDoublePrime.Controller.AccountController;
import BankDoublePrime.Controller.ReceiptController;
import BankDoublePrime.Controller.TokenController;
import BankDoublePrime.Model.Account;
import BankDoublePrime.Model.Token;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

import static BankDoublePrime.Controller.Controller.giveTokenToAccount;

public class BankServer {
    public static HashMap<Token, Account> onlineUsers = new HashMap<>();

    public static void main(String[] args) throws Exception {
//        DataBase.dataBaseRun();
        new ServerImp().run();
    }

    private static void checkTokenIsDead(){
        for (Token token : onlineUsers.keySet()) {
            if(token.getTime() == 0){
                token.setAlive(false);
            }else{
                token.setTime(token.getTime() - 1);
                if(token.getTime() == 0) token.setAlive(false);
            }
        }
    }


    static class ServerImp {
        public void run() throws IOException {
            ServerSocket serverSocket = new ServerSocket(8080);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> checkTokenIsDead()));
                    timeline.setCycleCount(Timeline.INDEFINITE);
                    timeline.play();
                }
            }).start();

            while (true) {
                Socket clientSocket;
                clientSocket = serverSocket.accept();
                DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                new ClientHandler(clientSocket, dataOutputStream, dataInputStream).start();
            }
        }
    }

    static class ClientHandler extends Thread {

        private Socket socket;
        private DataOutputStream dataOutputStream;
        private DataInputStream dataInputStream;
        private boolean shouldRun;

        public ClientHandler(Socket socket, DataOutputStream dataOutputStream, DataInputStream dataInputStream) {
            this.socket = socket;
            this.dataOutputStream = dataOutputStream;
            this.dataInputStream = dataInputStream;
            shouldRun = true;
        }

        private void handleClient() {
            try {
                while (shouldRun) {
                    String command = dataInputStream.readUTF();

                    if (command.equals("isThereAnyAccountWithUsernameInBank")) {
                        String username = dataInputStream.readUTF();
                        String response = "false";
                        if (AccountController.isThereAnyAccountsWithUsername(username)) response = "true";
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                        continue;
                    }
                    if (command.equals("createAccountInBank")) {
                        String message = dataInputStream.readUTF();
                        String[] input = message.split("!@");
                        AccountController.createAccount(input);
                        dataOutputStream.writeUTF("done");
                        dataOutputStream.flush();
                        continue;
                    }
                    if (command.equals("isPasswordCorrectForBankAccount")) {
                        String message = dataInputStream.readUTF();
                        String[] input = message.split("!@");
                        String username = input[0];
                        String password = input[1];
                        String response = "false";
                        if (AccountController.isThisPassCorrectForThisAccount(username, password)) response = "true";
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                        continue;
                    }
                    if (command.equals("getTokenInBank")) {
                        String username = dataInputStream.readUTF();
                        String response = giveTokenToAccount(username);
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                        continue;
                    }
                    if (command.equals("isTokenExpired")) {
                        String bankToken = dataInputStream.readUTF();
                        String response = TokenController.isTokenExpired(bankToken);
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                        continue;
                    }
                    if (command.equals("getBankAccountInformation")) {
                        String bankToken = dataInputStream.readUTF();
                        String response = AccountController.getAccountInformation(onlineUsers.get(bankToken));
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                        continue;
                    }
                    if (command.equals("isThereAnyBankAccountWithID")) {
                        String accountID = dataInputStream.readUTF();
                        String response = AccountController.isThereAnyBankAccountWithID(accountID);
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                        continue;
                    }
                    if (command.equals("createReceipt")) {
                        String message = dataInputStream.readUTF();
                        ReceiptController.createReceipt(message.split("!@"));
                        dataOutputStream.writeUTF("done");
                        dataOutputStream.flush();
                        continue;
                    }
                    if (command.equals("isThereAnyReceiptWithID")) {
                        String[] input = (dataInputStream.readUTF()).split("!@");
                        String receiptID =  input[0];
                        String bankToken = input[1];
                        String response = "false";
                        if(ReceiptController.isThereAnyReceiptWithID(receiptID , bankToken)) response = "true";
                        dataOutputStream.writeUTF(response);
                        dataOutputStream.flush();
                        continue;
                    }
                    if (command.equals("getReceiptAndAccountDetailForPay")) {
                        String[] input = (dataInputStream.readUTF()).split("!@");
                        String receiptID =  input[0];
                        String bankToken = input[1];
                        String detail= ReceiptController.getReceiptDetails(receiptID , bankToken);
                        dataOutputStream.writeUTF(detail);
                        dataOutputStream.flush();
                        continue;
                    }

                }
                dataInputStream.close();
                dataOutputStream.close();
                socket.close();
            } catch (Exception e) {

            }

        }


    }
}