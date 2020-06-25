package GUIControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class PaymentPage extends GraphicFather implements Initializable {
    public Label totalAmountLabel;
    private static double totalAmount;

    public static void setTotalAmount(double totalAmount) {
        PaymentPage.totalAmount = totalAmount;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        totalAmountLabel.setText("Total Amount: " + totalAmount + "$");
    }
}
