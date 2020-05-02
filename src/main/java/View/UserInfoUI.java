package View;

public class UserInfoUI extends UI {
    private static UserInfoUI instance;

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

    }
}
