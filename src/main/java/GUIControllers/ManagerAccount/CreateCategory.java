package GUIControllers.ManagerAccount;

import Controller.ControlManager;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import Model.Product;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class CreateCategory extends GraphicFather {

    public TextField name;
    public TextField productsToAdd;
    public Label alertLabel;

    public void done(MouseEvent mouseEvent) {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<String> productsNotExist = new ArrayList<>();
        String[] productNames = productsToAdd.getText().split(",");
        for (String productName : productNames) {
            productName = productName.trim();
            if(Product.getAllProducts().containsKey(productName)){
                products.add(Product.getAllProducts().get(productName));
            }
            else {
                productsNotExist.add(productName);
            }
        }
        try {
            ControlManager.getInstance().addCategory(name.getText(),products);
            showError(alertLabel,"Category successfully been made without these products cause they don't exist!:" + productsNotExist,Error.POSITIVE);
            Product.rewriteFiles();
        } catch (Exception e) {
            showError(alertLabel,e.getMessage(), Error.NEGATIVE);
        }
    }
}
