package Server.Model.Chat;

import java.util.ArrayList;

public class Message {
    private String sender;
    private String receiver;
    private String message;
    public static ArrayList<Message> allMessages = new ArrayList<>();

    public Message(String sender, String receiver, String message){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        allMessages.add(this);
    }

    public String getMessage() {
        return message;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSender() {
        return sender;
    }
}
