package Client.GUIControllers.Chat;

import Client.GUIControllers.GraphicFather;
import Server.Model.Chat.Group_Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Group_ClientChatController extends GraphicFather implements Initializable {
    public TextField messageTextField;
    public GridPane messagesGridPane;
    public GridPane contactsGridPane;
    public Label nameLabel;
    public Label whoToChatLabel;
    public Label activityLabel;
//    public Label errorLabel;
    private Group_Client Group_Client;

//    private ArrayList<Button> contactButtons = new ArrayList<>();
//    public static Vector<TwoByTwoChat.Server.ClientHandler> allClients = new Vector<>();

    private static String groupChatId;

    public static void setGroupChatId(String groupChatId) {
        Group_ClientChatController.groupChatId = groupChatId;
    }

    public static String getGroupChatId() {
        return groupChatId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showContacts();
        nameLabel.setText("You are: " + Group_Client.getId());
        whoToChatLabel.setText("Group Chat: " + getGroupChatId());
//        activityLabel.setText(ClientHandler.allClients.get(this.client));

    }

    public Group_ClientChatController() throws IOException {
        Group_Client = new Group_Client(this);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("Running Shutdown Hook");
                try {
                    Group_Client.sendFinalMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void sendButton(ActionEvent actionEvent) throws IOException {

//        errorLabel.setText("");
//        if(!whoToChatLabel.getText().equalsIgnoreCase("Chat other side: NOBODY")){
            Group_Client.readMessage(messageTextField.getText());

            printMessages();

            showContacts();

            messageTextField.clear();
//        } else {
//            errorLabel.setText("Please select a person to chat with!");
//        }
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
//        comm.getStyleClass().add("messageBox");
//        vBox.getChildren().add(comm);

        for (Group_Message groupMessage : Group_Message.allGroupMessages) {
            System.out.println("mess " + groupMessage.getSender() + "  " + " to " + groupMessage.getReceiver() + "jmes " + groupMessage.getMessage());
          if(groupMessage.getGroupId().equalsIgnoreCase(groupChatId)){
              Label comment = new Label();
              comment.setText(groupMessage.getMessage());
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

//        contactButtons.clear();
        for (String client : Group_Client.allClientsArr) {
            Label comment = new Label();
//            contactButtons.add(comment);
            comment.setText(client);
            comment.setMinHeight(15);
            comment.getStyleClass().add("contactBox");
            vBox2.getChildren().add(comment);
            System.out.println("hah");
        }
        Platform.runLater(()->contactsGridPane.getChildren().add(vBox2));

//        for (Button contactButton : contactButtons) {
//
//            // action event
//            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
//                public void handle(ActionEvent e)
//                {
//                    Group_Client.chatOtherSide = contactButton.getText();
//                    whoToChatLabel.setText("Chat other side: " + Group_Client.chatOtherSide);
//                    printMessages();
//                }
//            };
//            contactButton.setOnAction(event);
//        }
    }
}
