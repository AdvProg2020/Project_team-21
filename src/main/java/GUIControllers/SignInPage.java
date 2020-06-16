package GUIControllers;

import Controller.Control;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SignInPage extends GraphicFather {

    public TextField password;
    public TextField username;
    public Label AlertLabel;

    public void done(MouseEvent mouseEvent) {
        try {
            Control.getInstance().login(username.getText(),password.getText());
            goToNextPage(GUICenter.getInstance().getLanding(),mouseEvent);
        } catch (Control.notFoundUserOrPass notFoundUserOrPass) {
            showError(AlertLabel,notFoundUserOrPass.getMessage(),Error.NEGATIVE);
        }
    }

}
