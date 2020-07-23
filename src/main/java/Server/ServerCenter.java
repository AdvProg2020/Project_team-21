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

    public void sendMessageToBank(String request,String... data){
        String result = request;
        for (String s : data) {
            result += " " + s;
        }
        try {
            bankOutput.writeUTF(result);
            bankOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAccountBank(String firstName, String lastName, String username, String password, String repeat_password){
        sendMessageToBank("create_account",firstName,lastName,username,password,repeat_password);
    }

    public void getTokenBank(String username, String password){
        sendMessageToBank("get_token",username,password);
    }

    public void createReceiptBank(String token, String receiptType, String money, String sourceID, String destID, String description){
        sendMessageToBank("create_receipt",token,receiptType,money,sourceID,destID,description);
    }

    public void getTransactionsBank(String token, String type){
        sendMessageToBank("get_transactions",token,type);
    }

    public void payBank(String receiptID){
        sendMessageToBank("pay",receiptID);
    }

    public void getBalanceBank(String token){
        sendMessageToBank("get_balance",token);
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
