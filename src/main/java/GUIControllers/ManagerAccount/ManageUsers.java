package GUIControllers.ManagerAccount;

import GUIControllers.GraphicFather;
import Model.Account.Account;
import com.sun.glass.ui.Accessible;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsers extends GraphicFather implements Initializable {

    public TableView<Account> listUsers;
    public TableColumn<Account,String> userName = new TableColumn<>("Username");
    public TableColumn<Account,String> type = new TableColumn<>("Type");

    public void deleteUser(MouseEvent mouseEvent) {
    }
    ObservableList<Account> getAccounts(){
        ObservableList<Account> result =  FXCollections.observableArrayList();
        for (String s : Account.getAllAccounts().keySet()) {
            result.add(Account.getAllAccounts().get(s));
        }
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listUsers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userName.setCellValueFactory(new PropertyValueFactory<>("username"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        listUsers.setItems(getAccounts());
        listUsers.getColumns().add(userName);
        listUsers.getColumns().add(type);
    }

    public void viewUser(MouseEvent mouseEvent) {
    }
}
