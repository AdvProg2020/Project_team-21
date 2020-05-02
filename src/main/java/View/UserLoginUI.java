package View;

public class UserLoginUI extends UI {
    private static UserLoginUI instance;

    private UserLoginUI()
    {

    }
    public static UserLoginUI getInstance() {
        if(instance == null)
            instance = new UserLoginUI();
        return instance;
    }

    @Override
    public void run() {

    }
}
