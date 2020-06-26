package GUIControllers;

import Controller.Control;
import Controller.ControlCustomer;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Seller;
import Model.Category;
import Model.Product;
import javafx.event.Event;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentPage extends GraphicFather implements Initializable {
    public Label totalAmountLabel;
    private static double totalAmount;
    public TextField address;
    public TextField phone;
    public TextField discountCode;
    public Label alertLabel;
    public TextField receiverName;
    public TextField postalCode;

    private void showPopup(Event event,String text){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        Text txt = new Text(text + " [̲̅$̲̅(̲̅ ͡° ͜ʖ ͡°̲̅)̲̅$̲̅]");
        txt.setFill(Paint.valueOf("069438"));
        Font font = new Font("Hiragino Sans W3" , 20);
        txt.setFont(font);
        dialogVbox.getChildren().add(txt);
        dialogVbox.setBackground(new Background(new BackgroundFill(Paint.valueOf("80A7EB"), CornerRadii.EMPTY, Insets.EMPTY)));
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 550, 350);
        dialogScene.setFill(Paint.valueOf("80A7EB"));
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public static void setTotalAmount(double totalAmount) {
        PaymentPage.totalAmount = totalAmount;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        totalAmountLabel.setText("Total Amount: " + totalAmount + "$");
    }

    public void pay(MouseEvent mouseEvent) {
        Customer customer = (Customer) Control.getInstance().getUser();

        try
        {
            String logID = ControlCustomer.getInstance().purchase(customer,receiverName.getText(),address.getText(),phone.getText(),postalCode.getText(),discountCode.getText());

            Customer.rewriteFiles();
            Account.rewriteFiles();
            Product.rewriteFiles();
            Seller.rewriteFiles();
            Category.rewriteFiles();

            goToMain(mouseEvent);
            showPopup(mouseEvent , "Your purchase has been finalized successfully with logID: " + logID + "/n your new balance is: " + customer.getBalance());
        }
        catch (Exception e)
        {
            showError(alertLabel,"Sorry your purchase didn't get complete with error: " + e.getMessage(),Error.NEGATIVE);
        }
    }
}
