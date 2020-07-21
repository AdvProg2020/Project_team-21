package BankZegond.BankDB;

import BankZegond.BankServer;
import BankZegond.Model.BankAccount;
import BankZegond.Model.Receipt;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LoadDataBaseForBank {
    private Gson yaGson;

    public LoadDataBaseForBank() {
        this.yaGson = new Gson();
        File file = new File("BankResources");
        if (!file.exists())
            file.mkdir();
        file = new File("BankResources\\Accounts");
        if (!file.exists())
            file.mkdir();
        file = new File("BankResources\\Receipts");
        if (!file.exists())
            file.mkdir();
    }

    public void loadAccounts() throws IOException {
        File[] files = loadFolder("BankResources\\Accounts");
        if (files != null) {
            for (File file : files) {
                BankServer.getInstance().addBankAccount(yaGson.fromJson(readFile(file), BankAccount.class));
            }
        }
    }

    public void loadReceipts() throws IOException {
        File[] files = loadFolder("BankResources\\Receipts");
        if (files != null) {
            for (File file : files) {
                BankServer.getInstance().addReceipt(yaGson.fromJson(readFile(file), Receipt.class));
            }
        }
    }

    private File[] loadFolder(String folderPath) {
        File file = new File(folderPath);
        if (!file.exists())
            file.mkdir();
        return file.listFiles();
    }

    private String readFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }
}
