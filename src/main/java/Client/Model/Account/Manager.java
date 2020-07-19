package Client.Model.Account;

public class Manager extends Account {

    public Manager(String username) {
        super(username);
    }

    @Override
    public String getType() {
        return "Manager";
    }
}
