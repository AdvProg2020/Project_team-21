package GUIControllers.SellerAccount;

import GUIControllers.GraphicFather;
import Model.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Products extends GraphicFather implements Initializable {
    public TextField productToRemove;
    public TextField productToEdit;
    public TextField productToBuyers;
    public Label AlertLabel;
    public TableView<Product> listProducts;
    public TextField productToView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void addProduct(MouseEvent mouseEvent) {
    }

    public void removeProduct(MouseEvent mouseEvent) {
    }

    public void editProduct(MouseEvent mouseEvent) {
    }

    public void viewBuyers(MouseEvent mouseEvent) {
    }

    public void viewProduct(MouseEvent mouseEvent) {
    }
}
