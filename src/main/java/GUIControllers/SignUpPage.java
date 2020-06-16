package GUIControllers;

import Controller.Control;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
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

public class SignUpPage extends GraphicFather {


    public ImageView profilePhoto;
    public TextField username;
    public TextField password;
    public TextField confirmPassword;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField phoneNumber;
    public ComboBox<String> type;
    public Label alertLabel;
    File imageFile = null;

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

    private void putImage(File sourceFile,String username){
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

    public void submit(MouseEvent mouseEvent) {
        boolean login = true;
        Page pageToGo = GUICenter.getInstance().getLanding();
        if(((String)type.getValue()).equalsIgnoreCase("seller")){
            login = false;
            pageToGo = Page.CREATECOMPANY;
        }
        try{
            String imagePath = "profilePhotos/account_icon.png" ;
            if(imageFile !=null)
                imagePath = "profilePhotos/" + username.getText() +"."+ getFileExt(imageFile);
            Control.getInstance().createAccount(type.getValue(),username.getText(),password.getText(),firstName.getText(),lastName.getText(),
                    email.getText(),phoneNumber.getText(),confirmPassword.getText(),null,login,imagePath);
            if(imageFile != null){
                putImage(imageFile, username.getText());
            }
            goToNextPage(pageToGo,mouseEvent);
        }catch (Exception e){
            showError(alertLabel, e.getMessage(), Error.NEGATIVE);
        }
    }


}
