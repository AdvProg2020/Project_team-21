package Client.GUIControllers;

import Client.ClientCenter;
import Client.ServerRequest;
import javafx.event.Event;
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
    String name = "";

    private void showPopupLogin(Event event) throws IOException {
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        Text txt = new Text("Wellcome " + name + " \\\\(•◡•)//");
        txt.setFill(Paint.valueOf("069438"));
        Font font = new Font("Hiragino Sans W3" , 20);
        txt.setFont(font);
        dialogVbox.getChildren().add(txt);
        dialogVbox.setBackground(new Background(new BackgroundFill(Paint.valueOf("80A7EB"), CornerRadii.EMPTY, Insets.EMPTY)));
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 400, 200);
        dialogScene.setFill(Paint.valueOf("80A7EB"));
        dialog.setScene(dialogScene);
        dialog.show();
    }

    public void done(MouseEvent mouseEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.LOGIN,username.getText() + "&" + password.getText());
        try {
            String message = ClientCenter.getInstance().readMessageFromServer();
            if(message.startsWith(ServerRequest.DONE.toString())){
                String[] parsedMessage = message.split("&");
                String token = parsedMessage[1];
                name = parsedMessage[2];
                ClientCenter.getInstance().setActiveToken(token);
                goToNextPage(GUICenter.getInstance().getLanding(),mouseEvent);
                showPopupLogin(mouseEvent);
            }
            else{
                showError(AlertLabel,message,Error.NEGATIVE);
            }
        } catch (IOException e) {
            System.out.println("error in login");
        }
    }


}
