package GUIControllers.SellerAccount;

import GUIControllers.GraphicFather;
import Model.Product;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewOFF extends GraphicFather implements Initializable {
    public Label percentage;
    public Label endTime;
    public Label startTime;
    public TableView<Product> listProducts;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
