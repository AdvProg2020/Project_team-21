package Controller;

import Model.Manager;

public class ControlManager {
    private static ControlManager instance;
    private ControlManager()
    {

    }
    public static ControlManager getInstance()
    {
        if(instance == null)
            instance = new ControlManager();
        return instance;
    }
    public void createAccount(String username, String password, String firstName, String lastName, String email, String phoneNumber)
    {
        new Manager(username,firstName,lastName,email,phoneNumber,password);
    }
}
