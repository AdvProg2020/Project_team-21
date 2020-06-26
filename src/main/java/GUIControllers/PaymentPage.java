package GUIControllers;

import Controller.Control;
import Controller.ControlCustomer;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Seller;
import Model.Category;
import Model.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
            showError(alertLabel,"Your purchase has been finalized successfully with logID: " + logID + "/n your new balance is: " + customer.getBalance(),Error.POSITIVE);

            Customer.rewriteFiles();
            Account.rewriteFiles();
            Product.rewriteFiles();
            Seller.rewriteFiles();
            Category.rewriteFiles();
        }
        catch (Exception e)
        {
            showError(alertLabel,"Sorry your purchase didn't get complete with error: " + e.getMessage(),Error.NEGATIVE);
        }
    }
}
