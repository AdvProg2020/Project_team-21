package GUIControllers.ManagerAccount;

import GUIControllers.GraphicFather;
import Model.DiscountCode;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageDiscountCodes extends GraphicFather implements Initializable {
    public Label AlertLabel;
    public TableView<DiscountCode> listDiscountCodes;

    public TableColumn<DiscountCode,String> id = new TableColumn<>("DiscountID");
    ObservableList<DiscountCode> getDiscountCode(){
        ObservableList<DiscountCode> result =  FXCollections.observableArrayList();
        for (String s : DiscountCode.getAllDiscountCodes().keySet()) {
            result.add(DiscountCode.getAllDiscountCodes().get(s));
        }
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listDiscountCodes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        id.setCellValueFactory(new PropertyValueFactory<>("discountId"));
        listDiscountCodes.setItems(getDiscountCode());
        listDiscountCodes.getColumns().add(id);
    }

    public void viewDiscountCode(MouseEvent mouseEvent) {
    }

    public void RemoveDiscountCode(MouseEvent mouseEvent) {
    }

    public void createDiscountCode(MouseEvent mouseEvent) {
    }

    public void EditDiscountCode(MouseEvent mouseEvent) {
    }
}
