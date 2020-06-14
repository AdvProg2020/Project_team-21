package GUIControllers.SellerAccount;

import GUIControllers.GraphicFather;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EditOFF extends GraphicFather implements Initializable {
    public TextField productsToAdd;
    public TextField productsToRemove;
    public TextField percentage;
    public TextField endDate;
    public TextField startDate;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void makeChange(MouseEvent mouseEvent) {
    }
}
