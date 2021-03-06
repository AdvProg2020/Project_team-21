package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Client.Model.Account.*;
import Client.ServerRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageUsers extends GraphicFather implements Initializable {

    public TableView<Account> listUsers;
    public TableColumn<Account,String> userName = new TableColumn<>("Username");
    public TableColumn<Account,String> type = new TableColumn<>("Type");
    public TableColumn<Account, Circle> status = new TableColumn<>("Status");
    public TextField userToView;
    public TextField userToRemove;
    public Label AlertLabel;
    private ArrayList<Account> allAccounts = new ArrayList<>();

    ObservableList<Account> getAccounts(){
        ObservableList<Account> result =  FXCollections.observableArrayList();
        result.addAll(allAccounts);
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLACCOUNTS);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(!response.equalsIgnoreCase("NONE")){
            String[] parsed = response.split(" - ");
            for (String s : parsed){
                String username = s.split("&")[0];
                String type = s.split("&")[1];
                String status = s.split("&")[2];
                Account account = null;
                if(type.equalsIgnoreCase("Customer"))
                    account = new Customer(username);
                else if(type.equalsIgnoreCase("Seller"))
                    account = new Seller(username);
                else if(type.equalsIgnoreCase("Manager"))
                    account = new Manager(username);
                else if(type.equalsIgnoreCase("Support"))
                   account = new Supporter(username);
                allAccounts.add(account);
                Circle statusCircle = new Circle(10.0);
                if(status.equalsIgnoreCase("online")){
                    statusCircle.setFill(Color.GREEN);
                }else{
                    statusCircle.setFill(Color.RED);
                }
                statusCircle.setStroke(Color.BLACK);
                statusCircle.setStrokeWidth(1.0);
                account.setStatus(statusCircle);
            }
        }
        listUsers.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        userName.setCellValueFactory(new PropertyValueFactory<>("username"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        listUsers.setItems(getAccounts());
        listUsers.getColumns().add(userName);
        listUsers.getColumns().add(type);
        listUsers.getColumns().add(status);
    }

    public void viewUser(MouseEvent mouseEvent){
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETCHECKUSEREXISTS,userToView.getText());
        String response = ClientCenter.getInstance().readMessageFromServer();

        if(response.startsWith(ServerRequest.TRUE.toString())){
            ClientCenter.getInstance().setUserToView(userToView.getText());
            goToNextPage(Page.VIEWUSER,mouseEvent);
        }else{
            showError(AlertLabel,"This username doesn't Exist",Error.NEGATIVE);
        }
    }

    public void removeUser(MouseEvent mouseEvent){
        ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTREMOVEUSER,userToRemove.getText());
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(response.startsWith(ServerRequest.DONE.toString()))
            showError(AlertLabel,"User "+userToRemove.getText()+" has been deleted successfully!",Error.POSITIVE);
        else
            showError(AlertLabel,ClientCenter.getInstance().getMessageFromError(response),Error.NEGATIVE);
    }

    public void createManage(MouseEvent mouseEvent) {
        goToNextPage(Page.CREATEMANAGER,mouseEvent);
    }

    public void selection(MouseEvent mouseEvent) {
        Account selectedItem = listUsers.getSelectionModel().getSelectedItem();
        userToRemove.setText(selectedItem.getUsername());
        userToView.setText(selectedItem.getUsername());
    }

    public void createSupport(MouseEvent mouseEvent) {
        goToNextPage(Page.CREATESUPPORT,mouseEvent);
    }
}
