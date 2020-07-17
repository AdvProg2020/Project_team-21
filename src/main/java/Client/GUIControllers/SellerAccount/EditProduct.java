package Client.GUIControllers.SellerAccount;

import Server.Controller.ControlSeller;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Server.Model.Account.Manager;
import Server.Model.Account.Seller;
import Server.Model.Product;
import Server.Model.Request.ProductRequest;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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
