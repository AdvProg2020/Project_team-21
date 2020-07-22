package BankDoublePrime.Controller;

import BankDoublePrime.Model.Account;
import BankDoublePrime.Model.Receipt;
import BankDoublePrime.Model.Token;
import BankDoublePrime.View.BankServer;

import javax.print.DocFlavor;

public class ReceiptController {
    public static void createReceipt(String[] input) {
        try {
            String type = input[0];
            double money = Double.parseDouble(input[1]);
            long source = Long.parseLong(input[2]);
            long destination = Long.parseLong((input[3]));
            String description = (input[4]);
            if (type.equals("move")) {
                Receipt receipt = new Receipt(AccountController.getAccountWithID(input[2]), type, money, source, destination, description);
            } else if (type.equals("deposit")) {
                Receipt receipt = new Receipt(AccountController.getAccountWithID(input[2]), type, money, -1, destination, description);
            } else if (type.equals("withdraw")) {
                Receipt receipt = new Receipt(AccountController.getAccountWithID(input[2]), type, money, source, -1, description);
            }
        } catch (Exception e) {
            System.out.println("can not create receipt");
        }


    }

    public static boolean isThereAnyReceiptWithID(String receiptID, String bankToken) {
        Account account = BankServer.onlineUsers.get(bankToken);
        for (Receipt receipt : account.getAllReceipts()) {
            if (receipt.getReceiptID().equals(receiptID)) return true;
        }
        return false;
    }

    public static Receipt getReceiptWithID(String receiptID, String bankToken) {
        Account account = BankServer.onlineUsers.get(bankToken);
        for (Receipt receipt : account.getAllReceipts()) {
            if (receipt.getReceiptID().equals(receiptID)) return receipt;
        }
        return null;
    }


    public static String getReceiptDetails(String receiptID, String bankToken) {
        Receipt receipt = getReceiptWithID(receiptID, bankToken);
        Account account = BankServer.onlineUsers.get(bankToken);
        return receipt.getMoney() + "!@" + account.getBalance() + "!@" + receipt.isDone();
    }
}
