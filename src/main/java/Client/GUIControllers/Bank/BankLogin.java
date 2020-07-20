package Client.GUIControllers.Bank;

import Client.ClientCenter;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class BankLogin {
    public TextField usernameTextField;
    public TextField passwordTextField;

    public void loginInBank(ActionEvent actionEvent){
        // string result mishe tabe e getbankTokenFromClient
        String result="";
        //TODO
        if (result.equals("invalid username or password")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in Login");
            alert.setContentText(result);
            return;
        }else {
            ClientCenter.getInstance().setBankAccountToken(result);
        }

    }
    public void goToRegisterBank(ActionEvent actionEvent){
        //Scene avaz mishe
        //TODO
    }
}
