package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EditDiscountCode extends GraphicFather{
    public TextField startTime;
    public TextField endTime;
    public TextField percentage;
    public TextField maxPerUser;
    public TextField addUsers;
    public TextField maxPercentage;
    public TextField removeUsers;
    public Label alertLabel;
    public Label okLabel;

    public void makeChange(MouseEvent mouseEvent) {
        if(startTime.getText().isEmpty() && endTime.getText().isEmpty() && percentage.getText().isEmpty() && maxPerUser.getText().isEmpty() && addUsers.getText().isEmpty() &&
        removeUsers.getText().isEmpty() && maxPercentage.getText().isEmpty()){
            okLabel.setText("");
            showError(alertLabel,"You should fill at least one field.",Error.NEGATIVE);
        }else{
            String code = ClientCenter.getInstance().getDiscountCodeToEdit();

            String output = "";
            if(!startTime.getText().isEmpty()){
                output += "startTime&" + startTime.getText() + "&" + code;
            }
            if(!endTime.getText().isEmpty()){
                if(!output.isEmpty())
                    output += "//";
                output += "endTime&" + endTime.getText() + "&" + code;
            }
            if(!percentage.getText().isEmpty()){
                if(!output.isEmpty())
                    output += "//";
                output += "percentage&" + percentage.getText() + "&" + code;
            }
            if(!maxPerUser.getText().isEmpty()){
                if(!output.isEmpty())
                    output += "//";
                output += "maxTimes&" + maxPerUser.getText() + "&" + code;
            }
            if(!addUsers.getText().isEmpty()){
                if(!output.isEmpty())
                    output += "//";
                output += "addOwner&" + addUsers.getText() + "&" + code;
            }
            if(!removeUsers.getText().isEmpty()){
                if(!output.isEmpty())
                    output += "//";
                output += "removeOwner&" + removeUsers.getText() + "&" + code;
            }
            if(!maxPercentage.getText().isEmpty()){
                if(!output.isEmpty())
                    output += "//";
                output += "maxPercentage&" + maxPercentage.getText() + "&" + code;
            }
            ClientCenter.getInstance().sendReqToServer(ServerRequest.UPDATEEDITDISCOUNTCODE,output);
            String message = ClientCenter.getInstance().readMessageFromServer();
            String ok=message.split( " - ")[0];
            String error=message.split(" - ")[1];

            showError(alertLabel,"Fields " + error + " had problems", Error.NEGATIVE);
            showError(okLabel,"Fields " + ok + " had been successfully changed",Error.POSITIVE);
        }
    }
}
