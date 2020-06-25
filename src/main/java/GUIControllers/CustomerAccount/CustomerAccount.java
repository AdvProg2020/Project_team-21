package GUIControllers.CustomerAccount;

import Controller.Control;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import Model.Account.Customer;
import Model.Account.Seller;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAccount extends GraphicFather implements Initializable {
    public Label Username;
    public Label Name;
    public Label Address;
    public Label Phone;
    public Label Email;
    public Label Password;
    public Circle photoCircle;
    public Label balance;
    public ImageView viewPassImage;
    private boolean showPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPass = false;
        Customer customer = (Customer) Control.getInstance().getUser();
        showImageUser(photoCircle);
        Username.setText(customer.getUsername());
        Name.setText(customer.getFirstName()+" "+ customer.getLastName());
        Address.setText(customer.getAddress());
        Phone.setText(customer.getPhoneNumber());
        Email.setText(customer.getEmail());
        Password.setText("*****");
        balance.setText(Double.toString(customer.getBalance()));
    }

    public void goToCart(MouseEvent mouseEvent) {
        goToNextPage(Page.SHOPPINGCART,mouseEvent);
    }

    public void goToOrders(MouseEvent mouseEvent) {
    }

    public void goToDiscountCodes(MouseEvent mouseEvent) {
        goToNextPage(Page.CUSTOMERDISCOUNTCODES,mouseEvent);
    }

    public void editField(MouseEvent mouseEvent) {
        goToNextPage(Page.EDITFIELDSMANAGER,mouseEvent);
    }

    public void viewPass(MouseEvent mouseEvent) {
        Customer seller = (Customer) Control.getInstance().getUser();
        if(!showPass){
            Password.setText(seller.getPassword());
            viewPassImage.setImage(new Image(getClass().getResource("/Images/unview.png").toString()));
        }else{
            Password.setText("*****");
            viewPassImage.setImage(new Image(getClass().getResource("/Images/view.png").toString()));
        }
        showPass = !showPass;
    }
}
