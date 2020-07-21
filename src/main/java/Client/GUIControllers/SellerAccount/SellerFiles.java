package Client.GUIControllers.SellerAccount;//package Products;


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
import java.net.URL;
import java.util.ResourceBundle;


public class SellerFiles extends GraphicFather implements Initializable {


    public GridPane productsGridPane;
    public Circle profilePhoto;
    public Label profileName;
    public Button userPage;
    public Label fileName;
    public TextField price;
    public Label alertLabel;
    public Button plus;
    private File selectedFile = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topBarShowRest(profilePhoto,profileName,userPage);
        plus.getStyleClass().add("viewProductBtn");

        ClientCenter.getInstance().sendReqToServer(ServerRequest.GETALLSELLERFILES);
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
                FileCard card = new FileCard(data[1],data[0],data[2],ClientCenter.getInstance().getImageExt(data[3]));
                productsGridPane.add(card, i, j);
                i++;
            }
        }
    }

    public void addFile(ActionEvent actionEvent) {
        final FileChooser fileChooser = new FileChooser();
        File sourceFile = fileChooser.showOpenDialog((Stage)((Node)actionEvent.getSource()).getScene().getWindow());
        if (sourceFile != null) {
            selectedFile = sourceFile;
            fileName.setText(selectedFile.getName());
        }
    }

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

    public void uploadFile(ActionEvent actionEvent) {
        if(selectedFile == null){
            showError(alertLabel,"Please select a file first.", Error.NEGATIVE);
        }
        else if(price.getText().isEmpty()){
            showError(alertLabel,"Please enter a price for your file.",Error.NEGATIVE);
        }
        else if(!price.getText().matches("\\d+.?\\d*")){
            showError(alertLabel,"Your price format is wrong.",Error.NEGATIVE);
        }else{
            ClientCenter.getInstance().sendReqToServer(ServerRequest.POSTUPLOADFILE,price.getText());
            File copyToTemp = new File("temp");
            try {
                FileUtils.copyFileToDirectory(selectedFile,copyToTemp);
                ClientCenter.getInstance().sendFile(copyToTemp + "/" +selectedFile.getName(),selectedFile.getName());
                File file = new File(copyToTemp + "/" +selectedFile.getName());
                file.delete();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String response = ClientCenter.getInstance().readMessageFromServer();
            showPopup(actionEvent,ClientCenter.getInstance().getMessageFromError(response));
            selectedFile = null;
            price.setText("");
            alertLabel.setText("");
            fileName.setText("Select a file");
            productsGridPane.getChildren().removeAll(productsGridPane.getChildren());
            initialize(null,null);
        }
    }

    private static class FileCard extends VBox {

        private FileCard(String name,String price,String space,Image image){

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
            cardDescription.setText((name + "\n" + "Size: " + space + "MB" + "\n" + "Price: " + price));
            this.getChildren().add(cardDescription);
            cardDescription.getStyleClass().add("mainPageProductCardsDetail");
            cardDescription.setWrapText(true);

            Separator separator = new Separator();
            this.getChildren().add(separator);


            // action event
//            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
//                public void handle(ActionEvent e)
//                {
//                    try {
//                        removeFile(e, name);
//                    } catch (IOException ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            };

            this.getStyleClass().add("mainPageProductCards");
        }

    }
}
