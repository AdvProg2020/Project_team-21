package Client.GUIControllers.CustomerAccount;//package Products;


import Client.ClientCenter;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;


public class CustomerFiles extends GraphicFather implements Initializable {


    public GridPane productsGridPane;
    public Circle profilePhoto;
    public Label profileName;
    public Button userPage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShowRest(profilePhoto,profileName,userPage);

        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETCUSTOMERFILES);
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
                FileCard card = new FileCard(data[0],data[2],ClientCenter.getInstance().getImageExt(data[1]));
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
            Text txt = new Text(message);
            txt.setFill(Paint.valueOf("069438"));
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

        private FileCard(String name,String space,Image image){

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
            cardDescription.setText(("Name: " + name + "\n" + "Size: " + space + "MB"));
            this.getChildren().add(cardDescription);
            cardDescription.getStyleClass().add("mainPageProductCardsDetail");
            cardDescription.setWrapText(true);

            Separator separator = new Separator();
            this.getChildren().add(separator);

            Button cardButton = new Button();
            URL res = getClass().getClassLoader().getResource("images/download.png");
            Image download = null;
            try {
                download = new Image(res.toURI().toString());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            ImageView show = new ImageView(download);
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
                        download(e, name);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            cardButton.setOnAction(event);
            this.getStyleClass().add("mainPageProductCards");
        }

        public void download(ActionEvent actionEvent, String file) throws IOException {
            ClientCenter.getInstance().sendReqToServer(ServerRequest.GETFILE,file);
            ClientCenter.getInstance().downloadFile();
            showPopup(actionEvent,"Downloaded in your Downloads folder.\nEnjoy ᕙ(⇀‸↼‶)ᕗ");
        }
    }
}
