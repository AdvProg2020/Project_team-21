package Client.GUIControllers.SellerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateProduct extends GraphicFather implements Initializable {


    public TextField name;
    public Label alertLabel;
    public Circle photo;
    public TextField category;
    public TextField companyName;
    public TextField companyLocation;
    public TextField price;
    File imageFile = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        photo.setFill(new ImagePattern(new Image("/images/product.png")));
    }

    public void done(MouseEvent mouseEvent) {
        if(imageFile !=null)
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTCREATEPRODUCTREQ,name.getText() + "//" + companyName.getText()+"//" +category.getText()+"//" +
                price.getText()+"//" +companyLocation.getText() +"//" + getFileExt(imageFile));
        else
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTCREATEPRODUCTREQ,name.getText() + "//" + companyName.getText()+"//" +category.getText()+"//" +
                    price.getText()+"//" +companyLocation.getText() +"//" + "NULL");

        if(imageFile != null)
            sendImage(imageFile);
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString())){
            showError(alertLabel,"Your request with id " + ClientCenter.getInstance().getMessageFromError(message) + " has been sent!",Error.POSITIVE);
        }else{
            showError(alertLabel, ClientCenter.getInstance().getMessageFromError(message), Error.NEGATIVE);
        }
    }

    private void sendImage(File sourceFile){
        File copyToTemp = new File("temp");
        try {
            FileUtils.copyFileToDirectory(sourceFile,copyToTemp);
            ClientCenter.getInstance().sendImage(copyToTemp + "/" +sourceFile.getName());
            File file = new File(copyToTemp + "/" +sourceFile.getName());
            file.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileExt(File file){
        String name = file.getName();
        String extReversed = "";
        String ext = "";
        for(int i = name.length()-1;name.charAt(i) != '.';i--){
            extReversed += name.charAt(i);
        }
        for(int i = extReversed.length()-1;i>=0;i--){
            ext += extReversed.charAt(i);
        }
        return ext;
    }

    public void uploadPhotoButton(MouseEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("*.png","*.jpeg","*.jpg","(IMAGE files) *.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File sourceFile = fileChooser.showOpenDialog((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
        if (sourceFile != null) {
            Image profileImg = new Image(sourceFile.toURI().toString());
            photo.setFill(new ImagePattern(profileImg));
            imageFile = sourceFile;
        }
    }
}
