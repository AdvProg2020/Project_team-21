package GUIControllers;

import Controller.Control;
import Model.Account.Account;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPage extends GraphicFather implements Initializable {
    public Button signinButton;
    public Button signupButton;
    public Label accountName;
    public Circle profilePhoto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShow(signinButton,signupButton,profilePhoto,accountName);
    }

    public void gotoProductsPage(ActionEvent actionEvent) throws IOException {
    }


}
