package Client.GUIControllers;//package Products;


import Client.ClientCenter;
import Client.GUIControllers.Error;
import Client.GUIControllers.GraphicFather;
import Client.ServerRequest;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;


public class FilesPage extends GraphicFather implements Initializable {


    public GridPane productsGridPane;
    public Circle profilePhoto;
    public Label profileName;
    public Button userPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShowRest(profilePhoto,profileName,userPage);

        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLFILES);
        String dataInput = ClientCenter.getInstance().readMessageFromServer();

        if(!dataInput.equalsIgnoreCase("NONE")){
            String[] inputParsed = dataInput.split(" - ");
            int i = 0;
            int j = 0;
            for (String s : inputParsed) {
                if(i==4){
                    i=0;
                    j++;
                }
                String[] data = s.split("&");
                FileCard card = new FileCard(data[1],data[0],data[2],ClientCenter.getInstance().getImageExt(data[3]),data[4]);
                productsGridPane.add(card, i, j);
                i++;
            }
        }
    }



    private static class FileCard extends VBox {

        private void showPopup(Event event,String message){
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

        private FileCard(String name,String price,String space,Image image,String seller){

            ImageView cardImageView = new ImageView(image);

            cardImageView.setFitHeight(140);
            cardImageView.setFitWidth(140);
            cardImageView.setImage(image);
            this.getChildren().add(cardImageView);

            Label cardTitle = new Label();
            cardTitle.setText(name);
            this.getChildren().add(cardTitle);
            cardTitle.getStyleClass().add("mainPageProductCardsTitle");

            Label cardDescription = new Label();
            cardDescription.setText(("Name: " + name + "\n" + "Size: " + space + "MB" + "\n" + "Price: " + price + "$\nSeller: " + seller));
            this.getChildren().add(cardDescription);
            cardDescription.getStyleClass().add("mainPageProductCardsDetail");
            cardDescription.setWrapText(true);

            Separator separator = new Separator();
            this.getChildren().add(separator);

            Button cardButton = new Button();
            URL res = getClass().getClassLoader().getResource("images/buy.png");
            Image buy = null;
            try {
                buy = new Image(res.toURI().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            ImageView show = new ImageView(buy);
            show.setFitHeight(50);
            show.setFitWidth(50);
            cardButton.setGraphic(show);
            cardButton.getStyleClass().add("viewProductBtn");
            cardButton.prefHeight(42);
            cardButton.prefWidth(90);

            HBox buttonBox = new HBox();
            buttonBox.getChildren().add(cardButton);
            buttonBox.getStyleClass().add("viewProductBtnBox");
            this.getChildren().add(buttonBox);

            // action event
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    try {
                        buy(e, name,price,seller);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            cardButton.setOnAction(event);
            this.getStyleClass().add("mainPageProductCards");
        }

        public void buy(ActionEvent actionEvent, String file,String price,String seller) throws IOException {
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTBUYFILE,file + "//" + price + "//"+ seller);
            String response = ClientCenter.getInstance().readMessageFromServer();
            showPopup(actionEvent,response);
        }
    }
}
