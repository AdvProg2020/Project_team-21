package GUIControllers.ManagerAccount;

import Controller.Control;
import Controller.ControlManager;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import Model.Account.Account;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageProducts extends GraphicFather implements Initializable {
    public TextField productToRemove;
    public TableView<Product> listProducts;

    public TableColumn<Product,String> name = new TableColumn<>("Name");
    public TableColumn<Product,String> id = new TableColumn<>("ProductId");
    public Label alertLabel;

    ObservableList<Product> getProducts(){
        ObservableList<Product> result =  FXCollections.observableArrayList();
        for (String s : Product.getAllProducts().keySet()) {
            result.add(Product.getAllProducts().get(s));
        }
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listProducts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        listProducts.setItems(getProducts());
        listProducts.getColumns().add(name);
        listProducts.getColumns().add(id);
    }

    public void removeProduct(MouseEvent mouseEvent) {
        try {
            ControlManager.getInstance().removeProduct(productToRemove.getText());
            showError(alertLabel,"Product " + productToRemove.getText() + " has been successfully deleted!",Error.POSITIVE);
        } catch (Exception e) {
            showError(alertLabel,e.getMessage(), Error.NEGATIVE);
        }

    }

}
