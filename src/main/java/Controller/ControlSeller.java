package Controller;

public class ControlSeller {
    private static ControlSeller instance;
    private ControlSeller()
    {

    }
    public static ControlSeller getInstance()
    {
        if(instance == null)
            instance = new ControlSeller();
        return instance;
    }
    public void createAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber)
    {

    }
}
