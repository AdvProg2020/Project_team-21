package Client.GUIControllers.Bank;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class WalletPageControllers extends GraphicFather {

    public TextField ChargeWalletTextField;
    public TextField withdrawWalletTextField;

    private void showPopup(Event event,String message){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        Text txt = new Text(ClientCenter.getInstance().getMessageFromError(message));
        if(message.startsWith(ServerRequest.DONE.toString()))
            txt.setFill(Paint.valueOf("069438"));
        else
            txt.setFill(Paint.valueOf("f21a25"));
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

    public void withdraw(ActionEvent actionEvent) {
        if(!withdrawWalletTextField.getText().matches("\\d+.?\\d*")){
            showPopup(actionEvent,ServerRequest.ERROR + "&" + "Your data input is invalid.");
        }else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTWITHDRAWWALLET,withdrawWalletTextField.getText());
            showPopup(actionEvent,ClientCenter.getInstance().readMessageFromServer());
        }
    }

    public void charge(ActionEvent actionEvent) {
        if(!ChargeWalletTextField.getText().matches("\\d+.?\\d*")){
            showPopup(actionEvent,ServerRequest.ERROR + "&" + "Your data input is invalid.");
        }else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTCHARGEWALLET,ChargeWalletTextField.getText());
            showPopup(actionEvent,ClientCenter.getInstance().readMessageFromServer());
        }
    }
}
