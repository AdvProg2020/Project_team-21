package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CreateDisocuntCode extends GraphicFather{
    public TextField startDate;
    public TextField endDate;
    public TextField percentage;
    public TextField maxPercentage;
    public Label alertLabel;
    public TextField maxAmount;
    public TextField usersToAdd;

    public void done(MouseEvent mouseEvent) {
        if(startDate.getText().isEmpty() || endDate.getText().isEmpty()||percentage.getText().isEmpty()||maxAmount.getText().isEmpty()||maxPercentage.getText().isEmpty()){
            showError(alertLabel,"You should fill all necessary fields",Error.NEGATIVE);
        }else{
            String users = usersToAdd.getText();
            if (usersToAdd.getText().isEmpty())
                users = "NULL";
            String output = startDate.getText() + "&" + endDate.getText() + "&" + percentage.getText() +"&" + maxPercentage.getText() + "&" + maxAmount.getText() + "//" +
                    users;
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTCREATEDISCOUNTCODE,output);
            String message = ClientCenter.getInstance().readMessageFromServer();
            if(message.startsWith(ServerRequest.ERROR.toString())){
                showError(alertLabel,ClientCenter.getInstance().getMessageFromError(message),Error.NEGATIVE);
            }else{
                String[] parsed = message.split(" - ");
                showError(alertLabel,"New Discount Code with id " + parsed[1] + " has been successfully been made.\nUsers not detected: " + parsed[2]+"\nUsers not customer " + parsed[3]
                        , Error.POSITIVE);
            }
        }
    }
}
