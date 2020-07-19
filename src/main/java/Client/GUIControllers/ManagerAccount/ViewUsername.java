package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewUsername extends GraphicFather implements Initializable {
    public Circle photoCircle;
    public Label phone;
    public Label email;
    public Label name;
    public Label address;
    public Label username;
    public Label password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETUSERINFOS,ClientCenter.getInstance().getUserToView());
        String response = ClientCenter.getInstance().readMessageFromServer();
        String[] parseData = response.split(" - ");
        Image image = null;
        try {
            image = ClientCenter.getInstance().recieveImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImagePattern img = new ImagePattern(image);
        photoCircle.setFill(img);
        phone.setText(parseData[0]);
        email.setText(parseData[1]);
        name.setText(parseData[2]);
        address.setText(parseData[3]);
        username.setText(parseData[4]);
        password.setText(parseData[5]);
    }
}
