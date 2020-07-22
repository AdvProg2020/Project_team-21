package Client.Model.Account;

import javafx.scene.shape.Circle;

public abstract class Account {
    private String username;
    public Circle status;

    public Account(String username) {
        this.username = username;
    }

    public void setStatus(Circle status) {
        this.status = status;
    }

    public Circle getStatus() {
        return status;
    }

    public String getUsername() {
        return username;
    }
    public abstract String getType();
}
