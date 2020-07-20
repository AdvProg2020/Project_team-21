package Client.GUIControllers;

import Client.ClientCenter;
import Client.Model.Product;
import Client.Model.SellLog;
import Client.ServerRequest;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SellLogsPage extends GraphicFather implements Initializable {
    public GridPane logsGridPane;
    public Button userPage;
    public Circle profilePhoto;
    public Label profileName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETSELLLOGS);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(!response.equalsIgnoreCase("NONE")){
            String[] allSellLogs = response.split(" - ");
            int i = 0;
            for (String sellLog : allSellLogs) {
                String[] parsedData = sellLog.split("&");
                SellLog log = new SellLog(parsedData[0],parsedData[1],parsedData[2],parsedData[3],parsedData[4],parsedData[5],parsedData[6]);
                ClientCenter.getInstance().sendReqToServer(ServerRequest.GETSELLLOGPRODUCTS,parsedData[0]);
                String message = ClientCenter.getInstance().readMessageFromServer();
                if(!message.equalsIgnoreCase("NONE")){
                    String[] allProducts = message.split(" - ");
                    for (String product : allProducts){
                        Image image = null;
                        try {
                            image = ClientCenter.getInstance().recieveImage();
                        } catch (IOException e) {
                        }
                        String[] productData = product.split("&");
                        log.addProduct(new Product(productData[0],Double.parseDouble(productData[1]),Double.parseDouble(productData[3]),
                                Double.parseDouble(productData[2]),productData[4],productData[5],productData[6],image,productData[7]));
                    }
                }
                LogCard logCart = new LogCard(log);
                logsGridPane.add(logCart, 0, i);
                i++;
            }
        }
        topBarShowRest(profilePhoto,profileName,userPage);
//        Seller seller = (Seller) Control.getInstance().getUser();
//
//        int i=0;
//        for (SellLog sellLog : seller.getSellLogs()) {
//            LogCard logCart = new LogCard(sellLog);
//            logsGridPane.add(logCart, 0, i);
//            i++;
//        }
    }

    public void gotoAuctionsPage(ActionEvent actionEvent) {

    }

    private static class LogCard extends GridPane {

        private SellLog sellLog;

        private LogCard(SellLog sellLog){

            this.sellLog = sellLog;

            VBox vBoxFather = new VBox();
            HBox hBox = new HBox();

            VBox vBox1 = new VBox();
            Label label1 = new Label();
            label1.setText("Log Id");
            label1.getStyleClass().add("offLabels1");
            Label label1_Prime = new Label();
            label1_Prime.setText(sellLog.getLogId());
            vBox1.getChildren().add(label1);
            vBox1.getChildren().add(label1_Prime);
            hBox.getChildren().add(vBox1);
            vBox1.getStyleClass().add("labelBox");

            VBox vBox2 = new VBox();
            Label label2 = new Label();
            label2.getStyleClass().add("offLabels1");
            label2.setText("Total Discount Amount");
            Label label2_Prime = new Label();
            label2_Prime.setText(sellLog.getTotalDiscountAmount() + "$");
            vBox2.getChildren().add(label2);
            vBox2.getChildren().add(label2_Prime);
            hBox.getChildren().add(vBox2);
            vBox2.getStyleClass().add("labelBox");

            VBox vBox3 = new VBox();
            Label label3 = new Label();
            label3.getStyleClass().add("offLabels1");
            label3.setText("Total Amount");
            Label label3_Prime = new Label();
            label3_Prime.setText(sellLog.getPrice() + "$");
            vBox3.getChildren().add(label3);
            vBox3.getChildren().add(label3_Prime);
            hBox.getChildren().add(vBox3);
            vBox3.getStyleClass().add("labelBox");

            VBox vBox4 = new VBox();
            Label label4 = new Label();
            label4.getStyleClass().add("offLabels1");
            label4.setText("\t\t\t  Date");
            Label label4_Prime = new Label();
            label4_Prime.setText(String.valueOf(sellLog.getDate()));
            vBox4.getChildren().add(label4);
            vBox4.getChildren().add(label4_Prime);
            hBox.getChildren().add(vBox4);
            vBox4.getStyleClass().add("labelBox");

            vBoxFather.getChildren().add(hBox);


            // action event
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        viewProductButton(e, sellLog.getAllProducts());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };


            Button viewProductsButton = new Button();
            viewProductsButton.setText("View Log");
            viewProductsButton.getStyleClass().add("viewOffBtn");
            hBox.getChildren().add(viewProductsButton);
            viewProductsButton.setOnAction(event);

            this.getChildren().add(vBoxFather);

            this.getStyleClass().add("offDetailsCard");
            this.getStyleClass().add("offBox");

        }

        public void viewProductButton(ActionEvent actionEvent, ArrayList<Product> productArrayList) throws IOException {
            SellLogsPagePrime.setProducts(productArrayList);
            SellLogsPagePrime.setSellLog(sellLog);
            new GraphicFather().goToNextPage(Page.SELLLOGSPRIMEPAGE,actionEvent);
        }
    }
}
