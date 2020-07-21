package Client.GUIControllers;

import Client.ClientCenter;
import Client.Model.Product;
import Client.ServerRequest;
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
        System.out.println("in product page");
        rating.setDisable(true);
        String message = "null";
        if(!ClientCenter.getInstance().getActiveToken().equalsIgnoreCase("NULL")){
            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETACCOUNTTYPE);
            message = ClientCenter.getInstance().readMessageFromServer();
        }
        if(!(message.equalsIgnoreCase("Customer"))){
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
            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETUSERHASSCORED,product.getProductId());
            message = ClientCenter.getInstance().readMessageFromServer();
            if(message.equalsIgnoreCase(ServerRequest.TRUE.toString())){
                radioButton1.setDisable(true);
                radioButton2.setDisable(true);
                radioButton3.setDisable(true);
                radioButton4.setDisable(true);
                radioButton5.setDisable(true);
                submitScore.setDisable(true);
            }
        }
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton4.setToggleGroup(toggleGroup);
        radioButton5.setToggleGroup(toggleGroup);

        productPic.setImage(product.getImage());
        productLabel.setText(product.getName());

        String categoryName = product.getCategoryName();

        String offDetails;
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETPRODUCTOFF,product.getProductId());
        message = ClientCenter.getInstance().readMessageFromServer();

        if(message.equalsIgnoreCase(ServerRequest.NULL.toString())){
            offDetails = "NO OFF IS AVAILABLE!";
            productPrice.setText("Price: " + product.getPrice() + "$");
        } else {
            String[] offData = message.split("&");
            offDetails = "Off: " + offData[0] + " With Amount of: " + offData[1] + "%";
            productPrice.setText("Price: " + product.getPrice() + "$");
        }

        productDescription.setText((product.getName() + "\n" + "Product Id: " + product.getProductId() + "\n" + "Company: " + product.getCompanyName()
                + " -At Location: " + product.getCompanyAddress() + "\n" + "Category: " + categoryName + "\n" + "Original Price: " + product.getOrgPrice() + "$" +"\n"
                + offDetails));
        productDescription.setWrapText(true);
//        averageScore.setText("Score: " + String.valueOf(product.getBuyersAverageScore()));
        rating.setRating(product.getBuyersAverageScore());

        VBox vBox = new VBox();
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETPRODUCTREVIEWS,product.getProductId());
        message = ClientCenter.getInstance().readMessageFromServer();

        String[] reviews = message.split(" - ");
        for (String review : reviews) {
            Label comment = new Label();
            comment.setText(review);
            comment.setMinHeight(15);
            comment.getStyleClass().add("commentLabels");
            vBox.getChildren().add(comment);
        }
//        for (Review review : product.getReviewsList()) {
//            Label comment = new Label();
//            comment.setText(review.getReviewText());
//            comment.setMinHeight(15);
//            comment.getStyleClass().add("commentLabels");
//            vBox.getChildren().add(comment);
//        }
        commentsGridPane.getChildren().add(vBox);

        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETPRODUCTSELLERS,product.getProductId());
        message = ClientCenter.getInstance().readMessageFromServer();

        ArrayList<String> sellerNames = new ArrayList<>();
        String[] sellers = message.split(" - ");
        for (String sellerName : sellers) {
            sellerNames.add(sellerName);
        }
//        for (Seller seller : product.getSellers()) {
//            sellerNames.add(seller.getUsername());
//        }
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
        ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTSCORE,product.getProductId() + "&" + score);

        String message = null;
        message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString()))
            showError(alertLabel,"Your score has been submitted." , Error.POSITIVE);
        radioButton1.setDisable(true);
        radioButton2.setDisable(true);
        radioButton3.setDisable(true);
        radioButton4.setDisable(true);
        radioButton5.setDisable(true);
        submitScore.setDisable(true);
//        averageScore.setText("Score: " + String.valueOf(product.getBuyersAverageScore()));
        int newScore = Integer.parseInt(message.split("&")[1]);
        rating.setRating(newScore);
//        Product.rewriteFiles();
//        Score.rewriteFiles();
//        Customer.rewriteFiles();
    }

    private String getSeller(){
        return String.valueOf(sellersDropDown.getSelectionModel().getSelectedItem());
    }

    public void addButton(ActionEvent actionEvent) {
        System.out.println(getSeller());;
        if(getSeller().equalsIgnoreCase("NULL")){
            showError(alertLabel,"You should choose a seller first.",Error.NEGATIVE);
        }else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.UPDATEADDPRODUCTTOCART,product.getProductId()+"&"+ getSeller());
            String message = null;
            message = ClientCenter.getInstance().readMessageFromServer();

            if(message.startsWith(ServerRequest.DONE.toString())){
                showError(alertLabel,message.split("&")[1],Error.POSITIVE);
            }
            else{
                showError(alertLabel,message.split("&")[1],Error.NEGATIVE);
            }
        }
    }

    public void removeButton(ActionEvent actionEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.UPDATEREMOVEPRODUCTTOCART,product.getProductId());
        String message;
        message = ClientCenter.getInstance().readMessageFromServer();
        if(message.startsWith(ServerRequest.DONE.toString())){
            showError(alertLabel,message.split("&")[1],Error.POSITIVE);
        }
        else{
            showError(alertLabel,message.split("&")[1],Error.NEGATIVE);
        }
    }

    public void addCommentButton(ActionEvent actionEvent) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTREVIEW,product.getProductId() + "//" + addComment.getText());
        String message = null;
        message = ClientCenter.getInstance().readMessageFromServer();

        if(message.equalsIgnoreCase(ServerRequest.DONE.toString()))
            showError(alertLabel,"Your comment has been added.",Error.POSITIVE);
        commentButton.setDisable(true);
    }


    ImageView ivTarget = new ImageView();
    public void zoom(MouseEvent mouseEvent) {
        double x=mouseEvent.getX();
        double y=mouseEvent.getY();
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
