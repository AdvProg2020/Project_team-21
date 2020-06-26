package GUIControllers;

import Controller.Control;
import Model.Account.Account;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SignInPage extends GraphicFather {

    public TextField password;
    public TextField username;
    public Label AlertLabel;

    private void showPopupLogin(Event event){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        Text txt = new Text("Wellcome " + Account.getAllAccounts().get(username.getText()).getFirstName() + " \\\\(•◡•)//");
        txt.setFill(Paint.valueOf("7AF513"));
        Font font = new Font("Hiragino Sans W3" , 20);
        txt.setFont(font);
        dialogVbox.getChildren().add(txt);
        dialogVbox.setBackground(new Background(new BackgroundFill(Paint.valueOf("1365F5"), CornerRadii.EMPTY, Insets.EMPTY)));
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 400, 200);
        dialogScene.setFill(Paint.valueOf("1365F5"));
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void done(MouseEvent mouseEvent) {
        try {
            Control.getInstance().login(username.getText(),password.getText());
            goToNextPage(GUICenter.getInstance().getLanding(),mouseEvent);
            showPopupLogin(mouseEvent);
        } catch (Control.notFoundUserOrPass notFoundUserOrPass) {
            showError(AlertLabel,notFoundUserOrPass.getMessage(),Error.NEGATIVE);
        }
    }

}
