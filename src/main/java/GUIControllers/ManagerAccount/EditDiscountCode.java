package GUIControllers.ManagerAccount;

import GUIControllers.GraphicFather;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class EditDiscountCode extends GraphicFather implements Initializable {
    public TextField startTime;
    public TextField endTime;
    public TextField percentage;
    public TextField maxPerUser;
    public TextField addUsers;
    public TextField maxPercentage;
    public TextField removeUsers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startTime.setDisable(true);
        endTime.setDisable(true);
        percentage.setDisable(true);
        maxPerUser.setDisable(true);
        maxPercentage.setDisable(true);
        addUsers.setDisable(true);
        removeUsers.setDisable(true);

    }


    public void makeChange(MouseEvent mouseEvent) {
    }

    public void enableMaxPerUser(MouseEvent mouseEvent) {
        maxPerUser.setDisable(false);

    }

    public void enableEndTime(MouseEvent mouseEvent) {
        endTime.setDisable(false);
    }

    public void enablePercentage(MouseEvent mouseEvent) {
        percentage.setDisable(false);
    }

    public void enableStartTime(MouseEvent mouseEvent) {
        startTime.setDisable(false);
    }

    public void enableMaxPercentage(MouseEvent mouseEvent) {
        maxPercentage.setDisable(false);
    }

    public void enableAddUser(MouseEvent mouseEvent) {
        addUsers.setDisable(false);
    }

    public void enableRemoveUser(MouseEvent mouseEvent) {
        removeUsers.setDisable(false);
    }
}
