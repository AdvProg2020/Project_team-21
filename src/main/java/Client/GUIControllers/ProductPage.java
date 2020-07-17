package Client.GUIControllers;

import Server.Controller.Control;
import Server.Model.*;
import Server.Model.Account.Customer;
import Server.Model.Account.Seller;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;

import java.io.File;
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
    public Label alertLabel;
    public Button submitScore;
    public GridPane pane;
    public Rating rating;

    private ToggleGroup toggleGroup = new ToggleGroup();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rating.setDisable(true);
        if(!(Control.getInstance().getUser() instanceof Customer)){
            increaseButton.setDisable(true);
            decreaseButton.setDisable(true);
            commentButton.setDisable(true);
            radioButton1.setDisable(true);
            radioButton2.setDisable(true);
            radioButton3.setDisable(true);
            radioButton4.setDisable(true);
            radioButton5.setDisable(true);
            submitScore.setDisable(true);
        }else{
            Customer customer = (Customer) Control.getInstance().getUser();
            for (Score score : product.getScoresList()) {
                if(score.getUser().equals(customer)){
                    radioButton1.setDisable(true);
                    radioButton2.setDisable(true);
                    radioButton3.setDisable(true);
                    radioButton4.setDisable(true);
                    radioButton5.setDisable(true);
                    submitScore.setDisable(true);
                }
            }
        }
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton4.setToggleGroup(toggleGroup);
        radioButton5.setToggleGroup(toggleGroup);

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
            productPrice.setText("Price: " + product.getPrice() + "$");
        } else {
            offDetails = "Off: " + product.getOff().getOffId() + " With Amount of: " + product.getOff().getOffAmount() + "%";
            productPrice.setText("Price: " + product.getPrice() + "$");
        }
        productDescription.setText((product.getName() + "\n" + "Product Id: " + product.getProductId() + "\n" + "Company: " + product.getCompany().getName()
                + " -At Location: " + product.getCompany().getLocation() + "\n" + "Category: " + categoryName + "\n" + "Original Price: " + product.getOrgPrice() + "$" +"\n"
                + offDetails));
        productDescription.setWrapText(true);
//        averageScore.setText("Score: " + String.valueOf(product.getBuyersAverageScore()));
        rating.setRating(product.getBuyersAverageScore());

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
        Score scoore = new Score(Control.getInstance().getUser(),product,score);
        product.addScore(scoore);

        showError(alertLabel,"Your score has been submitted." , Error.POSITIVE);
        radioButton1.setDisable(true);
        radioButton2.setDisable(true);
        radioButton3.setDisable(true);
        radioButton4.setDisable(true);
        radioButton5.setDisable(true);
        submitScore.setDisable(true);
//        averageScore.setText("Score: " + String.valueOf(product.getBuyersAverageScore()));
        rating.setRating(product.getBuyersAverageScore());

        Product.rewriteFiles();
        Score.rewriteFiles();
        Customer.rewriteFiles();
    }

    private Seller getSeller() throws Exception {
        return Seller.getSellerWithUsername(String.valueOf(sellersDropDown.getSelectionModel().getSelectedItem()));
    }

    public void removeButton(ActionEvent actionEvent) {
        Customer user = (Customer)Control.getInstance().getUser();
        ShoppingCart cart = user.getShoppingCart();
        if(cart.getProductsQuantity().containsKey(product)){
            cart.decreaseQuantity(product);
            showError(alertLabel,"Now you have " + cart.getProductsQuantity().get(product) + " of this product",Error.POSITIVE);
        }
        else{
            showError(alertLabel,"You don't have this product.",Error.NEGATIVE);
        }
    }

    public void addButton(ActionEvent actionEvent) {
        Customer user = (Customer)Control.getInstance().getUser();
        ShoppingCart cart = user.getShoppingCart();
        if(!cart.getProductsQuantity().containsKey(product)){
            try {
                cart.addProduct(product,1,getSeller());
                showError(alertLabel,"Product has added to your cart",Error.POSITIVE);
            } catch (Exception e) {
                showError(alertLabel,e.getMessage(),Error.NEGATIVE);
            }
        }
        else{
            cart.increaseQuantity(product);
            showError(alertLabel,"Now you have " + cart.getProductsQuantity().get(product) + " of this product.", Error.POSITIVE);
        }
    }

    public void addCommentButton(ActionEvent actionEvent) {
        Customer user = (Customer)Control.getInstance().getUser();
        product.addReview(new Review(user,product,addComment.getText(),user.hasBought(product)));
        showError(alertLabel,"Your comment has been added.",Error.POSITIVE);
        commentButton.setDisable(true);

        Product.rewriteFiles();
        Review.rewriteFiles();
        Customer.rewriteFiles();
    }


    ImageView ivTarget = new ImageView();
    public void zoom(MouseEvent mouseEvent) {
        double x=mouseEvent.getX();
        double y=mouseEvent.getY();

//        if(x < productPic.getTranslateX() + 25){
//            x = (int)productPic.getTranslateX() + 25;
//        }
//        if(x > productPic.getTranslateX() + 210){
//            x = (int)productPic.getTranslateX() + 210;
//        }
//        if(y <  productPic.getTranslateY() + 25){
//            y = (int)productPic.getTranslateY() + 25;
//        }
//        if(y > productPic.getTranslateX() + 210){
//            y = (int)productPic.getTranslateY() + 210;
//        }
        Image image = productPic.getImage();

        ivTarget.setImage(image);
        double height = image.getHeight()/3;
        double width = image.getWidth()/3;
        if(height>150)
            height = 150;
        if(width>150)
            width = 150;
        Rectangle2D view=new Rectangle2D(x*image.getWidth()/200,y*image.getHeight()/200,width,height);
        ivTarget.setViewport(view);
        if(!pane.getChildren().contains(ivTarget))
            pane.add(ivTarget,1,0);
    }

    public void unzoom(MouseEvent mouseEvent) {
        pane.getChildren().remove(ivTarget);
    }
}
