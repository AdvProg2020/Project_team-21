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
import Model.Category;
import Model.Product;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.controlsfx.control.Rating;
import javafx.beans.value.ChangeListener;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class ProductsPage extends GraphicFather implements Initializable {


    public GridPane productsGridPane;
    public Circle profilePhoto;
    public Label profileName;
    public Button userPage;
    public ComboBox sortsDropDown;
    public ComboBox filtersDropDown;
    public GridPane filtersGridPane;
    public Slider slider;
    public Label sliderLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sliderLabel.setText("10000");
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

        ArrayList<String> sortTypes = new ArrayList<>();
        sortTypes.add("By Name");
        sortTypes.add("By Price");
        sortTypes.add("By Score");
        sortsDropDown.setItems(FXCollections.observableArrayList(sortTypes));
        ArrayList<String> filterTypes = new ArrayList<>();
        filterTypes.add("By Category");
        filterTypes.add("By Price");
        filterTypes.add("By CompanyName");
//        filtersDropDown.setItems(FXCollections.observableArrayList(filterTypes));
//        filtersDropDown.getSelectionModel().selectFirst();

        int k = 0;
        for (Category category : Category.getAllCategories()) {
            FilterCards filterCard = new FilterCards(category, productsGridPane, filtersGridPane, sortsDropDown, filtersDropDown);
            filtersGridPane.add(filterCard, 1, k);
            k++;
        }

        slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                sliderLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
                ArrayList<Product> prodArrayList = new ArrayList<>();
                for (Product prod : Product.getAllProducts().values()) {
                    if(prod.getPrice()<=newValue.intValue()){
                        prodArrayList.add(prod);
                    }
                }
                update(prodArrayList);

            }
        });
    }


    private String getSortType() throws Exception {
        return String.valueOf(sortsDropDown.getSelectionModel().getSelectedItem());
    }

    public void sort(ActionEvent actionEvent) throws Exception {
        String sortType = getSortType();
        ArrayList<Product> productArrayList = new ArrayList<>();
        if(sortType.equalsIgnoreCase("By Name")){
            productArrayList.addAll(ProductSort.sortProductHashMap(Product.getAllProducts()).values());
        } else if(sortType.equalsIgnoreCase("By Price")){
            Product.sortType = 1;
            ArrayList<Product> products = new ArrayList<>(Product.getAllProducts().values());
            productArrayList.addAll(ProductSort.sortProductByPrice(products));
        } else if(sortType.equalsIgnoreCase("By Score")){
            Product.sortType = 2;
            ArrayList<Product> products = new ArrayList<>(Product.getAllProducts().values());
            productArrayList.addAll(ProductSort.sortProductByBuyersAverageScore(products));
        }

        for (Product product : productArrayList) {
            System.out.println("pro: " + product.getBuyersAverageScore());
        }
        update(productArrayList);
    }

