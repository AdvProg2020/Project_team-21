package GUIControllers.SellerAccount;

import Controller.Control;
import GUIControllers.GraphicFather;
import Model.Account.Seller;
import Model.Log.SellLog;
import Model.Request.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class SalesHistory extends GraphicFather implements Initializable {
    public TableView<SellLog> listSales;
    public TableColumn<SellLog,String> id = new TableColumn<>("Sale ID");
    public TableColumn<SellLog,String>  money= new TableColumn<>("Total");
    public TableColumn<SellLog,String> customer = new TableColumn<>("Sold To");

    ObservableList<SellLog> getLogs(){
        ObservableList<SellLog> result =  FXCollections.observableArrayList();
        Seller user = (Seller) Control.getInstance().getUser();
        result.addAll(user.getSellLogs());
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listSales.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        money.setCellValueFactory(new PropertyValueFactory<>("price"));
        id.setCellValueFactory(new PropertyValueFactory<>("logID"));
        customer.setCellValueFactory(new PropertyValueFactory<>("receiverUserName"));
        listSales.setItems(getLogs());
        listSales.getColumns().add(money);
        listSales.getColumns().add(id);
        listSales.getColumns().add(customer);
    }
}
