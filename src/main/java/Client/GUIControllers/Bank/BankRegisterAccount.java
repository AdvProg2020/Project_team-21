package Client.GUIControllers.Bank;

import Client.ClientCenter;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class BankRegisterAccount {

    public TextField usernameTextField;
    public TextField passwordTextField;
    public TextField repeatPasswordTextField;
    public TextField firstNameTextField;
    public TextField lastNameField;


    public void registerInBank(ActionEvent actionEvent){
        String username=usernameTextField.getText();
        String password=passwordTextField.getText();
        String repeatPassword=repeatPasswordTextField.getText();
        String firstName=firstNameTextField.getText();
        String lastName=lastNameField.getText();

        String result=""; //TODO

        Alert alert;
        if(result.equals("passwords do not match”") || result.equals("username is not available”")){
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error in register process!");
            alert.setContentText(result);
        }
        else{
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("successful");
            alert.setContentText("your account id in bank is: "+result+" please keep record of your bank account id!");
            String token=""; //TODO
            //MakeRequest.getBankTokenForClient(username,password);
            ClientCenter.getInstance().setBankAccountToken(token);
        }
    }
}

