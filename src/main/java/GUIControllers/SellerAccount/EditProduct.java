package GUIControllers.SellerAccount;

import Controller.ControlSeller;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Product;
import Model.Request.OffRequest;
import Model.Request.ProductRequest;
import View.ConsoleView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class EditProduct extends GraphicFather {
    public TextField name;
    public TextField price;
    public Label alertLabel;
    public Label okLabel;
    private ArrayList<String> oks = new ArrayList<>();
    private ArrayList<String> errors = new ArrayList<>();

    public void makeChange(MouseEvent mouseEvent) {
        String productID = ControlSeller.getInstance().getProductToEdit();
        ArrayList<String> reqIDs = new ArrayList<>();
        if(!name.getText().isEmpty()){
            try
            {
                String reqID = ControlSeller.getInstance().sendProductEditReq(productID,"name",name.getText());
                System.out.println("edit name sent");
                reqIDs.add(reqID);
                oks.add("Name");
            }
            catch (Exception e)
            {
                System.out.println("name rid");
               errors.add("Name: " + e.getMessage());
            }
        }
        if(!price.getText().isEmpty()){
            try
            {
                String reqID = ControlSeller.getInstance().sendProductEditReq(productID,"price",price.getText());
                System.out.println("edit price sent");
                reqIDs.add(reqID);
                oks.add("Price");
            }
            catch (Exception e)
            {
                System.out.println("price ride");
                errors.add("Price: " + e.getMessage());
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
        Product.rewriteFiles();
        ProductRequest.rewriteFiles();
        Manager.rewriteFiles();
        Seller.rewriteFiles();
    }

}
