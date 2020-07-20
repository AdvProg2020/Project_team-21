package Client.GUIControllers;

import Client.ClientCenter;
import Client.Model.Off;
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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OffsPage extends GraphicFather implements Initializable {
    public GridPane offsGridPane;
    public Button userPage;
    public Circle profilePhoto;
    public Label profileName;
    ArrayList<Off> allOffs = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShowRest(profilePhoto,profileName,userPage);
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLOFFS);
        String response = ClientCenter.getInstance().readMessageFromServer();
        if(!response.equalsIgnoreCase("NONE")){
            String[] inputOffs = response.split(" - ");
            int i =0;
            for (String off : inputOffs) {
                String[] offDatas = off.split("&");
                Off ooff = new Off(Double.parseDouble(offDatas[0]),offDatas[1],offDatas[2],offDatas[3]);
                allOffs.add(ooff);
                OffsCard offsCard = new OffsCard(ooff);
                offsGridPane.add(offsCard, 0, i);
                i++;
            }
            for (Off allOff : allOffs) {
                ClientCenter.getInstance().sendReqToServer(ServerRequest.GETOFFSPRODUCTS,allOff.getOffId());
                response = ClientCenter.getInstance().readMessageFromServer();
                if(!response.equalsIgnoreCase("NONE")){
                    String[] allProducts = response.split(" - ");
                    for (String allProduct : allProducts){
                        String[] productData = allProduct.split("&");
                        Image productImage;
                        try {
                            productImage = ClientCenter.getInstance().recieveImage();
                            allOff.addProduct(new Product(productData[0],Double.parseDouble(productData[1]),Double.parseDouble(productData[3]),
                                    Double.parseDouble(productData[2]),productData[4],productData[5],productData[6],productImage,productData[7]));
                        } catch (IOException e){
                        }
                    }
                }
            }
        }


//        int i=0;
//        for (Off off : Off.allOffsList) {
//            OffsCard offsCard = new OffsCard(off);
//            offsGridPane.add(offsCard, 0, i);
//            i++;
//        }
    }

    public void gotoAuctionsPage(ActionEvent actionEvent) {

    }

    private static class OffsCard extends GridPane {

        private Off off;

        private OffsCard(Off off){

            this.off = off;

            VBox vBoxFather = new VBox();
            HBox hBox = new HBox();

            VBox vBox1 = new VBox();
            Label label1 = new Label();
            label1.setText("Off Id");
            label1.getStyleClass().add("offLabels1");
            Label label1_Prime = new Label();
            label1_Prime.setText(off.getOffId());
            vBox1.getChildren().add(label1);
            vBox1.getChildren().add(label1_Prime);
            hBox.getChildren().add(vBox1);
            vBox1.getStyleClass().add("labelBox");

            VBox vBox2 = new VBox();
            Label label2 = new Label();
            label2.getStyleClass().add("offLabels1");
            label2.setText("Off Amount");
            Label label2_Prime = new Label();
            label2_Prime.setText((int)off.getOffAmount() + "%");
            vBox2.getChildren().add(label2);
            vBox2.getChildren().add(label2_Prime);
            hBox.getChildren().add(vBox2);
            vBox2.getStyleClass().add("labelBox");

            VBox vBox3 = new VBox();
            Label label3 = new Label();
            label3.getStyleClass().add("offLabels1");
            label3.setText("\t\t\t  Start Time   -To-    End Time");
            Label label3_Prime = new Label();
            label3_Prime.setText(off.getStartTime() + "\t -To- \t" + off.getEndTime());
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
                        viewProductButton(e, off.getProductsList());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };


            Button viewProductsButton = new Button();
            viewProductsButton.setText("View Off");
            viewProductsButton.getStyleClass().add("viewOffBtn");
            hBox.getChildren().add(viewProductsButton);
            viewProductsButton.setOnAction(event);

            this.getChildren().add(vBoxFather);

            this.getStyleClass().add("offDetailsCard");
            this.getStyleClass().add("offBox");

        }

        public void viewProductButton(ActionEvent actionEvent, ArrayList<Product> productArrayList) throws IOException {
            OffsPagePrime.setProducts(productArrayList);
            OffsPagePrime.setOff(off);
            new GraphicFather().goToNextPage(Page.OFFSPRIMEPAGE,actionEvent);
        }

    }
}
