package Client.GUIControllers;

import Client.Model.BuyLog;
import Client.Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BuyLogsPagePrime extends GraphicFather implements Initializable {

    private static ArrayList<Product> products = new ArrayList<>();
    private static BuyLog buyLog;
    public Label logIdLabel;
    public Label totalAmountLabel;
    public Label totalDiscountAmountLabel;
    public Label dateLabel;
    public GridPane logsGridPane;
    public Label phoneNumberLabel;
    public Label buyerName;
    public Button userPage;
    public Label profileName;
    public Circle profilePhoto;

    public static void setProducts(ArrayList<Product> products) {
        BuyLogsPagePrime.products = products;
    }

    public static void setBuyLog(BuyLog buyLog) {
        BuyLogsPagePrime.buyLog = buyLog;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShowRest(profilePhoto,profileName,userPage);
        int i=0;
        int j=0;
        for (Product product : products) {
            if(i==3){
                i=0;
                j++;
            }
            ProductsCard card = new ProductsCard(product);
            logsGridPane.add(card, i, j);
            i++;
        }

        logIdLabel.setText("Log Id: " + buyLog.getLogId());
        totalAmountLabel.setText("Total Amount: " + buyLog.getPrice() + "$");
        totalDiscountAmountLabel.setText("Discount Amount: " + buyLog.getTotalDiscountAmount() + "$");
        dateLabel.setText(String.valueOf(buyLog.getDate()));
        buyerName.setText("Buyer Name: " + buyLog.getReceiverName());
        phoneNumberLabel.setText("Buyer's Phone Num: " + buyLog.getReceiverPhoneNo());
    }

    private static class ProductsCard extends HBox {

        private ProductsCard(Product product){

            this.getStyleClass().add("mainPageProductCards");

            VBox imageVBox = new VBox();
            ImageView cardImageView = new ImageView(product.getImage());
            cardImageView.setFitHeight(80);
            cardImageView.setFitWidth(80);
            cardImageView.setImage(product.getImage());
            imageVBox.getChildren().add(cardImageView);
            this.getChildren().add(imageVBox);

            VBox vBox = new VBox();
            Label cardTitle = new Label();
            cardTitle.setText(product.getName());
            cardTitle.getStyleClass().add("mainPageProductCardsTitle");
            vBox.getChildren().add(cardTitle);

            Label cardTitle2 = new Label();
            cardTitle2.setText(String.valueOf(product.getPrice()));
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
