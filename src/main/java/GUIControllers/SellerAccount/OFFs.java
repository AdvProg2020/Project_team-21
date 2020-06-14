package GUIControllers.SellerAccount;

import GUIControllers.GraphicFather;
import Model.Off;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class OFFs extends GraphicFather implements Initializable {
    public TextField offToView;
    public TableView<Off> listOFFs;
    public TextField offToEdit;
    public Label AlertLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void viewOFF(MouseEvent mouseEvent) {
    }

    public void editOFF(MouseEvent mouseEvent) {
    }

    public void createOFF(MouseEvent mouseEvent) {
    }
}
