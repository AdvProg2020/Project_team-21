package Client.GUIControllers.SellerAccount;

import Server.Controller.ControlSeller;
import Client.GUIControllers.GraphicFather;
import Server.Model.Off;
import Server.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewOFF extends GraphicFather implements Initializable {
    public Label percentage;
    public Label endTime;
    public Label startTime;
    public TableView<Product> listProducts;
    public TableColumn<Product,String> name = new TableColumn<>("Name");
    public TableColumn<Product,String> id = new TableColumn<>("ID");
    private String offID = ControlSeller.getInstance().getOffToView();
    private Off off = Off.getAllOffs().get(offID);

    ObservableList<Product> getProducts(){
        ObservableList<Product> result =  FXCollections.observableArrayList();
        result.addAll(off.getProductsList());
        return result;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        percentage.setText(Double.toString(off.getOffAmount()));
        endTime.setText(off.getEndTime().toString());
        startTime.setText(off.getStartTime().toString());

        listProducts.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        id.setCellValueFactory(new PropertyValueFactory<>("productId"));
        listProducts.setItems(getProducts());
        listProducts.getColumns().add(name);
        listProducts.getColumns().add(id);
    }
}
