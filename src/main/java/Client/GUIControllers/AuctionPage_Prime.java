package Client.GUIControllers;

import Client.GUIControllers.Chat.Group_ClientChatController;
import Client.Model.Auction;
import Client.Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuctionPage_Prime extends GraphicFather implements Initializable {

    private static Auction auction;
    public Label auctionTitleLabel;
    public Label sellerLabel;
    public Label winnerLabel;

    public static void setAuction(Auction auction) {
        AuctionPage_Prime.auction = auction;
    }



    public GridPane auctionGridPane;
    public Label startTimeLabel;
    public Label maxSuggestedAmount;
    public Label endTimeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ProductCard productCard = new ProductCard(auction.getAuctionProduct());
        auctionGridPane.add(productCard, 0, 0);


        startTimeLabel.setText("Start Time: " + auction.getStartTime());
        endTimeLabel.setText("End Time: " + auction.getEndTime());
        maxSuggestedAmount.setText("Max Suggested Amount: " + auction.getMaxSuggestedAmount() + "$");
        auctionTitleLabel.setText("Auction: " + auction.getAuctionId());
        sellerLabel.setText("Seller: " + auction.getSellerUsername() +"\n" + "  Name: " +auction.getSellerFirstName() + " " + auction.getSellerLastName());
    }

    public void suggestAmountBtn(ActionEvent actionEvent) {

    }

    public void gotoChat(ActionEvent actionEvent) throws IOException {
        Group_ClientChatController.setGroupChatId(auction.getAuctionId());
        goToNextPage(Page.GROUPCHATPAGE, actionEvent);
    }

    private static class ProductCard extends HBox {

        private ProductCard(Product product){

            this.getStyleClass().add("mainPageProductCards");

            VBox imageVBox = new VBox();
            ImageView cardImageView = new ImageView(product.getImage());
            cardImageView.setFitHeight(80);
            cardImageView.setFitWidth(80);
            cardImageView.setImage(product.getImage());
            imageVBox.getChildren().add(cardImageView);
            this.getChildren().add(imageVBox);

            VBox vBox = new VBox();

            HBox hBox = new HBox();
            Label cardTitle = new Label();
            cardTitle.setText(product.getName());
            cardTitle.getStyleClass().add("mainPageProductCardsTitle");
            hBox.getChildren().add(cardTitle);
            Label scoreLabel = new Label();
            scoreLabel.setText("Score: " + product.getBuyersAverageScore());
            scoreLabel.getStyleClass().add("scoreLabel");
            hBox.getChildren().add(scoreLabel);

            vBox.getChildren().add(hBox);

            Label cardTitle2 = new Label();
            cardTitle2.setText(product.getCompanyName());
            cardTitle2.getStyleClass().add("mainPageProductCardsTitle");
            vBox.getChildren().add(cardTitle2);
            this.getChildren().add(vBox);

            VBox vBox2 = new VBox();
            vBox2.getStyleClass().add("viewProductBtnBox");
            Button viewProductBtn = new Button();
            viewProductBtn.setText("View Product");
            viewProductBtn.getStyleClass().add("viewProductBtn");
            vBox2.getChildren().add(viewProductBtn);
            vBox.getChildren().add(vBox2);

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
            viewProductBtn.setOnAction(event);
        }

        public void viewProductButton(ActionEvent actionEvent, Product product) throws IOException {
            ProductPage.setProduct(product);
            new GraphicFather().goToNextPage(Page.PRODUCTPAGE,actionEvent);
        }
    }
}
