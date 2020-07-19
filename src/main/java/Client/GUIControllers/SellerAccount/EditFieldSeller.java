package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class EditFieldSeller extends GraphicFather {
    public TextField Phone;
    public TextField Email;
    public TextField Address;
    public TextField Password;
    public TextField companyName;
    public TextField companyAddress;
    public Label alertLabel;
    public TextField firstName;
    public TextField lastName;
    private ArrayList<String> editedFields = new ArrayList<>();

    public void makeChange(MouseEvent mouseEvent) {
        String output = "";

        if(!firstName.getText().isEmpty()){
            editedFields.add("First Name");
            output += "firstname&" + firstName.getText();
        }
        if(!lastName.getText().isEmpty()){
            editedFields.add("Last Name");
            if(!output.isEmpty())
                output += "//";
            output += "lastname&" + lastName.getText();
        }
        if(!Phone.getText().isEmpty()){
            editedFields.add("Phone Number");
            if(!output.isEmpty())
                output += "//";
            output += "phone&" + Phone.getText();
        }
        if(!Email.getText().isEmpty()){
            editedFields.add("Email");
            if(!output.isEmpty())
                output += "//";
            output += "email&" + Email.getText();
        }
        if(!Address.getText().isEmpty()){
            editedFields.add("Address");
            if(!output.isEmpty())
                output += "//";
            output += "address&" + Address.getText();
        }
        if(!Password.getText().isEmpty()){
            editedFields.add("Password");
            if(!output.isEmpty())
                output += "//";
            output += "password&" + Password.getText();
        }
        if(!companyName.getText().isEmpty()){
            editedFields.add("Company Name");
            if(!output.isEmpty())
                output += "//";
            output += "companyname&" + companyName.getText();
        }
        if(!companyAddress.getText().isEmpty()){
            editedFields.add("Company Address");
            if(!output.isEmpty())
                output += "//";
            output += "companyaddress&" + companyAddress.getText();
        }
        if(editedFields.isEmpty()){
            showError(alertLabel,"No field has been updated!", Error.NEGATIVE);
        }
        else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.UPDATEEDITFIELDSELLER,output);
            String fields = "";
            for (String editedField : editedFields) {
                fields += editedField + " ";
            }
            showError(alertLabel,"Fields: " + fields + "has been updated for you!",Error.POSITIVE);

        }
    }
}
