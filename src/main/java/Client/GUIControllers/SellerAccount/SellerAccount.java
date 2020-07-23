package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Client.ServerRequest;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;

import java.io.IOException;
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
    public Button viewPassButton;
    public ImageView viewPassImage;
    public Label walletBalance;
    public Label accountBalance;
    private boolean showPass;
    String username;
    String name;
    String password;
    String email;
    String address;
    String phoneNumber;
    String companyNameString;
    String companyAddressString;
    String walletMoney;
    String accountMoney;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPass = false;
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETSELLERINFO);
        String input = ClientCenter.getInstance().readMessageFromServer();
        String[] parsedInput = input.split(" - ");
        username = parsedInput[0];
        name = parsedInput[1] + " " + parsedInput[2];
        email = parsedInput[3];
        address = parsedInput[4];
        phoneNumber = parsedInput[5];
        password = parsedInput[6];
        companyNameString = parsedInput[7];
        companyAddressString = parsedInput[8];
        walletMoney = parsedInput[9];
        accountMoney = parsedInput[10];

        Username.setText(username);
        Name.setText(name);
        Address.setText(address);
        Phone.setText(phoneNumber);
        Email.setText(email);
        Password.setText("*****");
        companyAddress.setText(companyAddressString);
        companyName.setText(companyNameString);
        walletBalance.setText(walletMoney);
        accountBalance.setText(accountMoney);
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
        if(!showPass){
            Password.setText(password);
            viewPassImage.setImage(new Image(getClass().getResource("/Images/unview.png").toString()));
        }else{
            Password.setText("*****");
            viewPassImage.setImage(new Image(getClass().getResource("/Images/view.png").toString()));
        }
        showPass = !showPass;

    }

    public void goToFiles(MouseEvent mouseEvent) {
        goToNextPage(Page.SELLERFILES,mouseEvent);
    }

    public void goToAuctions(MouseEvent mouseEvent) {
        goToNextPage(Page.SELLERAUCTIONS,mouseEvent);
    }

    public void goToWallet(MouseEvent mouseEvent) {
        goToNextPage(Page.WALLETPAGE, mouseEvent);
    }
}
