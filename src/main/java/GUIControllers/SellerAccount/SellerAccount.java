package GUIControllers.SellerAccount;

import Controller.Control;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import Model.Account.Seller;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class SellerAccount extends GraphicFather implements Initializable {

    public Circle photoCircle;
    public Label companyAddress;
    public Label companyName;
    public Label Password;
    public Label Email;
    public Label Phone;
    public Label Address;
    public Label Name;
    public Label Username;
    public Label balance;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Seller seller = (Seller) Control.getInstance().getUser();
        Username.setText(seller.getUsername());
        Name.setText(seller.getFirstName() + " " + seller.getLastName());
        Address.setText(seller.getAddress());
        Phone.setText(seller.getPhoneNumber());
        Email.setText(seller.getEmail());
        Password.setText(seller.getPassword());
        companyAddress.setText(seller.getCompany().getLocation());
        companyName.setText(seller.getCompany().getName());
        balance.setText(Double.toString(seller.getCredit()));
        showImageUser(photoCircle);
    }

    public void goToProducts(MouseEvent mouseEvent) {
        goToNextPage(Page.SELLERPRODUCTS,mouseEvent);
    }

    public void goToSalesHistory(MouseEvent mouseEvent) {
        goToNextPage(Page.SALESHISTORY,mouseEvent);
    }

    public void goToOFFs(MouseEvent mouseEvent) {
        goToNextPage(Page.SELLEROFFS,mouseEvent);
    }

    public void goToCategories(MouseEvent mouseEvent) {
        goToNextPage(Page.SELLERCATEGORIIES,mouseEvent);
    }

    public void editField(MouseEvent mouseEvent) {
        goToNextPage(Page.EDITFIELDSELLER,mouseEvent);
    }
}
