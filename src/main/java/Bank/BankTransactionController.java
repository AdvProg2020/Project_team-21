package Bank;

import Client.ClientCenter;
import Client.ServerRequest;
import Server.Controller.ControlSeller;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;
import Server.Model.Account.Seller;
import Server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BankTransactionController {

    private static BankTransactionController instance;
    private int wage;
    private int buyLogCode;
    private static DataOutputStream bankDos;
    private static DataInputStream bankDis;

    public static void setBankDos(DataOutputStream bankDos) {
        BankTransactionController.bankDos = bankDos;
    }

    public static void setBankDis(DataInputStream bankDis) {
        BankTransactionController.bankDis = bankDis;
    }

    private BankTransactionController()
    {

    }
    public static BankTransactionController getInstance()
    {
        if(instance == null)
            instance = new BankTransactionController();
        return instance;
    }


//    public void ChargeWallet(double amount) {
//
//        Account person ;
//        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETACCOUNTTYPE , Double.toString(amount));
//        String res = ClientCenter.getInstance().readMessageFromServer();
//        if (res.equalsIgnoreCase("Customer")) {
//            Customer customer = (Customer) person;
//            String charge = "get_token " + customer.getWallet().getBankAccountUsername() + " " +
//                    customer.getWallet().getBankAccountPassword();
//            System.out.println(charge);
//            String token = getTokenFromBank(charge);
////            if (token.equalsIgnoreCase(""))
////                ye errori mide
//            int receipt = moveToShopAccount(token, amount, customer.getWallet().getAccountId(), "charging_wallet");
//            boolean wasPaid = pay(receipt);
//            if (wasPaid) {
//                customer.setBalance(customer.getBalance() + amount);
//            }
//
//        } else if (person instanceof Seller) {
//            Seller seller = (Seller) person;
//            String charge = "get_token " + seller.getWallet().getBankAccountUsername() + " " +
//                    seller.getWallet().getBankAccountPassword();
//            System.out.println(charge);
//            String token = getTokenFromBank(charge);
////            if (token.equalsIgnoreCase(""))
////                ye errori mide
//            int receipt = moveToShopAccount(token, amount, seller.getWallet().getAccountId(), "charging_wallet");
//            boolean wasPaid = pay(receipt);
//            if (wasPaid) {
//                seller.setBalance(seller.getBalance() + amount);
//            }
//        }
//    }
//
//    public void withdrawWallet(double amount) {
//        Account person;
//        Seller seller = (Seller) person;
//        String withdraw = "get_token " + seller.getWallet().getBankAccountUsername() + " " +
//                seller.getWallet().getBankAccountPassword();
//        double minBalanceInWallet = ControlSeller.getInstance().getMinBalance();
//        if (isValidWithdrawal(minBalanceInWallet, seller, amount)) {
//            String token = getTokenFromBank(withdraw);
////            if (token.equals(""))
////                ye Errori mide ke token Valid nist mese ghabli //TODO SINA
//            int receipt = moveFromShopAccount(token, amount,
//                    seller.getWallet().getAccountId(), "withdrawing_from_wallet");
//            boolean wasPaid = pay(receipt);
//            if (wasPaid) {
//                seller.setBalance(seller.getBalance() - amount);
//            }
//        }
//    }





    public boolean paymentWithBankIsSuccessful(double totalPrice,
                                                double percentage, Account person) {
        double moneyToTransfer = totalPrice - totalPrice * (1.0 * percentage / 100);
        try {
            Customer customer = (Customer) person;
            String charge = "get_token " + customer.getWallet().getBankAccountUsername() + " " +
                    customer.getWallet().getBankAccountPassword();
            String token = getTokenFromBank(charge);
            if (token.equals(""))
                throw new Exception("username/password is invalid");
            int receipt = moveToShopAccount(token,moneyToTransfer, customer.getWallet().getAccountId(), "payment");
            boolean wasPaid = pay(receipt);
            if (wasPaid) {
                return true;
            } else return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    //TODO: ask sina about srevers
    public boolean pay(int id) {
        try {
            bankDos.writeUTF("pay " + id);
            bankDos.flush();
            String result = bankDis.readUTF();
            System.out.println(result);
            if (result.startsWith("done"))
                return true;
            else return false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public int moveToShopAccount(String token, double money, int srcId, String description) {
        String request = "create_receipt " + token + " move " + money + " " + srcId + " 1 " + description;
//        try {
//            server.bankDataOutputStream.writeUTF(request);
//            server.bankDataOutputStream.flush();
//            int id = Integer.parseInt(server.bankDataInputStream.readUTF());
//            return id;
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        return 0;
    }

    public int moveFromShopAccount(String token, double money, int destId, String description) {
        String request = "create_receipt " + token + " move " + money + " 1 " + destId + " " + description;
        System.out.println(request);
//        try {
//            server.bankDataOutputStream.writeUTF(request);
//            server.bankDataOutputStream.flush();
//            int id = Integer.parseInt(server.bankDataInputStream.readUTF());
//            System.out.println(id);
//            return id;
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        return 0;
    }

    public boolean isValidWithdrawal(double min, Seller seller, double toWithdraw) {
//        try {
//            String info = "get_token " + seller.getWallet().getBankAccountUsername() + " " +
//                    seller.getWallet().getBankAccountPassword();
//            String token = getTokenFromBank(info);
//            server.bankDataOutputStream.writeUTF("get_balance " + token);
//            server.bankDataOutputStream.flush();
//            double balance = Double.parseDouble(server.bankDataInputStream.readUTF());
//            return (balance - toWithdraw) > min;
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        return false;
    }

    public String getTokenFromBank(String info) {
//        try {
//            server.bankDataOutputStream.writeUTF(info);
//            server.bankDataOutputStream.flush();
//            String token = server.bankDataInputStream.readUTF();
//            if (token.equals("Username is wrong") || token.equals("Password is wrong"))
//                return "";
//            else return token;
//        } catch (IOException e) {
//            System.out.println(e.getMessage());
//        }
        return null;
    }
}
