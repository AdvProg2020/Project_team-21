package GUIControllers.ManagerAccount;

import Controller.ControlManager;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import Model.Account.Customer;
import Model.DiscountCode;
import Model.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private ArrayList<String> oks = new ArrayList<>();
    private ArrayList<String> errors = new ArrayList<>();

    public void makeChange(MouseEvent mouseEvent) {
        String code = ControlManager.getInstance().getDiscountCodeToEdit();

        if(!startTime.getText().isEmpty()){
            try {
                ControlManager.getInstance().editDiscountCode("Start date",startTime.getText(),code);
                oks.add("Start time");
            } catch (Exception e) {

                errors.add("Start time: "+ e.getMessage());
            }
        }
        if(!endTime.getText().isEmpty()){
            try {
                ControlManager.getInstance().editDiscountCode("End date",endTime.getText(),code);
                oks.add("End time");
            } catch (Exception e) {
                errors.add("End time: "+ e.getMessage());
            }
        }
        if(!percentage.getText().isEmpty()){
            try {
                ControlManager.getInstance().editDiscountCode("Percentage",percentage.getText(),code);
                oks.add("Percentage");
            } catch (Exception e) {
                errors.add("Percentage: " + e.getMessage());
            }
        }
        if(!maxPerUser.getText().isEmpty()){
            try {
                ControlManager.getInstance().editDiscountCode("Max times",maxPerUser.getText(),code);
                oks.add("Max per user");
            } catch (Exception e) {
                errors.add("Max per user: " + e.getMessage());
            }
        }
        if(!addUsers.getText().isEmpty()){
            try {
                ControlManager.getInstance().editDiscountCode("add owner",addUsers.getText(),code);
                oks.add("Add user");
            } catch (Exception e) {
                errors.add("Add user: "+e.getMessage());
            }
        }
        if(!removeUsers.getText().isEmpty()){
            try {
                ControlManager.getInstance().editDiscountCode("remove owner",removeUsers.getText(),code);
                oks.add("Remove user");
            } catch (Exception e) {
                errors.add("Remove user: " + e.getMessage());
            }
        }
        if(!maxPercentage.getText().isEmpty()){
            try {
                ControlManager.getInstance().editDiscountCode("Max amount",maxPercentage.getText(),code);
                oks.add("Max percentage");
            } catch (Exception e) {
                errors.add("Max percentage: " + e.getMessage());
            }
        }
        String ok="";
        String error="";
        for (String s : oks) {
            ok += s +" ";
        }
        for (String s : errors) {
            error += s+" ";
        }
        showError(alertLabel,"Fields " + error + "had problems", Error.NEGATIVE);
        showError(okLabel,"Fields " + ok + "had been successfully changed",Error.POSITIVE);

        Customer.rewriteFiles();
        DiscountCode.rewriteFiles();
        Product.rewriteFiles();
    }
}
