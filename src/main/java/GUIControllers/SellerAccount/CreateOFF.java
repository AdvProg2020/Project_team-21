package GUIControllers.SellerAccount;

import Controller.Control;
import Controller.ControlSeller;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.Off;
import Model.Product;
import Model.Request.OffRequest;
import View.ConsoleView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class CreateOFF extends GraphicFather {
    public TextField productsToAdd;
    public TextField percentage;
    public TextField endDate;
    public TextField startDate;
    public Label alertLabel;
    private Seller seller = (Seller) Control.getInstance().getUser();
    public void done(MouseEvent mouseEvent) {
        ArrayList<Product> products = new ArrayList<>();
        String[] productIDs = productsToAdd.getText().split(",");
        ArrayList<String> productsNotExist = new ArrayList<>();

        for (String productID : productIDs){
            productID = productID.trim();
            if (!ControlSeller.getInstance().checkProductExists(productID) || !ControlSeller.getInstance().checkSellerGotProduct(productID, seller))
            {
                productsNotExist.add(productID);
                continue;
            }
            products.add(Product.getAllProducts().get(productID));
        }
        try{
            String reqID = ControlSeller.getInstance().sendAddOfRequest(products,percentage.getText(),startDate.getText(),endDate.getText());
            showError(alertLabel,"Your request with ID " + reqID + " has been sent without these products cause they don't exist: "+productsNotExist, Error.POSITIVE);

            Off.rewriteFiles();
            OffRequest.rewriteFiles();
            Product.rewriteFiles();
            Seller.rewriteFiles();
            Manager.rewriteFiles();
        }
        catch (Exception e){
            showError(alertLabel,e.getMessage(),Error.NEGATIVE);
        }
    }
}
