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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPage extends GraphicFather implements Initializable {
    public Button signinButton;
    public Button signupButton;
    public Label accountName;
    public Circle profilePhoto;
    boolean signedIn = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        profilePhoto.setFill(new ImagePattern(new Image("/images/account_icon.png")));
        if(Control.getInstance().getUser() != null)
            signedIn = true;
        if(signedIn)
        {
            Account user = Control.getInstance().getUser();
            signinButton.setText("");
            signinButton.setDisable(true);
            signupButton.setText("View Your Account");
            accountName.setText(user.getFirstName());
            ImagePattern img = new ImagePattern(new Image(user.getImagePath()));
            profilePhoto.setFill(img);
        }
    }

    public void gotoSignUpPage(ActionEvent actionEvent){
        if(!signedIn)
        {
            goToNextPage(Page.SIGNUP,actionEvent);
            GUICenter.getInstance().setLanding(Page.MAIN);
        }
        else{
            goToAccount(actionEvent);
        }
    }

    public void gotoSignInPage(ActionEvent actionEvent){
        goToNextPage(Page.SIGNIN,actionEvent);
        GUICenter.getInstance().setLanding(Page.MAIN);
    }

    public void gotoProductsPage(ActionEvent actionEvent) throws IOException {
    }


}
