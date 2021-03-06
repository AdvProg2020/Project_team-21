package Client.GUIControllers;

import Client.ClientCenter;
import Client.ServerRequest;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;

public class MakeManagerFirst extends GraphicFather {
    public TextField username;
    public TextField password;
    public TextField confirmPassword;
    public TextField firstName;
    public TextField lastName;
    public TextField email;
    public TextField phoneNumber;
    public Label alertLabel;
    public ImageView profilePhoto;
    File imageFile = null;


    private void showPopupLogin(Event event){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        Text txt = new Text("Wellcome " + firstName.getText() + " \\\\(•◡•)//");
        txt.setFill(Paint.valueOf("069438"));
        Font font = new Font("Hiragino Sans W3" , 20);
        txt.setFont(font);
        dialogVbox.getChildren().add(txt);
        dialogVbox.setBackground(new Background(new BackgroundFill(Paint.valueOf("80A7EB"), CornerRadii.EMPTY, Insets.EMPTY)));
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 400, 200);
        dialogScene.setFill(Paint.valueOf("80A7EB"));
        dialog.setScene(dialogScene);
        dialog.show();
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

    public void submit(MouseEvent mouseEvent) {

        if(imageFile == null){
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTMAKEMANAGERFIRST,username.getText() + "//"
                    +password.getText() + "//" + firstName.getText() + "//" + lastName.getText() + "//" + email.getText()+"//"+phoneNumber.getText()+"//"+
                    confirmPassword.getText() +"//"+"NULL");
        }
        else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTMAKEMANAGERFIRST,username.getText() + "//"
                    +password.getText() + "//" + firstName.getText() + "//" + lastName.getText() + "//" + email.getText()+"//"+phoneNumber.getText()+"//"+
                    confirmPassword.getText() + "//" + getFileExt(imageFile));
            sendImage(imageFile);
        }
        String message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString())){
            ClientCenter.getInstance().setActiveToken(ClientCenter.getInstance().getMessageFromError(message));
            goToNextPage(Page.MAIN,mouseEvent);
            showPopupLogin(mouseEvent);
        }
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
