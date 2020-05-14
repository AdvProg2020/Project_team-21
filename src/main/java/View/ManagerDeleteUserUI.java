package View;

import Controller.Control;
import Model.Manager;

public class ManagerDeleteUserUI extends UI {
    private static ManagerDeleteUserUI instance;
    private ManagerDeleteUserUI()
    {

    }

    public static ManagerDeleteUserUI getInstance()
    {
        if(instance == null)
            instance = new ManagerDeleteUserUI();
        return instance;
    }
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        try
        {
            Control.getInstance().deleteUser(username);
            System.out.println("User "+username+" has been deleted succesfuly!");
        }
        catch (Exception e)
        {
            ConsoleView.getInstance().errorInput(e.getMessage(),ManagerManageUsersUI.getInstance());
        }finally {
            ConsoleView.getInstance().goToNextPage(ConsoleView.getInstance().getLastMenu());
        }

    }

    @Override
    public void help() {

    }
}
