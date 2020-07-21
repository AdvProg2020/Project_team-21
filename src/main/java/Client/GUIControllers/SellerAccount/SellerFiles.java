package Client.GUIControllers.SellerAccount;//package Products;


import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Client.GUIControllers.ProductPage;
import Client.Model.Category;
import Client.Model.Product;
import Client.ServerRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;


public class SellerFiles extends GraphicFather implements Initializable {


    public GridPane productsGridPane;
    public Circle profilePhoto;
    public Label profileName;
    public Button userPage;
    public Label fileName;
    public TextField price;
    public Label alertLabel;
    private File selectedFile = null;
//    public ComboBox sortsDropDown;
//    public ComboBox filtersDropDown;
//    public GridPane filtersGridPane;
//    public Slider slider;
//    public Label sliderLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        sliderLabel.setText("10000");
        topBarShowRest(profilePhoto,profileName,userPage);

        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLSELLERFILES);
        String dataInput = ClientCenter.getInstance().readMessageFromServer();
        System.out.println(dataInput);
        if(!dataInput.equalsIgnoreCase("NONE")){
            String[] inputParsed = dataInput.split(" - ");
            int i = 0;
            int j = 0;
            for (String s : inputParsed) {
                if(i==4){
                    i=0;
                    j++;
                }
                String[] data = s.split("&");
                FileCard card = new FileCard(data[1],data[0],data[2],ClientCenter.getInstance().getImageExt(data[3]));
                productsGridPane.add(card, i, j);
                i++;
            }
        }

//        ArrayList<String> sortTypes = new ArrayList<>();
//        sortTypes.add("By Name");
//        sortTypes.add("By Price");
//        sortTypes.add("By Score");
//        sortsDropDown.setItems(FXCollections.observableArrayList(sortTypes));
//        ArrayList<String> filterTypes = new ArrayList<>();
//        filterTypes.add("By Category");
//        filterTypes.add("By Price");
//        filterTypes.add("By CompanyName");

//        for (Product product : products) {
//            if(!categories.containsValue(product.getCategoryName()))
//                categories.put(new Category(product.getCategoryName()),product.getCategoryName());
//        }
//
//        int k = 0;
//        for (Category category : categories.keySet()) {
//            FilterCards filterCard = new FilterCards(category, productsGridPane, filtersGridPane, sortsDropDown, filtersDropDown);
//            filtersGridPane.add(filterCard, 1, k);
//            k++;
//        }
//
//        slider.valueProperty().addListener(new ChangeListener<Number>() {
//
//            @Override
//            public void changed(
//                    ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
//                sliderLabel.textProperty().setValue(String.valueOf(newValue.intValue()));
//                ArrayList<Product> prodArrayList = new ArrayList<>();
//                for (Product product : products) {
//                    if(product.getPrice()<=newValue.intValue()){
//                        prodArrayList.add(product);
//                    }
//                }
//                update(prodArrayList);
//            }
//        });
    }


//    private String getSortType() throws Exception {
//        return String.valueOf(sortsDropDown.getSelectionModel().getSelectedItem());
//    }

