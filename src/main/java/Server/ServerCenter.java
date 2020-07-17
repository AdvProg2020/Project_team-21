package Server;

import Server.Controller.Control;
import Server.Controller.ServerRequest;
import Server.Model.Account.Account;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class ServerCenter {
    private static ServerCenter instance;
    private HashMap<String, Account> allTokens = new HashMap<>();
    private HashMap<String, Account> activeTokens = new HashMap<>();
    private HashMap<String, Account> expiredTokens = new HashMap<>();

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
        System.out.println("Token for " + account.getUsername() + " has been created with token id: " + id);
    }

    public void expireToken(String id){
        Account account = allTokens.get(id);
        getExpiredTokens().put(id,account);
        activeTokens.remove(id);
    }

}
