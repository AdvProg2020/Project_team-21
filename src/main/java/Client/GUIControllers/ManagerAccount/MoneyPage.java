package Client.GUIControllers.ManagerAccount;

import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Server.Model.Account.Account;
import Server.Model.Account.Customer;
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

public class MoneyPage extends GraphicFather implements Initializable {
    public TextField customerToAdd;
    public TableView<Customer> listCustomers;
    public Label alertLabel;
    public TableColumn<Customer,String> userName = new TableColumn<>("Username");
    public TableColumn<Customer,Double> balance = new TableColumn<>("Balance");
    public TextField amountMoney;

    ObservableList<Customer> getCustomers(){
        ObservableList<Customer> result =  FXCollections.observableArrayList();
        for (String s : Account.getAllAccounts().keySet()) {
            if(Account.getAllAccounts().get(s) instanceof Customer)
                result.add((Customer)Account.getAllAccounts().get(s));
        }
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listCustomers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userName.setCellValueFactory(new PropertyValueFactory<>("username"));
        balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        listCustomers.setItems(getCustomers());
        listCustomers.getColumns().add(userName);
        listCustomers.getColumns().add(balance);
    }

    public void addMoney(MouseEvent mouseEvent) {
        if(Account.getAllAccounts().containsKey(customerToAdd.getText())){
            if(!(Account.getAllAccounts().get(customerToAdd.getText()) instanceof Customer)){
                showError(alertLabel,"This user is not a customer",Error.NEGATIVE);
            }
            else if(!amountMoney.getText().matches("\\d+.?(\\d+)?")){
                showError(alertLabel,"Amount format is incorrect",Error.NEGATIVE);
            }
            else{
                Customer customer = (Customer) Account.getAllAccounts().get(customerToAdd.getText());
                customer.addBalance(Double.parseDouble(amountMoney.getText()));
                showError(alertLabel,"Money added to balance successfully.",Error.POSITIVE);
//                Customer.rewriteFiles();
//                Account.rewriteFiles();
            }
        }
        else{
            showError(alertLabel,"This username doesn't exist.", Error.NEGATIVE);
        }
    }

    public void selection(MouseEvent mouseEvent) {
        Customer selectedItem = listCustomers.getSelectionModel().getSelectedItem();
        customerToAdd.setText(selectedItem.getUsername());
    }
}
