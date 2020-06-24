package GUIControllers.SellerAccount;

import Controller.ControlManager;
import Controller.ControlSeller;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditOFF extends GraphicFather{
    public TextField percentage;
    public TextField endDate;
    public TextField startDate;
    public Label alertLabel;
    public TextField productToAdd;
    public TextField productToRemove;
    public Label okLabel;
    private ArrayList<String> oks = new ArrayList<>();
    private ArrayList<String> errors = new ArrayList<>();

    public void makeChange(MouseEvent mouseEvent) {
        String offID = ControlSeller.getInstance().getOffToEdit();
        ArrayList<String> reqIDs = new ArrayList<>();
        if(!percentage.getText().isEmpty()){
            try {
                String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,"amount",percentage.getText(),"null");
                reqIDs.add(reqID);
                oks.add("Percentage");
            } catch (Exception e) {
                errors.add("Percentage: "+ e.getMessage());
            }
        }
        if(!endDate.getText().isEmpty()){
            try {
                String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,"end",endDate.getText(),"null");
                reqIDs.add(reqID);
                oks.add("End Date");
            } catch (Exception e) {
                errors.add("End Date: "+ e.getMessage());
            }
        }
        if(!startDate.getText().isEmpty()){
            try {
                String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,"start",startDate.getText(),"null");
                reqIDs.add(reqID);
                oks.add("Start Date");
            } catch (Exception e) {
                errors.add("Start Date: "+ e.getMessage());
            }
        }
        if(!productToAdd.getText().isEmpty()){
            try {
                String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,"add product","",productToAdd.getText());
                reqIDs.add(reqID);
                oks.add("Add Product");
            } catch (Exception e) {
                errors.add("Add Product: "+ e.getMessage());
            }
        }
        if(!productToRemove.getText().isEmpty()){
            try {
                String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,"remove product","",productToRemove.getText());
                reqIDs.add(reqID);
                oks.add("Remove Product");
            } catch (Exception e) {
                errors.add("Remove Product: "+ e.getMessage());
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
        showError(okLabel,"Fields " + ok + "had been successfully changed with these Request IDs:",Error.POSITIVE);
        for (String reqID : reqIDs) {
            okLabel.setText(okLabel.getText() + "\n" + reqID);
        }
    }
}