//    public void sort(ActionEvent actionEvent) throws Exception {
//        String sortType = getSortType();
//        ArrayList<Product> productArrayList = new ArrayList<>();
//        HashMap<String,Product> productsHashMap = new HashMap<>();
//        for (Product product : products) {
//            productsHashMap.put(product.getName(),product);
//        }
//
//        if(sortType.equalsIgnoreCase("By Name")){
//            productArrayList.addAll(ProductSort.sortProductHashMap(productsHashMap).values());
//        } else if(sortType.equalsIgnoreCase("By Price")){
//            Product.sortType = 1;
//            productArrayList.addAll(ProductSort.sortProductByPrice(products));
//        } else if(sortType.equalsIgnoreCase("By Score")){
//            Product.sortType = 2;
//            productArrayList.addAll(ProductSort.sortProductByBuyersAverageScore(products));
//        }
//
//        for (Product product : productArrayList) {
//            System.out.println("pro: " + product.getBuyersAverageScore());
//        }
//        update(productArrayList);
//    }
//    private void update(ArrayList<Product> products){
//
//        productsGridPane.getChildren().removeAll(productsGridPane.getChildren());
//    //        filtersGridPane.getChildren().removeAll(filtersGridPane.getChildren());
//
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
//
//
//        ArrayList<String> filterTypes = new ArrayList<>();
//        filterTypes.add("By Category");
//        filterTypes.add("By Price");
//        filterTypes.add("By CompanyName");
//    }

    public void addFile(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        File sourceFile = fileChooser.showOpenDialog((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
        if (sourceFile != null) {
            selectedFile = sourceFile;
            fileName.setText(selectedFile.getName());
        }
    }

    public void uploadFile(ActionEvent actionEvent) {
        if(selectedFile == null){
            showError(alertLabel,"Please select a file first.", Error.NEGATIVE);
        }
        else if(price.getText().isEmpty()){
            showError(alertLabel,"Please enter a price for your file.",Error.NEGATIVE);
        }else{

        }
    }

    private static class FileCard extends VBox {

        private FileCard(String name,String price,String space,Image image){

            ImageView cardImageView = new ImageView(image);

            cardImageView.setFitHeight(140);
            cardImageView.setFitWidth(140);
            cardImageView.setImage(image);
            this.getChildren().add(cardImageView);

            Label cardTitle = new Label();
            cardTitle.setText(name);
            this.getChildren().add(cardTitle);
            cardTitle.getStyleClass().add("mainPageProductCardsTitle");

            Label cardDescription = new Label();
            cardDescription.setText((name + "\n" + "Size: " + space + "\n" + "Price: " + price));
            this.getChildren().add(cardDescription);
            cardDescription.getStyleClass().add("mainPageProductCardsDetail");
            cardDescription.setWrapText(true);

//            Rating rating = new Rating();
//            rating.setRating(product.getBuyersAverageScore());
//            rating.setDisable(true);
//            rating.prefWidth(10);
//            this.getChildren().add(rating);

            Separator separator = new Separator();
            this.getChildren().add(separator);

            Button cardButton = new Button();
            cardButton.setText("-");
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
                        removeFile(e, name);
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

        public void removeFile(ActionEvent actionEvent, String file) throws IOException {
//            ProductPage.setProduct(product);
//            System.out.println("Going to product page");
//            new GraphicFather().goToNextPage(Page.PRODUCTPAGE,actionEvent);
        }
    }

//    private static class FilterCards extends HBox{
//
//        private CheckBox checkBox;
//        GridPane productsGridPane;
//        GridPane filtersGridPane;
//        ComboBox sortsDropDown;
//        ComboBox filtersDropDown;
//
//        private static HashMap<CheckBox, Category> categoriesArrayList = new HashMap<>();
//
//        private FilterCards(Category category, GridPane productsGridPane, GridPane filtersGridPane, ComboBox sortsDropDown, ComboBox filtersDropDown){
//
//            this.productsGridPane = productsGridPane;
//            this.filtersGridPane = filtersGridPane;
//            this.sortsDropDown = sortsDropDown;
//            this.filtersDropDown = filtersDropDown;
//
//            VBox vBox = new VBox();
//            vBox.getStyleClass().add("filterLabelsBox");
//            Label label = new Label();
//            label.setText(category.getName());
//
//            checkBox = new CheckBox();
//            categoriesArrayList.put(checkBox, category);
//            checkBox.setSelected(true);
//
//            vBox.getChildren().add(label);
//            this.getChildren().add(vBox);
//            this.getChildren().add(checkBox);
//
//            this.getStyleClass().add("filterCardsBox");
//
//            checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
//                @Override
//                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//                    ArrayList<Product> newProducts = new ArrayList<>();
//                    for (CheckBox box : categoriesArrayList.keySet()) {
//                        if(box.isSelected()){
//                            for (Product product : categoriesArrayList.get(box).getProductsList()) {
//                                if(!isThereProductAtArrayList(product, newProducts)){
//                                    newProducts.add(product);
//                                }
//                            }
//                        }
//                    }
//                    update(newProducts);
//                }
//            });
//        }
//
//        private static boolean isThereProductAtArrayList(Product product, ArrayList<Product> productArrayList){
//            for (Product productArr : productArrayList) {
//                if(product.getProductId().equalsIgnoreCase(productArr.getProductId())){
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        private void update(ArrayList<Product> products){
//
//            productsGridPane.getChildren().removeAll(productsGridPane.getChildren());
////            filtersGridPane.getChildren().removeAll(filtersGridPane.getChildren());
//            int i = 0;
//            int j = 0;
//            for (Product product : products) {
//                if (i == 4) {
//                    i = 0;
//                    j++;
//                }
//                ProductsCard card = new ProductsCard(product);
//                productsGridPane.add(card, i, j);
//                i++;
//            }
//
//            ArrayList<String> sortTypes = new ArrayList<>();
//            sortTypes.add("By Name");
//            sortTypes.add("By Price");
//            sortTypes.add("By Score");
//            sortsDropDown.setItems(FXCollections.observableArrayList(sortTypes));
//
//
//            ArrayList<String> filterTypes = new ArrayList<>();
//            filterTypes.add("By Category");
//            filterTypes.add("By Price");
//            filterTypes.add("By CompanyName");
////        filtersDropDown.setItems(FXCollections.observableArrayList(filterTypes));
////        filtersDropDown.getSelectionModel().selectFirst();
//
////            int k = 0;
////            for (Category category : Category.getAllCategories()) {
////                FilterCards filterCard = new FilterCards(category, productsGridPane, filtersGridPane, sortsDropDown, filtersDropDown);
////                filtersGridPane.add(filterCard, 1, k);
////                k++;
////            }
//
//        }
//    }


}
