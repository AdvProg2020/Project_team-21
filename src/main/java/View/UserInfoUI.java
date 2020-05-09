package View;

import Controller.Control;
import Model.Account;

public class UserInfoUI extends UI {
    private static UserInfoUI instance;
    private Control controller = Control.getInstance();
    private Account user = controller.getUser();
    private UserInfoUI()
    {

    }
    public static UserInfoUI getInstance() {
        if(instance == null)
            instance = new UserInfoUI();
        return instance;
    }

    @Override
    public void run() {
        if(user == null)
        {
            UserLoginUI.getInstance().run();
        }
        else
        {

        }
    }
}
