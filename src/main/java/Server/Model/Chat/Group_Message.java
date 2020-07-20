package Server.Model.Chat;

import java.util.ArrayList;

public class Group_Message {
    private String sender;
    private String receiver;
    private String message;
    private String groupId;
    public static ArrayList<Group_Message> allGroupMessages = new ArrayList<>();

    public Group_Message(String sender, String receiver, String message, String groupId){
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.groupId = groupId;
        allGroupMessages.add(this);
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

    public String getGroupId() {
        return groupId;
    }
}
