package Client.GUIControllers;

import Client.ClientCenter;
import Client.Model.Auction;
import Client.Model.Product;
import Client.ServerRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.ResourceBundle;
import java.util.TimerTask;


public class AuctionPage extends GraphicFather implements Initializable {
    public GridPane auctionsGridPane;
    public Button userPage;
    public Circle profilePhoto;
    public Label profileName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShowRest(profilePhoto,profileName,userPage);
        ArrayList<Image> allProductsImages = new ArrayList<>();
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLAUCTIONS);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(!response.equalsIgnoreCase("NONE")){
            String[] parsedData = response.split(" - ");
            for (String s : parsedData) {
                try {
                    allProductsImages.add(ClientCenter.getInstance().recieveImage());
                } catch (IOException e) {
                }
            }
            int i=0;
            for (String s : parsedData){
                String[] auctionData = s.split("&");
                boolean isExpired = false;
                if(auctionData[15].equalsIgnoreCase("true"))
                    isExpired = true;
                Auction auction = new Auction(auctionData[0],auctionData[1],auctionData[2],auctionData[3],auctionData[4],auctionData[13],auctionData[14],isExpired,auctionData[16]);
                auction.setAuctionProduct(new Product(auctionData[5],Double.parseDouble(auctionData[6]),Double.parseDouble(auctionData[8]),Double.parseDouble(auctionData[7]),
                        auctionData[9],auctionData[10],auctionData[11],allProductsImages.get(i),auctionData[12]));
                AuctionsCard auctionsCard = new AuctionsCard(auction);
                auctionsGridPane.add(auctionsCard, 0, i);
                i++;
            }
        }

    }

    private static class AuctionsCard extends GridPane {

        private AuctionsCard(Auction auction){

            VBox vBoxFather = new VBox();
            HBox hBox = new HBox();

            VBox vBox1 = new VBox();
            Label label1 = new Label();
            label1.setText("Auction Id");
            label1.getStyleClass().add("offLabels1");
            Label label1_Prime = new Label();
            label1_Prime.setText(auction.getAuctionId());
            vBox1.getChildren().add(label1);
            vBox1.getChildren().add(label1_Prime);
            hBox.getChildren().add(vBox1);
            vBox1.getStyleClass().add("labelBox");


            HBox hB = new HBox();
            hB.getStyleClass().add("labelBox");

            VBox imageVBox = new VBox();
            imageVBox.getStyleClass().add("productImage");
            ImageView cardImageView = new ImageView(auction.getAuctionProduct().getImage());
            cardImageView.setFitHeight(32);
            cardImageView.setFitWidth(32);
            imageVBox.getChildren().add(cardImageView);

            VBox expiredBox = new VBox();
            if(auction.isExpired()){
                URL res = getClass().getClassLoader().getResource("images/expired.png");
                Image expired = null;
                try {
                    expired = new Image(res.toURI().toString());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                ImageView imageExpired = new ImageView(expired);
                imageExpired.setFitHeight(50);
                imageExpired.setFitWidth(90);
                expiredBox.getChildren().add(imageExpired);

            }


            VBox vBox2 = new VBox();
            Label label2 = new Label();
            label2.getStyleClass().add("offLabels1");
            label2.setText("Product");
            Label label2_Prime = new Label();
            label2_Prime.setText(auction.getAuctionProduct().getName());
            vBox2.getChildren().add(label2);
            vBox2.getChildren().add(label2_Prime);
            hBox.getChildren().add(vBox2);
            vBox2.getStyleClass().add("labelBox");

            hB.getChildren().add(vBox2);
            hB.getChildren().add(imageVBox);
            HBox separate = new HBox();
            separate.getStyleClass().add("hboxSeparate");
            hB.getChildren().add(separate);
            hB.getChildren().add(expiredBox);
            hB.getStyleClass().add("proHBox");
            vBoxFather.getChildren().add(hB);


            VBox vBox3 = new VBox();
            Label label3 = new Label();
            label3.getStyleClass().add("offLabels1");
            label3.setText("\t\t\t  Start Time   -To-    End Time");
            Label label3_Prime = new Label();
            label3_Prime.setText(auction.getStartTime() + "\t -To- \t" + auction.getEndTime());
            vBox3.getChildren().add(label3);
            vBox3.getChildren().add(label3_Prime);
            hBox.getChildren().add(vBox3);
            vBox3.getStyleClass().add("labelBox");

            vBoxFather.getChildren().add(hBox);


            // action event
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        viewProductButton(e, auction);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };


            Button viewProductsButton = new Button();
            viewProductsButton.setText("View Auction");
            viewProductsButton.getStyleClass().add("viewOffBtn");
            hBox.getChildren().add(viewProductsButton);
            viewProductsButton.setOnAction(event);

            this.getChildren().add(vBoxFather);

            this.getStyleClass().add("offDetailsCard");
            if(auction.isExpired())
                this.getStyleClass().add("expiredAuction");
            else
                this.getStyleClass().add("offBox");

        }

        public void viewProductButton(ActionEvent actionEvent, Auction auction) throws IOException {
            AuctionPage_Prime.setAuction(auction);
            new GraphicFather().goToNextPage(Page.AUCTIONPAGEPRIME,actionEvent);
        }
    }
}
