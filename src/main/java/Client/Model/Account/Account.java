package Client.Model.Account;

public abstract class Account {
    private String username;

    public Account(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    public abstract String getType();
}
