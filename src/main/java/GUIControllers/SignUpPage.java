package GUIControllers;

import Controller.Control;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SignUpPage extends GraphicFather {


    public ImageView profilePhoto;
    File imageFile;

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
    private void putImage(File sourceFile){
        File copyToTemp = new File("src/main/resources/images/temp");
        File finalCopy = new File("src/main/resources/images/profilePhotos/" + Control.getInstance().randomString(5) +"."+ getFileExt(sourceFile));
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
        putImage(imageFile);
    }

//    public void goBack(ActionEvent actionEvent) throws IOException {
//        sample.ScreenController screenController = new sample.ScreenController(Main.getScene());
//        screenController.addScreen("MainPage", FXMLLoader.load(getClass().getResource( "/Main/MainPage.fxml" )));
//        screenController.activate("MainPage");
//    }
}
