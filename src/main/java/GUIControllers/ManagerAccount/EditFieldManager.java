package GUIControllers.ManagerAccount;

import Controller.Control;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import Model.Account.Account;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.ResourceBundle;

public class EditFieldManager extends GraphicFather{
    public TextField Phone;
    public TextField Email;
    public TextField Address;
    public TextField Password;
    public Label alertLabel;
    public TextField firstName;
    public TextField lastName;
    private ArrayList<String> editedFields = new ArrayList<>();

    public void submit(MouseEvent mouseEvent) {
        Account user = Control.getInstance().getUser();
        if(!firstName.getText().isEmpty()){
            editedFields.add("First Name");
            user.setFirstName(firstName.getText());
        }
        if(!lastName.getText().isEmpty()){
            editedFields.add("Last Name");
            user.setLastName(lastName.getText());
        }
        if(!Phone.getText().isEmpty()){
            editedFields.add("Phone Number");
            user.setPhoneNumber(Phone.getText());
        }
        if(!Email.getText().isEmpty()){
            editedFields.add("Email");
            user.setEmail(Email.getText());
        }
        if(!Address.getText().isEmpty()){
            editedFields.add("Address");
            user.setAddress(Address.getText());
        }
        if(!Password.getText().isEmpty()){
            editedFields.add("Password");
            user.setPassword(Password.getText());
        }
        if(editedFields.isEmpty()){
            showError(alertLabel,"No field has been updated!", Error.NEGATIVE);
        }
        else{
            String fields = "";
            for (String editedField : editedFields) {
                fields += editedField + " ";
            }
            showError(alertLabel,"Fields: " + fields + "has been updated for you!",Error.POSITIVE);
        }
    }
}
