package View.ManagerProfileUIs;

import Controller.Control;
import Model.Account;
import View.UI;

public class ManagerManageUsersUI extends UI {

    private static ManagerManageUsersUI instance;
    private ManagerManageUsersUI()
    {

    }

    public static ManagerManageUsersUI getInstance()
    {
        if(instance == null)
            instance = new ManagerManageUsersUI();
        return instance;
    }

    public void sort()
    {
        Control.getInstance().sortHashmap(Account.getAllAccounts());
    }

    @Override
    public void run()
    {
        for (String s : Account.getAllAccounts().keySet())
        {
            System.out.println(s + "     " + Account.getAllAccounts().get(s).getType());
        }
    }

    @Override
    public void help() {
        System.out.println("to show information of a user : view [username]");
        System.out.println("to delete a user : delete user [username]");
        System.out.println("To add a new manager : create manager profile");
        System.out.println("to sort by alphabet : sort by alphabet");
    }
}
