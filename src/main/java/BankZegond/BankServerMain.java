package BankZegond;

import BankZegond.BankDB.DataBaseForBank;

import java.io.IOException;

public class BankServerMain {

    public static void main(String[] args) throws IOException {
        DataBaseForBank.getInstance().initializeBankServer();
        BankServer.getInstance().start();
    }
}
