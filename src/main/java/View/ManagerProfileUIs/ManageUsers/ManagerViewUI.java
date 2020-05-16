package View.ManagerProfileUIs.ManageUsers;

import Controller.ControlManager;
import Model.Account.Account;
import View.ConsoleView;
import View.UI;

public class ManagerViewUI extends UI {
    private static ManagerViewUI instance;
    private ManagerViewUI()
    {

    }

    public static ManagerViewUI getInstance()
    {
        if(instance == null)
            instance = new ManagerViewUI();
        return instance;
    }
    private String username = "";

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        try {
            ControlManager.getInstance().viewUsername(username);
            Account userTemp = Account.getAllAccounts().get(username);
            System.out.println("First Name: " + userTemp.getFirstName());
            System.out.println("Last Name: " + userTemp.getLastName());
            System.out.println("Username: " + userTemp.getUsername());
            System.out.println("Password: " + userTemp.getPassword());
            System.out.println("Email: " + userTemp.getEmail());
            System.out.println("Phone Number: " + userTemp.getPhoneNumber());
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }catch (Exception e) {
            ConsoleView.getInstance().errorInput(e.getMessage(), ConsoleView.getInstance().getLastMenu());
        }
    }

    @Override
    public void help() {

    }
}
