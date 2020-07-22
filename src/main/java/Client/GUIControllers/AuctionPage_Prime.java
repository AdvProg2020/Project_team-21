package Client.GUIControllers;

import Client.ClientCenter;
import Client.GUIControllers.Chat.Group_ClientChatController;
import Client.Model.Auction;
import Client.Model.Product;
import Client.ServerRequest;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuctionPage_Prime extends GraphicFather implements Initializable {

    private static Auction auction;
    public Label auctionTitleLabel;
    public Label sellerLabel;
    public Label winnerLabel;
    public Button userPage;
    public Circle profilePhoto;
    public Label profileName;
    public TextField suggestedAmount;
    public GridPane clientPane;
    public VBox chatButton;
    public VBox suggestButton;
    public HBox textFieldToRemove;
    public GridPane auctionGridPane;
    public Label startTimeLabel;
    public Label maxSuggestedAmount;
    public Label endTimeLabel;

    public static void setAuction(Auction auction) {
        AuctionPage_Prime.auction = auction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShowRest(profilePhoto,profileName,userPage);
        if(auction.isExpired()){
            clientPane.getChildren().remove(chatButton);
            clientPane.getChildren().remove(textFieldToRemove);
            clientPane.getChildren().remove(suggestButton);
            Label expired = new Label("The auction has been expired.");
            clientPane.getChildren().add(expired);
        }

        ProductCard productCard = new ProductCard(auction.getAuctionProduct());
        auctionGridPane.add(productCard, 0, 0);

        startTimeLabel.setText("Start Time: " + auction.getStartTime());
        endTimeLabel.setText("End Time: " + auction.getEndTime());
        maxSuggestedAmount.setText("Max Suggested Amount: " + auction.getMaxSuggestedAmount() + "$");
        auctionTitleLabel.setText("Auction: " + auction.getAuctionId());
        sellerLabel.setText("Seller: " + auction.getSellerUsername() +"\n" + "  Name: " +auction.getSellerFirstName() + " " + auction.getSellerLastName());
        if(auction.isExpired())
            winnerLabel.setText("Winner: " + auction.getWinner());
        else
            winnerLabel.setText("Winner until now: " + auction.getWinner());
    }

    public void suggestAmountBtn(ActionEvent actionEvent) {
        if(suggestedAmount.getText().matches("\\d+.?\\d*")){
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTSUGGESTAUCTION,auction.getAuctionId() + "&" + suggestedAmount.getText());
            String response = ClientCenter.getInstance().readMessageFromServer();
            if(response.startsWith(ServerRequest.DONE.toString())){
                update();
                popUp(actionEvent,response);
            }else{
                popUp(actionEvent,response);
            }
        }else{
            popUp(actionEvent,ServerRequest.ERROR +"&" +"Your suggestion format is wrong.");
        }
    }

    public void update(){
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETUPDATEAUCTION,auction.getAuctionId());
        String response = ClientCenter.getInstance().readMessageFromServer();
        String[] parsed = response.split("&");
        auction.setMaxSuggestedAmount(parsed[0]);
        auction.setWinner(parsed[1]);
        auction.setExpired(parsed[2]);
        maxSuggestedAmount.setText("Max Suggested Amount: " + auction.getMaxSuggestedAmount() + "$");
        winnerLabel.setText("Winner until now: " + auction.getWinner());
        if(auction.isExpired()){
            if(auction.isExpired()){
                clientPane.getChildren().remove(chatButton);
                clientPane.getChildren().remove(textFieldToRemove);
                clientPane.getChildren().remove(suggestButton);
                Label expired = new Label("The auction has been expired.");
                winnerLabel.setText("Winner:" + auction.getWinner());
                clientPane.getChildren().add(expired);
            }
        }
    }

    private void popUp(Event event,String message){
        final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
        VBox dialogVbox = new VBox(20);
        Text txt = new Text(ClientCenter.getInstance().getMessageFromError(message));
        if(message.startsWith(ServerRequest.DONE.toString()))
            txt.setFill(Paint.valueOf("069438"));
        else
            txt.setFill(Paint.valueOf("f21a25"));
        Font font = new Font("Hiragino Sans W3" , 20);
        txt.setFont(font);
        dialogVbox.getChildren().add(txt);
        dialogVbox.setBackground(new Background(new BackgroundFill(Paint.valueOf("80A7EB"), CornerRadii.EMPTY, Insets.EMPTY)));
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 400, 200);
        dialogScene.setFill(Paint.valueOf("80A7EB"));
        dialog.setScene(dialogScene);
        dialog.show();
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
