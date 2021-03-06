package Client.GUIControllers.CustomerAccount;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Client.ServerRequest;
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
    public ImageView viewPassImage;
    public Label walletBalance;
    public Label accountBalance;
    String username;
    String password;
    String name;
    String phoneNumber;
    String address;
    String email;
    String walletMoney;
    String accountMoney;
    private boolean showPass;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPass = false;
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETCUSTOMERINFOS);
//            String input = ClientCenter.getInstance().getDataInputStream().readUTF();
        String input = ClientCenter.getInstance().readMessageFromServer();
        String[] parsedInput = input.split(" - ");
        username = parsedInput[0];
        name = parsedInput[1] + " " + parsedInput[2];
        address = parsedInput[3];
        phoneNumber = parsedInput[4];
        email = parsedInput[5];
        password = parsedInput[6];
        walletMoney = parsedInput[7];
        accountMoney = parsedInput[8];

        showImageUser(photoCircle);
        Username.setText(username);
        Name.setText(name);
        Address.setText(address);
        Phone.setText(phoneNumber);
        Email.setText(email);
        Password.setText("*****");
        walletBalance.setText(walletMoney);
        accountBalance.setText(accountMoney);
    }

    public void goToCart(MouseEvent mouseEvent) {
        goToNextPage(Page.SHOPPINGCART,mouseEvent);
    }

    public void goToOrders(MouseEvent mouseEvent) {
        goToNextPage(Page.BUYLOGSPAGE,mouseEvent);
    }

    public void goToDiscountCodes(MouseEvent mouseEvent) {
        goToNextPage(Page.CUSTOMERDISCOUNTCODES,mouseEvent);
    }

    public void editField(MouseEvent mouseEvent) {
        goToNextPage(Page.EDITFIELDSMANAGER,mouseEvent);
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
        goToNextPage(Page.CUSTOMERFILES,mouseEvent);
    }

    public void goToWallet(MouseEvent mouseEvent) {
        goToNextPage(Page.WALLETPAGE, mouseEvent);
    }
}
