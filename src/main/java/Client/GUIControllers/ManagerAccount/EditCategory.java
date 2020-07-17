package Client.GUIControllers.ManagerAccount;

import Server.Controller.ControlManager;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Server.Model.Category;
import Server.Model.Product;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class EditCategory extends GraphicFather {
    public Label alertLabel;
    public TextField newName;
    public TextField productsToRemove;
    public TextField productsToAdd;
    public Label okLabel;

    public void makeChange(MouseEvent mouseEvent) {
        String editCategory = ControlManager.getInstance().getCategoryToEdit();
        ArrayList<String> oks = new ArrayList<>();
        ArrayList<String> errors = new ArrayList<>();
        if(!newName.getText().isEmpty()){
            try {
                ControlManager.getInstance().changeCategoryName(newName.getText(),editCategory);
                oks.add("Name");
            } catch (Exception e) {
                errors.add("Name: " + e.getMessage());
            }
        }
        if(!productsToAdd.getText().isEmpty()){
            if(!Product.getAllProducts().containsKey(productsToAdd.getText())){
                errors.add("Add Product: This product doesn't exist!");
            }else{
                for (Category category : Category.getAllCategories()) {
                    if(category.getName().equalsIgnoreCase(editCategory))
                    {
                        category.addProductToCategory(Product.getAllProducts().get(productsToAdd.getText()));
                        oks.add("Add Product: Added");
                    }
                }
            }
        }
        if(!productsToRemove.getText().isEmpty()){
            if(!Product.getAllProducts().containsKey(productsToRemove.getText())){
                errors.add("Remove Product: This product doesn't exist!");
            }else{
                for (Category category : Category.getAllCategories()) {
                    if(category.getName().equalsIgnoreCase(editCategory))
                    {
                        if(category.getProductsList().contains(Product.getAllProducts().get(productsToRemove.getText()))){
                            category.removeProductFromCategory(Product.getAllProducts().get(productsToRemove.getText()));
                            oks.add("Remove Product: Removed");
                        }
                        else
                            errors.add("Remove product: This product isn't in this category!");
                    }
                }
            }
        }
        String ok = "";
        String error = "";
        for (String s : errors) {
            error += s+ " ";
        }
        for (String s : oks) {
            ok += s+ " ";
        }
        showError(alertLabel,"Fields " + error + "had problems", Error.NEGATIVE);
        showError(okLabel,"Fields " + ok + "had been successfully changed",Error.POSITIVE);

        Category.rewriteFiles();
        Product.rewriteFiles();
    }
}
