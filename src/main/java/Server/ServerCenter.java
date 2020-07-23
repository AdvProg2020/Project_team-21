package Server;

import Server.Model.Account.Account;

import java.io.*;
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

    public void sendMessageToBank(String message){
        try {
            bankOutput.writeUTF(message);
            bankOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
