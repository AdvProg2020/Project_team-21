package Server.Model;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ShopBankZegondAccount {
    private String userName;
    private String password;
    private String accountID;
    private int minimumAmount;
    private int bankingFeePercent;

    public ShopBankZegondAccount(String userName, String password, String accountID) {
        this.userName = userName;
        this.password = password;
        this.accountID = accountID;
        this.minimumAmount =10000;
        this.bankingFeePercent = 5;
    }

    public String getAccountID() {
        return accountID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getMinimumAmount() {
        return minimumAmount;
    }

    public int getBankingFeePercent() {
        return bankingFeePercent;
    }

    public void setMinimumAmount(int minimumAmount) {
        this.minimumAmount = minimumAmount;
        String value=String.valueOf(minimumAmount);
        Gson gson=new Gson();
        String content=gson.toJson(value);
        String path="BankResource"+ File.separator+"MinimumAmountBankZegond.gson";
        File file=new File(path);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setBankingFeePercent(int bankingFeePercent) {
        this.bankingFeePercent = bankingFeePercent;
        String value=String.valueOf(bankingFeePercent);
        Gson gson=new Gson();
        String content=gson.toJson(value);
        String path="BankResource"+ File.separator+"BankingFeePercentBankZegond.gson";
        File file=new File(path);
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
