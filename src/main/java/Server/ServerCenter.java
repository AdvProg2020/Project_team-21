package Server;

import Server.Model.Account.Account;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerCenter {
    private static ServerCenter instance;
    private HashMap<String, Account> allTokens = new HashMap<>();
    private HashMap<String, Account> activeTokens = new HashMap<>();
    private HashMap<String, Account> expiredTokens = new HashMap<>();
    private DataOutputStream bankOutput;
    private DataInputStream bankInput;

    public static ServerCenter getInstance() {
        if(instance == null)
            instance = new ServerCenter();
        return instance;
    }

    public HashMap<String, Account> getActiveTokens() {
        return activeTokens;
    }

    public HashMap<String, Account> getAllTokens() {
        return allTokens;
    }

    public HashMap<String, Account> getExpiredTokens() {
        return expiredTokens;
    }

    public Account getAccountFromToken(String token){
        return getActiveTokens().get(token);
    }

    public void addToken(String id,Account account){
        getAllTokens().put(id,account);
        getActiveTokens().put(id,account);
        System.out.println(account.getUsername() + " ADDED!");
    }

    public void expireToken(String id){
        Account account = activeTokens.get(id);
        getExpiredTokens().put(id,account);
        activeTokens.remove(id);
        System.out.println(account.getUsername() + " EXPIRED!");
    }

    public void setBankOutput(DataOutputStream bankOutput) {
        this.bankOutput = bankOutput;
    }

    public void setBankInput(DataInputStream bankInput) {
        this.bankInput = bankInput;
    }

    public void sendMessageToBank(String request,String... data){
        String result = request;
        for (String s : data) {
            result += " " + s;
        }
        System.out.println("also here " + result);
        try {
            bankOutput.writeUTF(result);
            bankOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String createAccountBank(String firstName, String lastName, String username, String password, String repeat_password){
        sendMessageToBank("create_account",firstName,lastName,username,password,repeat_password);
        String response = readMessageFromBank();
        return response;
    }

    public String getTokenBank(String username, String password){
        sendMessageToBank("get_token",username,password);
        String response = readMessageFromBank();
        return response;
    }

    public String createReceiptBank(String receiptType, String money, String sourceID, String destID, String description, String username, String password){
        String token = getTokenBank(username,password);
        sendMessageToBank("create_receipt",token,receiptType,money,sourceID,destID,description);
        String response = readMessageFromBank();
        return response;
    }

    public String getTransactionsBank(String type, String username, String password){
        String token = getTokenBank(username,password);
        sendMessageToBank("get_transactions",token,type);
        String response = readMessageFromBank();
        return response;
    }

    public String payBank(String receiptID){
        sendMessageToBank("pay",receiptID);
        String response = readMessageFromBank();
        return response;
    }

    public String getBalanceBank(String username, String password){
        String token = getTokenBank(username,password);
        System.out.println("avvale balance " + token);
        sendMessageToBank("get_balance",token);
        String response = readMessageFromBank();
        return response;
    }

    public String readMessageFromBank(){
        String response = "NULL";
        try {
            response = bankInput.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void connectToBank(){
        try {
            Socket bankSocket = new Socket("localhost",8585);
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(bankSocket.getInputStream()));
            DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(bankSocket.getOutputStream()));
            ServerCenter.getInstance().setBankOutput(dataOutputStream);
            ServerCenter.getInstance().setBankInput(dataInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
