package GUIControllers;//package Products;
//
//import Main.Main;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
//
//import java.io.IOException;
//
//public class ProductsPage {
//
//    public void goBack(ActionEvent actionEvent) throws IOException {
//        sample.ScreenController screenController = new sample.ScreenController(Main.getScene());
//        screenController.addScreen("MainPage", FXMLLoader.load(getClass().getResource( "/Main/MainPage.fxml" )));
//        screenController.activate("MainPage");
//    }
//
//    public void viewProductButton(ActionEvent actionEvent) throws IOException {
//        sample.ScreenController screenController = new sample.ScreenController(Main.getScene());
//        screenController.addScreen("ProductPage", FXMLLoader.load(getClass().getResource( "/Product/ProductPage.fxml" )));
//        screenController.activate("ProductPage");
//    }
//}

import Controller.Control;
import GUIControllers.GraphicFather;
import Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ProductsPage extends GraphicFather implements Initializable {


    public GridPane productsGridPane;
    public Circle profilePhoto;
    public Label profileName;
    public Button userPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShowRest(profilePhoto,profileName,userPage);
        int i = 0;
        int j = 0;
        for (Product product : Product.getAllProducts().values()) {
            if(i==4){
                i=0;
                j++;
            }
            ProductsCard card = new ProductsCard(product);
            productsGridPane.add(card, i, j);
            i++;
        }
    }

    private static class ProductsCard extends VBox {

        private ProductsCard(Product product){

            File file = new File(product.getImagePath());
            Image cardImage = new Image(file.toURI().toString());
            ImageView cardImageView = new ImageView(cardImage);

            cardImageView.setFitHeight(140);
            cardImageView.setFitWidth(140);
            cardImageView.setImage(cardImage);
            this.getChildren().add(cardImageView);

            Label cardTitle = new Label();
            cardTitle.setText(product.getName());
            this.getChildren().add(cardTitle);
            cardTitle.getStyleClass().add("mainPageProductCardsTitle");

            Label cardDescription = new Label();
            cardDescription.setText((product.getName() + "\n" + "Company: " + product.getCompany().getName() + "\n" + "Category: " + product.getCategory().getName() + "\n" + "Price: " + product.getPrice() + "\n" + "Score" + product.getBuyersAverageScore()));
            this.getChildren().add(cardDescription);
            cardDescription.getStyleClass().add("mainPageProductCardsDetail");
            cardDescription.setWrapText(true);


            Button cardButton = new Button();
            cardButton.setText("View Product");
            cardButton.getStyleClass().add("viewProductBtn");

            HBox buttonBox = new HBox();
            buttonBox.getChildren().add(cardButton);
            buttonBox.getStyleClass().add("viewProductBtnBox");
            this.getChildren().add(buttonBox);

            // action event
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        viewProductButton(e, product);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            cardButton.setOnAction(event);


            this.getStyleClass().add("mainPageProductCards");


//            this.setMinHeight(350);
//            this.setPrefHeight(350);
        }

        public void viewProductButton(ActionEvent actionEvent, Product product) throws IOException {
            ProductPage.setProduct(product);
            new GraphicFather().goToNextPage(Page.PRODUCTPAGE,actionEvent);
        }
    }


}
