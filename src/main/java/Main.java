import Model.*;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Log.BuyLog;
import Model.Log.Log;
import Model.Log.SellLog;
import Model.Request.OffRequest;
import Model.Request.ProductRequest;
import Model.Request.SellerRequest;
import View.ConsoleView;
import View.MainMenuUI;

public class Main {
    public static void main(String[] args) {
        new SaveData();
        SaveData.createAllFiles();



        BuyLog.getObjectFromDatabase();
        Log.getObjectFromDatabase();
        SellLog.getObjectFromDatabase();
        Category.getObjectFromDatabase();
        DiscountCode.getObjectFromDatabase();
        Off.getObjectFromDatabase();
        Product.getObjectFromDatabase();

        Customer.getObjectFromDatabase();
        Manager.getObjectFromDatabase();
        Seller.getObjectFromDatabase();

        OffRequest.getObjectFromDatabase();
        ProductRequest.getObjectFromDatabase();
        SellerRequest.getObjectFromDatabase();
//        Account.getObjectFromDatabase();

        ConsoleView.getInstance().goToNextPage(MainMenuUI.getInstance());
        ConsoleView.getInstance().processInput("main menu");
    }
}