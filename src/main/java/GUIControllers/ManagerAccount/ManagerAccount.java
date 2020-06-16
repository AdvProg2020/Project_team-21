package GUIControllers.ManagerAccount;

import Controller.Control;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import Model.Account.Account;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagerAccount extends GraphicFather implements Initializable {


    public Circle photoCircle;
    public Label Username;
    public Label Name;
    public Label Address;
    public Label Phone;
    public Label Email;
    public Label Password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showImageUser(photoCircle);
        Account user = Control.getInstance().getUser();
        Username.setText(user.getUsername());
        Name.setText(user.getFirstName() + " " + user.getLastName());
        Address.setText(user.getAddress());
        Phone.setText(user.getPhoneNumber());
        Email.setText(user.getEmail());
        Password.setText(user.getPassword());
    }

    public void manageUsers(MouseEvent mouseEvent) {
        goToNextPage(Page.MANAGEUSERS,mouseEvent);
    }

    public void manageRequests(MouseEvent mouseEvent) {
        goToNextPage(Page.MANAGEREQUESTS,mouseEvent);
    }

    public void manageProducts(MouseEvent mouseEvent) {
        goToNextPage(Page.MANAGEPRODUCTS,mouseEvent);
    }

    public void manageDiscountCodes(MouseEvent mouseEvent) {
        goToNextPage(Page.MANAGEDISCOUNTCODES,mouseEvent);
    }

    public void manageCategories(MouseEvent mouseEvent) {
        goToNextPage(Page.MANAGECATEGORIES,mouseEvent);
    }

    public void editFields(MouseEvent mouseEvent) {
        goToNextPage(Page.EDITFIELDSMANAGER,mouseEvent);
    }
}
