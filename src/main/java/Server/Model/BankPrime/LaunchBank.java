package Server.Model.BankPrime;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class LaunchBank {

    public static HashMap<String , String> tokens = new HashMap<>();
    public static ArrayList<String> expiredTokens = new ArrayList<>();

    public static HashMap<String, String> getTokens() {
        return tokens;
    }

    public static void setTokens(HashMap<String, String> tokens) {
        LaunchBank.tokens = tokens;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(9000);
        while (true){
            Socket s = ss.accept();
            new BankClientHandler(s).start();
        }
    }
}
