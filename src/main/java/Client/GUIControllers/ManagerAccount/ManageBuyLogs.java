package Client.GUIControllers.ManagerAccount;

import Client.ClientCenter;
import Client.GUIControllers.BuyLogsPagePrime;
import Client.GUIControllers.GraphicFather;
import Client.GUIControllers.Page;
import Client.Model.BuyLog;
import Client.Model.Product;
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

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ManageBuyLogs extends GraphicFather implements Initializable {
    public GridPane logsGridPane;
    public Button userPage;
    public Circle profilePhoto;
    public Label profileName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLBUYLOGS);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(!response.equalsIgnoreCase("NONE")){
            String[] allBuyLogs = response.split(" - ");
            int i = 0;
            for (String buyLog : allBuyLogs) {
                String[] parsedData = buyLog.split("&");
                BuyLog log = new BuyLog(parsedData[0],parsedData[1],parsedData[2],parsedData[3],parsedData[4],parsedData[5],parsedData[6]);
                if(parsedData[7].equalsIgnoreCase("true"))
                    log.setDelivered(true);
                else
                    log.setDelivered(false);
                ClientCenter.getInstance().sendReqToServer(ServerRequest.GETBUYLOGPRODUCTS,parsedData[0]);
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
//        Customer customer = (Customer) Control.getInstance().getUser();
//        int i=0;
//        for (BuyLog buyLog : customer.getBuyLogs()) {
//            LogCard logCart = new LogCard(buyLog);
//            logsGridPane.add(logCart, 0, i);
//            i++;
//        }
    }


    private static class LogCard extends GridPane {

        private BuyLog buyLog;

        private LogCard(BuyLog buyLog){

            this.buyLog = buyLog;

            VBox vBoxFather = new VBox();
            HBox hBox = new HBox();

            VBox vBox1 = new VBox();
            Label label1 = new Label();
            label1.setText("Log Id");
            label1.getStyleClass().add("offLabels1");
            Label label1_Prime = new Label();
            label1_Prime.setText(buyLog.getLogId());
            vBox1.getChildren().add(label1);
            vBox1.getChildren().add(label1_Prime);
            hBox.getChildren().add(vBox1);
            vBox1.getStyleClass().add("labelBox");

            VBox vBox2 = new VBox();
            Label label2 = new Label();
            label2.getStyleClass().add("offLabels1");
            label2.setText("Total Discount Amount");
            Label label2_Prime = new Label();
            label2_Prime.setText(buyLog.getTotalDiscountAmount() + "$");
            vBox2.getChildren().add(label2);
            vBox2.getChildren().add(label2_Prime);
            hBox.getChildren().add(vBox2);
            vBox2.getStyleClass().add("labelBox");

            VBox vBox3 = new VBox();
            Label label3 = new Label();
            label3.getStyleClass().add("offLabels1");
            label3.setText("Total Amount");
            Label label3_Prime = new Label();
            label3_Prime.setText(buyLog.getPrice() + "$");
            vBox3.getChildren().add(label3);
            vBox3.getChildren().add(label3_Prime);
            hBox.getChildren().add(vBox3);
            vBox3.getStyleClass().add("labelBox");

            VBox vBox4 = new VBox();
            Label label4 = new Label();
            label4.getStyleClass().add("offLabels1");
            label4.setText("\t\t\t  Date");
            Label label4_Prime = new Label();
            label4_Prime.setText(String.valueOf(buyLog.getDate()));
            vBox4.getChildren().add(label4);
            vBox4.getChildren().add(label4_Prime);
            hBox.getChildren().add(vBox4);
            vBox4.getStyleClass().add("labelBox");

            vBoxFather.getChildren().add(hBox);

            Button viewProductsButton = new Button();
            viewProductsButton.setText("View Log");
            viewProductsButton.getStyleClass().add("viewOffBtn");
            hBox.getChildren().add(viewProductsButton);

            Button deliveredButton = new Button();
            if(buyLog.isDelivered()){
                deliveredButton.setText("Delivered");
                deliveredButton.getStyleClass().add("deliveredBtn");
                deliveredButton.setDisable(true);
            }else{
                deliveredButton.setText("Waiting");
                deliveredButton.getStyleClass().add("notDeliveredBtn");
            }
            hBox.getChildren().add(deliveredButton);


            // action event
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        viewProductButton(e, buyLog.getAllProducts());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            EventHandler<ActionEvent> eventDeliver = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        deliver(e,deliveredButton);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };


            viewProductsButton.setOnAction(event);
            deliveredButton.setOnAction(eventDeliver);

            this.getChildren().add(vBoxFather);

            this.getStyleClass().add("offDetailsCard");
            this.getStyleClass().add("offBox");
        }

        public void viewProductButton(ActionEvent actionEvent, ArrayList<Product> productArrayList) throws IOException {
            BuyLogsPagePrime.setProducts(productArrayList);
            BuyLogsPagePrime.setBuyLog(buyLog);
            new GraphicFather().goToNextPage(Page.BUYLOGSPRIMEPAGE,actionEvent);
        }

        public void deliver(ActionEvent actionEvent,Button button) throws IOException {
            ClientCenter.getInstance().sendReqToServer(ServerRequest.UPDATEDELIVERLOG,buyLog.getLogId());
            buyLog.setDelivered(true);
            button.setText("Delivered");
            button.getStyleClass().add("deliveredBtn");
            button.setDisable(true);
        }
    }
}
