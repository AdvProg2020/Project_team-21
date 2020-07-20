package Client.Model.Account;

public class Supporter extends Account{

    public Supporter(String username) {
        super(username);
    }

    @Override
    public String getType() {
        return "Supporter";
    }
}