//    private void update(ArrayList<Product> products){
//
//        productsGridPane.getChildren().removeAll(productsGridPane.getChildren());
//        int i = 0;
//        int j = 0;
//        for (Product product : products) {
//            if(i==4){
//                i=0;
//                j++;
//            }
//            ProductsCard card = new ProductsCard(product);
//            productsGridPane.add(card, i, j);
//            i++;
//        }
//
//
//        ArrayList<String> sortTypes = new ArrayList<>();
//        sortTypes.add("By Name");
//        sortTypes.add("By Price");
//        sortTypes.add("By Score");
//        sortsDropDown.setItems(FXCollections.observableArrayList(sortTypes));
//    }
    private void update(ArrayList<Product> products){

        productsGridPane.getChildren().removeAll(productsGridPane.getChildren());
    //        filtersGridPane.getChildren().removeAll(filtersGridPane.getChildren());

        int i = 0;
        int j = 0;
        for (Product product : products) {
            if(i==4){
                i=0;
                j++;
            }
            ProductsCard card = new ProductsCard(product);
            productsGridPane.add(card, i, j);
            i++;
        }


        ArrayList<String> sortTypes = new ArrayList<>();
        sortTypes.add("By Name");
        sortTypes.add("By Price");
        sortTypes.add("By Score");
        sortsDropDown.setItems(FXCollections.observableArrayList(sortTypes));


        ArrayList<String> filterTypes = new ArrayList<>();
        filterTypes.add("By Category");
        filterTypes.add("By Price");
        filterTypes.add("By CompanyName");
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
            cardDescription.setText((product.getName() + "\n" + "Company: " + product.getCompany().getName() + "\n" + "Category: " + product.getCategory().getName() + "\n" + "Original Price: " + product.getOrgPrice()));
            this.getChildren().add(cardDescription);
            cardDescription.getStyleClass().add("mainPageProductCardsDetail");
            cardDescription.setWrapText(true);

            Rating rating = new Rating();
            rating.setRating(product.getBuyersAverageScore());
            rating.setDisable(true);
            rating.prefWidth(10);
            this.getChildren().add(rating);

            Separator separator = new Separator();
            this.getChildren().add(separator);

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

    private static class FilterCards extends HBox{

        private CheckBox checkBox;
        GridPane productsGridPane;
        GridPane filtersGridPane;
        ComboBox sortsDropDown;
        ComboBox filtersDropDown;

        private static HashMap<CheckBox, Category> categoriesArrayList = new HashMap<>();

        private FilterCards(Category category, GridPane productsGridPane, GridPane filtersGridPane, ComboBox sortsDropDown, ComboBox filtersDropDown){

            this.productsGridPane = productsGridPane;
            this.filtersGridPane = filtersGridPane;
            this.sortsDropDown = sortsDropDown;
            this.filtersDropDown = filtersDropDown;

            VBox vBox = new VBox();
            vBox.getStyleClass().add("filterLabelsBox");
            Label label = new Label();
            label.setText(category.getName());

            checkBox = new CheckBox();
            categoriesArrayList.put(checkBox, category);
            checkBox.setSelected(true);

            vBox.getChildren().add(label);
            this.getChildren().add(vBox);
            this.getChildren().add(checkBox);

            this.getStyleClass().add("filterCardsBox");

            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    ArrayList<Product> products = new ArrayList<>();
                    for (CheckBox box : categoriesArrayList.keySet()) {
                        if(box.isSelected()){
                            for (Product product : categoriesArrayList.get(box).getProductsList()) {
                                if(!isThereProductAtArrayList(product, products)){
                                    products.add(product);
                                }
                            }
                        }
                    }
                    update(products);
                }
            });
        }

        private static boolean isThereProductAtArrayList(Product product, ArrayList<Product> productArrayList){
            for (Product productArr : productArrayList) {
                if(product.getProductId().equalsIgnoreCase(productArr.getProductId())){
                    return true;
                }
            }
            return false;
        }

        private void update(ArrayList<Product> products){

            productsGridPane.getChildren().removeAll(productsGridPane.getChildren());
//            filtersGridPane.getChildren().removeAll(filtersGridPane.getChildren());
            int i = 0;
            int j = 0;
            for (Product product : products) {
                if (i == 4) {
                    i = 0;
                    j++;
                }
                ProductsCard card = new ProductsCard(product);
                productsGridPane.add(card, i, j);
                i++;
            }

            ArrayList<String> sortTypes = new ArrayList<>();
            sortTypes.add("By Name");
            sortTypes.add("By Price");
            sortTypes.add("By Score");
            sortsDropDown.setItems(FXCollections.observableArrayList(sortTypes));


            ArrayList<String> filterTypes = new ArrayList<>();
            filterTypes.add("By Category");
            filterTypes.add("By Price");
            filterTypes.add("By CompanyName");
//        filtersDropDown.setItems(FXCollections.observableArrayList(filterTypes));
//        filtersDropDown.getSelectionModel().selectFirst();

//            int k = 0;
//            for (Category category : Category.getAllCategories()) {
//                FilterCards filterCard = new FilterCards(category, productsGridPane, filtersGridPane, sortsDropDown, filtersDropDown);
//                filtersGridPane.add(filterCard, 1, k);
//                k++;
//            }

        }
    }


}
