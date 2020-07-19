package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.Model.Account.Customer;
import Client.ServerRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewBuyersProduct extends GraphicFather implements Initializable {

    public TableView <Customer>listBuyers;
    public TableColumn<Customer,String> userName = new TableColumn<>("Username");
    private ArrayList<Customer> customers = new ArrayList<>();

    ObservableList<Customer> getBuyers(){
        ObservableList<Customer> result =  FXCollections.observableArrayList();
        result.addAll(customers);
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETBUYERSPRODUCT,ClientCenter.getInstance().getProductToViewBuyers());
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(!message.equalsIgnoreCase("NONE")){
            String[] usernames = message.split("&");
            for (String username : usernames) {
                customers.add(new Customer(username));
            }
        }
        listBuyers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userName.setCellValueFactory(new PropertyValueFactory<>("username"));
        listBuyers.setItems(getBuyers());
        listBuyers.getColumns().add(userName);
    }
}
