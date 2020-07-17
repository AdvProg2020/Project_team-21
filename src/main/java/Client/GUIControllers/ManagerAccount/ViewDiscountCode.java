package Client.GUIControllers.ManagerAccount;

import Server.Controller.ControlManager;
import Client.GUIControllers.GraphicFather;
import Server.Model.Account.Customer;
import Server.Model.DiscountCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewDiscountCode extends GraphicFather implements Initializable {
    public Label percentage;
    public Label startTime;
    public Label endTime;
    public Label MaxAmount;
    public Label maxForEach;
    public TableView<Customer> listOwners;
    String code = ControlManager.getInstance().getDiscountCodeToView();
    DiscountCode discountCode =  DiscountCode.getAllDiscountCodes().get(code);

    public TableColumn<Customer,String> username = new TableColumn<>("Username");

    ObservableList<Customer> getOwners(){
        ObservableList<Customer> result =  FXCollections.observableArrayList();
        for (String s : discountCode.getDiscountOwners().keySet()) {
            result.add(discountCode.getDiscountOwners().get(s));
        }
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listOwners.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
        listOwners.setItems(getOwners());
        listOwners.getColumns().add(username);

        percentage.setText(discountCode.getDiscountPercentage() + " %");
        startTime.setText(discountCode.getStartTime().toString());
        endTime.setText(discountCode.getEndTime().toString());
        MaxAmount.setText(discountCode.getMaxDiscountAmount()+" ");
        maxForEach.setText(discountCode.getDiscountNumberForEachUser() + " ");

    }
}
