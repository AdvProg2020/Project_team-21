package Client.GUIControllers;

import Server.Controller.Control;
import Server.Model.Account.Customer;
import Server.Model.Log.BuyLog;
import Server.Model.Product;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BuyLogsPage extends GraphicFather implements Initializable {
    public GridPane logsGridPane;
    public Button userPage;
    public Circle profilePhoto;
    public Label profileName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShowRest(profilePhoto,profileName,userPage);
        Customer customer = (Customer) Control.getInstance().getUser();
        int i=0;
        for (BuyLog buyLog : customer.getBuyLogs()) {
            LogCard logCart = new LogCard(buyLog);
            logsGridPane.add(logCart, 0, i);
            i++;
        }
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
            label2_Prime.setText((int)buyLog.getTotalDiscountAmount() + "$");
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
            BuyLogsPagePrime.setProducts(productArrayList);
            BuyLogsPagePrime.setBuyLog(buyLog);
            new GraphicFather().goToNextPage(Page.BUYLOGSPRIMEPAGE,actionEvent);
        }
    }
}
