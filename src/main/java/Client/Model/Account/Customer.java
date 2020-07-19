package Client.Model.Account;

public class Customer extends Account {
    public Customer(String username) {
        super(username);
    }

    @Override
    public String getType() {
        return "Customer";
    }
}
