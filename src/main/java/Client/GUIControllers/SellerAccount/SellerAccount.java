package Client.GUIControllers.SellerAccount;

import Server.Controller.Control;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Server.Model.Account.Seller;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public Button viewPassButton;
    public ImageView viewPassImage;
    private boolean showPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPass = false;
        Seller seller = (Seller) Control.getInstance().getUser();
        Username.setText(seller.getUsername());
        Name.setText(seller.getFirstName() + " " + seller.getLastName());
        Address.setText(seller.getAddress());
        Phone.setText(seller.getPhoneNumber());
        Email.setText(seller.getEmail());
        Password.setText("*****");
        if(seller.getCompany() != null){
            companyAddress.setText(seller.getCompany().getLocation());
            companyName.setText(seller.getCompany().getName());
        }
        else{
            companyName.setText("----");
            companyAddress.setText("----");
        }
        balance.setText(Double.toString(seller.getCredit()));
        showImageUser(photoCircle);
    }

    public void goToProducts(MouseEvent mouseEvent) {
        goToNextPage(Page.SELLERPRODUCTS,mouseEvent);
    }

    public void goToSalesHistory(MouseEvent mouseEvent) {
        goToNextPage(Page.SELLLOGSPAGE,mouseEvent);
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

    public void viewPass(MouseEvent mouseEvent) {
        Seller seller = (Seller) Control.getInstance().getUser();
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
