package Server.Controller.BankZegondController;

import Server.Controller.Control;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;
import Server.Model.Account.Seller;

import java.io.IOException;
import java.util.regex.Pattern;

public class BankZegondTransactionsController {
    public String moveMoneyFromUserToShop(String username, String password, String money) throws  IOException {
        String token = Control.getInstance().getBankAccountsController().getToken(username, password);
        if (token.startsWith("invalid"))
            return token;
        String receiptId = "";
        if (true /*TODO Shop.getInstance().findUser(username) instanceof Customer*/) {
            Customer user; //TODO = (Customer) Shop.getInstance().findUser(username);
//            receiptId = Control.getInstance().getBankAccountsController().createReceipt(token, "move", money, user.getBankAccountId(), Shop.getInstance().getShopBankId(), "");
        }else if (true/* TODO Shop.getInstance().findUser(username) instanceof Seller*/) {
            Seller user; //TODO = (Seller) Shop.getInstance().findUser(username);
//            receiptId = Control.getInstance().getBankAccountsController().createReceipt(token, "move", money, user.getBankAccountId(), Shop.getInstance().getShopBankId(), "");
        }
        if (!Pattern.matches("[\\d]+", receiptId))
            return receiptId;
        String finalResponse = Control.getInstance().getBankAccountsController().pay(receiptId);
        return finalResponse;
    }

    public String increaseCustomerCredit(String username, String password, String money) throws IOException {
        String finalResponse = moveMoneyFromUserToShop(username, password, money);
        if (finalResponse.equals("done successfully")) {
//            Customer user;//TODO = (Customer) Shop.getInstance().findUser(username);
//            user.setCredit(user.getCredit() + Long.parseLong(money));
        }
        return finalResponse;
    }

    public String withdrawMoneySeller(String username, String password, String money) throws IOException {
        String token = "S" ;// TODO = Control.getInstance().getBankAccountsController().getToken("Shop", Shop.getInstance().getShopBankAccount().getPassword());
        if (token.startsWith("invalid"))
            return token;
        if (Pattern.matches("[\\d]+", money)) {
//            TODO long balance = ((Seller) Shop.getInstance().findUser(username)).getBalance();
//            if (balance - Long.parseLong(money) < Shop.getInstance().getShopBankAccount().getMinimumAmount())
//                return "not enough money in account";
        }
        Seller user ; //TODO = (Seller) Shop.getInstance().findUser(username);
        String receiptId = "SALAM"; //TODO= Control.getInstance().getBankAccountsController().createReceipt(token, "move", money, Shop.getInstance().getShopBankId(), user.getBankAccountId(), "");
        if (!Pattern.matches("[\\d]+", receiptId))
            return receiptId;
        String finalResponse = Control.getInstance().getBankAccountsController().pay(receiptId);
        if (finalResponse.equals("done successfully"))
            //TODO Ask sina
//            user.setCredit(user.getCredit() - Long.parseLong(money));
        return finalResponse;
        return "SLAM";
    }

    public String depositMoneySeller(String username, String password, String money) throws IOException {
        String finalResponse = moveMoneyFromUserToShop(username, password, money);
        if (finalResponse.equals("done successfully")) {
//            TODO Seller user = (Seller) Shop.getInstance().findUser(username);
//            user.setCredit(user.getCredit() + Long.parseLong(money));
        }
        return finalResponse;
    }

    public void payMoneyToSellerAfterPurchaseByWallet(String money, String username) {
//        Seller seller = (Seller) Shop.getInstance().findUser(username);
//        seller.setCredit(seller.getCredit() + (Long.parseLong(money) * (100 - Shop.getInstance().getShopBankAccount().getBankingFeePercent()) / 100));
    }

    public String depositFirstAfterCreating(String username, String password, String money) throws IOException {
//        String token = Control.getInstance().getBankAccountsController().getToken(username, password);
//        if (token.startsWith("invalid"))
//            return token;
//        Account user = Shop.getInstance().findUser(username);
//        String receiptId = "";
//        if (user instanceof Customer) {
//            Customer customer = (Customer) user;
//            receiptId = Control.getInstance().getBankAccountsController().createReceipt(token, "deposit", money, "-1", customer.getBankAccountId(), "");
//        } else if (user instanceof Seller) {
//            Seller seller = (Seller) user;
//            receiptId = Control.getInstance().getBankAccountsController().createReceipt(token, "deposit", money, "-1", seller.getBankAccountId(), "");
//        }
//        if (!Pattern.matches("[\\d]+", receiptId))
//            return receiptId;
//        return Control.getInstance().getBankAccountsController().pay(receiptId);
        return "SALAM";
    }
}
