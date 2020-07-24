package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.Model.Account.Account;
import Client.Model.Account.Customer;
import Client.Model.Account.Seller;
import Client.ServerRequest;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MoneyPage extends GraphicFather implements Initializable {
    public TextField customerToAdd;
    public TableView<Account> listCustomers;
    public Label alertLabel;
    public TableColumn<Account,String> userName = new TableColumn<>("Username");
    public TableColumn<Account,Double> balance = new TableColumn<>("Account Balance");
    public TextField amountMoney;
    private ArrayList<Account> allSellersAndCustomers = new ArrayList<>();

    ObservableList<Account> getCustomers(){
        ObservableList<Account> result =  FXCollections.observableArrayList();
        result.addAll(allSellersAndCustomers);
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETSELLERSCUSTOMERSBALANCE);
        String response = ClientCenter.getInstance().readMessageFromServer();
        String[] allData = response.split(" - ");
        for (String s : allData) {
            String username = s.split("&")[0];
            String balance = s.split("&")[1];
            String type = s.split("&") [2];
            if(type.equalsIgnoreCase("seller")){
                Seller seller = new Seller(username);
                seller.setAccountBalance(balance);
                allSellersAndCustomers.add(seller);
            }else{
                Customer customer = new Customer(username);
                customer.setAccountBalance(balance);
                allSellersAndCustomers.add(customer);
            }
        }

        listCustomers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userName.setCellValueFactory(new PropertyValueFactory<>("username"));
        balance.setCellValueFactory(new PropertyValueFactory<>("accountBalance"));
        listCustomers.setItems(getCustomers());
        listCustomers.getColumns().add(userName);
        listCustomers.getColumns().add(balance);
    }

    public void addMoney(MouseEvent mouseEvent) {
        if(amountMoney.getText().isEmpty() || customerToAdd.getText().isEmpty()){
            showError(alertLabel,"You should fill all fields.",Error.NEGATIVE);
        }else{
            if(!amountMoney.getText().matches("\\d+.?(\\d+)?")){
                showError(alertLabel,"Amount format is incorrect",Error.NEGATIVE);
            }
            else{
                ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTDEPOSITMONEY,customerToAdd.getText() + "&" + amountMoney.getText());
                String response = ClientCenter.getInstance().readMessageFromServer();
                if(response.startsWith(ServerRequest.DONE.toString())){
                    showError(alertLabel,ClientCenter.getInstance().getMessageFromError(response),Error.POSITIVE);
                }else{
                    showError(alertLabel,ClientCenter.getInstance().getMessageFromError(response),Error.NEGATIVE);
                }
            }
        }
    }

    public void selection(MouseEvent mouseEvent) {
        Account selectedItem = listCustomers.getSelectionModel().getSelectedItem();
        customerToAdd.setText(selectedItem.getUsername());
    }
}
