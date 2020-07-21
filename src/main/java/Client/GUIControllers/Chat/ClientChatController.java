package Client.GUIControllers.Chat;

import Client.GUIControllers.GraphicFather;
import Client.Model.Chat.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ClientChatController extends GraphicFather implements Initializable {
    public TextField messageTextField;
    public GridPane messagesGridPane;
    public GridPane contactsGridPane;
    public Label nameLabel;
    public Label whoToChatLabel;
    public Label activityLabel;
    public Label errorLabel;
    private static Client client;

    private ArrayList<Button> contactButtons = new ArrayList<>();




//    public static Vector<TwoByTwoChat.Server.ClientHandler> allClients = new Vector<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showContacts();
        nameLabel.setText("You are: " + client.getId());
        whoToChatLabel.setText("Chat other side: " + client.chatOtherSide);
//        activityLabel.setText(ClientHandler.allClients.get(this.client));



    }

    public ClientChatController() throws IOException {
        if(client==null){
            client = new Client(this);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Running Shutdown Hook");
                try {
                    client.sendFinalMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void sendButton(ActionEvent actionEvent) throws IOException {

        errorLabel.setText("");
        if(!whoToChatLabel.getText().equalsIgnoreCase("Chat other side: NOBODY")){
            client.readMessage(messageTextField.getText());

            printMessages();

            showContacts();

            messageTextField.clear();
        } else {
            errorLabel.setText("Please select a person to chat with!");
        }
    }

    public TextField getMessageTextField() {
        return messageTextField;
    }

    private boolean a = true;

    public void printMessages() {

//        if(!a){
            Platform.runLater(()->messagesGridPane.getChildren().removeAll(messagesGridPane.getChildren()));
//            a = false;
//        }

        VBox vBox = new VBox();

//        Label comm = new Label();
//        comm.setText("Messages: ");
//        comm.setMinHeight(15);
//        vBox.getChildren().add(comm);

        for (Message message : Message.allMessages) {
            System.out.println("mess " + message.getSender() + "  " + " to " + message.getReceiver() + "jmes " + message.getMessage());
          if((message.getReceiver().equals(client.getId()) && message.getSender().equals(client.chatOtherSide)) ||
                  (message.getSender().equals(client.getId()) && message.getReceiver().equals(client.chatOtherSide))){
              Label comment = new Label();
              comment.setText(message.getMessage());
              comment.setMinHeight(15);
              comment.getStyleClass().add("messageBox");
              vBox.getChildren().add(comment);
          }
        }
        Platform.runLater(()->messagesGridPane.getChildren().add(vBox));
    }

    public void showContacts(){
//        if(!a){
                    Platform.runLater(()->contactsGridPane.getChildren().removeAll(contactsGridPane.getChildren()));

//        }
        a = false;

        VBox vBox2 = new VBox();

//        Label comm = new Label();
//        comm.setText("Contacts: ");
//        comm.setMinHeight(15);
//        vBox2.getChildren().add(comm);

        contactButtons.clear();
        for (String client : client.allClientsArr) {
            VBox v = new VBox();
            Button comment = new Button();
            contactButtons.add(comment);
            comment.setText(client);
            comment.setMinHeight(15);
            comment.getStyleClass().add("contactButtonBox");
            v.getChildren().add(comment);
            v.getStyleClass().add("conBox");
            vBox2.getChildren().add(v);
            System.out.println("hah");
        }
        Platform.runLater(()->contactsGridPane.getChildren().add(vBox2));

        for (Button contactButton : contactButtons) {

            // action event
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    client.chatOtherSide = contactButton.getText();
                    whoToChatLabel.setText("Chat other side: " + client.chatOtherSide);
                    if(!client.activityMap.containsKey(client.chatOtherSide)){
//                        activityLabel.setText(UserStatusEnum.OFFLINE.toString());
                    } else {
//                        activityLabel.setText(client.activityMap.get(client.chatOtherSide).toString());
                    }

//                    client.sendActivityStatus();
                    printMessages();
                }
            };
            contactButton.setOnAction(event);
        }
    }
}
