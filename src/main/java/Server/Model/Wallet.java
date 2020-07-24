package Server.Model;

import Server.DatabaseHandler;

import java.io.Serializable;
import java.util.ArrayList;

public class Wallet implements Serializable {

    private double money;
    private String account;
    private static ArrayList<Wallet> allWallets = new ArrayList<>();

    public Wallet(double money, String account) {
        this.money = money;
        this.account = account;
        allWallets.add(this);
    }

    public double getMoney() {
        return money;
    }

    public void depositMoney(double amount){
        System.out.println(amount + " deposit krd");
        money += amount;
    }

    public void withdrawMoney(double amount){
        System.out.println(amount + " withdraw krd");
        money -= amount;
    }

    public String getAccount() {
        return account;
    }

    public static ArrayList<Wallet> getAllWallets() {
        return allWallets;
    }

    public static void reloadObjectsFromDatabase(){
        ArrayList<Wallet> wallets = new ArrayList<>(DatabaseHandler.selectFromWallet());
        for (Wallet wallet : wallets) {
            allWallets.add(wallet);
        }
    }
}