package Controller;

public class ControlCustomer {
    private static ControlCustomer instance;
    private ControlCustomer()
    {

    }
    public static ControlCustomer getInstance()
    {
        if(instance == null)
            instance = new ControlCustomer();
        return instance;
    }
    public void createAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber)
    {

    }
}
