package Server.Model;

import java.util.ArrayList;

public class Wallet {

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
        money += amount;
    }

    public void withdrawMoney(double amount){
        money -= amount;
    }

    public String getAccount() {
        return account;
    }

    public static ArrayList<Wallet> getAllWallets() {
        return allWallets;
    }
}