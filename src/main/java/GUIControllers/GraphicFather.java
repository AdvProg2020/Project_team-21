package GUIControllers;

import Controller.Control;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Manager;
import Model.Account.Seller;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;

public class GraphicFather {

//    public void goBack(Event event){
//        ArrayList<Scene> seenPages = GUICenter.getInstance().getSeenPages();
//        if(seenPages.size()>1)
//        {
//            Scene back = GUICenter.getInstance().getCurrentMenu();
//            GUICenter.getInstance().setCurrentMenu(seenPages.get(seenPages.size()-2));
//            GUICenter.getInstance().removeFromScenePage(seenPages.size()-1);
//            goReverse(event,back);
//        }
//    }
//
//    private void goReverse(Event event,Scene back){
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.setScene(GUICenter.getInstance().getCurrentMenu());
//        stage.setResizable(true);
//        stage.setWidth(back.getWidth());
//        stage.setHeight(back.getHeight());
//        stage.hide();
//        stage.show();
//    }
//
//    public void goToNextPage(Page page, Event event)
//    {
//        Scene menu = null;
//        try {
//            menu = GUICenter.getInstance().getSceneFromPage(page);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        GUICenter.getInstance().addSeenPage(menu);
//        GUICenter.getInstance().setCurrentMenu(menu);
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.setScene(menu);
//        stage.hide();
//        stage.show();
//    }

    public void goBack(Event event) throws IOException {
        ArrayList<Page> seenPages = GUICenter.getInstance().getSeenPages();
        if(seenPages.size()>1)
        {
            Scene back = GUICenter.getInstance().getCurrentScene();
            GUICenter.getInstance().setCurrentMenu(seenPages.get(seenPages.size()-2));
            GUICenter.getInstance().setCurrentScene(GUICenter.getInstance().getSceneFromPage(GUICenter.getInstance().getCurrentMenu()));
            GUICenter.getInstance().removeFromScenePage(seenPages.size()-1);
            goReverse(event,back);
        }
    }

    private void goReverse(Event event,Scene back) throws IOException {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(GUICenter.getInstance().getSceneFromPage(GUICenter.getInstance().getCurrentMenu()));
        stage.setResizable(true);
        stage.setWidth(back.getWidth());
        stage.setHeight(back.getHeight());
        stage.hide();
        stage.show();
    }

    public void goToNextPage(Page page, Event event)
    {
        Scene menu = null;
        try {
            menu = GUICenter.getInstance().getSceneFromPage(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GUICenter.getInstance().addSeenPage(page);
        GUICenter.getInstance().setCurrentMenu(page);
        GUICenter.getInstance().setCurrentScene(menu);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(menu);
        stage.hide();
        stage.show();
    }

    public void showError(Label alertLabel,String error,Error type)
    {
        if(type.equals(Error.NEGATIVE))
            alertLabel.setTextFill(RED);
        else
            alertLabel.setTextFill(GREEN);
        alertLabel.setText(error);
    }
    public void goToAccount(Event event){
        if(Control.getInstance().getUser() != null){
            Account user = Control.getInstance().getUser();
            if(user instanceof Customer){
                goToNextPage(Page.CUSTOMER,event);
            }
            else if(user instanceof Manager){
                goToNextPage(Page.MANAGER,event);
            }
            else if(user instanceof Seller){
                goToNextPage(Page.SELLER,event);
            }
        }
    }
    public void goToMain(MouseEvent mouseEvent) {
        goToNextPage(Page.MAIN,mouseEvent);
    }
    public void signOut(Event event){
        Control.getInstance().logout();
        goToNextPage(GUICenter.getInstance().getLanding(),event);
    }

    public void showImageUser(Circle profilePhoto){
        File file = new File(Control.getInstance().getUser().getImagePath());
        Image image = new Image(file.toURI().toString());
        ImagePattern img = new ImagePattern(image);
        profilePhoto.setFill(img);
    }
    private boolean signedIn(){
        boolean result = false;
        if(Control.getInstance().getUser() != null)
            result = true;
        return result;
    }

    public void topBarShow(Button signinButton, Button signupButton, Circle profilePhoto,Label accountName){
        profilePhoto.setFill(new ImagePattern(new Image("/images/account_icon.png")));
        if(signedIn())
        {
            Account user = Control.getInstance().getUser();
            signinButton.setText("");
            signinButton.setDisable(true);
            signupButton.setText("View Your Account");
            accountName.setText(user.getFirstName());
            showImageUser(profilePhoto);
        }
    }
    public void gotoSignUpPage(ActionEvent actionEvent){
        if(!signedIn())
        {
            goToNextPage(Page.SIGNUP,actionEvent);
            GUICenter.getInstance().setLanding(Page.MAIN);
        }
        else{
            goToAccount(actionEvent);
        }
    }

    public void gotoSignInPage(ActionEvent actionEvent){
        goToNextPage(Page.SIGNIN,actionEvent);
        GUICenter.getInstance().setLanding(Page.MAIN);
    }
}
