package GUIControllers;

import Controller.Control;
import Model.*;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Log.BuyLog;
import Model.Log.SellLog;
import Model.Request.OffRequest;
import Model.Request.ProductRequest;
import Model.Request.SellerRequest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPage extends GraphicFather implements Initializable {
    public Button signinButton;
    public Button signupButton;
    public Label accountName;
    public Circle profilePhoto;
    public Button quitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShow(signinButton,signupButton,profilePhoto,accountName);
    }

    public void gotoProductsPage(ActionEvent actionEvent) throws IOException {
    }
//
//    public void end(MouseEvent mouseEvent) {
//        Account.rewriteFiles();
//        Customer.rewriteFiles();
//        Manager.rewriteFiles();
//        Seller.rewriteFiles();
//        BuyLog.rewriteFiles();
//        SellLog.rewriteFiles();
//        OffRequest.rewriteFiles();
//        ProductRequest.rewriteFiles();
//        SellerRequest.rewriteFiles();
//        Category.rewriteFiles();
//        Company.rewriteFiles();
//        DiscountCode.rewriteFiles();
//        Off.rewriteFiles();
//        Product.rewriteFiles();
//        Review.rewriteFiles();
//        Score.rewriteFiles();
////        ShoppingCart.rewriteFiles();
//        Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
//        stage.close();
////        Platform.exit();
//    }

    public void goToSettings(MouseEvent mouseEvent) {
        goToNextPage(Page.SETTINGS,mouseEvent);
    }
}
