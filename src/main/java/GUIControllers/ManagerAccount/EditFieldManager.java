package GUIControllers.ManagerAccount;

import GUIControllers.GraphicFather;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.EnumMap;
import java.util.ResourceBundle;

public class EditFieldManager extends GraphicFather implements Initializable {
    public TextField Username;
    public TextField Phone;
    public TextField Email;
    public TextField Address;
    public TextField Password;
    public TextField Name;
    public Label AlertLabel;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Username.setDisable(true);
        Phone.setDisable(true);
        Email.setDisable(true);
        Address.setDisable(true);
        Password.setDisable(true);
        Name.setDisable(true);
    }

    public void enableName(MouseEvent mouseEvent) {
        Name.setDisable(false);
    }

    public void enablePassword(MouseEvent mouseEvent) {
        Password.setDisable(false);
    }

    public void enableEmail(MouseEvent mouseEvent) {
        Email.setDisable(false);
    }

    public void enablePhone(MouseEvent mouseEvent) {
        Email.setDisable(false);
    }

    public void enableAddress(MouseEvent mouseEvent) {
        Address.setDisable(false);
    }

    public void enableUsername(MouseEvent mouseEvent) {
        Username.setDisable(false);
    }

    public void makeChange(MouseEvent mouseEvent) {
    }

}
