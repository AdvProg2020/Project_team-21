package GUIControllers;

import Controller.Control;
import GUIControllers.GraphicFather;
import Model.Account.Customer;
import Model.Product;
import Model.Review;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image productImage = new Image(getClass().getResourceAsStream(product.getImagePath()));
        productPic.setImage(productImage);
        productLabel.setText(product.getName());
        productDescription.setText((product.getName() + "\n" + "Company: " + product.getCompany() + "\n" + "Category: " + product.getCategory() + "\n"));
        productDescription.setWrapText(true);
        productPrice.setText(String.valueOf(product.getPrice()));
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
    }

    public static void setProduct(Product product) {
        ProductPage.product = product;
    }

    /*
    imagepath product
    add comment button
    add to cart button
    not visible comment for seller and manager
     */


}

