package GUIControllers.ManagerAccount;

import Controller.Control;
import Controller.ControlManager;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import Model.DiscountCode;
import View.ConsoleView;
import com.sun.glass.ui.Accessible;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageUsers extends GraphicFather implements Initializable {

    public TableView<Account> listUsers;
    public TableColumn<Account,String> userName = new TableColumn<>("Username");
    public TableColumn<Account,String> type = new TableColumn<>("Type");
    public TextField userToView;
    public TextField userToRemove;
    public Label AlertLabel;

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

    public void viewUser(MouseEvent mouseEvent){
        if(Account.getAllAccounts().keySet().contains(userToView.getText())){
            ControlManager.getInstance().setUserToView(userToView.getText());
            goToNextPage(Page.VIEWUSER,mouseEvent);
        }
        else{
            showError(AlertLabel,"This username doesn't Exist",Error.NEGATIVE);
        }
    }

    public void removeUser(MouseEvent mouseEvent){
        try
        {
            Control.getInstance().deleteUser(userToRemove.getText());
            showError(AlertLabel,"User "+userToRemove.getText()+" has been deleted successfully!",Error.POSITIVE);
//            Account.rewriteFiles();
//            Customer.rewriteFiles();
//            Manager.rewriteFiles();
//            Seller.rewriteFiles();
        }
        catch (Exception e)
        {
            showError(AlertLabel,e.getMessage(),Error.NEGATIVE);
        }
    }

    public void createManage(MouseEvent mouseEvent) {
        goToNextPage(Page.CREATEMANAGER,mouseEvent);
    }

    public void selection(MouseEvent mouseEvent) {
        Account selectedItem = listUsers.getSelectionModel().getSelectedItem();
        userToRemove.setText(selectedItem.getUsername());
        userToView.setText(selectedItem.getUsername());
    }
}
