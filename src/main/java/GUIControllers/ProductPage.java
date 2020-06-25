package GUIControllers;

import Controller.Control;
import Model.*;
import Model.Account.Account;
import Model.Account.Customer;
import Model.Account.Seller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProductPage extends GraphicFather implements Initializable {

    private static Product product;
    public ImageView productPic;
    public Label productLabel;
    public Label productDescription;
    public Label productPrice;
    public Label averageScore;
    public TextField addComment;
    public GridPane commentsGridPane;
    public ComboBox sellersDropDown;
    public RadioButton radioButton1;
    public RadioButton radioButton2;
    public RadioButton radioButton3;
    public RadioButton radioButton4;
    public RadioButton radioButton5;
    public Button increaseButton;
    public Button decreaseButton;
    public Button commentButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!(Control.getInstance().getUser() instanceof Customer)){
            increaseButton.setDisable(true);
            decreaseButton.setDisable(true);
            commentButton.setDisable(true);
            radioButton1.setDisable(true);
            radioButton2.setDisable(true);
            radioButton3.setDisable(true);
            radioButton4.setDisable(true);
            radioButton5.setDisable(true);
        }

        File file = new File(product.getImagePath());
        Image productImage = new Image(file.toURI().toString());
        productPic.setImage(productImage);
        productLabel.setText(product.getName());

        String categoryName;
        if (product.getCategory()==null){
            categoryName = "NO CATEGORY IS AVAILABLE!";
        } else {
            categoryName = product.getCategory().getName();
        }

        String offDetails;
        if(product.getOff()==null){
            offDetails = "NO OFF IS AVAILABLE!";
        } else {
            offDetails = "Off: " + product.getOff().getOffId() + " With Amount of: " + product.getOff().getOffAmount() + " With State of: " + product.getOff().getOffState();
        }
        productDescription.setText((product.getName() + "\n" + "Product Id: " + product.getProductId() + "\n" + "Company: " + product.getCompany().getName()
                + " -At Location: " + product.getCompany().getLocation() + "\n" + "Category: " + categoryName + "\n" + "Price: " + product.getPrice() + "$" +"\n"
                + offDetails));
        productDescription.setWrapText(true);
        productPrice.setText("Price: " + product.getPrice() + "$");
        averageScore.setText("Score: " + String.valueOf(product.getBuyersAverageScore()));

        VBox vBox = new VBox();
        for (Review review : product.getReviewsList()) {
            Label comment = new Label();
            comment.setText(review.getReviewText());
            comment.setMinHeight(15);
            comment.getStyleClass().add("commentLabels");
            vBox.getChildren().add(comment);
        }
        commentsGridPane.getChildren().add(vBox);

        ArrayList<String> sellerNames = new ArrayList<>();
        for (Seller seller : product.getSellers()) {
            sellerNames.add(seller.getUsername());
        }
        sellersDropDown.setItems(FXCollections.observableArrayList(sellerNames));
    }

    public static void setProduct(Product product) {
        ProductPage.product = product;
    }

    public void scoreButton(ActionEvent actionEvent) {
        ToggleGroup toggleGroup = new ToggleGroup();
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton4.setToggleGroup(toggleGroup);
        radioButton5.setToggleGroup(toggleGroup);
        RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
        String radioButtonId = selectedRadioButton.getId();
        int score=0;
        if(radioButtonId.equalsIgnoreCase("radioButton1")){
            score = 1;
        } else if(radioButtonId.equalsIgnoreCase("radioButton2")){
            score = 2;
        } else if(radioButtonId.equalsIgnoreCase("radioButton3")){
            score = 3;
        } else if(radioButtonId.equalsIgnoreCase("radioButton4")){
            score = 4;
        } else if(radioButtonId.equalsIgnoreCase("radioButton5")){
            score = 5;
        }
        product.addScore(new Score(Control.getInstance().getUser(),product,score));
    }

    private Seller getSeller() throws Exception {
        return Seller.getSellerWithUsername(String.valueOf(sellersDropDown.getSelectionModel().getSelectedItem()));
    }

    public void removeButton(ActionEvent actionEvent) {
        Customer user = (Customer)Control.getInstance().getUser();
        ShoppingCart cart = user.getShoppingCart();
        if(cart.getProductsQuantity().containsKey(product)){
            cart.decreaseQuantity(product);
        }
    }

    public void addButton(ActionEvent actionEvent) {
        Customer user = (Customer)Control.getInstance().getUser();
        ShoppingCart cart = user.getShoppingCart();
        if(!cart.getProductsQuantity().containsKey(product)){
            try {
                cart.addProduct(product,1,getSeller());
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
        else{
            cart.increaseQuantity(product);
        }
    }

    public void addCommentButton(ActionEvent actionEvent) {
        Customer user = (Customer)Control.getInstance().getUser();
        product.addReview(new Review(user,product,addComment.getText(),user.hasBought(product)));
    }
}
