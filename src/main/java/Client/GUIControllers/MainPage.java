package Client.GUIControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPage extends GraphicFather implements Initializable {
    public Button signinButton;
    public Button signupButton;
    public Label accountName;
    public Circle profilePhoto;
    public Button quitButton;
    public Button userPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShow(signinButton,signupButton,profilePhoto,accountName,userPage);
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
