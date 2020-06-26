package GUIControllers;

import Model.Log.BuyLog;
import Model.Log.SellLog;
import Model.Product;
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

public class SellLogsPagePrime extends GraphicFather implements Initializable {

    private static ArrayList<Product> products = new ArrayList<>();
    private static SellLog sellLog;
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
        SellLogsPagePrime.products = products;
    }

    public static void setSellLog(SellLog sellLog) {
        SellLogsPagePrime.sellLog = sellLog;
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

        logIdLabel.setText("Log Id: " + sellLog.getLogId());
        totalAmountLabel.setText("Total Amount: " + sellLog.getPrice() + "$");
        totalDiscountAmountLabel.setText("Discount Amount: " + sellLog.getTotalDiscountAmount() + "$");
        dateLabel.setText(String.valueOf(sellLog.getDate()));
        buyerName.setText("Buyer Name: " + sellLog.getReceiverName());
        phoneNumberLabel.setText("Buyer's Phone Num: " + sellLog.getReceiverPhoneNo());
    }

    private static class ProductsCard extends HBox {

        private ProductsCard(Product product){

            this.getStyleClass().add("mainPageProductCards");

            VBox imageVBox = new VBox();
            File file = new File(product.getImagePath());
            Image cardImage = new Image(file.toURI().toString());
            ImageView cardImageView = new ImageView(cardImage);
            cardImageView.setFitHeight(80);
            cardImageView.setFitWidth(80);
            cardImageView.setImage(cardImage);
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
