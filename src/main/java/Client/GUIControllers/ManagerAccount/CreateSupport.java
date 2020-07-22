package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class CreateSupport extends GraphicFather {
    public Label alertLabel;
    public TextField phoneNumber;
    public TextField email;
    public TextField lastName;
    public TextField firstName;
    public TextField confirmPassword;
    public TextField password;
    public TextField username;
    public ImageView profilePhoto;
    private File imageFile = null;

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

    public void submit(MouseEvent mouseEvent) {
        if(imageFile == null){
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTCREATESUPPORT,username.getText() + "//"
                    +password.getText() + "//" + firstName.getText() + "//" + lastName.getText() + "//" + email.getText()+"//"+phoneNumber.getText()+"//"+
                    confirmPassword.getText() +"//"+"NULL");
        }
        else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTCREATESUPPORT,username.getText() + "//"
                    +password.getText() + "//" + firstName.getText() + "//" + lastName.getText() + "//" + email.getText()+"//"+phoneNumber.getText()+"//"+
                    confirmPassword.getText() + "//" + getFileExt(imageFile));
            sendImage(imageFile);
        }
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString()))
            showError(alertLabel,"Support " + username.getText() + " has been created successfully.",Error.POSITIVE);
        else
            showError(alertLabel, ClientCenter.getInstance().getMessageFromError(message), Error.NEGATIVE);
    }

    public void uploadPhotoButton(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("*.png","*.jpeg","*.jpg","(IMAGE files) *.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File sourceFile = fileChooser.showOpenDialog((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
        if (sourceFile != null) {
            Image profileImg = new Image(sourceFile.toURI().toString());
            profilePhoto.setImage(profileImg);
            imageFile = sourceFile;
        }
    }
}
