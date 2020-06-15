package GUIControllers.CustomerAccount;

import GUIControllers.GraphicFather;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAccount extends GraphicFather implements Initializable {
    public Label Username;
    public Label Name;
    public Label Address;
    public Label Phone;
    public Label Email;
    public Label Password;
    public Circle photoCircle;
    public Label balance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goToCart(MouseEvent mouseEvent) {
    }

    public void goToOrders(MouseEvent mouseEvent) {
    }

    public void goToDiscountCodes(MouseEvent mouseEvent) {
    }

    public void editField(MouseEvent mouseEvent) {
    }
}
