package BankZegond.BankDB;

import BankZegond.Model.BankAccount;
import BankZegond.Model.Receipt;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveDataBaseForBank {
    private Gson yaGson;

    public SaveDataBaseForBank() {
        yaGson = new Gson();
    }

    public void saveAccount(BankAccount bankAccount) throws IOException, FileCantBeSavedException {
        String filePath="BankResources\\Accounts\\" + bankAccount.getAccountNumber() + ".json";
        saveFile(yaGson.toJson(bankAccount, BankAccount.class), filePath);
    }

    public void saveReceipts(Receipt receipt) throws IOException, FileCantBeSavedException {
        String filePath="BankResources\\Receipts\\" + receipt.getId() + ".json";
        saveFile(yaGson.toJson(receipt, Receipt.class), filePath);
    }

    private void saveFile(String serializedData, String filePath) throws IOException, FileCantBeSavedException {
        File file = new File(filePath);
        if (!file.exists()) {
            if (!file.createNewFile())
                throw new FileCantBeSavedException();
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(serializedData);
        fileWriter.close();
    }
}

class FileCantBeSavedException extends Exception {
    public FileCantBeSavedException() {
        super("file could not be saved successfully.");
    }
}
