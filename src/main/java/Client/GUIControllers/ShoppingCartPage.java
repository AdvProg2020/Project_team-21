package Client.GUIControllers;

import Client.ClientCenter;
import Client.Model.Product;
import Client.ServerRequest;
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
import javafx.scene.text.TextAlignment;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ShoppingCartPage extends GraphicFather implements Initializable {

    public GridPane cartGridPane;
    public Label totalAmountLabel;
    private static double totalAmountToPay;
    public Circle profilePhoto;
    public Label profileName;
    public Button userPage;
    HashMap<Product,Integer> productQuantity = new HashMap<>();
    ArrayList<Image> productImages = new ArrayList<>();

    public static double getTotalAmountToPay() {
        return totalAmountToPay;
    }

    public static void setTotalAmountToPay(double totalAmountToPay) {
        ShoppingCartPage.totalAmountToPay = totalAmountToPay;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShowRest(profilePhoto,profileName,userPage);
        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETSHOPPINGCART);
        String dataInput = ClientCenter.getInstance().readMessageFromServer();
        System.out.println(dataInput);
        if(!dataInput.equalsIgnoreCase("NONE")){
            try {
                String[] inputParsed = dataInput.split(" - ");
                for (int k = 0; k < inputParsed.length; k++) {
                    productImages.add(ClientCenter.getInstance().recieveImage());
                }
                for (int k = 0; k < inputParsed.length; k++) {
                    String[] productData = inputParsed[k].split("&");
                    productQuantity.put(new Product(productData[0],Double.parseDouble(productData[1]),Double.parseDouble(productData[3]),
                            Double.parseDouble(productData[2]),productData[4],productData[5],productData[6],productImages.get(k),productData[7]),Integer.parseInt(productData[8]));
                }
            } catch (IOException e) {
            }
            int serial = 1;
            double totalAmount = 0;
            for (Product product : productQuantity.keySet()) {
                ShoppingCartCard shoppingCartCard = new ShoppingCartCard(serial, product, product.getPrice(), productQuantity.get(product), cartGridPane, totalAmountLabel);
                cartGridPane.add(shoppingCartCard, 2, serial);
                serial++;
                totalAmount+= (product.getPrice()*productQuantity.get(product));
            }
            totalAmountToPay = totalAmount;
            totalAmountLabel.setText(totalAmount + "$");
        }
    }

    private static class ShoppingCartCard extends HBox {

        private Product product;
        private double price;
        private int quantity;
        private GridPane cartGridPane;
        private Label totalAmountLabel;

        private ShoppingCartCard(int serial, Product product, double price, int quantity, GridPane cartGridPane, Label totoalAmountLabel){

            this.product = product;
            this.price = price;
            this.quantity = quantity;
            this.cartGridPane = cartGridPane;
            this.totalAmountLabel = totoalAmountLabel;

            // action event
            EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        viewProductButton(e, product);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };


            // action event
            EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        addProduct();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            // action event
            EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        removeProduct();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            // action event
            EventHandler<ActionEvent> event4 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        throwProductToBin();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            this.getStyleClass().add("cartBox");

            HBox hBox = new HBox();
            hBox.getStyleClass().add("labelBox");
            Label label = new Label();
            label.setText(String.valueOf(serial));
            label.setTextAlignment(TextAlignment.CENTER);
            hBox.getChildren().add(label);

            VBox imageVBox = new VBox();
            imageVBox.getStyleClass().add("productImage");
            ImageView cardImageView = new ImageView(product.getImage());
            cardImageView.setFitHeight(32);
            cardImageView.setFitWidth(32);
            cardImageView.setImage(product.getImage());
            imageVBox.getChildren().add(cardImageView);

            hBox.getChildren().add(imageVBox);
            this.getChildren().add(hBox);


            VBox vBox1 = new VBox();
            vBox1.getStyleClass().add("labelBox");
            Button label1 = new Button();
            label1.setOnAction(event1);
            label1.setText(product.getName());
            label1.getStyleClass().add("productNameBox");
            label1.setTextAlignment(TextAlignment.CENTER);
            vBox1.getChildren().add(label1);
            this.getChildren().add(vBox1);



            VBox vBox2 = new VBox();
            vBox2.getStyleClass().add("labelBox");
            Label label2 = new Label();
            label2.setText(price + "$");
            label2.setTextAlignment(TextAlignment.CENTER);
            vBox2.getChildren().add(label2);
            this.getChildren().add(vBox2);

            HBox hBox3 = new HBox();
            hBox3.getStyleClass().add("labelBox");
            Label label3 = new Label();
            label3.setText(String.valueOf(quantity));
            label3.getStyleClass().add("quantityBox");
            label3.setTextAlignment(TextAlignment.CENTER);
            Button addBtn = new Button();
            addBtn.setText("+");
            addBtn.setOnAction(event2);
            Button removeBtn = new Button();
            removeBtn.setText("-");
            removeBtn.setOnAction(event3);
            hBox3.getChildren().add(removeBtn);
            hBox3.getChildren().add(label3);
            hBox3.getChildren().add(addBtn);
            this.getChildren().add(hBox3);


            VBox vBox4 = new VBox();
            vBox4.getStyleClass().add("labelBox");
            Label label4 = new Label();
            label4.setText(price * quantity + "$");
            label4.setTextAlignment(TextAlignment.CENTER);
            vBox4.getChildren().add(label4);
            this.getChildren().add(vBox4);

            VBox vBox5 = new VBox();
            vBox5.getStyleClass().add("binButtonBox");
            Button binBtn = new Button();
            binBtn.setText("Remove");
            binBtn.setOnAction(event4);
            binBtn.setTextAlignment(TextAlignment.CENTER);
            vBox5.getChildren().add(binBtn);
            this.getChildren().add(binBtn);
        }

        private void viewProductButton(ActionEvent actionEvent, Product product) throws IOException {
            ProductPage.setProduct(product);
            new GraphicFather().goToNextPage(Page.PRODUCTPAGE,actionEvent);
        }

        private void addProduct() throws IOException {
            ClientCenter.getInstance().sendReqToServer(ServerRequest.UPDATEINCREASECART,product.getProductId());
            update();
        }

        private void removeProduct() throws IOException {
            ClientCenter.getInstance().sendReqToServer(ServerRequest.UPDATEDECREASECART,product.getProductId());
            update();
        }

        private void throwProductToBin() throws IOException {
            ClientCenter.getInstance().sendReqToServer(ServerRequest.UPDATEREMOVECART,product.getProductId());
            update();
        }

        public void update(){
            cartGridPane.getChildren().removeAll(cartGridPane.getChildren());
            ArrayList<Image> productImages = new ArrayList<>();
            HashMap<Product,Integer> productQuantity = new HashMap<>();

            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETSHOPPINGCART);
            String dataInput = ClientCenter.getInstance().readMessageFromServer();
            if(!dataInput.equalsIgnoreCase("NONE")){
                try {
                    String[] inputParsed = dataInput.split(" - ");
                    for (int k = 0; k < inputParsed.length; k++) {
                        productImages.add(ClientCenter.getInstance().recieveImage());
                    }
                    for (int k = 0; k < inputParsed.length; k++) {
                        String[] productData = inputParsed[k].split("&");
                        productQuantity.put(new Product(productData[0],Double.parseDouble(productData[1]),Double.parseDouble(productData[3]),
                                Double.parseDouble(productData[2]),productData[4],productData[5],productData[6],productImages.get(k),productData[7]),Integer.parseInt(productData[8]));
                    }
                } catch (IOException e) {
                }
                int serial = 1;
                double totalAmount = 0;
                for (Product product : productQuantity.keySet()) {
                    ShoppingCartCard shoppingCartCard = new ShoppingCartCard(serial, product, product.getPrice(), productQuantity.get(product), cartGridPane, totalAmountLabel);
                    cartGridPane.add(shoppingCartCard, 2, serial);
                    serial++;
                    totalAmount+= (product.getPrice()*productQuantity.get(product));
                }
                ShoppingCartPage.setTotalAmountToPay(totalAmount);
                totalAmountLabel.setText(totalAmount + "$");
            }
        }
    }


    public void goToPaymentPage(ActionEvent actionEvent) throws IOException {
        PaymentPage.setTotalAmount(getTotalAmountToPay());
        goToNextPage(Page.PAYMENTPAGE,actionEvent);
    }
}
