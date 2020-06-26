import Controller.Control;
import GUIControllers.GUICenter;
import GUIControllers.Page;
import Model.*;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Log.BuyLog;
import Model.Log.Log;
import Model.Log.SellLog;
import Model.Request.*;
import View.ConsoleView;
import View.MainMenuUI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Paths;

public class Main extends Application {
    static void putToAbstract(){
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
    static void readFilesFromDatabase(){
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
        Company.getObjectFromDatabase();
        Score.getObjectFromDatabase();
        Review.getObjectFromDatabase();
        putToAbstract();
    }
    MediaPlayer mediaPlayer;
    public void music() {
        Media h = new Media(getClass().getResource("/Musics/back.mp3").toString());
        mediaPlayer = new MediaPlayer(h);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.setVolume(GUICenter.getInstance().getMusicVolume());
        GUICenter.getInstance().setMediaPlayer(mediaPlayer);
    }

    private static Scene scene;

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        readFilesFromDatabase();
        for (String s : Account.getAllAccounts().keySet()) {
            System.out.println(s + "  "+Account.getAllAccounts().get(s).getType() + " " + Account.getAllAccounts().get(s).getImagePath());
        }
        launch(args);
        ConsoleView.getInstance().goToNextPage(MainMenuUI.getInstance());
        ConsoleView.getInstance().processInput("main menu");
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Idiots Market");
        music();
        if(Manager.getAllManagers().isEmpty())
        {
            Parent managerMaker = FXMLLoader.load(getClass().getResource("/fxml/MakeManagerFirst.fxml"));
            scene = new Scene(managerMaker);
            stage.setScene(scene);
            GUICenter.getInstance().addSeenPage(Page.MAKEMANAGERFIRST);
            GUICenter.getInstance().setCurrentMenu(Page.MAKEMANAGERFIRST);
            GUICenter.getInstance().setCurrentScene(scene);
        }
        else
        {
            Parent mainPage = FXMLLoader.load(getClass().getResource("/fxml/MainPage.fxml"));
            stage.setMaximized(true);
            scene = new Scene(mainPage);
            stage.setScene(scene);
            GUICenter.getInstance().addSeenPage(Page.MAIN);
            GUICenter.getInstance().setCurrentMenu(Page.MAIN);
            GUICenter.getInstance().setCurrentScene(scene);
        }
        stage.show();
    }
}