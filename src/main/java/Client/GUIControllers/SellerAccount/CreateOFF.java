package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CreateOFF extends GraphicFather {
    public TextField productsToAdd;
    public TextField percentage;
    public TextField endDate;
    public TextField startDate;
    public Label alertLabel;

    public void done(MouseEvent mouseEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTCREATEOFF, productsToAdd.getText() + "//" + percentage.getText() + "//" + startDate.getText()
        + "//" + endDate.getText());
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString())){
            String[] parsedMessage = message.split("&");
            showError(alertLabel,"Your request with ID " + parsedMessage[1] + " has been sent without these products cause they don't exist: "+parsedMessage[2], Error.POSITIVE);
        }else {
            showError(alertLabel,ClientCenter.getInstance().getMessageFromError(message),Error.NEGATIVE);
        }
    }
}
