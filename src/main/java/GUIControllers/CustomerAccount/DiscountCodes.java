package GUIControllers.CustomerAccount;

import Controller.Control;
import GUIControllers.GraphicFather;
import Model.Account.Customer;
import Model.Category;
import Model.DiscountCode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class DiscountCodes extends GraphicFather implements Initializable {

    public TableView<DiscountCode> listDiscountCodes;
    public TableColumn<DiscountCode,String> id = new TableColumn<>("DiscountID");
    public TableColumn<DiscountCode,Double> percentage = new TableColumn<>("Percentage");
    public TableColumn<DiscountCode,Integer> amounts = new TableColumn<>("Allowed Usage");
    public TableColumn<DiscountCode,Double> maxAmount = new TableColumn<>("Max Disount");


    ObservableList<DiscountCode> getDiscountCode(){
        ObservableList<DiscountCode> result =  FXCollections.observableArrayList();
        Customer user = (Customer) Control.getInstance().getUser();
        for (String s : user.getDiscountCodes().keySet()) {
            result.add(user.getDiscountCodes().get(s));
        }
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listDiscountCodes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        percentage.setCellValueFactory(new PropertyValueFactory<>("discountPercentage"));
        amounts.setCellValueFactory(new PropertyValueFactory<>("discountNumberForEachUser"));
        maxAmount.setCellValueFactory(new PropertyValueFactory<>("maxDiscountAmount"));
        listDiscountCodes.setItems(getDiscountCode());
        listDiscountCodes.getColumns().add(id);
        listDiscountCodes.getColumns().add(percentage);
        listDiscountCodes.getColumns().add(amounts);
        listDiscountCodes.getColumns().add(maxAmount);
    }
}
