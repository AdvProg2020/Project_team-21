package Client.Model.Account;

public class Seller extends Account {

    public Seller(String username) {
        super(username);
    }

    @Override
    public String getType() {
        return "Seller";
    }
}
