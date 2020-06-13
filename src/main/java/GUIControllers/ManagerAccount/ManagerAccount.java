package GUIControllers.ManagerAccount;

import Controller.Control;
import GUIControllers.GraphicFather;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerAccount extends GraphicFather implements Initializable {


    public Circle photoCircle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image;
        if(Control.getInstance().getUser() != null)
            image = new Image(Control.getInstance().getUser().getImagePath());
        else
            image = new Image("/images/profile.png");
        photoCircle.setFill(new ImagePattern(image));
    }

    public void goToManageUsers(MouseEvent mouseEvent) {
    }
}
