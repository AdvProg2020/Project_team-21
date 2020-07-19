package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.Model.Account.Customer;
import Client.ServerRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ViewDiscountCode extends GraphicFather implements Initializable {
    public Label percentage;
    public Label startTime;
    public Label endTime;
    public Label MaxAmount;
    public Label maxForEach;
    public TableView<Customer> listOwners;
    String code = ClientCenter.getInstance().getDiscountCodeToView();
    private ArrayList<Customer> customerOwners = new ArrayList<>();

    public TableColumn<Customer,String> username = new TableColumn<>("Username");

    ObservableList<Customer> getOwners(){
        ObservableList<Customer> result =  FXCollections.observableArrayList();
        result.addAll(customerOwners);
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETDISCOUNTCODEINFOS,code);
        String response = ClientCenter.getInstance().readMessageFromServer();
        String[] parsedData = response.split(" - ");

        if(!parsedData[0].equalsIgnoreCase("NONE")){
            String[] owners = parsedData[0].split("&");
            for (String owner : owners) {
                customerOwners.add(new Customer(owner));
            }
        }

        listOwners.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        listOwners.setItems(getOwners());
        listOwners.getColumns().add(username);

        percentage.setText(parsedData[1] + " %");
        startTime.setText(parsedData[2]);
        endTime.setText(parsedData[3]);
        MaxAmount.setText(parsedData[4]+" ");
        maxForEach.setText(parsedData[5] + " ");

    }
}
