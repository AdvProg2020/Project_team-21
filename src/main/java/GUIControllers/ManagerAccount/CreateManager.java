package GUIControllers.ManagerAccount;

import Controller.Control;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
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

public class CreateManager extends GraphicFather {
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

    private void putImage(File sourceFile, String username){
        File copyToTemp = new File("temp");
        File finalCopy = new File("profilePhotos/" + username +"."+ getFileExt(sourceFile));
        try {
            FileUtils.copyFileToDirectory(sourceFile,copyToTemp);
            File copied = new File(copyToTemp + "/" +sourceFile.getName());
            copied.renameTo(finalCopy);
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
        try{
            String imagePath = "profilePhotos/account_icon.png" ;
            if(imageFile !=null)
                imagePath = "profilePhotos/" + username.getText() +"."+ getFileExt(imageFile);
            Control.getInstance().createAccount("Manager",username.getText(),password.getText(),firstName.getText(),lastName.getText(),
                    email.getText(),phoneNumber.getText(),confirmPassword.getText(),null,false,imagePath);
            if(imageFile != null)
                putImage(imageFile, username.getText());
            showError(alertLabel,"Manager " + username.getText() + " has been created successfully.",Error.POSITIVE);
        }catch (Exception e){
            showError(alertLabel, e.getMessage(), Error.NEGATIVE);
        }
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
