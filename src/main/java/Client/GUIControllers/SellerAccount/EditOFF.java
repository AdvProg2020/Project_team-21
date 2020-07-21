package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EditOFF extends GraphicFather{
    public TextField percentage;
    public TextField endDate;
    public TextField startDate;
    public Label alertLabel;
    public TextField productToAdd;
    public TextField productToRemove;
    public Label okLabel;


    public void makeChange(MouseEvent mouseEvent) {
        if(percentage.getText().isEmpty() && endDate.getText().isEmpty() && startDate.getText().isEmpty()&&productToRemove.getText().isEmpty() && productToAdd.getText().isEmpty()){
            showError(alertLabel,"You should fill at least one field",Error.NEGATIVE);
        }else {
            String offID = ClientCenter.getInstance().getOffToEdit();

            String output = "";

            if(!percentage.getText().isEmpty()){
                output += "percentage&" + percentage.getText() + "&" + offID;
            }
            if(!endDate.getText().isEmpty()){
                if(!output.isEmpty())
                    output += "//";
                output += "endtime&" + endDate.getText() + "&" + offID;
            }
            if(!startDate.getText().isEmpty()){
                if(!output.isEmpty())
                    output += "//";
                output += "starttime&" + startDate.getText() + "&" + offID;
            }
            if(!productToAdd.getText().isEmpty()){
                if(!output.isEmpty())
                    output += "//";
                output += "addproduct&" + productToAdd.getText() + "&" + offID;
            }
            if(!productToRemove.getText().isEmpty()){
                if(!output.isEmpty())
                    output += "//";
                output += "removeproduct&" + productToRemove.getText() + "&" + offID;
            }
            ClientCenter.getInstance().sendReqToServer(ServerRequest.UPDATEEDITOFFREQ,output);
            String message = ClientCenter.getInstance().readMessageFromServer();
            String ok=message.split( " - ")[0];
            String error=message.split(" - ")[1];
            String reqIDs = message.split(" - ")[2];
            showError(alertLabel,"Fields " + error + "had problems", Error.NEGATIVE);
            showError(okLabel,"Fields " + ok + "had been successfully changed with these Request IDs:",Error.POSITIVE);
            okLabel.setText(okLabel.getText() + "/n" + reqIDs);
        }
    }
}
