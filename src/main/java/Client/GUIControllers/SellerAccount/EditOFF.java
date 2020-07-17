package Client.GUIControllers.SellerAccount;

import Server.Controller.Control;
import Server.Controller.ControlSeller;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Server.Model.Account.Manager;
import Server.Model.Account.Seller;
import Server.Model.Off;
import Server.Model.Product;
import Server.Model.Request.OffRequest;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

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
    private Seller seller = (Seller) Control.getInstance().getUser();

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
                if (ControlSeller.getInstance().checkProductExists(productToRemove.getText())&&ControlSeller.getInstance().checkSellerGotProduct(productToRemove.getText(), seller))
                {
                    String reqID = ControlSeller.getInstance().sendEditOffRequest(offID,"remove product","",productToRemove.getText());
                    reqIDs.add(reqID);
                    oks.add("Remove Product");
                }
                else{
                    errors.add("Remove Product: You don't have this product");
                }
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

        Off.rewriteFiles();
        OffRequest.rewriteFiles();
        Manager.rewriteFiles();
        Seller.rewriteFiles();
        Product.rewriteFiles();
    }
}
