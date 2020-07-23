package Client.GUIControllers;

import Client.GUIControllers.Chat.Group_ClientChatController;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class MainPage extends GraphicFather implements Initializable {
    public Button signinButton;
    public Button signupButton;
    public Label accountName;
    public Circle profilePhoto;
    public Button quitButton;
    public Button userPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShow(signinButton,signupButton,profilePhoto,accountName,userPage);
    }

    public void goToSettings(MouseEvent mouseEvent) {
        goToNextPage(Page.SETTINGS,mouseEvent);
    }

    public void gotoChatRoom(ActionEvent actionEvent) {
        goToNextPage(Page.CHATPAGE,actionEvent);
    }

//    public void chat(ActionEvent actionEvent) {
//        Group_ClientChatController.setGroupChatId("123");
//        goToNextPage(Page.GROUPCHATPAGE, actionEvent);
//    }
}
