package Client.GUIControllers;

import Client.ClientCenter;
import Client.ServerRequest;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import java.net.URL;
import java.util.ResourceBundle;

public class SupportAccount extends GraphicFather implements Initializable {

    public Circle photoCircle;
    public Label Username;
    public Label Name;
    public Label Address;
    public Label Phone;
    public Label Email;
    public Label Password;
    public ImageView viewPassImage;
    public Button viewPassButton;
    private boolean showPass;
    String password;
    String username;
    String name;
    String email;
    String address;
    String phoneNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Here");
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETMANAGERINFO);
        String input = ClientCenter.getInstance().readMessageFromServer();
        System.out.println("Now here " + input);
        String[] parsedInput = input.split(" - ");
        username = parsedInput[0];
        name = parsedInput[1] + " " + parsedInput[2];
        email = parsedInput[3];
        address = parsedInput[4];
        phoneNumber = parsedInput[5];
        password = parsedInput[6];

        showPass = false;
        showImageUser(photoCircle);
        Username.setText(username);
        Name.setText(name);
        Address.setText(address);
        Phone.setText(phoneNumber);
        Email.setText(email);
        Password.setText("*****");
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
}
