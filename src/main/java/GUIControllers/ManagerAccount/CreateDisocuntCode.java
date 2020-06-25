package GUIControllers.ManagerAccount;

import Controller.Control;
import Controller.ControlManager;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import Model.Account.Account;
import Model.Account.Customer;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CreateDisocuntCode extends GraphicFather{
    public TextField startDate;
    public TextField endDate;
    public TextField percentage;
    public TextField maxPercentage;
    public Label alertLabel;
    public TextField maxAmount;
    public TextField usersToAdd;

    public void done(MouseEvent mouseEvent) {
        String discountCode = Control.getInstance().randomString(10);
        ArrayList<String> usersNotExist = new ArrayList<>();
        ArrayList<String> usersNotCustomer = new ArrayList<>();
        HashMap<String,Customer> codeOwners = new HashMap<>();
        String[] userInputUsers = usersToAdd.getText().split(",");
        for (String owner : userInputUsers){
            owner = owner.trim();
            if(!Account.getAllAccounts().containsKey(owner)){
                usersNotExist.add(owner);
                continue;
            }
            if(!(Account.getAllAccounts().get(owner) instanceof Customer))
            {
                usersNotCustomer.add(owner);
                continue;
            }
            codeOwners.put(owner,(Customer) Account.getAllAccounts().get(owner));
        }
        try {
            ControlManager.getInstance().createDiscountCode(discountCode,startDate.getText(),endDate.getText()
            ,percentage.getText(),maxPercentage.getText(),maxAmount.getText(),codeOwners);
            showError(alertLabel,"New Discount Code with id " + discountCode + " has been successfully been made.\nUsers not detected: " + usersNotExist+"\nUsers not customer " + usersNotCustomer
                    , Error.POSITIVE);
        } catch (Exception e) {
            showError(alertLabel,e.getMessage(),Error.NEGATIVE);
        }
    }
}
