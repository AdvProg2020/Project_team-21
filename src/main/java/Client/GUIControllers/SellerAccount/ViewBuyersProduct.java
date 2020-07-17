package Client.GUIControllers.SellerAccount;

import Server.Controller.ControlSeller;
import Client.GUIControllers.GraphicFather;
import Server.Model.Account.Customer;
import Server.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewBuyersProduct extends GraphicFather implements Initializable {

    public TableView <Customer>listBuyers;
    public TableColumn<Customer,String> userName = new TableColumn<>("Username");
    private Product product = Product.getAllProducts().get(ControlSeller.getInstance().getProductViewBuyers());

    ObservableList<Customer> getBuyers(){
        ObservableList<Customer> result =  FXCollections.observableArrayList();
        result.addAll(product.getBuyers());
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listBuyers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userName.setCellValueFactory(new PropertyValueFactory<>("username"));
        listBuyers.setItems(getBuyers());
        listBuyers.getColumns().add(userName);
    }
}
