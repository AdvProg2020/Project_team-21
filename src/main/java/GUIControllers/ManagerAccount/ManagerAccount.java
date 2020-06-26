package GUIControllers.ManagerAccount;

import Controller.Control;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import Model.Account.Account;
import Model.Account.Manager;
import Model.Account.Seller;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public ImageView viewPassImage;
    private boolean showPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPass = false;
        showImageUser(photoCircle);
        Account user = Control.getInstance().getUser();
        Username.setText(user.getUsername());
        Name.setText(user.getFirstName() + " " + user.getLastName());
        Address.setText(user.getAddress());
        Phone.setText(user.getPhoneNumber());
        Email.setText(user.getEmail());
        Password.setText("*****");
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

    public void viewPass(MouseEvent mouseEvent) {
        Manager seller = (Manager) Control.getInstance().getUser();
        if(!showPass){
            Password.setText(seller.getPassword());
            viewPassImage.setImage(new Image(getClass().getResource("/Images/unview.png").toString()));
        }else{
            Password.setText("*****");
            viewPassImage.setImage(new Image(getClass().getResource("/Images/view.png").toString()));
        }
        showPass = !showPass;
    }

    public void giveMoney(MouseEvent mouseEvent) {
        goToNextPage(Page.MONEYPAGE,mouseEvent);
    }
}
