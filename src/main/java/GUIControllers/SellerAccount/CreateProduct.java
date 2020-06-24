package GUIControllers.SellerAccount;

import Controller.Control;
import Controller.ControlSeller;
import GUIControllers.Error;
import GUIControllers.GraphicFather;
import GUIControllers.Page;
import javafx.event.ActionEvent;
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
        String productID = Control.getInstance().randomString(10);
        try{
            String imagePath = "productPhotos/product.png" ;
            if(imageFile !=null)
                imagePath = "productPhotos/" + productID +"."+ getFileExt(imageFile);
            String reqID = ControlSeller.getInstance().sendAddProductReq(name.getText(),companyName.getText(),category.getText(),price.getText(),companyLocation.getText(),productID,imagePath);
            showError(alertLabel,"Your request with id " + reqID + " has been sent!",Error.POSITIVE);
            if(imageFile != null)
                putImage(imageFile, productID);
        }catch (Exception e){
            showError(alertLabel, e.getMessage(), Error.NEGATIVE);
        }
    }

    private void putImage(File sourceFile,String id){
        File copyToTemp = new File("temp");
        File finalCopy = new File("productPhotos/" + id +"."+ getFileExt(sourceFile));
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
