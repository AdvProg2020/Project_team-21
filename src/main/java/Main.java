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
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    static void readFilesFromDatabase()
    {
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
        for (Seller seller : Seller.getAllSeller()) {
            Account.addAccount(seller);
        }
        for (Manager manager : Manager.getAllManagers()) {
            Account.addAccount(manager);
        }
        for (Customer customer : Customer.getaAllCustomers()) {
            Account.addAccount(customer);
        }
    }



    public static void main(String[] args) {
        readFilesFromDatabase();
        launch(args);
        ConsoleView.getInstance().goToNextPage(MainMenuUI.getInstance());
        ConsoleView.getInstance().processInput("main menu");
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Idiots Market");
        Scene scene = null;
        if(Manager.getAllManagers().isEmpty())
        {
            Parent managerMaker = FXMLLoader.load(getClass().getResource("/fxml/MakeManagerFirst.fxml"));
            scene = new Scene(managerMaker, 1000, 720);
            stage.setScene(scene);
        }
        else
        {
            Parent mainPage = FXMLLoader.load(getClass().getResource("/fxml/mainPage.fxml"));
            scene = new Scene(mainPage, 1000, 720);
            stage.setScene(scene);
        }
        stage.show();
    }
}