import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Category;
import Model.DiscountCode;
import Model.Log.BuyLog;
import Model.Log.Log;
import Model.Log.SellLog;
import Model.Off;
import Model.Product;
import View.ConsoleView;
import View.MainMenuUI;

public class Main {
    public static void main(String[] args) {
        Account.getObjectFromDatabase();
        Customer.getObjectFromDatabase();
        Manager.getObjectFromDatabase();
        Seller.getObjectFromDatabase();
        BuyLog.getObjectFromDatabase();
        Log.getObjectFromDatabase();
        SellLog.getObjectFromDatabase();
        Category.getObjectFromDatabase();
        DiscountCode.getObjectFromDatabase();
        Off.getObjectFromDatabase();
        Product.getObjectFromDatabase();

        ConsoleView.getInstance().goToNextPage(MainMenuUI.getInstance());
        ConsoleView.getInstance().processInput("main menu");
    }
}