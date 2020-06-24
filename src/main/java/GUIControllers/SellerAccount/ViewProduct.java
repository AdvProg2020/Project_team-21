package GUIControllers.SellerAccount;

import Controller.ControlSeller;
import GUIControllers.GraphicFather;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewProduct extends GraphicFather implements Initializable {
    public Label name;
    public Label category;
    public Label id;
    public TableView listSellers;
    public Label company;
    public Label price;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String productID = ControlSeller.getInstance().getProductToView();
//        ControlSeller.getInstance().get
        
    }
}
