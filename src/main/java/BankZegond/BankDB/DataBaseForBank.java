package BankZegond.BankDB;

import BankZegond.Model.BankAccount;
import BankZegond.Model.Receipt;

import java.io.IOException;

public class DataBaseForBank {
    private DeleteDataBaseForBank deletingData;
    private SaveDataBaseForBank savingData;
    private LoadDataBaseForBank loadingData;
    private static DataBaseForBank database;

    private DataBaseForBank() {
        this.deletingData = new DeleteDataBaseForBank();
        this.savingData = new SaveDataBaseForBank();
        this.loadingData = new LoadDataBaseForBank();
    }

    public static DataBaseForBank getInstance() {
        if (database == null)
            database = new DataBaseForBank();
        return database;
    }

    public void initializeBankServer() throws IOException {
        loadingData.loadAccounts();
        loadingData.loadReceipts();
    }

    public void saveItem(Object item) throws IOException, FileCantBeSavedException {
        if (item instanceof BankAccount) {
            savingData.saveAccount((BankAccount) item);
        } else if (item instanceof Receipt) {
            savingData.saveReceipts((Receipt) item);
        }
    }
}
