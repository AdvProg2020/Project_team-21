package GUIControllers.ManagerAccount;

import Controller.Control;
import Controller.ControlManager;
import GUIControllers.GraphicFather;
import Model.Account.Account;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewUsername extends GraphicFather implements Initializable {
    public Circle photoCircle;
    public Label phone;
    public Label email;
    public Label name;
    public Label address;
    public Label username;
    public Label password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Account user = Account.getAllAccounts().get(ControlManager.getInstance().getUserToView());
        File file = new File(user.getImagePath());
        Image image = new Image(file.toURI().toString());
        ImagePattern img = new ImagePattern(image);
        photoCircle.setFill(img);

        phone.setText(user.getPhoneNumber());
        email.setText(user.getEmail());
        name.setText(user.getFirstName() + " " + user.getLastName());
        address.setText(user.getAddress());
        username.setText(user.getUsername());
        password.setText(user.getPassword());
    }
}
