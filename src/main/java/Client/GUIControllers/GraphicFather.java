package Client.GUIControllers;

//import Server.Controller.Control;
//import Server.Model.Account.Account;
//import Server.Model.Account.Customer;
//import Server.Model.Account.Manager;
//import Server.Model.Account.Seller;
import Client.ClientCenter;
import Client.ServerRequest;
//import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
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
        click();
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
        click();
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
    public void goToAccountCircle(Event event) throws IOException {
        if(ClientCenter.getInstance().isTokenActivate()){
            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETACCOUNTTYPE);
            String type = ClientCenter.getInstance().readMessageFromServer();
//            type = ClientCenter.getInstance().getDataInputStream().readUTF();
            if(type.equalsIgnoreCase("Customer")){
                goToNextPage(Page.CUSTOMER,event);
            }
            else if(type.equalsIgnoreCase("Manager")){
                goToNextPage(Page.MANAGER,event);
            }
            else if(type.equalsIgnoreCase("Seller")){
                goToNextPage(Page.SELLER,event);
            }
        }
        else
            gotoSignInPage(event);
    }
    public void goToAccountButton(Event event) throws IOException {
        if(ClientCenter.getInstance().isTokenActivate()){
            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETACCOUNTTYPE);
            String type = ClientCenter.getInstance().readMessageFromServer();
//            type = ClientCenter.getInstance().getDataInputStream().readUTF();
            if(type.equalsIgnoreCase("Customer")){
                goToNextPage(Page.CUSTOMER,event);
            }
            else if(type.equalsIgnoreCase("Manager")){
                goToNextPage(Page.MANAGER,event);
            }
            else if(type.equalsIgnoreCase("Seller")){
                goToNextPage(Page.SELLER,event);
            }
        }
        else
            gotoSignUpPage(event);
    }
    public void goToMain(MouseEvent mouseEvent) {
        goToNextPage(Page.MAIN,mouseEvent);
    }
    public void signOut(Event event){
        ClientCenter.getInstance().sendReqToServer(ServerRequest.SIGNOUT);
//        Control.getInstance().logout();
        ClientCenter.getInstance().expireToken();
        goToNextPage(Page.MAIN,event);
    }

    public void showImageUser(Circle profilePhoto){
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETPROFILEPHOTO);
        Image image = null;
        try {
            image = ClientCenter.getInstance().recieveImage();
        } catch (IOException e) {
        }
        ImagePattern img = new ImagePattern(image);
        profilePhoto.setFill(img);
    }
    private boolean signedIn(){
        boolean result = false;
        if(ClientCenter.getInstance().isTokenActivate())
            result = true;
        return result;
    }

    public void topBarShow(Button signinButton, Button signupButton, Circle profilePhoto,Label accountName,Button userPage){
        profilePhoto.setFill(new ImagePattern(new Image("/images/account_icon.png")));
        userPage.setText("Sign UP");
        if(signedIn())
        {
            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETNAME);
            String name = "";
//                name = ClientCenter.getInstance().getDataInputStream().readUTF();
            name = ClientCenter.getInstance().readMessageFromServer();
            signinButton.setText("");
            signinButton.setDisable(true);
            signupButton.setText("View Your Account");
            accountName.setText(name);
            showImageUser(profilePhoto);
            userPage.setText("User Page");
        }
    }
    public void topBarShowRest(Circle profilePhoto,Label accountName,Button userPage){

        profilePhoto.setFill(new ImagePattern(new Image("/images/account_icon.png")));
        userPage.setText("Sign UP");
        if(signedIn())
        {
            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETNAME);
            String name = "";
            name = ClientCenter.getInstance().readMessageFromServer();
            accountName.setText(name);
            showImageUser(profilePhoto);
            userPage.setText("User Page");
        }
    }

    public void gotoSignUpPage(Event actionEvent){
        if(!signedIn())
        {
            goToNextPage(Page.SIGNUP,actionEvent);
            GUICenter.getInstance().setLanding(Page.MAIN);
        }
        else{
            try {
                goToAccountCircle(actionEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void gotoSignInPage(Event actionEvent){
        goToNextPage(Page.SIGNIN,actionEvent);
        GUICenter.getInstance().setLanding(Page.MAIN);
    }
    public void click(){
//        MediaPlayer mediaPlayer;
//        Media h = new Media(getClass().getResource("/Musics/click.mp3").toString());
//        mediaPlayer = new MediaPlayer(h);
//        mediaPlayer.play();
//        mediaPlayer.setVolume(GUICenter.getInstance().getClickVolume());
    }
    public void gotoProductsPage(Event actionEvent) throws IOException {
        goToNextPage(Page.PRODUCTSPAGE,actionEvent);
    }
    public void goToOffsPage(MouseEvent mouseEvent) {
        goToNextPage(Page.OFFSPAGE,mouseEvent);
    }
    public void gotoAuctionsPage(ActionEvent actionEvent){
        goToNextPage(Page.AUCTIONPAGE,actionEvent);
    }
    public void goToFiles(ActionEvent actionEvent){
        goToNextPage(Page.FILESPAGE,actionEvent);
    }
    public void goToWalletPage(ActionEvent actionEvent) {
        goToNextPage(Page.WALLETPAGE, actionEvent);
    }


}
